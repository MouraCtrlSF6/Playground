package br.com.alura.forum.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.alura.forum.controller.dto.CursoDto;
import br.com.alura.forum.modelo.Curso;
import br.com.alura.forum.repository.CursoRepository;

@RestController
@RequestMapping("/cursos")
public class CursoController {
	
	@Autowired
	private CursoRepository repository;
	
	@GetMapping
	public List<CursoDto> lista(String cursoNome) {
		List<Curso> cursos = cursoNome == null
			? repository.findAll()
			: repository.findByNome(cursoNome);
		
		return CursoDto.converte(cursos);
	}
}
