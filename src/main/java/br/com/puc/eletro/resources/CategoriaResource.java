package br.com.puc.eletro.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.puc.eletro.domain.Categoria;
import br.com.puc.eletro.dto.CategoriaDTO;
import br.com.puc.eletro.services.CategoriaService;

@RestController
@RequestMapping(value="/categorias")
public class CategoriaResource {

	@Autowired
	private CategoriaService service;
	
	@RequestMapping(value="/{codigo}", method=RequestMethod.GET)
	public ResponseEntity<Categoria> find(@PathVariable Integer codigo) {
				
		Categoria obj = service.find(codigo);
		return ResponseEntity.ok().body(obj);
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Void> insert(@RequestBody Categoria obj){
		obj = service.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{codigo}").buildAndExpand(obj.getCodigo()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@RequestMapping(value="/{codigo}", method=RequestMethod.PUT)
	public ResponseEntity<Void> update(@RequestBody Categoria obj, @PathVariable Integer codigo){
		obj.setCodigo(codigo);
		obj = service.update(obj);
		
		return ResponseEntity.noContent().build();
	}
	@RequestMapping(value="/{codigo}", method=RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer codigo){
		service.delete(codigo);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<CategoriaDTO>> findAll(){
		List <Categoria> list = service.findAll();
		List<CategoriaDTO> listDTO = list.stream().map(obj -> new CategoriaDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDTO);
	}
}
