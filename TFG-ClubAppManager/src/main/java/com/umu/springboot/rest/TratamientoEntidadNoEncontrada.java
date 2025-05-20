package com.umu.springboot.rest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import com.umu.springboot.repositorios.EntidadNoEncontrada;

@ControllerAdvice
public class TratamientoEntidadNoEncontrada {
	@ExceptionHandler(EntidadNoEncontrada.class)
	@ResponseBody
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public RespuestaError handleGlobalException(EntidadNoEncontrada ex) {
		return new RespuestaError("Not Found", ex.getMessage());
	}
}
