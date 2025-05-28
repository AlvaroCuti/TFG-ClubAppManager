package com.umu.springboot.rest;

public class CreacionJugadorDTO {
	private String tel;
	private String nombre;
	private String fechaNac;
	private String email;
	private String pass;

	private String emailTutor1;

	private String emailTutor2;

	public CreacionJugadorDTO() {
		super();
	}

	public CreacionJugadorDTO(String tel, String nombre, String fechaNac, String email, String pass, String emailTutor1,
			String emailTutor2) {
		super();
		this.tel = tel;
		this.nombre = nombre;
		this.fechaNac = fechaNac;
		this.email = email;
		this.pass = pass;
		this.emailTutor1 = emailTutor1;
		this.emailTutor2 = emailTutor2;
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

	public String getEmailTutor1() {
		return emailTutor1;
	}

	public void setEmailTutor1(String emailTutor1) {
		this.emailTutor1 = emailTutor1;
	}

	public String getEmailTutor2() {
		return emailTutor2;
	}

	public void setEmailTutor2(String emailTutor2) {
		this.emailTutor2 = emailTutor2;
	}

}
