package br.com.alura.forum.modelo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
public class Curso {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	private String categoria;
	
	public Curso(
		String nome,
		String categoria
	) {
		this.setNome(nome);
		this.setCategoria(categoria);
	}
	
	public Curso() {
		this("", "");
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
		
		Curso other = (Curso) obj;
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
	
	public String getCategoria() {
		return this.categoria;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	
}
