package com.umu.springboot.modelo;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

public class Jugador extends Usuario {
	private long dniDelantera; // TODO
	private long dniTrasera; // TODO

	private String emailTutor1;
	private long dniDelanteraTutor1; // TODO
	private long dniTraseraTutor1; // TODO

	private String emailTutor2;
	private long dniDelanteraTutor2; // TODO
	private long dniTraseraTutor2; // TODO

	private List<Asistencia> asistencias;
	private String equipo;

	public Jugador(String tel, String nombre, LocalDate fechaNac, String email, String pass, String rol,
			long dniDelantera, long dniTrasera, String emailTutor1, long dniDelanteraTutor1, long dniTraseraTutor1,
			String emailTutor2, long dniDelanteraTutor2, long dniTraseraTutor2) {

		super(tel, nombre, fechaNac, email, pass, rol);
		this.dniDelantera = dniDelantera;
		this.dniTrasera = dniTrasera;
		this.emailTutor1 = emailTutor1;
		this.dniDelanteraTutor1 = dniDelanteraTutor1;
		this.dniTraseraTutor1 = dniTraseraTutor1;
		this.emailTutor2 = emailTutor2;
		this.dniDelanteraTutor2 = dniDelanteraTutor2;
		this.dniTraseraTutor2 = dniTraseraTutor2;
		this.asistencias = new LinkedList<>();
		this.equipo = null;
	}

	public List<Asistencia> getAsistencias() {
		return asistencias;
	}

	public void setAsistencias(List<Asistencia> asistencias) {
		this.asistencias = asistencias;
	}

	public long getDniDelantera() {
		return dniDelantera;
	}

	public void setDniDelantera(long dniDelantera) {
		this.dniDelantera = dniDelantera;
	}

	public long getDniTrasera() {
		return dniTrasera;
	}

	public void setDniTrasera(long dniTrasera) {
		this.dniTrasera = dniTrasera;
	}

	public long getDniDelanteraTutor1() {
		return dniDelanteraTutor1;
	}

	public void setDniDelanteraTutor1(long dniDelanteraTutor1) {
		this.dniDelanteraTutor1 = dniDelanteraTutor1;
	}

	public long getDniTraseraTutor1() {
		return dniTraseraTutor1;
	}

	public void setDniTraseraTutor1(long dniTraseraTutor1) {
		this.dniTraseraTutor1 = dniTraseraTutor1;
	}

	public String getEmailTutor2() {
		return emailTutor2;
	}

	public void setEmailTutor2(String emailTutor2) {
		this.emailTutor2 = emailTutor2;
	}

	public long getDniDelanteraTutor2() {
		return dniDelanteraTutor2;
	}

	public void setDniDelanteraTutor2(long dniDelanteraTutor2) {
		this.dniDelanteraTutor2 = dniDelanteraTutor2;
	}

	public long getDniTraseraTutor2() {
		return dniTraseraTutor2;
	}

	public void setDniTraseraTutor2(long dniTraseraTutor2) {
		this.dniTraseraTutor2 = dniTraseraTutor2;
	}

	public String getEmailTutor1() {
		return emailTutor1;
	}

	public void setEmailTutor1(String emailTutor1) {
		this.emailTutor1 = emailTutor1;
	}

	public void añadirAsistencia(Asistencia asistencia) {
		this.asistencias.add(asistencia);
	}

	public String getEquipo() {
		return equipo;
	}

	public void setEquipo(String equipo) {
		this.equipo = equipo;
	}

	public boolean comprobarAsistencia(Asistencia asistencia) {
		for (Asistencia a : asistencias) {
			if ((a.getIdEntrenamiento().equals(asistencia.getIdEntrenamiento()))
					&& (a.getIdJugador().equals(asistencia.getIdJugador())))
				return false;
		}
		return true;
	}
	
	public void borrarEquipo() {
		this.equipo = null;
	}
	
}
