package com.umu.springboot.modelo;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

public class Entrenador extends Usuario {
	private long dniDelantera; // TODO
	private long dniTrasera; // TODO
	private long certificadoDelitosSexuales; // TODO
	
	private List<String> equipos;

	public Entrenador(String tel, String nombre, LocalDate fechaNac, String email, String pass, String rol,
			long dniDelantera, long dniTrasera, long certificadoDelitosSexuales) {
		super(tel, nombre, fechaNac, email, pass, rol);
		this.dniDelantera = dniDelantera;
		this.dniTrasera = dniTrasera;
		this.certificadoDelitosSexuales = certificadoDelitosSexuales;
		this.equipos = new LinkedList<String>();
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

	public long getCertificadoDelitosSexuales() {
		return certificadoDelitosSexuales;
	}

	public void setCertificadoDelitosSexuales(long certificadoDelitosSexuales) {
		this.certificadoDelitosSexuales = certificadoDelitosSexuales;
	}

	public List<String> getEquipos() {
		return equipos;
	}

	public void setEquipos(List<String> equipos) {
		this.equipos = equipos;
	}
	
	public void addEquipo(String equipo) {
		this.equipos.add(equipo);
	}

	public void modificar(String tel, String nombre, LocalDate fechaNac, String email, String pass, long dniDelantera,
			long dniTrasera, long certificadoDelitosSexuales) {
		this.setTel(tel);
		this.setNombre(nombre);
		this.setFechaNac(fechaNac);
		this.setEmail(email);
		this.setPass(pass);
		this.setDniDelantera(dniDelantera);
		this.setDniTrasera(dniTrasera);
		this.setCertificadoDelitosSexuales(certificadoDelitosSexuales);
	}

}
