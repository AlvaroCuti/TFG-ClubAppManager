package com.umu.springboot.security;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.umu.springboot.servicio.IServicioUsuarios;

@Component
public class SecuritySuccessHandler implements AuthenticationSuccessHandler{

	@Autowired
	private IServicioUsuarios servicioUsuarios;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		
		User user = (User) authentication.getPrincipal();
		
		Map<String, Object> mapaClaims = servicioUsuarios.verificarCredenciales(user.getName(), user.getPassword());
		
		if(mapaClaims != null) {
			
		}
			
		
	}

}
