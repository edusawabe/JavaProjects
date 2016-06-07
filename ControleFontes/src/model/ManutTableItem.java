package model;

import javafx.beans.property.SimpleStringProperty;

public class ManutTableItem {
	SimpleStringProperty tipo;
	SimpleStringProperty tipoFonte;
	SimpleStringProperty quantidade;

	public ManutTableItem() {
		tipo = new SimpleStringProperty();
		tipoFonte = new SimpleStringProperty();
		quantidade = new SimpleStringProperty();
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
		return quantidade.get();
	}

	public void setQuantidade(String quantidade) {
		this.quantidade.set(quantidade);
	}
}
