package model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;

public class RenameModel {
	private BooleanProperty selected;
	private SimpleStringProperty nomeAtual;
	private SimpleStringProperty nomeApos;

	public RenameModel() {
		selected = new SimpleBooleanProperty(false);
		nomeAtual = new SimpleStringProperty();
		nomeApos= new SimpleStringProperty();
	}

    public BooleanProperty selectedProperty() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected.set(selected);
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
