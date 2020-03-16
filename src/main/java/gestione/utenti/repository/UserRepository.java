package gestione.utenti.repository;

import gestione.utenti.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

}