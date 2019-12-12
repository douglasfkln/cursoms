package com.cursomc2.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cursomc2.domain.Categoria;
import com.cursomc2.repositories.CategoriaRepository;

@Service
public class CategoriaService {

	// Automaticamente instancia o repo
	@Autowired
	private CategoriaRepository repo;
	
	public Categoria buscar(Integer id) {
		Optional<Categoria> obj = repo.findById(id);
		return obj.orElse(null);
	}	
}