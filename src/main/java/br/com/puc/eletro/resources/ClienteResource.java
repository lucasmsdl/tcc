package br.com.puc.eletro.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.puc.eletro.domain.Cliente;
import br.com.puc.eletro.services.ClienteService;

@RestController
@RequestMapping(value="/clientes")
public class ClienteResource {

	@Autowired
	private ClienteService service;
	
	@RequestMapping(value="/{codigo}", method=RequestMethod.GET)
	public ResponseEntity<?> find(@PathVariable Integer codigo) {
				
		Cliente obj = service.buscar(codigo);
		return ResponseEntity.ok().body(obj);
	}
}
