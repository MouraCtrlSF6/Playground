package br.com.alura.forum.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import br.com.alura.forum.modelo.Curso;

@Component
public interface CursoRepository extends JpaRepository<Curso, Long>{
	List<Curso> findByNome(String nome);
}
