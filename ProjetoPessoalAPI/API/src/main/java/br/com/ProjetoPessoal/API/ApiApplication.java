package br.com.ProjetoPessoal.API;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.ProjetoPessoal.API.models.User;
import br.com.ProjetoPessoal.API.repository.RoleRepository;
import br.com.ProjetoPessoal.API.repository.UserRepository;
import br.com.ProjetoPessoal.API.enums.RolesEnum;

@SpringBootApplication
@RestController
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
	
	public void generateAndDisplayPassword() {
		System.out.println("\n\n-----------------------------------");
		System.out.println("Password: " + new BCryptPasswordEncoder().encode("@Senha123"));
		System.out.println("-----------------------------------\n\n");
		
		return;
	} 
	
	public void generateDefaultUser() {
		User user = new User(
			"admin",
			"@Senha123",
			"00000000000",
			Arrays.asList(roleRepository.findByRole(RolesEnum.valueOf("ROLE_ADMIN")))
		);
		
		userRepository.save(user);
	}

	@Override
	public void run(String... args) throws Exception {
		this.generateAndDisplayPassword();
		this.generateDefaultUser();
	}
}
