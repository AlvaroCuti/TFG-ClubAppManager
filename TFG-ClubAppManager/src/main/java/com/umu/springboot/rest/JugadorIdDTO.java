package com.umu.springboot.rest;

public class JugadorIdDTO {
	private String tel;

	public JugadorIdDTO(String tel) {
		this.tel = tel;
	}

	public JugadorIdDTO() {
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}
}
