package br.com.puc.eletro.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import br.com.puc.eletro.domain.Categoria;
import br.com.puc.eletro.repositories.CategoriaRepository;
import br.com.puc.eletro.services.excptions.DataIntegrityException;
import br.com.puc.eletro.services.excptions.ObjectNotFoundException;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository repo;
	public Categoria find(Integer codigo) {
		Optional<Categoria> obj = repo.findById(codigo);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Código: "+ codigo + ", Tipo: " + Categoria.class.getName()));
	}
	
	public Categoria insert(Categoria obj) {
		obj.setCodigo(null);
		return repo.save(obj);
	}
	
	public Categoria update(Categoria obj) {
		find(obj.getCodigo());
		return repo.save(obj);
	}
	
	public void delete(Integer codigo) {
		find(codigo);
		try {
			repo.deleteById(codigo);
		}
		catch(DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir uma categoria que possui produtos");
		}
	}
	
	public List<Categoria> findAll(){
		
		return repo.findAll();
	}
}
