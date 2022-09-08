package br.com.GerenciamentoDeUsuarios.API.validators.User;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import br.com.GerenciamentoDeUsuarios.API.models.User;
import br.com.GerenciamentoDeUsuarios.API.repository.UserRepository;

public class UserUpdateValidator {
	
	private String oldPassword;
	
	private String newPassword;
	
	public String getOldPassword() {
		return this.oldPassword;
	}
	
	public String getNewPassword() {
		return this.newPassword;
	}
	
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}
	
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	
	public User update(UserRepository userRepository, Long id) {
		User user = userRepository.getReferenceById(id);
		return user;
	}
	
	public void updatePassword(UserRepository userRepository, Long id) throws Exception {		
		User user = userRepository.getReferenceById(id);
		
		String userPassword = user.getPassword();
		
		Boolean samePassword = new BCryptPasswordEncoder().matches(this.getOldPassword(), userPassword);
		
		if(!samePassword) {
			throw new Exception("Wrong old password provided");
		}
		
		if(this.getNewPassword().length() < 8 || this.getNewPassword().length() > 12) {
			throw new Exception("New password length must be between 8 and 12 characters");
		}
		
		user.setPassword(this.getNewPassword());
		
		return;
	}
}
