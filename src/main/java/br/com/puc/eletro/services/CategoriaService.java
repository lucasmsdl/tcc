package br.com.puc.eletro.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.puc.eletro.domain.Categoria;
import br.com.puc.eletro.repositories.CategoriaRepository;
import br.com.puc.eletro.services.excptions.ObjectNotFoundException;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository repo;
	public Categoria buscar(Integer codigo) {
		Optional<Categoria> obj = repo.findById(codigo);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Código: "+ codigo + ", Tipo: " + Categoria.class.getName()));
	}
}
