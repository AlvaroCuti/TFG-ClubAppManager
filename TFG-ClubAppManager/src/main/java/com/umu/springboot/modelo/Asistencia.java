package com.umu.springboot.modelo;

public class Asistencia {
	private boolean confirmacion;

	public Asistencia(boolean confirmacion) {
		this.confirmacion = confirmacion;
	}

	public boolean isConfirmacion() {
		return confirmacion;
	}

	public void setConfirmacion(boolean confirmacion) {
		this.confirmacion = confirmacion;
	}

}
