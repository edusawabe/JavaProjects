package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public final class InfoExtractor {

	public static String getFluxoFrwkPDC(String file) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(new File(file)));
		String line = reader.readLine();
		int begin = 0, end = 0;

		while (line != null) {
			if(line.contains("<FWO-transaction>"))
			{
				begin = line.indexOf(">");
				end = line.indexOf("</");
				reader.close();
				return line.substring(begin+1, end);
			}
			line = reader.readLine();
		}
		reader.close();
		return null;
	}

	public static String getPDCProcessCall(String file, String process) throws IOException {
		File f = new File(file);
		BufferedReader reader = new BufferedReader(new FileReader(f));
		String line = reader.readLine();
		while (line != null) {
			line = line.replaceAll(" ", "");
			if(line.contains("."+process+"(")){
				reader.close();
				return f.getName().replace(Constants.BEAN_FILE, "");
			}
			line = reader.readLine();
		}
		reader.close();
		return null;
	}
}
