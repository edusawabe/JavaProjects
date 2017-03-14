package util;

import java.io.File;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import infoExtractor.MainGUIController;

public final class FileUtil {
	final static Logger logger = Logger.getLogger(FileUtil.class);

	public static void listf(String directoryName, ArrayList<File> files, String[] type) {
	    File directory = new File(directoryName);

	    // get all the files from a directory
	    File[] fList = directory.listFiles();
	    for (File file : fList) {
	        if (file.isFile()) {
	        	if(type != null) {
	        		for (int i = 0; i < type.length; i++) {
	        			if(type[i].contains("\\")){
			        		if(file.getAbsolutePath().contains(type[i]) && file.getName().contains(Constants.JAVA_FILE)){
			        			logger.info("Arquivo "+ type[i] +" Identificado: " + file.getAbsolutePath());
			        			files.add(file);
			        		}
		        		}else{
			        		if(file.getName().contains(type[i])){
			        			logger.info("Arquivo "+ type[i] +" Identificado: " + file.getAbsolutePath());
			        			files.add(file);
			        		}
		        		}
					}
	        	}
	        	else
	        		files.add(file);
	        } else if (file.isDirectory()) {
	            listf(file.getAbsolutePath(), files, type);
	        }
	    }
	}

	public static void listf(String directoryName, ArrayList<File> files, String type) {
	    File directory = new File(directoryName);

	    // get all the files from a directory
	    File[] fList = directory.listFiles();
	    for (File file : fList) {
	        if (file.isFile()) {
	        	if(type != null) {
        			if(type.contains("\\")){
		        		if(file.getAbsolutePath().contains(type) && file.getName().contains(Constants.JAVA_FILE)){
		        			logger.info("Arquivo "+ type +" Identificado: " + file.getAbsolutePath());
		        			files.add(file);
		        		}
	        		}else{
		        		if(file.getName().contains(type)){
		        			logger.info("Arquivo "+ type +" Identificado: " + file.getAbsolutePath());
		        			files.add(file);
		        		}
	        		}
	        	}
	        	else
	        		files.add(file);
	        } else if (file.isDirectory()) {
	            listf(file.getAbsolutePath(), files, type);
	        }
	    }
	}
}
