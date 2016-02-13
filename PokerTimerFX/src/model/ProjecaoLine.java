package model;

import javafx.beans.property.SimpleStringProperty;

public class ProjecaoLine {
	private SimpleStringProperty jogador;
	private SimpleStringProperty atual;
	private SimpleStringProperty nestaRodada;
	private SimpleStringProperty projecao1;
	private SimpleStringProperty projecao2;
	private SimpleStringProperty projecao3;
	private SimpleStringProperty projecao4;
	private SimpleStringProperty projecao5;

	public ProjecaoLine() {
		jogador = new SimpleStringProperty();
		atual = new SimpleStringProperty();
		nestaRodada = new SimpleStringProperty();
		projecao1 = new SimpleStringProperty();
		projecao2 = new SimpleStringProperty();
		projecao3 = new SimpleStringProperty();
		projecao4 = new SimpleStringProperty();
		projecao5 = new SimpleStringProperty();
	}

	public String getJogador() {
		return jogador.get();
	}
	public void setJogador(String jogador) {
		this.jogador.set(jogador);
	}
	public String getatual() {
		return atual.get();
	}
	public void setatual(String atual) {
		this.atual.set(atual);
	}
	public String getnestaRodada() {
		return nestaRodada.get();
	}
	public void setnestaRodada(String nestaRodada) {
		this.nestaRodada.set(nestaRodada);
	}
	public String getprojecao1() {
		return projecao1.get();
	}
	public void setprojecao1(String projecao1) {
		this.projecao1.set(projecao1);
	}
	public String getprojecao2() {
		return projecao2.get();
	}
	public void setprojecao2(String projecao2) {
		this.projecao2.set(projecao2);
	}
	public String getprojecao3() {
		return projecao3.get();
	}
	public void setprojecao3(String projecao3) {
		this.projecao3.set(projecao3);
	}
	public String getprojecao4() {
		return projecao4.get();
	}
	public void setprojecao4(String projecao4) {
		this.projecao4.set(projecao4);
	}
	public String getprojecao5() {
		return projecao5.get();
	}
	public void setprojecao5(String projecao5) {
		this.projecao5.set(projecao5);
	}
}
