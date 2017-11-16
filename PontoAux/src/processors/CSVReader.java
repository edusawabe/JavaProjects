package processors;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import model.ListMarcacaoCSV;
import model.MarcacaoCSV;
import util.HorasUtil;

public class CSVReader {
	private ListMarcacaoCSV lMarcacaoCSV;
	private File file;

	public void readFile(boolean marcacaoManualFile){
		if (file == null) {
			file = new File("./");
			File[] f = file.listFiles();
			long lastModified = 0;

			for (int i = 0; i < f.length; i++) {
				if(f[i].getName().endsWith(".csv")){
					if(f[i].lastModified() > lastModified){
						file = f[i];
						lastModified = f[i].lastModified();
					}
				}
			}
		}
		String line;
		boolean semSaida = false;
		lMarcacaoCSV = new ListMarcacaoCSV();
		String[] s;
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
					if(line.contains("Dia abonado"))
					{
						line = bufferedReader.readLine();
						semSaida = false;
						break;
					}
					line = bufferedReader.readLine();
				}
			}
			while (line != null && !line.contains("Total de Horas;")) {
				String[] lItem = line.split(";");
				MarcacaoCSV marcacaoCSV = new MarcacaoCSV();
				marcacaoCSV.setData(lItem[1]);
				marcacaoCSV.setDiaSemana("Teste");
				if(lItem.length > 7){
					if (!lItem[8].isEmpty()){
						s = lItem[8].split(" ");
						marcacaoCSV.setEntrada(s[1].replaceAll(" ", ""));
					}
					if(lItem.length > 9)
						semSaida = false;
					else
						semSaida = true;
					if(semSaida)
						marcacaoCSV.setQtdeHoras("00:00");
					else{
						try {
							if (!lItem[9].isEmpty()){
								s = lItem[9].split(" ");
								marcacaoCSV.setSaida(s[1].replaceAll(" ", ""));
								marcacaoCSV.setQtdeHoras(HorasUtil.subTractHours(marcacaoCSV.getSaida(), marcacaoCSV.getEntrada()));
							}
						} catch (Exception e) {
							System.err.println("");
						}
					}
				} else{
					marcacaoCSV.setEntrada("00:00");
					marcacaoCSV.setSaida("00:00");
					marcacaoCSV.setQtdeHoras("00:00");
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
