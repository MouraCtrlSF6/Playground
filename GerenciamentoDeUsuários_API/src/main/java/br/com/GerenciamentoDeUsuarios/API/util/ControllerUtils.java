package br.com.GerenciamentoDeUsuarios.API.util;

import java.util.Optional;

import br.com.GerenciamentoDeUsuarios.API.models.User;
import br.com.GerenciamentoDeUsuarios.API.repository.UserRepository;

public interface ControllerUtils {
	static Boolean exists(UserRepository repository, Long id) {
		Optional<User> user = repository.findById(id);
		return user.isPresent();
	}
}	
