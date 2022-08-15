package br.com.alura.gerenciador.servlet;

import java.util.ArrayList;
import java.util.List;

public class Database {
	public static Integer id = 0;
	private static List<Enterprise> enterprises = new ArrayList<>();
	
	
	public static void register(Enterprise enterprise) {
		enterprise.setId(id);
		enterprises.add(enterprise);
		
		id++;
	}
	
	public static List<Enterprise> getEnterprises() {
		return enterprises;
	}
}
