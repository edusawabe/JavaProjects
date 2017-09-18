package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

import view.CSVLine;

public class CSVReader {
	private LinkedList<CSVLine> lLines = new LinkedList<CSVLine>();
	private String fName;
	private String pt = "175435";
	private String group = "FIAV";
	private String centroCusto = "FIAV";
	private String status = "Backlog";
	private String category = "Tarefa";
	private String priority = "4 - Low";
	private String plannedFor = "FIAV > PT 175435 - Impeditivos Fase 1";

	public void readFile(){
		File f = new File(fName);
		BufferedReader reader;
		String line;
		String tmp;
		int fieldNum = 0;
		CSVLine csvLine;
		try {
			reader = new BufferedReader(new FileReader(f));
			line = reader.readLine();
			line = reader.readLine();
			while (line != null) {
				csvLine = new CSVLine();
				csvLine.setNomeTarefa(StringUtil.eliminateInitialSpaces(parseLine(0, line)));
				csvLine.setDuracao(parseLine(1, line));
				csvLine.setTrabalho(StringUtil.eliminateHourLabel(parseLine(2, line)));
				csvLine.setInicio(StringUtil.eliminateWeekDayNames(parseLine(3, line)));
				csvLine.setTermino(StringUtil.eliminateWeekDayNames(parseLine(4, line)));
				csvLine.setPredecessora(parseLine(5, line));
				csvLine.setLrecurso(parseRecursosLine(parseLineAndExtractList(6, line)));
				lLines.add(csvLine);
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

	public String parseLine(int fieldNum, String toParse) {
		String ret = new String();
		int tokenfound = 0;

		for (int i = 0; i < toParse.length(); i++) {
			if(tokenfound == fieldNum){
				if(toParse.charAt(i) == ';')
					break;
				else
					ret = ret + toParse.charAt(i);
			}else{
				if(toParse.charAt(i) == ';')
					tokenfound++;
			}
		}
		return ret;
	}

	public String parseLineAndExtractList(int fieldNum, String toParse) {
		String ret = new String();
		int tokenfound = 0;
		int count = 0;

		for (int i = 0; i < toParse.length(); i++) {
			if(tokenfound == fieldNum){
				if(toParse.charAt(i) == '\"'){
					if(count == 1)
						break;
					else
						count++;
				}
				else
					ret = ret + toParse.charAt(i);
			}else{
				if(toParse.charAt(i) == ';')
					tokenfound++;
			}
		}
		if(ret.isEmpty())
			return ret;
		else{
			if(ret.contains(";"))
				return ret = '\"' + ret + '\"';
			else
				return ret;
		}
	}

	public LinkedList<String> parseRecursosLine(String toParse) {

		LinkedList<String> lRecursos = new LinkedList<String>();

		if(toParse.isEmpty())
			return lRecursos;

		if(toParse.charAt(0) != '\"'){
			lRecursos.add(toParse);
			return lRecursos;
		}

		String ret = new String();
		int tokenfound = 0;

		for (int i = 1; i < toParse.length(); i++) {
			if(toParse.charAt(i) == ';'){
				lRecursos.add(ret);
				ret = new String();
			}else{
				if(toParse.charAt(i) == '\"'){
					break;
				}
				else{
					ret = ret + toParse.charAt(i);
				}
			}
		}
		if(!ret.isEmpty())
			lRecursos.add(ret);
		return lRecursos;
	}

	public String getfName() {
		return fName;
	}

	public void setfName(String fName) {
		this.fName = fName;
	}

	public LinkedList<CSVLine> getlLines() {
		return lLines;
	}

	public String generateCSVLines() {
		String ret = new String();
		ret = ret + "Title" + ",";
		ret = ret + "Description" + ",";
		ret = ret + "PT" + ",";
		ret = ret + "IN/TP" + ",";
		ret = ret + "Group" + ",";
		ret = ret + "Centro de Custo" + ",";
		ret = ret + "Status" + ",";
		ret = ret + "Category" + ",";
		ret = ret + "Priority" + ",";
		ret = ret + "Assigned To" + ",";
		ret = ret + "Planned For" + ",";
		ret = ret + "Estimated Effort" + ",";
		ret = ret + "Remaining Effort" + ",";
		ret = ret + "Data Inicial" + ",";
		ret = ret + "Data Final" + ",";
		ret = ret + "Dependency Parent" + "\n";

		for (int i = 0; i < lLines.size(); i++) {
			if(lLines.get(i).getLrecurso().size() > 0){
				for (int j = 0; j < lLines.get(i).getLrecurso().size(); j++) {
					ret = ret + "PT" + pt + " - " + lLines.get(i).getNomeTarefa() + ",";
					ret = ret + "PT" + pt + " - " + lLines.get(i).getNomeTarefa() + ",";
					ret = ret + "00" + pt + ",";
					ret = ret + "" + ",";
					ret = ret + group + ",";
					ret = ret + centroCusto + ",";
					ret = ret + status + ",";
					ret = ret + category + ",";
					ret = ret + priority + ",";
					ret = ret + "" + ",";
					ret = ret + plannedFor + ",";
					ret = ret + lLines.get(i).getTrabalho() + ",";
					ret = ret + lLines.get(i).getTrabalho() + ",";
					ret = ret + lLines.get(i).getInicio() + ",";
					ret = ret + lLines.get(i).getTermino() + ",";
					ret = ret + "" + "\n";
				}
			}
		}
		return ret;
	}
}
