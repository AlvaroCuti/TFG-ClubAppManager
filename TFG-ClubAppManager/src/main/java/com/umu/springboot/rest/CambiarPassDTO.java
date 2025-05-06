package com.umu.springboot.rest;

public class CambiarPassDTO {
	private String oldPass;
	private String newPass;

	public CambiarPassDTO(String oldPass, String newPass) {
		super();
		this.oldPass = oldPass;
		this.newPass = newPass;
	}

	public CambiarPassDTO() {
		super();
	}

	public String getOldPass() {
		return oldPass;
	}

	public void setOldPass(String oldPass) {
		this.oldPass = oldPass;
	}

	public String getNewPass() {
		return newPass;
	}

	public void setNewPass(String newPass) {
		this.newPass = newPass;
	}

}
