package br.com.ProjetoPessoal.API.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.ProjetoPessoal.API.models.Annotation;

@Repository
public interface AnnotationRepository extends JpaRepository<Annotation, Long> {
	
}
