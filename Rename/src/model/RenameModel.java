package model;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;

public class RenameModel {
	private SimpleBooleanProperty selecionado;
	private SimpleStringProperty nomeAtual;
	private SimpleStringProperty nomeApos;

	public RenameModel() {
		selecionado = new SimpleBooleanProperty();
		nomeAtual = new SimpleStringProperty();
		nomeApos= new SimpleStringProperty();
	}

	public Boolean getSelecionado() {
		return selecionado.getValue();
	}

	public void setSelecionado(Boolean selecionado) {
		this.selecionado.set(selecionado);
	}

	public String getNomeAtual() {
		return nomeAtual.get();
	}

	public void setNomeAtual(String nomeAtual) {
		this.nomeAtual.set(nomeAtual);
	}

	public String getNomeApos() {
		return nomeApos.get();
	}

	public void setNomeApos(String nomeApos) {
		this.nomeApos.set(nomeApos);
	}

}
