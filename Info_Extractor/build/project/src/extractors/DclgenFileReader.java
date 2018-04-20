package extractors;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class DclgenFileReader {
	private File dclgenFile;
	private String dclgenName;
	private String dclgenTableName;

	public DclgenFileReader(File dclgenFileName) {
		this.setDclgenFileName(dclgenFileName);
		dclgenName = null;
		dclgenTableName = null;
	}

	public boolean getFileInfos(){
		String line = null;
		String[] splitAux = null;
		boolean canRead = true;
		boolean nameOK = false;
		boolean tableOK = false;

		try {
			BufferedReader reader = new BufferedReader(new FileReader(dclgenFile));
			line = reader.readLine();
			while(line != null && canRead){
				if(line.contains("TABLE"))
					splitAux = line.split("TABLE\\(");
				else
					splitAux = null;

				if(splitAux != null){
					if(splitAux.length > 1){
						dclgenTableName = splitAux[1].replaceAll("\\)", "");
						dclgenTableName = dclgenTableName.replaceAll(" ","");
						dclgenTableName = dclgenTableName.replaceAll("\\*", "");
						tableOK = true;
					}
				}
				if(line.contains("AD.DB2.DCLGEN"))
					splitAux = line.split("AD.DB2.DCLGEN\\(");
				else
					splitAux = null;

				if(splitAux != null){
					if(splitAux.length > 1){
						dclgenName = splitAux[1].replaceAll("\\)", "");
						dclgenName = dclgenName.replaceAll(" ","");
						dclgenName = dclgenName.replaceAll("\\*", "");
						nameOK = true;
					}
				}
				line = reader.readLine();
			}
			reader.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		if(nameOK && tableOK)
			return true;
		else
			return false;
	}

	public String getDclgenName() {
		return dclgenName;
	}

	public String getDclgenTableName() {
		return dclgenTableName;
	}

	public File getDclgenFile() {
		return dclgenFile;
	}

	public void setDclgenFileName(File dclgenFile) {
		this.dclgenFile = dclgenFile;
	}

}
