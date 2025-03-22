package com.umu.springboot.rest;

public class CrearEntrenadorDTO {
	private String tel;
	private String nombre;
	private String fechaNac;
	private String email;
	private String pass;
	private String dniDelantera;
	private String dniTrasera;
	private String certificadoDelitosSexuales;

	public CrearEntrenadorDTO() {
	}

	public CrearEntrenadorDTO(String tel, String nombre, String fechaNac, String enamil, String pass,
			String dniDelantera, String dniTrasera, String certificadoDelitosSexuales) {
		this.tel = tel;
		this.nombre = nombre;
		this.fechaNac = fechaNac;
		this.enamil = enamil;
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

	public String getFechaNac() {
		return fechaNac;
	}

	public void setFechaNac(String fechaNac) {
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
