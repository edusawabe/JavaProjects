package util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ConfigManager {
	private File configFile;

	public File getConfigFile() {
		return configFile;
	}

	public void setConfigFile(File configFile) {
		this.configFile = configFile;
	}

	public BufferedReader openConfigFile(){
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(configFile));
		} catch (FileNotFoundException e) {
			try {
				configFile.createNewFile();
				reader = new BufferedReader(new FileReader(configFile));
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		return reader;
	}

	public String getLastDir(BufferedReader reader) {
		String lastDir = null;

		String line;
		try {
			line = reader.readLine();

			while(line != null){
				if (line.contains("#LAST_DIR")){
					String[] split = line.split("=");
					lastDir = split[1];
					break;
				}
				line = reader.readLine();
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return lastDir;
	}

	public File getLastDirFile() {
		return new File(getLastDir(openConfigFile()));
	}

	public void saveLastDir(boolean create, String lastDir) {
		BufferedWriter writer;

		try {
			if (create){
				writer = new BufferedWriter(new FileWriter(configFile));
				writer.append("#LAST_DIR =" + lastDir);
			}
			else{
				BufferedReader reader = openConfigFile();
				String lines = new String(), currLine;
				currLine = reader.readLine();

				while (currLine != null) {
					if (currLine.contains("#LAST_DIR")) {
						currLine = "#LAST_DIR =" + lastDir;
					}
					lines  = lines + currLine;
					currLine = reader.readLine();
				}
				reader.close();
				writer = new BufferedWriter(new FileWriter(configFile));
				writer.write(lines);
			}
			writer.flush();
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
