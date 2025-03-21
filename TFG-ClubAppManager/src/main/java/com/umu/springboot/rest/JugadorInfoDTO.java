package com.umu.springboot.rest;

public class JugadorInfoDTO {

	private String dniDelantera;
	private String dniTrasera;
	private String dniDelanteraTutor1;
	private String dniTraseraTutor1;
	private String dniDelanteraTutor2;
	private String dniTraseraTutor2;

	public JugadorInfoDTO() {
	}

	public JugadorInfoDTO(String dniDelantera, String dniTrasera, String dniDelanteraTutor1, String dniTraseraTutor1,
			String dniDelanteraTutor2, String dniTraseraTutor2) {
		this.dniDelantera = dniDelantera;
		this.dniTrasera = dniTrasera;
		this.dniDelanteraTutor1 = dniDelanteraTutor1;
		this.dniTraseraTutor1 = dniTraseraTutor1;
		this.dniDelanteraTutor2 = dniDelanteraTutor2;
		this.dniTraseraTutor2 = dniTraseraTutor2;
	}

	public String getDniDelantera() {
		return dniDelantera;
	}

	public void setDniDelantera(String dniDelantera) {
		this.dniDelantera = dniDelantera;
	}

	public String getDniTrasera() {
		return dniTrasera;
	}

	public void setDniTrasera(String dniTrasera) {
		this.dniTrasera = dniTrasera;
	}

	public String getDniDelanteraTutor1() {
		return dniDelanteraTutor1;
	}

	public void setDniDelanteraTutor1(String dniDelanteraTutor1) {
		this.dniDelanteraTutor1 = dniDelanteraTutor1;
	}

	public String getDniTraseraTutor1() {
		return dniTraseraTutor1;
	}

	public void setDniTraseraTutor1(String dniTraseraTutor1) {
		this.dniTraseraTutor1 = dniTraseraTutor1;
	}

	public String getDniDelanteraTutor2() {
		return dniDelanteraTutor2;
	}

	public void setDniDelanteraTutor2(String dniDelanteraTutor2) {
		this.dniDelanteraTutor2 = dniDelanteraTutor2;
	}

	public String getDniTraseraTutor2() {
		return dniTraseraTutor2;
	}

	public void setDniTraseraTutor2(String dniTraseraTutor2) {
		this.dniTraseraTutor2 = dniTraseraTutor2;
	}

}
