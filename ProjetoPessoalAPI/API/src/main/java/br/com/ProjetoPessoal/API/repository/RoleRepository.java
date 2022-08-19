package br.com.ProjetoPessoal.API.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.ProjetoPessoal.API.enums.RolesEnum;
import br.com.ProjetoPessoal.API.models.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long>{
	Role findByRole(RolesEnum role);
}
