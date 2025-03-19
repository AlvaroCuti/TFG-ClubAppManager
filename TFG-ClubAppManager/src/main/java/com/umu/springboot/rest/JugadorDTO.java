package com.umu.springboot.rest;

public class JugadorDTO {
	private String tel;

	public JugadorDTO() {
	}

	public JugadorDTO(String tel) {
		this.tel = tel;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

}
