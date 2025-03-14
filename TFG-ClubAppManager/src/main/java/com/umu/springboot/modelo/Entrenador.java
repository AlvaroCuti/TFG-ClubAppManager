package com.umu.springboot.modelo;

public class Entrenador extends Usuario {
	private String dniDelantera; // TODO
	private String dniTrasera; // TODO
	private String certificadoDelitosSexuales; // TODO

	public String getDniDelantera() {
		return dniDelantera;
	}

	public void setDniDelantera(String dniDelantera) {
		this.dniDelantera = dniDelantera;
	}

	public String getDniTrasera() {
		return dniTrasera;
	}

	public void setDniTrasera(String dniTrasera) {
		this.dniTrasera = dniTrasera;
	}

	public String getCertificadoDelitosSexuales() {
		return certificadoDelitosSexuales;
	}

	public void setCertificadoDelitosSexuales(String certificadoDelitosSexuales) {
		this.certificadoDelitosSexuales = certificadoDelitosSexuales;
	}

}
