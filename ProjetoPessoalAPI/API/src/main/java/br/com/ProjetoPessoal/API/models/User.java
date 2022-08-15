package br.com.ProjetoPessoal.API.models;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String password;
	private String cpf;
	private LocalDateTime registerDate;
	
	public User() {}
	
	public User(
		String name,
		String password,
		String cpf
	) {
		this.setName(name);
		this.setPassword(password);
		this.setCpf(cpf);
		this.setRegisterDate(LocalDateTime.now());
	}
	
	public Long getId() {
		return this.id;
	}
	public String getName() {
		return this.name;
	}
	public String getPassword() {
		return this.password;
	}
	public String getCpf() {
		return this.cpf;
	}
	public LocalDateTime getRegisterDate() {
		return this.registerDate;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setName(String name) { 
		this.name = name;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public void setRegisterDate(LocalDateTime registerDate) {
		this.registerDate = registerDate;
	}
	
	public String toString() {
		return "\nName:" +
		this.getName() +
		"\nCPF: " + 
		this.getCpf() + 
		"\nRegistered at: " + 
		this.getRegisterDate() + 
		"\n";
	}
}