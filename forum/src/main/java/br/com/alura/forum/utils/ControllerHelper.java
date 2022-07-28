package br.com.alura.forum.utils;

import java.util.Optional;

import br.com.alura.forum.modelo.Curso;
import br.com.alura.forum.modelo.Topico;
import br.com.alura.forum.repository.CursoRepository;
import br.com.alura.forum.repository.TopicoRepository;

public class ControllerHelper {
	public static Boolean exists(TopicoRepository repository, Long id) {
		try {
			Optional<Topico> presenceList = repository.findById(id);
			
			return presenceList.isPresent();
			
		} catch(Exception e) {
			return false;
		}
		
	}
	
	public static Boolean exists(CursoRepository repository, Long id) {
		try {
			Optional<Curso> presenceList = repository.findById(id);
			
			return presenceList.isPresent();
			
		} catch(Exception e) {
			return false;
		}
		
	}
}
