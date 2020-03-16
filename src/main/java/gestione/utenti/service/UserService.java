package gestione.utenti.service;

import java.util.List;

import gestione.utenti.model.User;

public interface UserService {
	List<User> findAll();

	Long count();

	void deleteById(Long userId);

	void add(User user);
}
