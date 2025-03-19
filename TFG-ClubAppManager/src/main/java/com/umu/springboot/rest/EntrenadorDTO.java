package com.umu.springboot.rest;

public class EntrenadorDTO {
	private String tel;

	public EntrenadorDTO(String tel) {
		this.tel = tel;
	}

	public EntrenadorDTO() {
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

}
