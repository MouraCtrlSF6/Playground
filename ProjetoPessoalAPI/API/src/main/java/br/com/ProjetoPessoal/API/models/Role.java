package br.com.ProjetoPessoal.API.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;

import br.com.ProjetoPessoal.API.enums.RolesEnum;

@Entity
@Table(name="TB_ROLES")
public class Role implements GrantedAuthority, Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="role_id")
	private Long id;
  
	@Enumerated(EnumType.STRING)
	@Column(name="role_name", nullable=false, unique=true)
	private RolesEnum role;
	
	public Role() {}
	
	public Role(RolesEnum role) {
		this.setRole(role);
	}
	
	@Override
	public String getAuthority() {
		return this.getRole().toString();
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public RolesEnum getRole() {
		return role;
	}

	public void setRole(RolesEnum role) {
		this.role = role;
	}
}
