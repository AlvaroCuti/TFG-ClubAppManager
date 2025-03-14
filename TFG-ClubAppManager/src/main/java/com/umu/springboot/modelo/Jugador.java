package com.umu.springboot.modelo;

import java.time.LocalDate;
import java.util.List;

public class Jugador extends Usuario {
	private String dniDelantera; // TODO
	private String dniTrasera; // TODO

	private String emailTutor1;
	private String dniDelanteraTutor1; // TODO
	private String dniTraseraTutor1; // TODO

	private String emailTutor2;
	private String dniDelanteraTutor2; // TODO
	private String dniTraseraTutor2; // TODO

	private List<Asistencia> asistencias;

	public Jugador(String tel, String nombre, LocalDate fechaNac, String email, String pass, String rol,
			String dniDelantera, String dniTrasera, String emailTutor1, String dniDelanteraTutor1,
			String dniTraseraTutor1, String emailTutor2, String dniDelanteraTutor2, String dniTraseraTutor2,
			List<Asistencia> asistencias) {
		
		super(tel, nombre, fechaNac, email, pass, rol);
		this.dniDelantera = dniDelantera;
		this.dniTrasera = dniTrasera;
		this.emailTutor1 = emailTutor1;
		this.dniDelanteraTutor1 = dniDelanteraTutor1;
		this.dniTraseraTutor1 = dniTraseraTutor1;
		this.emailTutor2 = emailTutor2;
		this.dniDelanteraTutor2 = dniDelanteraTutor2;
		this.dniTraseraTutor2 = dniTraseraTutor2;
		this.asistencias = asistencias;
	}

	public List<Asistencia> getAsistencias() {
		return asistencias;
	}

	public void setAsistencias(List<Asistencia> asistencias) {
		this.asistencias = asistencias;
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

	public String getEmailTutor1() {
		return emailTutor1;
	}

	public void setEmailTutor1(String emailTutor1) {
		this.emailTutor1 = emailTutor1;
	}

	public String getDniDelanteraTutor1() {
		return dniDelanteraTutor1;
	}

	public void setDniDelanteraTutor1(String dniDelanteraTutor1) {
		this.dniDelanteraTutor1 = dniDelanteraTutor1;
	}

	public String getDniTraseraTutor1() {
		return dniTraseraTutor1;
	}

	public void setDniTraseraTutor1(String dniTraseraTutor1) {
		this.dniTraseraTutor1 = dniTraseraTutor1;
	}

	public String getEmailTutor2() {
		return emailTutor2;
	}

	public void setEmailTutor2(String emailTutor2) {
		this.emailTutor2 = emailTutor2;
	}

	public String getDniDelanteraTutor2() {
		return dniDelanteraTutor2;
	}

	public void setDniDelanteraTutor2(String dniDelanteraTutor2) {
		this.dniDelanteraTutor2 = dniDelanteraTutor2;
	}

	public String getDniTraseraTutor2() {
		return dniTraseraTutor2;
	}

	public void setDniTraseraTutor2(String dniTraseraTutor2) {
		this.dniTraseraTutor2 = dniTraseraTutor2;
	}

}
