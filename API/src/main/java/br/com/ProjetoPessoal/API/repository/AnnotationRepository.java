package br.com.ProjetoPessoal.API.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.ProjetoPessoal.API.models.Annotation;

@Repository
public interface AnnotationRepository extends JpaRepository<Annotation, Long> {
  List<Annotation> findByUser_id(Long id);

  List<Annotation> findByUser_name(String name);

  Page<Annotation> findByUser_id(Long id, Pageable pagination);

  Page<Annotation> findByUser_name(String name, Pageable pagination);
}
