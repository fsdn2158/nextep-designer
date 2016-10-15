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
package com.nextep.datadesigner.beng.gui;

import com.nextep.datadesigner.vcs.gui.external.TypePersister;
import com.nextep.designer.beng.model.IDeliveryItem;

/**
 * @author Christophe Fondacci
 *
 */
public class DeliveryPersister extends TypePersister {

	/**
	 * @see com.nextep.datadesigner.vcs.gui.external.TypePersister#getPersistableClass()
	 */
	@Override
	protected Class<?> getPersistableClass() {
		return IDeliveryItem.class;
	}

	/**
	 * @see org.eclipse.ui.IPersistableElement#getFactoryId()
	 */
	@Override
	public String getFactoryId() {
		return "com.neXtep.designer.beng.ui.deliveries"; //$NON-NLS-1$
	}

}
