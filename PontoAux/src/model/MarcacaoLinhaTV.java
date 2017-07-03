package model;

import javafx.beans.property.SimpleStringProperty;

public class MarcacaoLinhaTV {
	private String date;
	private SimpleStringProperty horasDom;
	private SimpleStringProperty horasSeg;
	private SimpleStringProperty horasTer;
	private SimpleStringProperty horasQua;
	private SimpleStringProperty horasQui;
	private SimpleStringProperty horasSex;
	private SimpleStringProperty horasSab;

	public MarcacaoLinhaTV(){
		horasDom = new SimpleStringProperty();
		horasSeg = new SimpleStringProperty();
		horasTer = new SimpleStringProperty();
		horasQua = new SimpleStringProperty();
		horasQui = new SimpleStringProperty();
		horasSex = new SimpleStringProperty();
		horasSab = new SimpleStringProperty();
	}

	public String getHorasDom() {
		return horasDom.get();
	}

	public void setHorasDom(String horasDom) {
		this.horasDom.set(horasDom);
	}

	public String getHorasSeg() {
		return horasSeg.get();
	}

	public void setHorasSeg(String horasSeg) {
		this.horasSeg.set(horasSeg);
	}

	public String getHorasTer() {
		return horasTer.get();
	}

	public void setHorasTer(String horasTer) {
		this.horasTer.set(horasTer);
	}

	public String getHorasQua() {
		return horasQua.get();
	}

	public void setHorasQua(String horasQua) {
		this.horasQua.set(horasQua);
	}

	public String getHorasQui() {
		return horasQui.get();
	}

	public void setHorasQui(String horasQui) {
		this.horasQui.set(horasQui);
	}

	public String getHorasSex() {
		return horasSex.get();
	}

	public void setHorasSex(String horasSex) {
		this.horasSex.set(horasSex);
	}

	public String getHorasSab() {
		return horasSab.get();
	}

	public void setHorasSab(String horasSab) {
		this.horasSab.set(horasSab);
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

}
