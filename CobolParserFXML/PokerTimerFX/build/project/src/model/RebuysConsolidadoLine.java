package model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;

public class RebuysConsolidadoLine {
	private SimpleStringProperty nome;
	private SimpleStringProperty rebuys;
	private SimpleBooleanProperty pago;

	public RebuysConsolidadoLine() {
		nome = new SimpleStringProperty();
		rebuys = new SimpleStringProperty();
		pago = new SimpleBooleanProperty();
	}

	public String getNome() {
		return nome.get();
	}

	public void setNome(String nome) {
		this.nome.set(nome);
	}

	public String getRebuys() {
		return rebuys.get();
	}

	public void setRebuys(String rebuys) {
		this.nome.set(rebuys);
	}

    public boolean isPago() {
        return pago.get();
    }

    public BooleanProperty pagoProperty() {
        return pago;
    }

    public void setPago(boolean pago) {
        this.pago.set(pago);
    }

    public boolean getPago() {
        return pago.get();
    }
}
