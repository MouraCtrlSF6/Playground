package br.com.alura.forum.controller;

import java.net.URI;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.alura.forum.controller.dto.DetalhesTopicoDto;
import br.com.alura.forum.controller.dto.TopicoDto;
import br.com.alura.forum.controller.form.AtualizaTopicoForm;
import br.com.alura.forum.controller.form.TopicoForm;
import br.com.alura.forum.modelo.Topico;
import br.com.alura.forum.repository.CursoRepository;
import br.com.alura.forum.repository.TopicoRepository;
import br.com.alura.forum.repository.UsuarioRepository;
import br.com.alura.forum.utils.ControllerHelper;

// @Controller -> Necessario adicionar a anotação @ResponseBody em cada método
@RestController
@RequestMapping("/topicos")
public class TopicosController {
	
	 @Autowired
	private TopicoRepository repository;
	 
	@Autowired
	private CursoRepository cursoRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@GetMapping
	@Cacheable(value = "listaDeTopicos")
	public Page<TopicoDto> lista(
		@RequestParam(required = false) String cursoNome, 
		@PageableDefault(sort = "id", direction = Direction.DESC) Pageable paginacao
	) {
		Page<Topico> topicos = cursoNome == null
			? repository.findAll(paginacao)
			: repository.findByCurso_Nome(cursoNome, paginacao);
		
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
	@CacheEvict(value = "listaDeTopicos", allEntries = true)
	public ResponseEntity<TopicoDto> registra(@RequestBody @Valid TopicoForm form, UriComponentsBuilder uriBuilder) {
		try {
			Topico novoTopico = repository.save(form.toModel(cursoRepository, usuarioRepository));
			
			URI uri = uriBuilder
				.path("/topicos/{id}")
				.buildAndExpand(novoTopico.getId())
				.toUri();
			
			return ResponseEntity
				.created(uri)
				.body(TopicoDto.converter(novoTopico));
		} catch(Exception e) {
			return ResponseEntity
				.badRequest()
				.build();
		}
	}
	
	@PutMapping("/{id}")
	@Transactional
	@CacheEvict(value = "listaDeTopicos", allEntries = true)
	public ResponseEntity<TopicoDto> atualiza(@PathVariable Long id, @RequestBody @Valid AtualizaTopicoForm form) {
		if(!ControllerHelper.exists(repository, id)) {
			return ResponseEntity.notFound().build();
		}
		
		TopicoDto topicoAtualizado = TopicoDto.converter(form.update(id, repository));
		return ResponseEntity.ok(topicoAtualizado);
	}
	
	@DeleteMapping("/{id}")
	@Transactional
	@CacheEvict(value = "listaDeTopicos", allEntries = true)
	public ResponseEntity<?> remover(@PathVariable Long id) {
		if(!ControllerHelper.exists(repository, id)) {
			return ResponseEntity.notFound().build();
		}
		
		repository.deleteById(id);
		
		return ResponseEntity.ok().build();
	}
}
