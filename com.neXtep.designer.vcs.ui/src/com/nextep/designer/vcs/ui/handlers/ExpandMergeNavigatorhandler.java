/*******************************************************************************
 * Copyright (c) 2011 neXtep Software and contributors.
 * All rights reserved.
 *
 * This file is part of neXtep designer.
 *
 * NeXtep designer is free software: you can redistribute it 
 * and/or modify it under the terms of the GNU General Public 
 * License as published by the Free Software Foundation, either 
 * version 3 of the License, or any later version.
 *
 * NeXtep designer is distributed in the hope that it will be 
 * useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  
 * See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Foobar.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Contributors:
 *     neXtep Softwares - initial API and implementation
 *******************************************************************************/
package com.nextep.designer.vcs.ui.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;
import com.nextep.datadesigner.vcs.gui.dialog.MergeNavigator;
import com.nextep.designer.vcs.model.IComparisonItem;

public class ExpandMergeNavigatorhandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		final ISelection sel = HandlerUtil.getCurrentSelection(event);
		if(!sel.isEmpty()) {
			MergeNavigator nav = (MergeNavigator)((IStructuredSelection)sel).getFirstElement();
			IComparisonItem compItem = (IComparisonItem)nav.getModel();
			
		}
		
		// TODO Auto-generated method stub
		return null;
	}

}
