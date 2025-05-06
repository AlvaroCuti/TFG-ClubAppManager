package com.umu.springboot.utils;

import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.umu.springboot.modelo.Entrenador;
import com.umu.springboot.modelo.Usuario;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtilidades {
	private static final String SECRET_KEY = "TFG_CLUB_APP";
	private static final int tiempoExpiracion = 7200;

	public String generacionTokenJWT(Map<String, Object> usuario) {
		Map<String, Object> mapaClaims = new HashMap<>(usuario);
		return Jwts.builder().setClaims(mapaClaims)
							 .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
							 .setExpiration(Date.from(Instant.now().plusSeconds(tiempoExpiracion)))
							 .compact();
	}

	private Claims getClaims(String token) {
		return Jwts.parser().setSigningKey(SECRET_KEY)
							.parseClaimsJws(token)
							.getBody();
	}

	public Map<String, Object> usuarioAClaims(Usuario usuario) {
		Map<String, Object> mapaClaims = new HashMap<>();
		mapaClaims.put("tel", usuario.getTel());
		mapaClaims.put("nombre", usuario.getNombre());
		mapaClaims.put("pass", usuario.getPass());
		mapaClaims.put("rol", usuario.getRol());
		if(usuario instanceof Entrenador) {
			mapaClaims.put("debeCambiarPass", ((Entrenador)usuario).isDebeCambiarPassword());
		}
		return mapaClaims;
	}
	
	public boolean comprobacionTokenJWT(String token) {
		Claims claims = getClaims(token);
		
		if(claims.getExpiration().after(new Date()))
			return true;
		return false;
	}
}
