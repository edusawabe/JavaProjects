package model;

import javafx.beans.property.SimpleStringProperty;

public class MarcacaoMes {
	private SimpleStringProperty mes;
	private SimpleStringProperty horasMes;
	private SimpleStringProperty horasRealizadas;
	private SimpleStringProperty diferencaHoras;

	public MarcacaoMes(){
		mes = new SimpleStringProperty();
		horasMes = new SimpleStringProperty();
		horasRealizadas = new SimpleStringProperty();
		diferencaHoras = new SimpleStringProperty();
	}

	public String getMes() {
		return mes.get();
	}

	public void setMes(String mes) {
		this.mes.set(mes);
	}

	public String getHorasMes() {
		return horasMes.get();
	}

	public void setHorasMes(String horasMes) {
		this.horasMes.set(horasMes);
	}

	public String getHorasRealizadas() {
		return horasRealizadas.get();
	}

	public void setHorasRealizadas(String horasRealizadas) {
		this.horasRealizadas.set(horasRealizadas);
	}

	public String getDiferencaHoras() {
		return diferencaHoras.get();
	}

	public void setDiferencaHoras(String diferencaHoras) {
		this.diferencaHoras.set(diferencaHoras);
	}

}
