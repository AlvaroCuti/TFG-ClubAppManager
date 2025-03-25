package com.umu.springboot.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

//	@Autowired
//	private SecuritySuccessHandler successHandler;

	@Autowired
	private JwtRequestFilter authenticationRequestFilter;

	@Override
	public void configure(HttpSecurity httpSecurity) throws Exception {
				
		httpSecurity.httpBasic().disable()
	    .csrf().disable()
	    .authorizeRequests()
	    .antMatchers("/api/usuario/register").permitAll() // Permitidos sin autenticación
	    .anyRequest().authenticated() // Todo lo demás requiere autenticación
	    .and()
	    .sessionManagement()
	    .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

	}

}
