package gestione.utenti.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.security.crypto.bcrypt.BCrypt;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String username;
	private String email;
	private String password;
	
	@JsonFormat(pattern = "yyyy-MM-dd") // date will be formatted in yyyy-MM-dd
	private LocalDate dateOfBirth;
	
	public User(String username, String email, String password, LocalDate dateOfBirth) {
		super();
		this.username = username;
		this.email = email;
		this.password = password;
		this.dateOfBirth = dateOfBirth;
	}
	
	public User() {}

	public String getPassword() {
		return password;
	}

    public void setPassword(String password, boolean salt) {
        if (salt) {
            this.password = BCrypt.hashpw(password, BCrypt.gensalt());
        } else {
            this.password = password;
        }
    }

	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}
	
	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}	
	
}
