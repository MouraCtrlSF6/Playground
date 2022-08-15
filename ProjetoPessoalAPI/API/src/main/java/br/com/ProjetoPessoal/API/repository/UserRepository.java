package br.com.ProjetoPessoal.API.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.ProjetoPessoal.API.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	List<User> findByName(String name);
}	
