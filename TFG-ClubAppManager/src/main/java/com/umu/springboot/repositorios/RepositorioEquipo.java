package com.umu.springboot.repositorios;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.umu.springboot.modelo.Equipo;

@NoRepositoryBean
public interface RepositorioEquipo extends PagingAndSortingRepository<Equipo, String>{

}
