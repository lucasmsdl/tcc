package br.com.puc.eletro.dto;

import java.io.Serializable;

import br.com.puc.eletro.domain.Estado;

public class EstadoDTO implements Serializable{
	private static final long serialVersionUID = 1L;

	private Integer codigo;
	private String nome;
	
	public EstadoDTO() {
		
	}

	public EstadoDTO(Estado obj) {
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
