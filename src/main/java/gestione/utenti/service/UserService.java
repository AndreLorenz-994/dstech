package gestione.utenti.service;

import java.util.HashSet;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import gestione.utenti.model.User;
import gestione.utenti.model.Image;
import gestione.utenti.model.Role;
import gestione.utenti.repository.RoleRepository;
import gestione.utenti.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	public User findUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	public User findUserByUserName(String username) {
		return userRepository.findByUsername(username);
	}

	public User saveAdmin(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		user.setActive(true);
		Role userRole = roleRepository.findByRole("ADMIN");
		user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
		return userRepository.save(user);
	}
	
	public User updateUsername(User user, String username) {
		user.setUsername(username);
		return userRepository.save(user);
	}	
	
	public User updateBirthday(User user, LocalDate birthday) {
		user.setDateOfBirth(birthday);
		return userRepository.save(user);
	}

	public void uploadImage(User user, Image image) {
		// TODO Auto-generated method stub
		user.setImage(image);
		userRepository.save(user);
	}	

}
