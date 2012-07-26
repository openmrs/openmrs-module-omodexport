package org.openmrs.module.omodexport.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.module.Module;
import org.openmrs.module.ModuleFactory;
import org.springframework.util.FileCopyUtils;

public class OmodExportUtil {
	/** Logger for this class */
	protected final Log log = LogFactory.getLog(OmodExportUtil.class);

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

	public static void doExport(String moduleId, String exportType,
			HttpServletResponse response) {
		ExportType et = ExportType.valueOf(exportType);

		if (et == ExportType.SINGLE) {
			Module module = ModuleFactory.getModuleById(moduleId);
			exportSingleModule(module, response);
		}

	}

	public static void exportSingleModule(Module module,
			HttpServletResponse response) {
		File file = module.getFile();
		response.setContentLength(new Long(file.length()).intValue());
		response.setContentType("application/zip");
		response.setHeader("Content-Disposition", "attachment; filename=\""+ file.getName() + "\"");
		try {
			FileCopyUtils.copy(new FileInputStream(file),
					response.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
