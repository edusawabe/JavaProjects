package processors;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import model.MarcacaoCSV;

public class MarcacaoManualFileManager {
	private CSVReader csvReader;

	public void readFile(){
		csvReader = new CSVReader();
		csvReader.setFile(new File("./marcacaoManual.txt"));
		csvReader.readFile(true);
	}

	public MarcacaoCSV getMarcacaoManual(String date) {
		String tmpDate;

		for (int i = 0; i < csvReader.getlMarcacaoCSV().getLMarcacaoCSV().size(); i++) {
			tmpDate = csvReader.getlMarcacaoCSV().getLMarcacaoCSV().get(i).getData();
			if(tmpDate.equals(date))
				return csvReader.getlMarcacaoCSV().getLMarcacaoCSV().get(i);
		}
		return null;
	}

	public void writeLine(MarcacaoCSV marcacao) {
		try {
			PrintWriter writer = new PrintWriter(new FileWriter(csvReader.getFile()));
			for (int i = 0; i < csvReader.getlMarcacaoCSV().getLMarcacaoCSV().size(); i++) {
				writer.println(csvReader.getlMarcacaoCSV().getLMarcacaoCSV().get(i).toCSVString());
			}
			writer.println(marcacao.toCSVString());
			writer.flush();
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
