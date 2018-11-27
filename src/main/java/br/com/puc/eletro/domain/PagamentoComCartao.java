package br.com.puc.eletro.domain;

import javax.persistence.Entity;

import br.com.puc.eletro.domain.enums.EstadoPagamento;

@Entity
public class PagamentoComCartao extends Pagamento{

	private static final long serialVersionUID = 1L;
	
	private Integer numeroDeParcelas;
	
	public PagamentoComCartao() {
		
	}

	public PagamentoComCartao(Integer codigo, EstadoPagamento estado, Pedido pedido, Integer numeroDeParcelas) {
		super(codigo, estado, pedido);
		this.numeroDeParcelas = numeroDeParcelas;
	}

	public Integer getNumeroDeParcelas() {
		return numeroDeParcelas;
	}

	public void setNumeroDeParcelas(Integer numeroDeParcelas) {
		this.numeroDeParcelas = numeroDeParcelas;
	}
	
	
	
	
}
