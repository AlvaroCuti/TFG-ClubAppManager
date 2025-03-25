package com.umu.springboot.security;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.umu.springboot.servicio.IServicioUsuarios;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import utils.JwtUtilidades;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

	@Autowired
	private IServicioUsuarios servicioUsuarios;
	
	private JwtUtilidades utilitiesJWT;
	private static final String SECRET_KEY = "TFG_CLUB_APP";
	
	public JwtRequestFilter(JwtUtilidades utils) {
		this.utilitiesJWT = utils;
	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String requestURI = request.getRequestURI();

	    // Permitir sin autenticación ciertas rutas
	    if (requestURI.equals("/api/usuario/login") || requestURI.equals("/api/usuario/register")) {    
	        filterChain.doFilter(request, response);
	        return;
	    }
		
		String authHeader = request.getHeader("Authorization");
		try {
			if((request.getRequestURI().equals("/auth/login"))) {	
				filterChain.doFilter(request, response);
				return;
			}
			else {
				Claims claims;
				String authorization = request.getHeader("Authorization");
				if (authorization == null || !authorization.startsWith("Bearer ")) {								//NO tiene el formato correcto JWT
					response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token JWT no valido");
					return;
				}else {
					String token = authorization.substring("Bearer ".length()).trim();
					
					if (!utilitiesJWT.comprobacionTokenJWT(token)) {
						response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token JWT no valido");				//Esta caducado el token JWT
						return;
					}
					claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
					ArrayList<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
					authorities.add(new SimpleGrantedAuthority(claims.get("rol").toString()));
					UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(claims.getSubject(), null, authorities);
					// Establecemos la autenticación en el contexto de seguridad
					// Se interpreta como que el usuario ha superado la autenticación
					SecurityContextHolder.getContext().setAuthentication(auth);
				}
				filterChain.doFilter(request, response);
			}
			filterChain.doFilter(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
