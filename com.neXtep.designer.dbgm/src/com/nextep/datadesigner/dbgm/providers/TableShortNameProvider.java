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
package com.nextep.datadesigner.dbgm.providers;

import com.nextep.datadesigner.dbgm.DBGMMessages;
import com.nextep.datadesigner.dbgm.model.IBasicTable;
import com.nextep.datadesigner.model.ITypedObject;
import com.nextep.designer.vcs.model.INamingVariableProvider;

public class TableShortNameProvider implements INamingVariableProvider {

	public static final String VAR_NAME = "TABLE_SHORT";
	
    @Override
	public String getDescription() {
		return DBGMMessages.getString("namingVariableProvider.tableShort.description");
	}

	@Override
	public String getVariableName() {
		return VAR_NAME;
	}

	@Override
	public String getVariableValue(ITypedObject o) {
		if (isActiveFor(o)) {
	        return ((IBasicTable)o).getShortName();
		}
		return "";
	}

	@Override
	public boolean isActiveFor(ITypedObject o) {
		return o instanceof IBasicTable;
	}

}