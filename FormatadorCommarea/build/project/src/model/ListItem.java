package model;

import javafx.beans.property.SimpleStringProperty;

public class ListItem {

    private final SimpleStringProperty campo = new SimpleStringProperty("");
    private final SimpleStringProperty valor = new SimpleStringProperty("");
    private String mask;

    public ListItem() {
        this("", "", "");
    }

    public ListItem(String campo, String valor, String mask) {
        setCampo(campo);
        setValor(valor);
        setMask(mask);
    }

    public String getCampo() {
        return campo.get();
    }

    public void setCampo(String campo) {
        this.campo.set(campo);
    }

    public String getValor() {
        return valor.get();
    }

    public void setValor(String valor) {
    	this.valor.set(valor);
    }

	public String getMask() {
		return mask;
	}

	public void setMask(String mask) {
    	this.mask = mask;
    }

	public String toString(){
		return campo.get();
	}
}