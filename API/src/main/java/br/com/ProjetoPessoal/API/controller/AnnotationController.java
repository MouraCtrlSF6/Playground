package br.com.ProjetoPessoal.API.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.ProjetoPessoal.API.controller.dto.AnnotationDto;
import br.com.ProjetoPessoal.API.controller.dto.DefaultJsonDto;
import br.com.ProjetoPessoal.API.models.Annotation;
import br.com.ProjetoPessoal.API.models.User;
import br.com.ProjetoPessoal.API.repository.AnnotationRepository;
import br.com.ProjetoPessoal.API.util.ArrayUtils;
import br.com.ProjetoPessoal.API.util.JsonUtils;
import br.com.ProjetoPessoal.API.util.JwtTokenUtils;
import br.com.ProjetoPessoal.API.validators.Annotation.*;

@RestController
@RequestMapping("/annotations")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AnnotationController {

	@Autowired
	private AnnotationRepository annotationRepository;

	@Autowired
	private JwtTokenUtils jwtTokenUtils;
	
	@GetMapping
	public ResponseEntity<Object> list(
		@RequestParam(required = false) Long user_id,
		@RequestParam(required = false) String user_name,
		@PageableDefault(
			page = 0,
			size = 10,
			sort = "user_id", 
			direction = Direction.ASC
		) Pageable pagination
	) throws IOException {
		try {

			final Page<Annotation> annotations;

			if(user_id != null) {
				annotations = annotationRepository.findByUser_id(user_id, pagination);
			} else if (user_name != null) {
				annotations = annotationRepository.findByUser_name(user_name, pagination);
			} else {
				annotations = annotationRepository.findAll(pagination);
			}

			final Page<AnnotationDto> payload = AnnotationDto.convert(annotations);

			return ResponseEntity
				.status(HttpStatus.OK)
				.body(payload);

		} catch (Exception e) {
			String payload = DefaultJsonDto
				.generateJsonString("Error", e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
			
			return ResponseEntity
				.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(JsonUtils.parse(payload));
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<Object> find(
		@PathVariable Long id,
		@RequestHeader(HttpHeaders.AUTHORIZATION) String token
	) 
	throws IOException {
		try {
			token = jwtTokenUtils.formatToken(token);

			final User userFromToken = jwtTokenUtils.getUserFromToken(token);

			List<Annotation> userAnnotations = annotationRepository.findByUser_id(userFromToken.getId());

			Annotation annotation = ArrayUtils.find(userAnnotations, 
				(item, index, array) -> item.getUserAnnotationId().equals(userFromToken.getId())
			);

			if(annotation == null) {
				throw new EntityNotFoundException("Annotation with id " + id + " was not found among your annotations.");
			}

			return ResponseEntity
				.status(HttpStatus.OK)
				.body(AnnotationDto.convert(annotation));
		} catch(EntityNotFoundException e) {
			String payload = DefaultJsonDto
				.generateJsonString("Error", e.getMessage(), HttpStatus.NOT_FOUND);

			return ResponseEntity
				.status(HttpStatus.NOT_FOUND)
				.body(JsonUtils.parse(payload));
		} catch(Exception e) {			
			String payload = DefaultJsonDto
				.generateJsonString("Error", e.getMessage(), HttpStatus.BAD_REQUEST);

			return ResponseEntity
				.status(HttpStatus.BAD_REQUEST)
				.body(JsonUtils.parse(payload));
		}
	}
	
	@PostMapping
	@Transactional
	public ResponseEntity<Object> register(
		@RequestBody @Valid AnnotationRegisterValidator form,
		@RequestHeader(HttpHeaders.AUTHORIZATION) String token
	) 
	throws IOException {
		try {
			token = jwtTokenUtils.formatToken(token);

			final User userFromToken = jwtTokenUtils.getUserFromToken(token);

			Annotation annotation = AnnotationRegisterValidator.toModel(form);
			
			annotation.setUser(userFromToken);

			annotation = annotationRepository.save(annotation);

			final AnnotationDto payload = AnnotationDto.convert(annotation);
			
			return ResponseEntity
				.status(HttpStatus.OK)
				.body(payload);
		} catch(Exception e) {			
			String payload = DefaultJsonDto
				.generateJsonString("Error", e.getMessage(), HttpStatus.BAD_REQUEST);

			return ResponseEntity
				.status(HttpStatus.BAD_REQUEST)
				.body(JsonUtils.parse(payload));
		}
	}

	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity<Object> update(
		@RequestHeader(HttpHeaders.AUTHORIZATION) String token,
		@PathVariable Long id,
		@RequestBody AnnotationRegisterValidator form
	) throws IOException {
		try {
			
			token = jwtTokenUtils.formatToken(token);

			final User userFromToken = jwtTokenUtils.getUserFromToken(token);

			final List<Annotation> userAnnotations = annotationRepository.findByUser_id(userFromToken.getId());

			final Annotation updatingAnnotation = ArrayUtils.find(userAnnotations, 
				(item, index, array) -> item.getUserAnnotationId().equals(id)
			);

			if(updatingAnnotation == null) {
				throw new EntityNotFoundException("Annotation with id " + id + " was not found among your annotations.");
			}

			updatingAnnotation.setContent(form.getContent());
			updatingAnnotation.setUpdatedAt(LocalDateTime.now());

			String payload = DefaultJsonDto
			.generateJsonString("Success", "Annotation " + id + " was successfully updated.", HttpStatus.OK);

			return ResponseEntity
				.status(HttpStatus.OK)
				.body(JsonUtils.parse(payload));

		} catch(EntityNotFoundException e) {
			String payload = DefaultJsonDto
				.generateJsonString("Error", e.getMessage(), HttpStatus.NOT_FOUND);

			return ResponseEntity
				.status(HttpStatus.NOT_FOUND)
				.body(JsonUtils.parse(payload));
		} catch (Exception e) {
			String payload = DefaultJsonDto
				.generateJsonString("Error", e.getMessage(), HttpStatus.BAD_REQUEST);

			return ResponseEntity
				.status(HttpStatus.BAD_REQUEST)
				.body(JsonUtils.parse(payload));
		}
	}

	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<Object> delete(
		@PathVariable Long id,
		@RequestHeader(HttpHeaders.AUTHORIZATION) String token
	) throws IOException {
		try {
			token = jwtTokenUtils.formatToken(token);

			final User userFromToken = jwtTokenUtils.getUserFromToken(token);

			final List<Annotation> userAnnotations = annotationRepository.findByUser_id(userFromToken.getId());

			final Annotation removingAnnotation = ArrayUtils.find(userAnnotations, 
				(item, index, array) -> item.getUserAnnotationId().equals(id)
			);

			if(removingAnnotation == null) {
				throw new EntityNotFoundException("Annotation with id " + id + " was not found among your annotations.");
			}

			annotationRepository.delete(removingAnnotation);
				
			String payload = DefaultJsonDto
				.generateJsonString("Success", "Annotation " + id + " was successfully removed from your annotations.", HttpStatus.OK);

			return ResponseEntity
				.status(HttpStatus.OK)
				.body(JsonUtils.parse(payload));
		} catch(EntityNotFoundException e) {
			String payload = DefaultJsonDto
				.generateJsonString("Error", e.getMessage(), HttpStatus.NOT_FOUND);

			return ResponseEntity
				.status(HttpStatus.NOT_FOUND)
				.body(JsonUtils.parse(payload));
		} catch (Exception e) {
			String payload = DefaultJsonDto
				.generateJsonString("Error", e.getMessage(), HttpStatus.BAD_REQUEST);

			return ResponseEntity
				.status(HttpStatus.BAD_REQUEST)
				.body(JsonUtils.parse(payload));
		}
	}

}
