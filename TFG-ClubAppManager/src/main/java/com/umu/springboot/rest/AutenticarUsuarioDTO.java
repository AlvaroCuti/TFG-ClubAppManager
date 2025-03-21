package com.umu.springboot.rest;

public class AutenticarUsuarioDTO {
	private String tel;
	private String pass;

	public AutenticarUsuarioDTO(String tel, String pass) {
		this.tel = tel;
		this.pass = pass;
	}

	public AutenticarUsuarioDTO() {
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

}
