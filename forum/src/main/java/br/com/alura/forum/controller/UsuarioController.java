package br.com.alura.forum.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.alura.forum.controller.dto.DetalhesUsuarioDto;
import br.com.alura.forum.controller.dto.UsuarioDto;
import br.com.alura.forum.modelo.Usuario;
import br.com.alura.forum.repository.UsuarioRepository;
import br.com.alura.forum.utils.ControllerHelper;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

	@Autowired
	private UsuarioRepository repository;

	@GetMapping
	public List<UsuarioDto> findAll(String nome) {
		List<Usuario> usuarios = nome == null
			? repository.findAll()
			: repository.findByNome(nome);
		
		return UsuarioDto.converter(usuarios);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<DetalhesUsuarioDto> findOne(@PathVariable Long id) {		
		if(!ControllerHelper.exists(repository, id)) {
			return ResponseEntity
				.notFound()
				.build();
		}
		
		Usuario usuario = repository.getReferenceById(id);
		return ResponseEntity
			.ok()
			.body(new DetalhesUsuarioDto(usuario));
	}
}
