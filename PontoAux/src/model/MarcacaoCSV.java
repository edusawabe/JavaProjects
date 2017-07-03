package model;

public class MarcacaoCSV {
	private String data;
	private String diaSemana;
	private String entrada;
	private String saida;
	private String qtdeHoras;

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getEntrada() {
		return entrada;
	}

	public void setEntrada(String entrada) {
		this.entrada = entrada;
	}

	public String getSaida() {
		return saida;
	}

	public void setSaida(String saida) {
		this.saida = saida;
	}

	public String getDiaSemana() {
		return diaSemana;
	}

	public void setDiaSemana(String diaSemana) {
		this.diaSemana = diaSemana;
	}

	public String toCSVString() {
		return getData() + ";" + getDiaSemana() + ";" + getEntrada() + ";" + getSaida() + ";" + getQtdeHoras() + ";";
	}

	public String getQtdeHoras() {
		return qtdeHoras;
	}

	public void setQtdeHoras(String qtdeHoras) {
		this.qtdeHoras = qtdeHoras;
	}
}
