package com.umu.springboot.rest;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.umu.springboot.servicio.IServicioUsuarios;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthControladorRest {
	
	@Autowired
	private IServicioUsuarios servicioUsuarios;
	
	@PostMapping(value = "login", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<String, Object>> login(@Valid @RequestBody AutenticarUsuarioDTO autenticacionDTO){
		jwtUtilidades.
	}
}
