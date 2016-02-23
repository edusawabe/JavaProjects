package model;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class ResultadoRodada {
	int rebuys;
	String colocacao;
	double premiacao;
	double pontuacaoEtapa;

	public double getPontuacaoEtapa() {
		return pontuacaoEtapa;
	}

	public void setPontuacaoEtapa(double pontuacaoEtapa) {
	    BigDecimal bd = new BigDecimal(pontuacaoEtapa);
	    bd = bd.setScale(2, RoundingMode.HALF_UP);
		this.pontuacaoEtapa = bd.doubleValue();
	}

	public ResultadoRodada() {
		colocacao = null;
		premiacao = 0;
		rebuys = 0;
		pontuacaoEtapa = 0;
	}

	public ResultadoRodada(String resultado) {
		String[] res;
		res = resultado.split("@");
		rebuys = Integer.parseInt(res[0]);
		colocacao = res[1];
		premiacao = Double.parseDouble(res[2]);
		pontuacaoEtapa = Double.parseDouble(res[3]);
	}

	public void getResultadoFromFileLine(String resultado){
		String[] res;
		res = resultado.split("@");
		rebuys = Integer.parseInt(res[0]);
		colocacao = res[1];
		premiacao = Double.parseDouble(res[2]);
		pontuacaoEtapa = Double.parseDouble(res[3]);
	}

	public String getResultLine(){
		String res = new String();
		res =  rebuys + "@" + colocacao + "@" + premiacao + "@" + pontuacaoEtapa;
		return res;
	}

	public String getColocacao() {
		return colocacao;
	}
	public void setColocacao(String colocacao) {
		this.colocacao = colocacao;
	}
	public double getPremiacao() {
		return premiacao;
	}
	public void setPremiacao(double premiacao) {
		this.premiacao = premiacao;
	}
	public int getRebuys() {
		return rebuys;
	}
	public void setRebuys(int rebuys) {
		this.rebuys = rebuys;
	}
}