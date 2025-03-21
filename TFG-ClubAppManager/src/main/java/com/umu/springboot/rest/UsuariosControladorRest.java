package com.umu.springboot.rest;

import java.net.URI;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.umu.springboot.servicio.IServicioUsuarios;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class UsuariosControladorRest {
	
	@Autowired
	private IServicioUsuarios servicioUsuarios;
	
	@PostMapping(value = "/usuario/login", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<String, Object>> login(@Valid @RequestBody AutenticarUsuarioDTO autenticacionDTO){
		Map<String, Object> c = servicioUsuarios.verificarCredenciales(autenticacionDTO.getTel(), autenticacionDTO.getPass());
		return ResponseEntity.ok(c);
	}
	
	@PostMapping(value = "/usuario/register", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> registroUsuario(@Valid @RequestBody CreacionJugadorDTO creacionDTO){
		String idUsuario = servicioUsuarios.darDeAltaJugador(creacionDTO.getTel(), creacionDTO.getNombre(),
				creacionDTO.getFechaNac(), creacionDTO.getEmail(), creacionDTO.getPass(),
				creacionDTO.getDniDelantera(), creacionDTO.getDniTrasera(), creacionDTO.getEmailTutor1(),
				creacionDTO.getDniDelanteraTutor1(), creacionDTO.getDniTraseraTutor1(),
				creacionDTO.getEmailTutor2(), creacionDTO.getDniDelanteraTutor2(),
				creacionDTO.getDniTraseraTutor2());
		URI url = ServletUriComponentsBuilder.fromCurrentRequest().path("/{idUsuario}").buildAndExpand(idUsuario).toUri();

		return ResponseEntity.created(url).build();
	}
	
	@GetMapping(value = "/usuario", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> filtrarJugadores(){															//TODO
		servicioUsuarios.filtrarJugadores();
		return ResponseEntity.ok(c);
	}

	@GetMapping(value = "/usuario/{idUsuario}", produces = MediaType.APPLICATION_JSON_VALUE)
	public JugadorInfoDTO getInfoJugador(@PathVariable String idUsuario) {
		return servicioUsuarios.descargarInfoUsuario(idUsuario);
	}

	@GetMapping(value = "/entrenador", produces = MediaType.APPLICATION_JSON_VALUE)
	public Page<EntrenadorDTO> getListadoEntrenadores(){
		
	}
}
