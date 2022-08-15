package br.com.alura.forum.modelo;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Usuario {
	
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	private String email;
	private String senha;
	private LocalDateTime registeredAt;
	
	@OneToMany(mappedBy = "autor")
	private List<Topico> topicos;
	
	public Usuario() {}
	
	public Usuario(
		String nome,
		String email,
		String senha
	) {
		this.setNome(nome);
		this.setEmail(email);
		this.setSenha(senha);
		this.setRegisteredAt(LocalDateTime.now());
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.getId() == null) ? 0 : this.getId().hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(super.equals(obj)) {
			return true;
		}
		if(obj == null) {
			return false;
		}
		if(this.getClass() != obj.getClass()) {
			return false;
		}
		
		Usuario other = (Usuario) obj;
		if((this.getId() == null && other.getId() != null) 
		|| this.getId().equals(other.getId())) {
			return false;
		}
		
		return true;
	}
	
	public Long getId() {
		return this.id;
	}
	
	public String getNome() {
		return this.nome;
	}
	
	public String getEmail() {
		return this.email;
	}
	
	public String getSenha() {
		return this.senha;
	}
	
	public LocalDateTime getRegisteredAt() {
		return this.registeredAt;
	}
	
	public List<Topico> getTopicos() {
		return this.topicos;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	public void setRegisteredAt(LocalDateTime registeredAt) {
		this.registeredAt = registeredAt;
	}
	
	public void setTopicos(List<Topico> topicos) {
		this.topicos = topicos;
	}
}
