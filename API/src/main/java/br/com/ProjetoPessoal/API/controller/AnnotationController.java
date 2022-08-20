package br.com.ProjetoPessoal.API.controller;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.ProjetoPessoal.API.controller.form.AnnotationForm;
import br.com.ProjetoPessoal.API.models.Annotation;
import br.com.ProjetoPessoal.API.models.User;
import br.com.ProjetoPessoal.API.repository.AnnotationRepository;
import br.com.ProjetoPessoal.API.repository.UserRepository;
import br.com.ProjetoPessoal.API.util.ControllerUtils;

@RestController
@RequestMapping("/annotations")
public class AnnotationController {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private AnnotationRepository annotationRepository;
	
	@PostMapping
	@Transactional
	public ResponseEntity<Object> register(@RequestBody @Valid AnnotationForm form) {
		try {
			if(!ControllerUtils.exists(userRepository, form.getUser_id())) {
				return ResponseEntity
					.status(HttpStatus.NOT_FOUND)
					.body("Error: user with id " + form.getUser_id() + " was not found.");
			}
			
			final User user = userRepository.getReferenceById(form.getUser_id());
			Annotation annotation = AnnotationForm.toModel(form);
			
			annotation.setUser(user);
			annotation = annotationRepository.save(annotation);
			
			return ResponseEntity
				.status(HttpStatus.OK)
				.body(annotation);
		} catch(Exception e) {			
			return ResponseEntity
				.status(HttpStatus.BAD_REQUEST)
				.body("Error: " + e.getMessage());
		}
	}
}
