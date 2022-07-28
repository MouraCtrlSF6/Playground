package br.com.alura.forum.controller;

import java.net.URI;
import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.alura.forum.controller.dto.DetalhesTopicoDto;
import br.com.alura.forum.controller.dto.TopicoDto;
import br.com.alura.forum.controller.form.AtualizaTopicoForm;
import br.com.alura.forum.controller.form.TopicoForm;
import br.com.alura.forum.modelo.Topico;
import br.com.alura.forum.repository.CursoRepository;
import br.com.alura.forum.repository.TopicoRepository;
import br.com.alura.forum.utils.ControllerHelper;

// @Controller -> Necessario adicionar a anotação @ResponseBody em cada método
@RestController
@RequestMapping("/topicos")
public class TopicosController {
	
	 @Autowired
	private TopicoRepository repository;
	 
	@Autowired
	private CursoRepository cursoRepository;
	
	@GetMapping
	public List<TopicoDto> lista(String cursoNome) {
		System.out.println("Repositorio usado: " + repository);
		
		List<Topico> topicos = cursoNome == null
			? repository.findAll()
			: repository.findByCurso_Nome(cursoNome);
		
		return TopicoDto.converter(topicos);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<DetalhesTopicoDto> encontra(@PathVariable Long id) {
		if(!ControllerHelper.exists(repository, id)) {
			return ResponseEntity.notFound().build();
		}
		Topico topico = repository.getReferenceById(id);
		return ResponseEntity.ok().body(new DetalhesTopicoDto(topico));
		
	}
	
	@PostMapping
	@Transactional
	public ResponseEntity<TopicoDto> registra(@RequestBody @Valid TopicoForm form, UriComponentsBuilder uriBuilder) {
		Topico novoTopico = repository.save(form.toModel(cursoRepository));
		
		URI uri = uriBuilder
			.path("/topicos/{id}")
			.buildAndExpand(novoTopico.getId())
			.toUri();
		
		return ResponseEntity
			.created(uri)
			.body(TopicoDto.converter(novoTopico));
	}
	
	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity<TopicoDto> atualiza(@PathVariable Long id, @RequestBody @Valid AtualizaTopicoForm form) {
		if(!ControllerHelper.exists(repository, id)) {
			return ResponseEntity.notFound().build();
		}
		
		TopicoDto topicoAtualizado = TopicoDto.converter(form.update(id, repository));
		return ResponseEntity.ok(topicoAtualizado);
	}
	
	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<?> remover(@PathVariable Long id) {
		System.out.println("Entrei no método!");
		if(!ControllerHelper.exists(repository, id)) {
			return ResponseEntity.notFound().build();
		}
		
		repository.deleteById(id);
		
		return ResponseEntity.ok().build();
	}
}
