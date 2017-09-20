package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

import view.ConfigItem;

public class ConfigReader {
	private LinkedList<ConfigItem> lconfig;

	public ConfigReader() {
		lconfig = new LinkedList<ConfigItem>();
	}

	public void readFile() {
		File f = new File("./config.txt");
		String line = null;
		int token = 0;
		ConfigItem cItem = null;
		try {
			BufferedReader reader = new BufferedReader(new FileReader(f));
			line = reader.readLine();
			while(line != null){
				if(!line.contains("#")){
					switch (token) {
					case 0:
						cItem = new ConfigItem();
						cItem.setCentroCusto(line);
						token++;
						break;
					case 1:
						cItem.setPt(line);
						token++;
						break;
					case 2:
						cItem.setDescricaoPT(line);
						token++;
						break;
					case 3:
						cItem.setGrupo(line);
						token++;
						break;
					case 4:
						cItem.setPlanningFolder(line);
						lconfig.add(cItem);
						token = 0;
						break;
					}
				}
				line = reader.readLine();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public LinkedList<ConfigItem> getLconfig() {
		return lconfig;
	}
}
