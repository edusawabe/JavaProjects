package model;

import javafx.beans.property.SimpleStringProperty;

public class MarcacaoLinhaTV {
	private String dateDom;
	private String dateSeg;
	private String dateTer;
	private String dateQua;
	private String dateQui;
	private String dateSex;
	private String dateSab;
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

	public String getDateDom() {
		return dateDom;
	}

	public void setDateDom(String dateDom) {
		this.dateDom = dateDom;
	}

	public String getDateSeg() {
		return dateSeg;
	}

	public void setDateSeg(String dateSeg) {
		this.dateSeg = dateSeg;
	}

	public String getDateTer() {
		return dateTer;
	}

	public void setDateTer(String dateTer) {
		this.dateTer = dateTer;
	}

	public String getDateQua() {
		return dateQua;
	}

	public void setDateQua(String dateQua) {
		this.dateQua = dateQua;
	}

	public String getDateQui() {
		return dateQui;
	}

	public void setDateQui(String dateQui) {
		this.dateQui = dateQui;
	}

	public String getDateSex() {
		return dateSex;
	}

	public void setDateSex(String dateSex) {
		this.dateSex = dateSex;
	}

	public String getDateSab() {
		return dateSab;
	}

	public void setDateSab(String dateSab) {
		this.dateSab = dateSab;
	}

	public void setHorasDom(SimpleStringProperty horasDom) {
		this.horasDom = horasDom;
	}

	public void setHorasSeg(SimpleStringProperty horasSeg) {
		this.horasSeg = horasSeg;
	}

	public void setHorasTer(SimpleStringProperty horasTer) {
		this.horasTer = horasTer;
	}

	public void setHorasQua(SimpleStringProperty horasQua) {
		this.horasQua = horasQua;
	}

	public void setHorasQui(SimpleStringProperty horasQui) {
		this.horasQui = horasQui;
	}

	public void setHorasSex(SimpleStringProperty horasSex) {
		this.horasSex = horasSex;
	}

	public void setHorasSab(SimpleStringProperty horasSab) {
		this.horasSab = horasSab;
	}

}
