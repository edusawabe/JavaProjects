package processors;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import model.ListFeriados;

public class FeriadosReader {
	private ListFeriados lFeriados;

	public void readFile(){
		String line;
		lFeriados = new ListFeriados();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(new File("./Feriados.txt")));
			line = reader.readLine();
			while (line != null) {
				lFeriados.getLFeriados().add(line);
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

	public ListFeriados getlFeriados() {
		return lFeriados;
	}
}
