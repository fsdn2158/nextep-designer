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
package com.nextep.installer.handlers;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import com.nextep.installer.NextepInstaller;
import com.nextep.installer.exception.DeployException;
import com.nextep.installer.exception.InstallerException;
import com.nextep.installer.impl.req.DeliveryRequirement;
import com.nextep.installer.model.IArtefact;
import com.nextep.installer.model.IDeployHandler;
import com.nextep.installer.model.IInstallConfiguration;
import com.nextep.installer.model.IInstallConfigurator;
import com.nextep.installer.model.IRequirement;
import com.nextep.installer.services.IInstallerService;
import com.nextep.installer.services.ILoggingService;

/**
 * @author Christophe Fondacci
 */
public class DeliveryDeployHandler implements IDeployHandler {

	/**
	 * @see com.neXtep.installer.model.IDeployHandler#deploy(com.neXtep.installer.model.IArtefact,
	 *      java.lang.String, java.lang.String, java.lang.String)
	 */
	public void deploy(IInstallConfiguration conf, IArtefact artefact) throws DeployException {
		String descriptorPath = artefact.getRelativePath() + File.separator
				+ artefact.getFilename();
		File descriptor = new File(descriptorPath + File.separator + "delivery.xml");
		// Checking that our descriptor exists
		if (!descriptor.exists()) {
			throw new DeployException("No descriptor found for delivery artefact '"
					+ artefact.getFilename() + "'");
		}
		// Deplying nested delivery
		final ILoggingService loggingService = NextepInstaller.getService(ILoggingService.class);
		final IInstallerService installerService = NextepInstaller
				.getService(IInstallerService.class);
		try {
			loggingService.pad();
			// We copy the configurator so we make sure required deliveries will not interfere
			// with us
			IInstallConfigurator subConfigurator = installerService.copyConfigurator(conf);
			// We only need to check the delivery of this "required" package as all other
			// checks have been done (we deploy to the same target, with same admin, etc.)
			List<IRequirement> subRequirements = new ArrayList<IRequirement>();
			subRequirements.add(new DeliveryRequirement());
			// Recursively fires installation of the sub module
			installerService.install(subConfigurator, artefact.getRelativePath() + File.separator
					+ artefact.getFilename(), subRequirements); //$NON-NLS-1$
		} catch (InstallerException e) {
			throw new DeployException("Unable to deploy nested delivery dependency: "
					+ e.getMessage(), e);
		} finally {
			loggingService.unpad();
		}
	}

}
