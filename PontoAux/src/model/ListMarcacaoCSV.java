package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

public class ListMarcacaoCSV {
	private LinkedList<MarcacaoCSV> lMarcacaoCSV;

	public ListMarcacaoCSV(){
		lMarcacaoCSV = new LinkedList<MarcacaoCSV>();
	}

	public LinkedList<MarcacaoCSV> getLMarcacaoCSV(){
		return lMarcacaoCSV;
	}
}
