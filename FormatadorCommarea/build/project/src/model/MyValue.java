package model;

public class MyValue {
	private String valor;
	private String mask;
	private boolean dependingOn;

	public MyValue(){
		this.valor = new String();
		this.mask = null;
	}

	public MyValue(String valor, String mask){
		this.valor = valor;
		this.mask = mask;
	}

	public String getValue() {
		return valor;
	}
	public void setValor(String valor) {
		this.valor = valor;
	}
	public String getMask() {
		return mask;
	}
	public void setMask(String mask) {
		this.mask = mask;
	}

	public boolean isDependingOn() {
		return dependingOn;
	}

	public void setDependingOn(boolean dependingOn) {
		this.dependingOn = dependingOn;
	}

}
