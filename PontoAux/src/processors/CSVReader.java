package processors;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import model.ListMarcacaoCSV;
import model.MarcacaoCSV;

public class CSVReader {
	private ListMarcacaoCSV lMarcacaoCSV;
	private File file;

	public void readFile(boolean marcacaoManualFile){
		if (file == null) {
			return;
		}
		String line;
		boolean semSaida = false;
		lMarcacaoCSV = new ListMarcacaoCSV();
		try {
			BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
			line = bufferedReader.readLine();
			//pula linhas de cabecalho do arquivo gerado pela aplicacao de ponto
			if(!marcacaoManualFile){
				while (line != null){
					if(line.contains("Data;Dia;Marcação1;Marcação2;Total de horas;")){
						line = bufferedReader.readLine();
						semSaida = false;
						break;
					}
					if(line.contains("Data;Dia;Marcação1;Total de horas")){
						line = bufferedReader.readLine();
						semSaida = true;
						break;
					}
					line = bufferedReader.readLine();
				}
			}
			while (line != null && !line.contains("Total de Horas;")) {
				String[] lItem = line.split(";");
				MarcacaoCSV marcacaoCSV = new MarcacaoCSV();
				marcacaoCSV.setData(lItem[0]);
				marcacaoCSV.setDiaSemana(lItem[1]);
				marcacaoCSV.setEntrada(lItem[2]);
				if(semSaida)
					marcacaoCSV.setQtdeHoras("00:00");
				else{
					marcacaoCSV.setSaida(lItem[3]);
					marcacaoCSV.setQtdeHoras(lItem[4]);
				}
				lMarcacaoCSV.getLMarcacaoCSV().add(marcacaoCSV);
				line = bufferedReader.readLine();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getQtdeHorasDia(String date){
		for (int i = 0; i < lMarcacaoCSV.getLMarcacaoCSV().size(); i++) {
			if(lMarcacaoCSV.getLMarcacaoCSV().get(i).getData().equals(date))
				return lMarcacaoCSV.getLMarcacaoCSV().get(i).getQtdeHoras();
		}
		return "00:00";
	}

	public MarcacaoCSV getMarcacaoCSV(String date){
		for (int i = 0; i < lMarcacaoCSV.getLMarcacaoCSV().size(); i++) {
			if(lMarcacaoCSV.getLMarcacaoCSV().get(i).getData().equals(date))
				return lMarcacaoCSV.getLMarcacaoCSV().get(i);
		}
		return null;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public ListMarcacaoCSV getlMarcacaoCSV() {
		return lMarcacaoCSV;
	}

	public void setlMarcacaoCSV(ListMarcacaoCSV lMarcacaoCSV) {
		this.lMarcacaoCSV = lMarcacaoCSV;
	}
}
