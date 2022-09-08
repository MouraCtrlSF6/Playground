package br.com.GerenciamentoDeUsuarios.API.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.GerenciamentoDeUsuarios.API.controller.dto.RoleDto;
import br.com.GerenciamentoDeUsuarios.API.models.Role;
import br.com.GerenciamentoDeUsuarios.API.repository.RoleRepository;

@RestController
@RequestMapping("/roles")
public class RoleController {
	
	@Autowired
	private RoleRepository roleRepository;

	@GetMapping
	public ResponseEntity<Page<RoleDto>> list(
		@PageableDefault(
			page = 0,
			size = 10,
			sort = "id",
			direction = Direction.ASC
		) Pageable pagination
	) {
		final Page<Role> roles = roleRepository.findAll(pagination);
		
		return ResponseEntity
			.ok()
			.body(RoleDto.convert(roles));
	}
}
