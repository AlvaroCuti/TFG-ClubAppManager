package com.umu.springboot.rest;

public class VerificarUsuarioDTO {
	private String idTelefono;
	private String pass;
	
	public VerificarUsuarioDTO(String idTelefono, String pass) {
		this.idTelefono = idTelefono;
		this.pass = pass;
	}

	public String getIdTelefono() {
		return idTelefono;
	}

	public void setIdTelefono(String idTelefono) {
		this.idTelefono = idTelefono;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}
}
