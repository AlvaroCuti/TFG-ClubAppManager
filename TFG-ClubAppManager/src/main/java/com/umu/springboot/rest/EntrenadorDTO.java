package com.umu.springboot.rest;

public class EntrenadorDTO {
	private String tel;
	private long dniDelantera;
	private long dniTrasera;
	private long certDelitos;

	public EntrenadorDTO() {
		super();
	}

	public EntrenadorDTO(String tel, long dniDelantera, long dniTrasera, long certDelitos) {
		super();
		this.tel = tel;
		this.dniDelantera = dniDelantera;
		this.dniTrasera = dniTrasera;
		this.certDelitos = certDelitos;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
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

	public long getCertDelitos() {
		return certDelitos;
	}

	public void setCertDelitos(long certDelitos) {
		this.certDelitos = certDelitos;
	}

}
