package com.umu.springboot.security;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.umu.springboot.servicio.IServicioUsuarios;
import com.umu.springboot.utils.JwtUtilidades;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

	@Autowired
	private IServicioUsuarios servicioUsuarios;
	
	@Autowired
	private JwtUtilidades utilitiesJWT;
	
	@Value("${jwt.secret}")
	private String secretKey;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String requestURI = request.getRequestURI();

		// Permitir sin autenticación ciertas rutas
		if (requestURI.equals("/api/usuario/login") || requestURI.equals("/api/usuario/register")) {
			filterChain.doFilter(request, response);
			return;
		}

		String authorization = request.getHeader("Authorization");
		try {
				Claims claims;
				if (authorization == null || !authorization.startsWith("Bearer ")) { // NO tiene el formato correcto JWT
					response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token JWT no valido");
					return;
				} else {
					String token = authorization.substring("Bearer ".length()).trim();

					if (!utilitiesJWT.comprobacionTokenJWT(token)) {
						response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token JWT no valido"); // Esta caducado
																										// el token JWT
						return;
					}
					claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
					ArrayList<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
					authorities.add(new SimpleGrantedAuthority(claims.get("rol").toString()));
					UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
							claims.getSubject(), null, authorities);
					// Establecemos la autenticación en el contexto de seguridad
					// Se interpreta como que el usuario ha superado la autenticación
					SecurityContextHolder.getContext().setAuthentication(auth);
				}
				filterChain.doFilter(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
