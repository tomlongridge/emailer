package org.bathbranchringing.emailer.core.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table
public class UserRole {

	@Column(name = "userRoleId")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@ManyToOne
	@JoinColumn(name = "userId")
	private User user;
	
	@Column(nullable = false, length = 50)
	private String role;

    public long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public String getRole() {
        return role;
    }
	
}
