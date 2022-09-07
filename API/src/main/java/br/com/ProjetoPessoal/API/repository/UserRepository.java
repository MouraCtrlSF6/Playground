package br.com.ProjetoPessoal.API.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.ProjetoPessoal.API.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {	
	User findByName(String name);

	Page<User> findByName(String name, Pageable pagination);
}	
