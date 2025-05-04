package com.umu.springboot.rest;

import java.net.URI;
import java.time.LocalDateTime;

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
import org.springframework.security.access.prepost.PreAuthorize;
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

import com.umu.springboot.modelo.Categoria;
import com.umu.springboot.servicio.IServicioEntrenamiento;
import com.umu.springboot.servicio.IServicioEquipo;


@RestController
@RequestMapping("/api/equipo")
public class EquiposControladorRest {

	@Autowired
	private IServicioEquipo servicioEquipo;
	@Autowired
	private IServicioEntrenamiento servicioEntrenamiento;
	
	@Autowired
	private PagedResourcesAssembler<EquipoDTO> pagedResourcesAssembler1;
	@Autowired
	private PagedResourcesAssembler<EntrenamientoDTO> pagedResourcesAssembler2;
	
	@GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAuthority('ADMIN')")
	public PagedModel<EntityModel<EquipoDTO>> getListadoEquipo(@RequestParam int page, @RequestParam int size) {
		Pageable paginacion = PageRequest.of(page, size);
		
		Page<EquipoDTO> listaEquiposDTO = servicioEquipo.listarEquipos(paginacion);
		
		return this.pagedResourcesAssembler1.toModel(listaEquiposDTO, equipo -> {
			EntityModel<EquipoDTO> model = EntityModel.of(equipo);
			return model;
		});
	}

	@PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<Void> crearEquipo(@Valid @RequestBody CreacionEquipoDTO crearEquipoDTO) {	
		String idEquipo = servicioEquipo.crearEquipo(crearEquipoDTO.getNombre(), Categoria.valueOf(crearEquipoDTO.getCategoria()), servicioEquipo.dtoToModelEntrenador(crearEquipoDTO.getEntrenadores()));
		if(idEquipo == null)
			return ResponseEntity.badRequest().build();
		URI url = ServletUriComponentsBuilder.fromCurrentRequest().path("/{idEquipo}").buildAndExpand(idEquipo).toUri();
		return ResponseEntity.created(url).build();
	}

	@PutMapping(value = "/{idEquipo}/jugador/{idJugador}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<Void> addJugadorAEquipo(@PathVariable String idEquipo, @PathVariable String idJugador) {	
		servicioEquipo.addJugadorAEquipo(idEquipo, idJugador);
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping(value = "/{idEquipo}/jugador/{idJugador}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<Void> removeJugadorDeEquipo(@PathVariable String idEquipo, @PathVariable String idJugador) {	
		servicioEquipo.removeUsuarioDeEquipo(idEquipo, idJugador);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping(value = "/{idEquipo}", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAuthority('ADMIN')")
	public EquipoDTO getInfoEquipo(@PathVariable String idEquipo) {
		EquipoDTO equipoDTO = servicioEquipo.getEquipo(idEquipo);
		return equipoDTO;
	}
	
	@PutMapping(value = "/{idEquipo}", consumes = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<Void> modificarEquipo(@PathVariable String idEquipo, @Valid @RequestBody ModificacionEquipoDTO modificarEquipoDTO) {
		servicioEquipo.modificarEquipo(idEquipo, modificarEquipoDTO.getNombre(), servicioEquipo.dtoToModelEntrenador(modificarEquipoDTO.getEntrenadores()), servicioEquipo.dtoToModelJugador(modificarEquipoDTO.getJugadores()));
		return ResponseEntity.noContent().build();
	}	
	
	@DeleteMapping(value = "/{idEquipo}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<Void> borrarEquipo(@PathVariable String idEquipo) {
		servicioEquipo.borrarEquipo(idEquipo);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping(value = "/{idEquipo}/entrenamiento", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAnyAuthority('ENTRENADOR', 'JUGADOR')")
	public PagedModel<EntityModel<EntrenamientoDTO>> listarEntrenamientos(@PathVariable String idEquipo, @RequestParam int page, @RequestParam int size) {
		Pageable paginacion = PageRequest.of(page, size);
		Page<EntrenamientoDTO> listaEntrenamientosDTO = servicioEntrenamiento.listarEntrenamientos(idEquipo, paginacion);
		return this.pagedResourcesAssembler2.toModel(listaEntrenamientosDTO, entrenamiento -> {
			EntityModel<EntrenamientoDTO> model = EntityModel.of(entrenamiento);
			return model;
		});
	}
	
	@PostMapping(value = "/{idEquipo}/entrenamiento", consumes = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAuthority('ENTRENADOR')")
	public ResponseEntity<Void> programarEntrenamiento(@PathVariable String idEquipo, @Valid @RequestBody ProgramacionEntrenamientoDTO programarEntrenamientoDTO) {
		String idEntrenamiento = servicioEntrenamiento.programarEntrenamiento(idEquipo, programarEntrenamientoDTO.getFecha(), programarEntrenamientoDTO.getHora(), programarEntrenamientoDTO.getLugar());
		if(idEntrenamiento == null)
			return ResponseEntity.badRequest().build();
		URI url = ServletUriComponentsBuilder.fromCurrentRequest().path("/{idEntrenamiento}").buildAndExpand(idEntrenamiento).toUri();
		return ResponseEntity.created(url).build();
	}
	
	@PutMapping(value = "{idEquipo}/entrenamiento/{idEntrenamiento}/usuario/{idUsuario}", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAuthority('JUGADOR')")
	public ResponseEntity<Void> confirmarAsistencia(@PathVariable String idEquipo, @PathVariable String idEntrenamiento, @PathVariable String idUsuario) {
		servicioEntrenamiento.confirmarAsistencia(idEquipo,idEntrenamiento, idUsuario);
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping(value = "{idEquipo}/entrenamiento/{idEntrenamiento}/usuario/{idUsuario}", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAuthority('JUGADOR')")
	public ResponseEntity<Void> cancelarAsistencia(@PathVariable String idEquipo, @PathVariable String idEntrenamiento, @PathVariable String idUsuario) {
		servicioEntrenamiento.cancelarAsistencia(idEquipo, idEntrenamiento, idUsuario);
		return ResponseEntity.noContent().build();
	}
}