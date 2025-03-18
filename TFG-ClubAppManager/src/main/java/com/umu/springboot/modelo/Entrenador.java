package com.umu.springboot.modelo;

import java.time.LocalDate;

public class Entrenador extends Usuario {
	private String dniDelantera; // TODO
	private String dniTrasera; // TODO
	private String certificadoDelitosSexuales; // TODO

	public Entrenador(String tel, String nombre, LocalDate fechaNac, String email, String pass, String rol,
			String dniDelantera, String dniTrasera, String certificadoDelitosSexuales) {
		super(tel, nombre, fechaNac, email, pass, rol);
		this.dniDelantera = dniDelantera;
		this.dniTrasera = dniTrasera;
		this.certificadoDelitosSexuales = certificadoDelitosSexuales;
	}

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

	public void modificar(String tel, String nombre, LocalDate fechaNac, String email, String pass, String dniDelantera,
			String dniTrasera, String certificadoDelitosSexuales) {
		this.setTel(tel);
		this.setNombre(nombre);
		this.setFechaNac(fechaNac);
		this.setEmail(email);
		this.setPass(pass);
		this.setDniDelantera(dniDelantera);
		this.setDniTrasera(dniTrasera);
		this.setCertificadoDelitosSexuales(certificadoDelitosSexuales);
	}

}
