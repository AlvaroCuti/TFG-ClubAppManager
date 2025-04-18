package com.umu.springboot.rest;

import java.util.List;

public class EquiposIdsDTO {
	List<EquipoIdDTO> equipos;

	public EquiposIdsDTO(List<EquipoIdDTO> equipos) {
		super();
		this.equipos = equipos;
	}

	public EquiposIdsDTO() {
		super();
	}

	public List<EquipoIdDTO> getEquipos() {
		return equipos;
	}

	public void setEquipos(List<EquipoIdDTO> equipos) {
		this.equipos = equipos;
	}
}
