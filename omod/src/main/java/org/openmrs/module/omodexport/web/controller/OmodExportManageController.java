/**
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */
package org.openmrs.module.omodexport.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.module.Module;
import org.openmrs.module.ModuleFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * The main controller.
 */
@Controller
public class OmodExportManageController {

	protected final Log log = LogFactory.getLog(getClass());

	@RequestMapping(value = "/module/omodexport/exportModules")
	public void showModuleExportForm(ModelMap model) {

		HashMap<Module, List<Module>> moduleAndDependencies = new HashMap<Module, List<Module>>();

		for (Module module : ModuleFactory.getStartedModules()) {

			List<Module> dependecies = new ArrayList<Module>();

			for (String name : module.getRequiredModules()) {

				Module m = ModuleFactory.getModuleByPackage(name);
				dependecies.add(m);
			}
			moduleAndDependencies.put(module, dependecies);

		}
		model.addAttribute("modules", moduleAndDependencies);
	}

}
