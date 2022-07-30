package br.com.alura.gerenciador.servlet;

import java.util.Date;

public class Enterprise {
	private String name;
	private Integer id;
	private Date creationDate;
	
	public Enterprise(
		String name,
		Date creationDate
	) {
		this.setName(name);
		this.setCreationDate(creationDate);
	}
	
	public String getName() {
		return this.name;
	}

	public Integer getId() {
		return this.id;
	}
	
	public Date getCreationDate() {
		return this.creationDate;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	
	public String toString() {
		return this.getName();
	}
}
