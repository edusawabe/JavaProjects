package model;

import java.util.LinkedList;

public class Campo{
	private int tam;
	private int pos;
	private String nome;
	private String nivel;
	private boolean occurs;
	private int times;
	private String dependingOn;
	private String valor;
	private LinkedList<LinkedList<Campo>> listOccurs;
	private String type;
	private boolean dependingOnField;

	public Campo(){
		tam = 0;
		pos = 0;
		occurs = false;
		dependingOnField = false;
	}

	public LinkedList<LinkedList<Campo>> newListOccurs(){
		listOccurs = new LinkedList<LinkedList<Campo>>();
		LinkedList<Campo> l = new LinkedList<Campo>();
		listOccurs.add(l);
		return listOccurs;
	}

	public int getTam() {
		return tam;
	}

	public void setTam(int tam) {
		this.tam = tam;
	}

	public int getPos() {
		return pos;
	}

	public void setPos(int pos) {
		this.pos = pos;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getNivel() {
		return nivel;
	}

	public void setNivel(String nivel) {
		this.nivel = nivel;
	}

	public boolean isOccurs() {
		return occurs;
	}

	public void setOccurs(boolean occurs) {
		this.occurs = occurs;
	}

	public int getTimes() {
		return times;
	}

	public void setTimes(int times) {
		this.times = times;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public String getDependingOn() {
		return dependingOn;
	}

	public void setDependingOn(String dependingOn) {
		this.dependingOn = dependingOn;
	}

	public LinkedList<LinkedList<Campo>> getListOccurs() {
		return listOccurs;
	}

	public void setListOccurs(LinkedList<LinkedList<Campo>> listOccurs) {
		this.listOccurs = listOccurs;
	}

	public Campo clone(){
		Campo retClone = new Campo();
		retClone.setDependingOn(dependingOn);
		retClone.setListOccurs(listOccurs);
		retClone.setNivel(nivel);
		retClone.setNome(nome);
		retClone.setOccurs(occurs);
		retClone.setPos(pos);
		retClone.setTam(tam);
		retClone.setTimes(times);
		retClone.setValor(valor);
		retClone.setType(type);
		return retClone;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMask(){
		String mask = new String();
			for (int i = 0; i < tam; i++) {
				if(type.contains("X")){
					mask = mask + "*";
				}
				else{
					mask = mask + "N";
				}
			}
		return mask;

	}

	public boolean isDependingOnField() {
		return dependingOnField;
	}

	public void setDependingOnField(boolean dependingOnField) {
		this.dependingOnField = dependingOnField;
	}
}
