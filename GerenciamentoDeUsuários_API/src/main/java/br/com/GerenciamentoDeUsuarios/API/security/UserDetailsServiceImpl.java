package br.com.GerenciamentoDeUsuarios.API.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.GerenciamentoDeUsuarios.API.models.User;
import br.com.GerenciamentoDeUsuarios.API.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) 
	throws UsernameNotFoundException {
		User user = userRepository.findByName(username);
		
		if(user == null) {
			throw new UsernameNotFoundException("User with name " + username + " was not found!");
		}
		
		return user;
	}
}
