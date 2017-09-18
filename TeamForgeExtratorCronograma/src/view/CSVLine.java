package view;

import java.util.LinkedList;

public class CSVLine {
	private String nomeTarefa;
	private String duracao;
	private String trabalho;
	private String inicio;
	private String termino;
	private String predecessora;
	private LinkedList<String> lrecurso;

	public CSVLine() {
		nomeTarefa = new String();
		duracao = new String();
		trabalho = new String();
		inicio = new String();
		termino = new String();
		predecessora = new String();
		lrecurso = new LinkedList<String>();
	}

	public String getNomeTarefa() {
		return nomeTarefa;
	}

	public void setNomeTarefa(String nomeTarefa) {
		this.nomeTarefa = nomeTarefa;
	}

	public String getDuracao() {
		return duracao;
	}

	public void setDuracao(String duracao) {
		this.duracao = duracao;
	}

	public String getTrabalho() {
		return trabalho;
	}

	public void setTrabalho(String trabalho) {
		this.trabalho = trabalho;
	}

	public String getInicio() {
		return inicio;
	}

	public void setInicio(String inicio) {
		this.inicio = inicio;
	}

	public String getTermino() {
		return termino;
	}

	public void setTermino(String termino) {
		this.termino = termino;
	}

	public String getPredecessora() {
		return predecessora;
	}

	public void setPredecessora(String predecessora) {
		this.predecessora = predecessora;
	}

	public LinkedList<String> getLrecurso() {
		return lrecurso;
	}

	public void setLrecurso(LinkedList<String> lrecurso) {
		this.lrecurso = lrecurso;
	}
}
