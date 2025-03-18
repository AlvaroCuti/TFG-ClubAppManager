package com.umu.springboot.rest;

import java.time.LocalDate;

public class ModificacionEntrenadorDTO {
	private String tel;
	private String nombre;
	private LocalDate fechaNac;
	private String email;
	private String pass;
	private String dniDelantera; // TODO
	private String dniTrasera; // TODO
	private String certificadoDelitosSexuales; // TODO

	public ModificacionEntrenadorDTO(String tel, String nombre, LocalDate fechaNac, String email, String pass,
			String dniDelantera, String dniTrasera, String certificadoDelitosSexuales) {
		this.tel = tel;
		this.nombre = nombre;
		this.fechaNac = fechaNac;
		this.email = email;
		this.pass = pass;
		this.dniDelantera = dniDelantera;
		this.dniTrasera = dniTrasera;
		this.certificadoDelitosSexuales = certificadoDelitosSexuales;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public LocalDate getFechaNac() {
		return fechaNac;
	}

	public void setFechaNac(LocalDate fechaNac) {
		this.fechaNac = fechaNac;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
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

}
