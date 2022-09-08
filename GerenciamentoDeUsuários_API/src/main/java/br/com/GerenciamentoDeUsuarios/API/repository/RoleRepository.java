package br.com.GerenciamentoDeUsuarios.API.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.GerenciamentoDeUsuarios.API.enums.RolesEnum;
import br.com.GerenciamentoDeUsuarios.API.models.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long>{
	Role findByRole(RolesEnum role);
}
