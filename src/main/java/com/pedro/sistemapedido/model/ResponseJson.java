package com.pedro.sistemapedido.model;

public class ResponseJson {

	private String mensagem;
    private String observacao;
    
    public ResponseJson() {}
    
	public ResponseJson(String mensagem, String observacao) {
		super();
		this.mensagem = mensagem;
		this.observacao = observacao;
	}
	
	public String getMensagem() {
		return mensagem;
	}
	
	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
	
	public String getObservacao() {
		return observacao;
	}
	
	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
    
    
}
