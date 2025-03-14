package com.umu.springboot.modelo;

import java.time.LocalDate;

public class Usuario {
	private String tel;
	private String nombre;
	private LocalDate fechaNac;
	private String email;
	private String pass;
	
	private String rol;

	public Usuario(String tel, String nombre, LocalDate fechaNac, String email, String pass, String rol) {
		super();
		this.tel = tel;
		this.nombre = nombre;
		this.fechaNac = fechaNac;
		this.email = email;
		this.pass = pass;
		this.rol = rol;
	}

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
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

}
