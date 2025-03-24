package com.umu.springboot.rest;

import java.time.LocalDate;

import com.umu.springboot.modelo.Jugador;

public class JugadorDTO {
	private String tel;
	private String nombre;
	private LocalDate fechaNac;
	private String email;
	private String emailTutor1;
	private String emailTutor2;

	public JugadorDTO(String tel, String nombre, LocalDate fechaNac, String email, String emailTutor1,
			String emailTutor2) {
		this.tel = tel;
		this.nombre = nombre;
		this.fechaNac = fechaNac;
		this.email = email;
		this.emailTutor1 = emailTutor1;
		this.emailTutor2 = emailTutor2;
	}

	public JugadorDTO(Jugador jugador) {
		this.tel = jugador.getTel();
		this.nombre = jugador.getNombre();
		this.fechaNac = jugador.getFechaNac();
		this.email = jugador.getEmail();
		this.emailTutor1 = jugador.getEmailTutor1();
		this.emailTutor1 = jugador.getEmailTutor2();
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
