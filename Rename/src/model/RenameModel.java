package model;

import application.RenamerController;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.scene.control.TableView;

public class RenameModel {
	private BooleanProperty selected;
	private SimpleStringProperty nomeAtual;
	private SimpleStringProperty nomeApos;
	private SimpleStringProperty renomeado;

	public RenameModel() {
		selected = new SimpleBooleanProperty(false);
		nomeAtual = new SimpleStringProperty();
		nomeApos= new SimpleStringProperty();
		renomeado = new SimpleStringProperty();
	}

    public BooleanProperty isSelected() {
        return selected;
    }

    public BooleanProperty isSelected(RenamerController t) {
    	t.resetTable(new ActionEvent());
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

	public String getRenomeado() {
		return renomeado.get();
	}

	public void setRenomeado(String renomeado) {
		this.renomeado.set(renomeado);
	}

}
