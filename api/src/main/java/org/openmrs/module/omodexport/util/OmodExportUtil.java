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
package org.openmrs.module.omodexport.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.module.Module;
import org.openmrs.module.ModuleFactory;
import org.springframework.util.FileCopyUtils;

/**
 *
 */
public class OmodExportUtil {
	
	/** Logger for this class */
	protected final static Log log = LogFactory.getLog(OmodExportUtil.class);
	
	// Fields
	public enum ExportType {
		/**
		 * Indicates single module Export type
		 */
		SINGLE,
		/**
		 * Indicates custom selection module Export type
		 */
		CUSTOM,
		/**
		 * Indicates module and dependencies Export type
		 */
		WITH_DEPENDECIES,
		/**
		 * Indicates all modules Export type
		 */
		ALL;
		
	}
	
	public static void doExport(String moduleId, String exportType, HttpServletResponse response) throws IOException {
		
		switch (ExportType.valueOf(exportType)) {
			case SINGLE:
				exportSingleModule(ModuleFactory.getModuleById(moduleId), response);
				break;
			
			case ALL:
				exportAllModules(response);
				break;
			
			case WITH_DEPENDECIES:
				exportModuleWithDependecies(ModuleFactory.getModuleById(moduleId), response);
				break;
			
			case CUSTOM:
				exportSelectedModules(moduleId, response);
				break;
			
			default:
				log.info("Unable to find an export type");
				break;
		}
		
	}
	
	public static void exportSingleModule(Module module, HttpServletResponse response) {
		File file = module.getFile();
		response.setContentLength(new Long(file.length()).intValue());
		response.setContentType("application/zip");
		response.setHeader("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"");
		try {
			FileCopyUtils.copy(new FileInputStream(file), response.getOutputStream());
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void exportAllModules(HttpServletResponse response) throws IOException {
		
		Collection<Module> modules = ModuleFactory.getStartedModules();
		List<File> files = new ArrayList<File>();
		for (Module module : modules) {
			files.add(module.getFile());
		}
		response.setContentType("application/zip");
		response.setHeader("Content-Disposition", "attachment; filename=\"modules.ZIP\"");
		
		ZipOutputStream out = new ZipOutputStream(response.getOutputStream());
		
		zipFiles(files, out);
	}
	
	public static void exportModuleWithDependecies(Module module, HttpServletResponse response) throws IOException {
		List<File> files = new ArrayList<File>();
		List<String> dependecies = module.getRequiredModules();
		for (String name : dependecies) {
			Module m = ModuleFactory.getModuleByPackage(name);
			files.add(m.getFile());
		}
		files.add(module.getFile());
		
		response.setContentType("application/zip");
		response.setHeader("Content-Disposition", "attachment; filename=\"modules.ZIP\"");
		
		ZipOutputStream out = new ZipOutputStream(response.getOutputStream());
		
		zipFiles(files, out);
	}
	
	public static void exportSelectedModules(String moduleId, HttpServletResponse response) throws IOException {
		// Split the id string into an array of id's
		String[] moduleIds = moduleId.split("-");
		List<File> files = new ArrayList<File>();
		for (String mId : moduleIds) {
			Module m = ModuleFactory.getModuleById(mId);
			files.add(m.getFile());
		}
		response.setContentType("application/zip");
		response.setHeader("Content-Disposition", "attachment; filename=\"modules.ZIP\"");
		
		ZipOutputStream out = new ZipOutputStream(response.getOutputStream());
		
		zipFiles(files, out);
	}
	
	public static void zipFiles(List<File> files, ZipOutputStream out) throws FileNotFoundException, IOException {
		byte[] buffer = new byte[4096]; // Create a buffer for copying
		int bytesRead;
		
		for (File f : files) {
			if (f.isDirectory())
				continue;// Ignore directory
			FileInputStream in = new FileInputStream(f); // Stream to read file
			out.putNextEntry(new ZipEntry(f.getName())); // Store entry
			while ((bytesRead = in.read(buffer)) != -1)
				out.write(buffer, 0, bytesRead);
			in.close();
		}
		
		out.close();
	}
	
}
