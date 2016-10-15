package com.nextep.designer.sqlgen.ui.preferences;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.IScopeContext;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.ui.internal.ide.IDEWorkbenchMessages;
import org.eclipse.ui.internal.ide.IDEWorkbenchPlugin;
import org.osgi.service.prefs.BackingStoreException;
import com.nextep.designer.sqlgen.preferences.PreferenceConstants;
import com.nextep.designer.sqlgen.ui.SQLMessages;

/**
 * A class to handle editing of the Line delimiter preferences in core.
 * 
 * @since 3.1
 */
public class LineDelimiterEditor {

	private Button defaultButton;

	private Button otherButton;

	private Combo choiceCombo;

	private Group group;

	private IPreferenceStore store;

	/**
	 * Creates a new encoding field editor with the given preference name, label and parent.
	 * 
	 * @param composite the parent of the field editor's control
	 */
	public LineDelimiterEditor(Composite composite, IPreferenceStore store) {
		this.store = store;
		createControl(composite);
	}

	/**
	 * Creates this field editor's main control containing all of its basic controls.
	 * 
	 * @param parent the parent control
	 */
	protected void createControl(Composite parent) {
		Font font = parent.getFont();
		group = new Group(parent, SWT.NONE);
		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		group.setLayoutData(data);
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		group.setLayout(layout);
		group.setText(SQLMessages.getString("preferences.newline")); //$NON-NLS-1$
		group.setFont(font);

		SelectionAdapter buttonListener = new SelectionAdapter() {

			public void widgetSelected(SelectionEvent e) {
				if (e.widget.equals(defaultButton)) {
					updateState(true);
				} else {
					updateState(false);
				}
			}
		};

		defaultButton = new Button(group, SWT.RADIO);
		defaultButton.setText(IDEWorkbenchMessages.IDEWorkspacePreference_defaultLineDelim);

		data = new GridData();
		data.horizontalSpan = 2;
		defaultButton.setLayoutData(data);
		defaultButton.addSelectionListener(buttonListener);
		defaultButton.setFont(font);

		otherButton = new Button(group, SWT.RADIO);
		otherButton.setText(IDEWorkbenchMessages.IDEWorkspacePreference_otherLineDelim);
		otherButton.addSelectionListener(buttonListener);
		otherButton.setFont(font);

		choiceCombo = new Combo(group, SWT.NONE | SWT.READ_ONLY);
		data = new GridData();
		choiceCombo.setFont(font);
		choiceCombo.setLayoutData(data);
		populateChoiceCombo(getChoices());
	}

	/**
	 * Load the list items from core and update the state of the buttons to match what the
	 * preference is currently set to.
	 */
	public void doLoad() {
		if (choiceCombo != null) {
			populateChoiceCombo(getChoices());
			String resourcePreference = getStoredValue();
			updateState(resourcePreference == null);
		}
	}

	/**
	 * Initializes this field editor with the preference value from the preference store.
	 */
	public void loadDefault() {
		if (choiceCombo != null) {
			updateState(true);
		}
	}

	/**
	 * Returns the value that is currently stored for the encoding.
	 * 
	 * @return the currently stored encoding
	 */
	public String getStoredValue() {
		return store.getString(PreferenceConstants.SQL_SCRIPT_NEWLINE);
	}

	/**
	 * Answer the <code>IScopeContext</code> for the receiver, this will be a project scope if the
	 * receiver is editing project preferences, otherwise instance scope.
	 * 
	 * @return the scope context
	 */
	private IScopeContext getScopeContext() {
		return new InstanceScope();
	}

	/**
	 * Returns the default setting for the object being shown.
	 * 
	 * @return the default setting for the object being shown
	 */
	protected String[] getChoices() {
		Set keys = Platform.knownPlatformLineSeparators().keySet();
		String[] keyArray = new String[keys.size()];
		keys.toArray(keyArray);
		return keyArray;
	}

	/**
	 * Populates the list of choices combo.
	 */
	private void populateChoiceCombo(String[] items) {
		choiceCombo.setItems(items);

		if (getStoredValue() == null) {
			choiceCombo.setText(""); //$NON-NLS-1$
		} else {
			selectChoice();
		}
	}

	private void updateState(boolean useDefault) {
		if (useDefault) {
			defaultButton.setSelection(true);
			otherButton.setSelection(false);
			choiceCombo.setEnabled(false);
		} else {
			defaultButton.setSelection(false);
			otherButton.setSelection(true);
			choiceCombo.setEnabled(true);
			selectChoice();
		}
	}

	/**
	 * Select the item in the combo that matches the current preferences setting. NOTE: not handling
	 * the case where two platform line separators are defined with the same value. Assumption is
	 * that they are unique and the key will be modified to represent that. E.g. a key might be Mac
	 * OS 10/Linux if the same value is required for two platforms.
	 */
	private void selectChoice() {
		String selection = null;
		Map knownValues = Platform.knownPlatformLineSeparators();
		Set keys = knownValues.keySet();
		String current = getStoredValue();
		if (current != null) {
			for (Iterator iter = keys.iterator(); iter.hasNext();) {
				String element = (String) iter.next();
				if (knownValues.get(element).equals(current)) {
					selection = element;
					break;
				}
			}
		}
		String[] items = choiceCombo.getItems();
		for (int i = 0; i < items.length; i++) {
			String item = items[i];
			if (item.equals(selection)) {
				choiceCombo.select(i);
				break;
			}
		}
	}

	/**
	 * Store the currently selected line delimiter value in the preference store.
	 */
	public void store() {
		String val;
		if (defaultButton.getSelection() || choiceCombo.getText().equals("")) { //$NON-NLS-1$
			val = null;
		} else {
			Map lineSeparators = Platform.knownPlatformLineSeparators();
			val = (String) lineSeparators.get(choiceCombo.getText());
		}

		IEclipsePreferences node = getScopeContext().getNode(Platform.PI_RUNTIME);
		if (val == null) {
			node.remove(Platform.PREF_LINE_SEPARATOR);
			store.setValue(PreferenceConstants.SQL_SCRIPT_NEWLINE, null);
		} else {
			node.put(Platform.PREF_LINE_SEPARATOR, val);
			store.setValue(PreferenceConstants.SQL_SCRIPT_NEWLINE, val);
		}
		try {
			node.flush();
		} catch (BackingStoreException e) {
			IDEWorkbenchPlugin.log(e.getMessage(), e);
		}

	}

	/**
	 * Set whether or not the controls in the field editor are enabled.
	 * 
	 * @param enabled The enabled state.
	 */
	public void setEnabled(boolean enabled) {
		group.setEnabled(enabled);
		Control[] children = group.getChildren();
		for (int i = 0; i < children.length; i++) {
			children[i].setEnabled(enabled);

		}
	}

}
