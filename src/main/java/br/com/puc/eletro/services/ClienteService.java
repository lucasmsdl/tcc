package br.com.puc.eletro.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.puc.eletro.domain.Cliente;
import br.com.puc.eletro.repositories.ClienteRepository;
import br.com.puc.eletro.services.excptions.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repo;
	
	public Cliente find(Integer codigo) {
		
		Optional<Cliente> obj = repo.findById(codigo);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Código: " + codigo + ", Tipo: " + Cliente.class.getName()));
		
	}
	
}
