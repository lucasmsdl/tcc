package br.com.puc.eletro.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.puc.eletro.domain.Estado;
import br.com.puc.eletro.repositories.EstadoRepository;

@Service
public class EstadoService {

	@Autowired
	private EstadoRepository repo;
	
	public List<Estado> findAll(){
		
		return repo.findAllByOrderByNome();
	}
}
