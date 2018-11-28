package br.com.puc.eletro.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.puc.eletro.domain.Pedido;
import br.com.puc.eletro.repositories.PedidoRepository;
import br.com.puc.eletro.services.excptions.ObjectNotFoundException;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository repo;
	
	public Pedido buscar(Integer codigo) {
		
		Optional<Pedido> obj = repo.findById(codigo);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Código: " + codigo +", Tipo: " + Pedido.class.getName()));
	}
}
