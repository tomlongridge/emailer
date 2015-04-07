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
	
	@Column(length = 30)
	private String middleInitials;

	@Column(nullable = false, length = 150)
	private String emailAddress;
	
	public long getId() {
		return id;
	}

	public String getFirstName() {
		return firstName;
	}
	
	public String getSurname() {
		return surname;
	}

	public String getMiddleInitials() {
		return middleInitials;
	}

	public String getEmailAddress() {
		return emailAddress;
	}
}
