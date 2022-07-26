package br.com.alura.gerenciador.servlet;

public class Enterprise {
	private String name;
	private Integer id;
	
	public Enterprise(
			String name
	) {
		this.setName(name);
	}
	
	public String getName() {
		return this.name;
	}

	public Integer getId() {
		return this.id;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String toString() {
		return this.getName();
	}
}
