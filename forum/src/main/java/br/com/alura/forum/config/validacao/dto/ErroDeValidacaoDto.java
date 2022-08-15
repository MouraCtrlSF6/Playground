package br.com.alura.forum.config.validacao.dto;

public class ErroDeValidacaoDto {
	
	private String mensagem;
	private String campo;
	
	public ErroDeValidacaoDto(
		String mensagem,
		String campo
	) {
		this.mensagem = mensagem;
		this.campo = campo;
	}
	
	public String getMensagem() {
		return mensagem;
	}
	public String getCampo() {
		return campo;
	}
	
}
