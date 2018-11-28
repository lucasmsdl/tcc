package br.com.puc.eletro.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.puc.eletro.domain.Pedido;
import br.com.puc.eletro.services.PedidoService;

@RestController
@RequestMapping(value="/pedidos")
public class PedidoResource {

	@Autowired
	private PedidoService service;
	
	@RequestMapping(value= "/{codigo}", method=RequestMethod.GET)
	public ResponseEntity<?> find(@PathVariable Integer codigo){
		Pedido obj = service.buscar(codigo);
		return ResponseEntity.ok().body(obj);
	}
}
