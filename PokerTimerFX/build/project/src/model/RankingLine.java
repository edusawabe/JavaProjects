package model;

import javafx.beans.property.SimpleStringProperty;

public class RankingLine {
	private SimpleStringProperty jogador;
	private SimpleStringProperty result1;
	private SimpleStringProperty result2;
	private SimpleStringProperty result3;
	private SimpleStringProperty result4;
	private SimpleStringProperty result5;
	private SimpleStringProperty result6;
	private SimpleStringProperty result7;
	private SimpleStringProperty result8;
	private SimpleStringProperty result9;
	private SimpleStringProperty result10;
	private SimpleStringProperty result11;
	private SimpleStringProperty result12;
	private SimpleStringProperty total;
	private SimpleStringProperty totalRebuys;
	private SimpleStringProperty totalGasto;
	private SimpleStringProperty totalGanho;
	private SimpleStringProperty saldo;

	public RankingLine() {
		jogador = new SimpleStringProperty();
		result1 = new SimpleStringProperty();
		result2 = new SimpleStringProperty();
		result3 = new SimpleStringProperty();
		result4 = new SimpleStringProperty();
		result5 = new SimpleStringProperty();
		result6 = new SimpleStringProperty();
		result7 = new SimpleStringProperty();
		result8 = new SimpleStringProperty();
		result9 = new SimpleStringProperty();
		result10 = new SimpleStringProperty();
		result11 = new SimpleStringProperty();
		result12 = new SimpleStringProperty();
		total = new SimpleStringProperty();
		totalRebuys = new SimpleStringProperty();
		totalGasto = new SimpleStringProperty();
		totalGanho = new SimpleStringProperty();
		saldo = new SimpleStringProperty();
	}

	public String getJogador() {
		return jogador.get();
	}
	public void setJogador(String jogador) {
		this.jogador.set(jogador);
	}
	public String getResult1() {
		return result1.get();
	}
	public void setResult1(String result1) {
		this.result1.set(result1);
	}
	public String getResult2() {
		return result2.get();
	}
	public void setResult2(String result2) {
		this.result2.set(result2);
	}
	public String getResult3() {
		return result3.get();
	}
	public void setResult3(String result3) {
		this.result3.set(result3);
	}
	public String getResult4() {
		return result4.get();
	}
	public void setResult4(String result4) {
		this.result4.set(result4);
	}
	public String getResult5() {
		return result5.get();
	}
	public void setResult5(String result5) {
		this.result5.set(result5);
	}
	public String getResult6() {
		return result6.get();
	}
	public void setResult6(String result6) {
		this.result6.set(result6);
	}
	public String getResult7() {
		return result7.get();
	}
	public void setResult7(String result7) {
		this.result7.set(result7);
	}
	public String getResult8() {
		return result8.get();
	}
	public void setResult8(String result8) {
		this.result8.set(result8);
	}
	public String getResult9() {
		return result9.get();
	}
	public void setResult9(String result9) {
		this.result9.set(result9);
	}
	public String getResult10() {
		return result10.get();
	}
	public void setResult10(String result10) {
		this.result10.set(result10);
	}
	public String getResult11() {
		return result11.get();
	}
	public void setResult11(String result11) {
		this.result11.set(result11);
	}
	public String getResult12() {
		return result12.get();
	}
	public void setResult12(String result12) {
		this.result12.set(result12);
	}
	public String getTotal() {
		return total.get();
	}
	public void setTotal(String total) {
		this.total.set(total);
	}
	public String getTotalRebuys() {
		return totalRebuys.get();
	}
	public void setTotalRebuys(String totalRebuys) {
		this.totalRebuys.set(totalRebuys);
	}
	public String getTotalGasto() {
		return totalGasto.get();
	}
	public void setTotalGasto(String totalGasto) {
		this.totalGasto.set(totalGasto);
	}
	public String getTotalGanho() {
		return totalGanho.get();
	}
	public void setTotalGanho(String totalGanho) {
		this.totalGanho.set(totalGanho);
	}
	public String getSaldo() {
		return saldo.get();
	}
	public void setSaldo(String saldo) {
		this.saldo.set(saldo);
	}
}
