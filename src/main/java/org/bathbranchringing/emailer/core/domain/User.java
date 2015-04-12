package org.bathbranchringing.emailer.core.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class User {

	@Column(name = "userId")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(nullable = false, length = 100)
	private String firstName;
	
	@Column(nullable = false, length = 100)
	private String surname;
	
	@Column(nullable = false, length = 150, unique = true)
	private String emailAddress;
	
	@Column(nullable = false, length = 50)
	private String password;
	
	@Column
	private boolean enabled;
	
	public long getId() {
		return id;
	}

	public String getFirstName() {
		return firstName;
	}
	
	public String getSurname() {
		return surname;
	}

	public String getEmailAddress() {
		return emailAddress;
	}
	
	public String getPassword() {
        return password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public String getDisplayName() {
		return String.format("%s %s", firstName, surname);
	}
}
