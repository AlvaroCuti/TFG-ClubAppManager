package com.umu.springboot.rest;

import java.time.LocalDate;

public class EntrenadorCompletoDTO {
	private String tel;
	private String nombre;
	private String fechaNac;
	private String email;
	private String dniDelantera; // TODO
	private String dniTrasera; // TODO
	private String certificadoDelitosSexuales; // TODO

	public EntrenadorCompletoDTO() {
	}

	public EntrenadorCompletoDTO(String tel, String nombre, String fechaNac, String email, String dniDelantera,
			String dniTrasera, String certificadoDelitosSexuales) {
		this.tel = tel;
		this.nombre = nombre;
		this.fechaNac = fechaNac;
		this.email = email;
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

	public String getEmail() {
		return email;
	}

	public String getFechaNac() {
		return fechaNac;
	}

	public void setFechaNac(String fechaNac) {
		this.fechaNac = fechaNac;
	}

	public void setEmail(String email) {
		this.email = email;
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
