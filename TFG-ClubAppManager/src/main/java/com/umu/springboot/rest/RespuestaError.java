package com.umu.springboot.rest;

public class RespuestaError {
	
	private String estado;
	private String mensaje;

	public RespuestaError(String estado, String mensaje) {
		this.estado = estado;
		this.mensaje = mensaje;
	}
	
}