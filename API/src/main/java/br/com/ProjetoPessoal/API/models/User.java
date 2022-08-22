package br.com.ProjetoPessoal.API.models;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Entity
@Table(name="TB_USERS")
public class User implements Serializable, UserDetails {

	private static final long serialVersionUID = 1L;
	
	// Columns
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="user_id")
	private Long id;
	
	@Column(name="name", nullable = false, unique = true)
	private String name;
	
	@Column(name="password", nullable = false)
	private String password;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
		name = "TB_USERS_ROLES",
		joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "user_id"),
		inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "role_id")
	)
	private List<Role> roles;
	
	@Column(name="cpf", nullable = false, unique = true)
	private String cpf;
	
	@Column(name="registered_at")
	private LocalDateTime registerDate; 
	
	@Column(name="updated_at")
	private LocalDateTime updatedAt;
	
	@Column(name="last_access")
	private LocalDateTime lastAccessAt;
	
	@OneToMany(mappedBy = "user")
	private List<Annotation> annotations;

	// Constructors
	public User() {}
	
	public User(
		String name,
		String password, 
		String cpf,
		List<Role> roles
	) {
		this.setName(name);
		this.setPassword(password);
		this.setCpf(cpf);
		this.setRegisterDate(LocalDateTime.now());
		this.setUpdatedAt(LocalDateTime.now());
		this.setLastAccessAt(LocalDateTime.now());
		this.setRoles(roles);
	}
	
	// Getters
	public Long getId() {
		return this.id;
	}
	
	public String getName() {
		return this.name;
	}

	public String getCpf() {
		return this.cpf;
	}
	
	public List<Role> getRoles() {
		return this.roles;
	}
	
	public LocalDateTime getRegisterDate() {
		return this.registerDate;
	}
	
	public LocalDateTime getUpdatedAt() {
		return this.updatedAt;
	}
	
	public LocalDateTime getLastAccessAt() {
		return this.lastAccessAt;
	}
	
	// Setters
	public void setId(Long id) {
		this.id = id;
	}
	
	public void setName(String name) { 
		this.name = name;
	}
	
	public void setPassword(String password) {
		this.password = new BCryptPasswordEncoder().encode(password);
	}
	
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	
	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	
	public void setRegisterDate(LocalDateTime registerDate) {
		this.registerDate = registerDate;
	}
	
	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}
	
	public List<Annotation> getAnnotations() {
		return annotations;
	}
	
	public void setAnnotations(List<Annotation> annotations) {
		this.annotations = annotations;
	}
	
	public void setLastAccessAt(LocalDateTime lastAccessAt) {
		this.lastAccessAt = lastAccessAt;
	}
	
	// Overrides
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		List<GrantedAuthority> authorities = new ArrayList<>();
	 
		this.getRoles().forEach(role -> {
			authorities.add(new SimpleGrantedAuthority(role.getRole().toString()));
		});
		
		return authorities;
	}

	@Override
	public String getUsername() {
		return this.getName();
	}
	
	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}