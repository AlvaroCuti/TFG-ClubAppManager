package com.umu.springboot.rest;

import java.time.LocalDate;

public class EntrenadorCompletoDTO {
	private String tel;
	private String nombre;
	private String fechaNac;
	private String email;

	public EntrenadorCompletoDTO() {
	}

	public EntrenadorCompletoDTO(String tel, String nombre, String fechaNac, String email) {
		this.tel = tel;
		this.nombre = nombre;
		this.fechaNac = fechaNac;
		this.email = email;
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
}
