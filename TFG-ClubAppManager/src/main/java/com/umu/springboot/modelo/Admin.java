package com.umu.springboot.modelo;

import java.time.LocalDate;

public class Admin extends Usuario{

	public Admin(String tel, String nombre, LocalDate fechaNac, String email, String pass, String rol) {
		super(tel, nombre, fechaNac, email, pass, rol);
	}
	
}
