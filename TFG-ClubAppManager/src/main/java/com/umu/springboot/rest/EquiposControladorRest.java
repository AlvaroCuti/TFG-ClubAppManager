package com.umu.springboot.rest;

import java.net.URI;

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.umu.springboot.servicio.IServicioEquipo;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/equipos")
public class EquiposControladorRest {

	@Autowired
	private IServicioEquipo servicioEquipo;
	
	@Autowired
	private PagedResourcesAssembler<EquipoDTO> pagedResourcesAssembler;
	
	@GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAuthority('admin)")
	public PagedModel<EntityModel<EquipoDTO>> getListadoEquipo(@RequestParam int page, @RequestParam int size) {
		Pageable paginacion = PageRequest.of(page, size);
		
		Page<EquipoDTO> listaEquiposDTO = servicioEquipo.listarEquipos(paginacion);
		
		return this.pagedResourcesAssembler.toModel(listaEquiposDTO, equipo -> {
			EntityModel<EquipoDTO> model = EntityModel.of(equipo);
			return model;
		});
	}

	@PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)			
	@PreAuthorize("hasAuthority('admin)")
	public ResponseEntity<Void> crearEquipo(@Valid @RequestBody CreacionEquipoDTO crearEquipoDTO) {	
		String idEquipo = servicioEquipo.crearEquipo(servicioEquipo.dtoToModelEntrenador(crearEquipoDTO.getEntrenadores()));
		URI url = ServletUriComponentsBuilder.fromCurrentRequest().path("/{idEquipo}").buildAndExpand(idEquipo).toUri();
		return ResponseEntity.created(url).build();
	}

	@GetMapping(value = "/{idEquipo}", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAuthority('admin)")
	public EquipoDTO getInfoEquipo(@PathVariable String idEquipo) {
		EquipoDTO equipoDTO = servicioEquipo.getEquipo(idEquipo);
		return equipoDTO;
	}
	
	@PutMapping(value = "/{idEquipo}", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAuthority('admin)")
	public void modificarEquipo(@PathVariable String idEquipo, @Valid @RequestBody ModificacionEquipoDTO modificarEquipoDTO) {
		servicioEquipo.modificarEquipo(idEquipo, servicioEquipo.dtoToModelEntrenador(modificarEquipoDTO.getEntrenadores()), servicioEquipo.dtoToModelJugador(modificarEquipoDTO.getJugadores()));
	}	
	
	@PostMapping(value = "/{idEquipo}", consumes = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAuthority('admin)")
	public ResponseEntity<Void> borrarEquipo(@PathVariable String idEquipo) {
		servicioEquipo.borrarEquipo(idEquipo);
		return ResponseEntity.noContent().build();
	}
	
}