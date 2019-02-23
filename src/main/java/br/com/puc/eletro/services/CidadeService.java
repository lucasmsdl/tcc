package br.com.puc.eletro.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.puc.eletro.domain.Cidade;
import br.com.puc.eletro.repositories.CidadeRepository;

@Service
public class CidadeService {

	@Autowired
	private CidadeRepository repo;
	
	public List<Cidade> findByEstado(Integer estadoCodigo){
		return repo.findCidades(estadoCodigo);
	}
}
