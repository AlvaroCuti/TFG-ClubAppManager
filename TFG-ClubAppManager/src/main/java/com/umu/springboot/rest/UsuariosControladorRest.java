package com.umu.springboot.rest;

import java.net.URI;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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
	private PagedResourcesAssembler<EntrenadorDTO> pagedResourcesAssembler1;
	
	@Autowired
	private PagedResourcesAssembler<JugadorDTO> pagedResourcesAssembler2;

	@PostMapping(value = "/usuario/login", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<String, Object>> login(@Valid @RequestBody AutenticarUsuarioDTO autenticacionDTO) {
		Map<String, Object> c = servicioUsuarios.verificarCredenciales(autenticacionDTO.getTel(),
				autenticacionDTO.getPass());
		return ResponseEntity.ok(c);
	}

	@PostMapping(value = "/usuario/register", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> registroUsuario(@Valid @RequestBody CreacionJugadorDTO creacionDTO) {
		String idUsuario = servicioUsuarios.darDeAltaJugador(creacionDTO.getTel(), creacionDTO.getNombre(),
				creacionDTO.getFechaNac(), creacionDTO.getEmail(), creacionDTO.getPass(), creacionDTO.getDniDelantera(),
				creacionDTO.getDniTrasera(), creacionDTO.getEmailTutor1(), creacionDTO.getDniDelanteraTutor1(),
				creacionDTO.getDniTraseraTutor1(), creacionDTO.getEmailTutor2(), creacionDTO.getDniDelanteraTutor2(),
				creacionDTO.getDniTraseraTutor2());
		URI url = ServletUriComponentsBuilder.fromCurrentRequest().path("/{idUsuario}").buildAndExpand(idUsuario)
				.toUri();

		return ResponseEntity.created(url).build();
	}

	@GetMapping(value = "/usuario", consumes = MediaType.APPLICATION_JSON_VALUE)
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
		return servicioUsuarios.descargarInfoUsuario(idUsuario);
	}

	@GetMapping(value = "/entrenador", produces = MediaType.APPLICATION_JSON_VALUE)
	public PagedModel<EntityModel<EntrenadorDTO>> getListadoEntrenadores(@RequestParam int page,
			@RequestParam int size) {
		Pageable paginacion = PageRequest.of(page, size);
		Page<EntrenadorDTO> listaEntrenadoresDTO = servicioUsuarios.listaEntrenadores(paginacion);
		return this.pagedResourcesAssembler1.toModel(listaEntrenadoresDTO, entrenador -> {
			EntityModel<EntrenadorDTO> model = EntityModel.of(entrenador);
			return model;
		});
	}

	@PostMapping(value = "/entrenador", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> crearEntrenador(@Valid @RequestBody CrearEntrenadorDTO crearEntrenadorDTO) {
		String idEntrenador = servicioUsuarios.crearEntrenador(crearEntrenadorDTO.getTel(),
				crearEntrenadorDTO.getNombre(), crearEntrenadorDTO.getFechaNac(), crearEntrenadorDTO.getEmail(),
				crearEntrenadorDTO.getPass(), crearEntrenadorDTO.getDniDelantera(), crearEntrenadorDTO.getDniTrasera(),
				crearEntrenadorDTO.getCertificadoDelitosSexuales());
		URI url = ServletUriComponentsBuilder.fromCurrentRequest().path("/{idEntrenador}").buildAndExpand(idEntrenador)
				.toUri();
		return ResponseEntity.created(url).build();
	}

	@GetMapping(value = "/entrenador/{idEntrenador}", produces = MediaType.APPLICATION_JSON_VALUE)
	public EntrenadorDTO getEntrenador(@PathVariable String idUsuario) {
		EntrenadorDTO dto = servicioUsuarios.getEntrenador(idUsuario);
		return dto;
	}

	@PutMapping(value = "/entrenador/{idEntrenador}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> modificarEntrenador(@PathVariable String idUsuario,
			@Valid @RequestBody ModificacionEntrenadorDTO modificarEntrenadorDTO) {
		servicioUsuarios.modificarEntrenador(modificarEntrenadorDTO.getTel(), modificarEntrenadorDTO.getNombre(),
				modificarEntrenadorDTO.getFechaNac(), modificarEntrenadorDTO.getEmail(),
				modificarEntrenadorDTO.getPass(), modificarEntrenadorDTO.getDniDelantera(),
				modificarEntrenadorDTO.getDniTrasera(), modificarEntrenadorDTO.getCertificadoDelitosSexuales());
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping(value = "/entrenador/{idEntrenador}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> borrarEntrenador(@PathVariable String idUsuario) {
		servicioUsuarios.borrarEntrenador(idUsuario);
		return ResponseEntity.noContent().build();
	}
}
