package model;

import javafx.beans.property.SimpleStringProperty;

public class GeneratedTableItem {
	SimpleStringProperty tipo;
	SimpleStringProperty tipoFonte;
	SimpleStringProperty nome;

	public GeneratedTableItem() {
		tipo = new SimpleStringProperty();
		tipoFonte = new SimpleStringProperty();
		nome = new SimpleStringProperty();
	}

	public String getTipo() {
		return tipo.get();
	}

	public void setTipo(String tipo) {
		this.tipo.set(tipo);
	}

	public String getTipoFonte() {
		return tipoFonte.get();
	}

	public void setTipoFonte(String tipoFonte) {
		this.tipoFonte.set(tipoFonte);
	}

	public String getQuantidade() {
		return nome.get();
	}

	public void setNome(String nome) {
		this.nome.set(nome);
	}
}
