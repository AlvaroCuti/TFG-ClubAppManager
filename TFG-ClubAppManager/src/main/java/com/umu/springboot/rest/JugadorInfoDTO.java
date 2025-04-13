package com.umu.springboot.rest;

public class JugadorInfoDTO {

	private long dniDelantera;
	private long dniTrasera;
	private long dniDelanteraTutor1;
	private long dniTraseraTutor1;
	private long dniDelanteraTutor2;
	private long dniTraseraTutor2;

	public JugadorInfoDTO() {
		super();
	}

	public JugadorInfoDTO(long dniDelantera, long dniTrasera, long dniDelanteraTutor1, long dniTraseraTutor1,
			long dniDelanteraTutor2, long dniTraseraTutor2) {
		super();
		this.dniDelantera = dniDelantera;
		this.dniTrasera = dniTrasera;
		this.dniDelanteraTutor1 = dniDelanteraTutor1;
		this.dniTraseraTutor1 = dniTraseraTutor1;
		this.dniDelanteraTutor2 = dniDelanteraTutor2;
		this.dniTraseraTutor2 = dniTraseraTutor2;
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

	public long getDniDelanteraTutor1() {
		return dniDelanteraTutor1;
	}

	public void setDniDelanteraTutor1(long dniDelanteraTutor1) {
		this.dniDelanteraTutor1 = dniDelanteraTutor1;
	}

	public long getDniTraseraTutor1() {
		return dniTraseraTutor1;
	}

	public void setDniTraseraTutor1(long dniTraseraTutor1) {
		this.dniTraseraTutor1 = dniTraseraTutor1;
	}

	public long getDniDelanteraTutor2() {
		return dniDelanteraTutor2;
	}

	public void setDniDelanteraTutor2(long dniDelanteraTutor2) {
		this.dniDelanteraTutor2 = dniDelanteraTutor2;
	}

	public long getDniTraseraTutor2() {
		return dniTraseraTutor2;
	}

	public void setDniTraseraTutor2(long dniTraseraTutor2) {
		this.dniTraseraTutor2 = dniTraseraTutor2;
	}

}
