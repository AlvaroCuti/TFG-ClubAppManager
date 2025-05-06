package com.umu.springboot.rest;

import java.util.List;

public class EquiposIdsDTO {
	List<EquipoIdYNombreDTO> equipos;
	
	public EquiposIdsDTO(List<EquipoIdYNombreDTO> equipos) {
		super();
		this.equipos = equipos;
	}

	public EquiposIdsDTO() {
		super();
	}

	public List<EquipoIdYNombreDTO> getEquipos() {
		return equipos;
	}

	public void setEquipos(List<EquipoIdYNombreDTO> equipos) {
		this.equipos = equipos;
	}
}
