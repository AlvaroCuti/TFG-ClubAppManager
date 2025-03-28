package com.umu.springboot.rest;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.umu.springboot.servicio.IServicioUsuarios;

@RestController
@RequestMapping("/api")
public class UsuariosControladorRest {

	@Autowired
	private IServicioUsuarios servicioUsuarios;

	@Autowired
	private PagedResourcesAssembler<EntrenadorCompletoDTO> pagedResourcesAssembler1;
	
	@Autowired
	private PagedResourcesAssembler<JugadorDTO> pagedResourcesAssembler2;

	@PostMapping(value = "/usuario/login", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<String, Object>> login(@Valid @RequestBody AutenticarUsuarioDTO autenticacionDTO) {
		Map<String, Object> c = servicioUsuarios.verificarCredenciales(autenticacionDTO.getTel(),
				autenticacionDTO.getPass());
		if(c == null) {
			Map<String, Object> errorResponse = new HashMap<>();
	        errorResponse.put("error", "Unauthorized");
	        errorResponse.put("message", "Credenciales incorrectas");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
		}
		return ResponseEntity.ok(c);
	}

	@PostMapping(value = "/usuario/register", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> registroUsuario(@Valid @RequestBody CreacionJugadorDTO creacionDTO) {
		String idUsuario = servicioUsuarios.darDeAltaJugador(creacionDTO.getTel(), creacionDTO.getNombre(),
				creacionDTO.getFechaNac(), creacionDTO.getEmail(), creacionDTO.getPass(), creacionDTO.getDniDelantera(),
				creacionDTO.getDniTrasera(), creacionDTO.getEmailTutor1(), creacionDTO.getDniDelanteraTutor1(),
				creacionDTO.getDniTraseraTutor1(), creacionDTO.getEmailTutor2(), creacionDTO.getDniDelanteraTutor2(),
				creacionDTO.getDniTraseraTutor2());
		if(idUsuario == null)
			return ResponseEntity.badRequest().build();
			
		URI url = ServletUriComponentsBuilder.fromCurrentRequest().path("/{idUsuario}").buildAndExpand(idUsuario)
				.toUri();

		return ResponseEntity.created(url).build();
	}

	@GetMapping(value = "/usuario")
	public PagedModel<EntityModel<JugadorDTO>> filtrarJugadores(@RequestParam(required = false) String nombre,
			@RequestParam(required = false) String tel, @RequestParam(required = false) String fechaNac,
			@RequestParam(required = false) String email, @RequestParam(required = false) String emailTutor1,
			@RequestParam(required = false) String emailTutor2, @RequestParam int page,
			@RequestParam int size) {		
		
		Pageable paginacion = PageRequest.of(page, size);
		Page<JugadorDTO> listaJugadoresDTO = servicioUsuarios.filtrarJugadores(nombre, tel, fechaNac, email, emailTutor1, emailTutor2, paginacion);
		return this.pagedResourcesAssembler2.toModel(listaJugadoresDTO, jugador -> {
			EntityModel<JugadorDTO> model = EntityModel.of(jugador);
			return model;
		});
	}

	@GetMapping(value = "/usuario/{idUsuario}", produces = MediaType.APPLICATION_JSON_VALUE)
	public JugadorInfoDTO getInfoJugador(@PathVariable String idUsuario) {
		JugadorInfoDTO dto = servicioUsuarios.descargarInfoUsuario(idUsuario);
		return dto;
	}

	@GetMapping(value = "/entrenador", produces = MediaType.APPLICATION_JSON_VALUE)					
	public PagedModel<EntityModel<EntrenadorCompletoDTO>> getListadoEntrenadores(@RequestParam int page,
			@RequestParam int size) {
		Pageable paginacion = PageRequest.of(page, size);
		Page<EntrenadorCompletoDTO> listaEntrenadoresDTO = servicioUsuarios.listaEntrenadores(paginacion);
		return this.pagedResourcesAssembler1.toModel(listaEntrenadoresDTO, entrenador -> {
			EntityModel<EntrenadorCompletoDTO> model = EntityModel.of(entrenador);
			return model;
		});
	}

	@PostMapping(value = "/entrenador", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> crearEntrenador(@Valid @RequestBody CrearEntrenadorDTO crearEntrenadorDTO) {
		String idEntrenador = servicioUsuarios.crearEntrenador(crearEntrenadorDTO.getTel(),
				crearEntrenadorDTO.getNombre(), crearEntrenadorDTO.getFechaNac(), crearEntrenadorDTO.getEmail(),
				crearEntrenadorDTO.getPass(), crearEntrenadorDTO.getDniDelantera(), crearEntrenadorDTO.getDniTrasera(),
				crearEntrenadorDTO.getCertificadoDelitosSexuales());
		if(idEntrenador == null)
			return ResponseEntity.badRequest().build();
		
		URI url = ServletUriComponentsBuilder.fromCurrentRequest().path("/{idEntrenador}").buildAndExpand(idEntrenador)
				.toUri();
		return ResponseEntity.created(url).build();
	}

	@GetMapping(value = "/entrenador/{idEntrenador}", produces = MediaType.APPLICATION_JSON_VALUE)			//TODO cambiar DTO que devuelve
	public EntrenadorDTO getInfoEntrenador(@PathVariable String idEntrenador) {
		EntrenadorDTO dto = servicioUsuarios.getEntrenador(idEntrenador);
		return dto;
	}

	@PutMapping(value = "/entrenador/{idEntrenador}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> modificarEntrenador(@PathVariable String idEntrenador,
			@Valid @RequestBody ModificacionEntrenadorDTO modificarEntrenadorDTO) {
		servicioUsuarios.modificarEntrenador(idEntrenador, modificarEntrenadorDTO.getTel(), modificarEntrenadorDTO.getNombre(),
				modificarEntrenadorDTO.getFechaNac(), modificarEntrenadorDTO.getEmail(),
				modificarEntrenadorDTO.getPass(), modificarEntrenadorDTO.getDniDelantera(),
				modificarEntrenadorDTO.getDniTrasera(), modificarEntrenadorDTO.getCertificadoDelitosSexuales());
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping(value = "/entrenador/{idEntrenador}")
	public ResponseEntity<Void> borrarEntrenador(@PathVariable String idEntrenador) {
		servicioUsuarios.borrarEntrenador(idEntrenador);
		return ResponseEntity.noContent().build();
	}
	
	
//	@ExceptionHandler(IllegalArgumentException.class)										//TODO REVISAR
//	public ResponseEntity<String> handleIllegalArgument(IllegalArgumentException ex) {
//	    return ResponseEntity.badRequest().body(ex.getMessage());
//	}
}
