package model;

public class ResultadoRodada {
	int rebuys;
	String colocacao;
	double premiacao;

	public ResultadoRodada() {
		colocacao = null;
		premiacao = 0;
		rebuys = 0;
	}

	public ResultadoRodada(String resultado) {
		String[] res;
		res = resultado.split("@");
		rebuys = Integer.parseInt(res[0]);
		colocacao = res[1];
		premiacao = Double.parseDouble(res[2]);
	}

	public void getResultadoFromFileLine(String resultado){
		String[] res;
		res = resultado.split("@");
		rebuys = Integer.parseInt(res[0]);
		colocacao = res[1];
		premiacao = Double.parseDouble(res[2]);
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
