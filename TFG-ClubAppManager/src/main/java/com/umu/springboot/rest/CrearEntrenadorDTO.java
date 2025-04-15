package com.umu.springboot.rest;

public class CrearEntrenadorDTO {
	private String tel;
	private String nombre;
	private String fechaNac;
	private String email;
	private String pass;

	public CrearEntrenadorDTO() {
	}

	public CrearEntrenadorDTO(String tel, String nombre, String fechaNac, String email, String pass,
			String dniDelantera, String dniTrasera, String certificadoDelitosSexuales) {
		this.tel = tel;
		this.nombre = nombre;
		this.fechaNac = fechaNac;
		this.email = email;
		this.pass = pass;
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

}
