package br.com.puc.eletro.services;

import java.awt.image.BufferedImage;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import br.com.puc.eletro.domain.Cidade;
import br.com.puc.eletro.domain.Cliente;
import br.com.puc.eletro.domain.Endereco;
import br.com.puc.eletro.domain.enums.Perfil;
import br.com.puc.eletro.domain.enums.TipoCliente;
import br.com.puc.eletro.dto.ClienteDTO;
import br.com.puc.eletro.dto.ClienteNewDTO;
import br.com.puc.eletro.repositories.ClienteRepository;
import br.com.puc.eletro.repositories.EnderecoRepository;
import br.com.puc.eletro.security.UserSS;
import br.com.puc.eletro.services.excptions.AuthorizationException;
import br.com.puc.eletro.services.excptions.DataIntegrityException;
import br.com.puc.eletro.services.excptions.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private BCryptPasswordEncoder pe;
	
	@Autowired
	private ClienteRepository repo;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Autowired
	private S3Service s3Service;
	
	@Autowired
	private ImageService imageService;
	
	@Value("${img.prefix.client.profile}")
	private String prefix;
	
	@Value("${img.profile.size}")
	private Integer size;
	
	public Cliente find(Integer codigo) {
		
		UserSS user = UserService.authenticated();
		if (user == null || !user.hasRole(Perfil.ADMIN) && !codigo.equals(user.getCodigo())) {
			throw new AuthorizationException("Acesso negado");
			
		}
		Optional<Cliente> obj = repo.findById(codigo);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Código: " + codigo + ", Tipo: " + Cliente.class.getName()));
		
	}
	@Transactional
	public Cliente insert(Cliente obj) {
		obj.setCodigo(null);
		obj = repo.save(obj);
		enderecoRepository.saveAll(obj.getEnderecos());
		return obj;
	}
	
	public Cliente updtate(Cliente obj) {
		Cliente newObj = find(obj.getCodigo());
		updateData(newObj, obj);
		return repo.save(newObj);
	}
	
	public void delete(Integer codigo) {
		find(codigo);
		try {
			repo.deleteById(codigo);
		}
		catch(DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir porque há pedidos relacionados");
		}
	}
	public List<Cliente> findAll(){
		return repo.findAll();
	}
	
	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
		
	}
	public Cliente fromDTO(ClienteDTO objDto) {
		return new Cliente(objDto.getCodigo(), objDto.getNome(), objDto.getEmail(), null, null, null);
	}
	
	public Cliente fromDTO(ClienteNewDTO objDto) {
		Cliente cli = new Cliente(null, objDto.getNome(), objDto.getEmail(), objDto.getCpfOuCnpj(), TipoCliente.toEnum(objDto.getTipoCliente()), pe.encode(objDto.getSenha()));
		Cidade cid = new Cidade(objDto.getCidadeCodigo(), null, null);
		Endereco end = new Endereco(null, objDto.getLogradouro(), objDto.getNumero(), objDto.getComplemento(), objDto.getBairro(), objDto.getCep(), cli, cid);
		cli.getEnderecos().add(end);
		cli.getTelefones().add(objDto.getTelefone1());
		if(objDto.getTelefone2()!=null) {
			cli.getTelefones().add(objDto.getTelefone2());
		}
		if(objDto.getTelefone3()!=null) {
			cli.getTelefones().add(objDto.getTelefone3());
		}
		return cli;
	}
	
	private void updateData(Cliente newObj, Cliente obj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
	}
	
	public URI uploadProfilePicture(MultipartFile multipartFile) {
		UserSS user = UserService.authenticated();
		if(user == null) {
			throw new AuthorizationException("Acesso negado");
		}
		
		BufferedImage jpgImage = imageService.getJpgImageFromFile(multipartFile);
		jpgImage = imageService.cropSquare(jpgImage);
		jpgImage = imageService.resize(jpgImage, size);
		String fileName = prefix + user.getCodigo() +".jpg";
		return s3Service.uploadFile(imageService.getInputStream(jpgImage, "jpg"), fileName, "image");
	}
}
