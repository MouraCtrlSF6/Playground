package br.com.alura.forum.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {
	
	@RequestMapping("/") // Rota para acessar o método
	@ResponseBody // Devolve o retorno do método como body da response
	public String hello() {
		return "Hello World!";
	}
	
	
}
