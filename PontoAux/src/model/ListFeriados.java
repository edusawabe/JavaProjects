package model;

import java.util.LinkedList;

public class ListFeriados {
	private LinkedList<String> lFeriados;

	public ListFeriados(){
		lFeriados = new LinkedList<String>();
	}

	public LinkedList<String> getLFeriados() {
		return lFeriados;
	}

	public boolean isFeriado(String date) {
		String[] dateSplit = date.split("/");
		String[] listDateSplit = null;

		for (int i = 0; i < lFeriados.size(); i++) {
			listDateSplit = lFeriados.get(i).split("/");
			if(dateSplit[2].equals(listDateSplit[2]))
				if(date.equals(lFeriados.get(i)))
					return true;
			if(dateSplit[2].compareTo(listDateSplit[2]) < 0)
				return false;
		}
		return false;
	}
}
