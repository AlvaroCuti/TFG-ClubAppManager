package com.umu.springboot.rest;

public class UsuarioElementoDTO {
	private String tel;
	private String nombre;

	public UsuarioElementoDTO(String tel, String nombre) {
		super();
		this.tel = tel;
		this.nombre = nombre;
	}

	public UsuarioElementoDTO() {
		super();
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

}
