package br.com.puc.eletro.dto;

import java.io.Serializable;

import br.com.puc.eletro.domain.Categoria;

public class CategoriaDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Integer codigo;
	private String nome;
	
	public CategoriaDTO(){
		
	}
	
	public CategoriaDTO(Categoria obj) {
		
		codigo = obj.getCodigo();
		nome = obj.getNome();
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	
	
}
