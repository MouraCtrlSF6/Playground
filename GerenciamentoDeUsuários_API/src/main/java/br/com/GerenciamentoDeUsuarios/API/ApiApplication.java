package br.com.GerenciamentoDeUsuarios.API;

import java.util.Arrays;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.GerenciamentoDeUsuarios.API.enums.RolesEnum;
import br.com.GerenciamentoDeUsuarios.API.models.User;
import br.com.GerenciamentoDeUsuarios.API.repository.RoleRepository;
import br.com.GerenciamentoDeUsuarios.API.repository.UserRepository;

@SpringBootApplication
@RestController
@EnableWebSecurity
public class ApiApplication implements CommandLineRunner {
	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(ApiApplication.class, args);
	}
	
	@GetMapping("/")
	public String index() {
		return "Hello, world!";
	}

	@Override
	public void run(String... args) throws Exception { 
		generateDefaultUser();
	} 
	
	@Transactional
	public void generateDefaultUser() {
		final User user = new User(
			"admin",
			"admin",
			"00000000000",
			Arrays.asList(roleRepository.findByRole(RolesEnum.valueOf("ROLE_ADMIN")))
		);
		
		userRepository.save(user);
	}
}
