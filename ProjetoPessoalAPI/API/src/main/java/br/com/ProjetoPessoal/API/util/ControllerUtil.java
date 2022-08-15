package br.com.ProjetoPessoal.API.util;

import java.util.Optional;

import br.com.ProjetoPessoal.API.models.User;
import br.com.ProjetoPessoal.API.repository.UserRepository;

public interface ControllerUtil {
	static Boolean exists(UserRepository repository, Long id) {
		Optional<User> user = repository.findById(id);
		return user.isPresent();
	}
}	
