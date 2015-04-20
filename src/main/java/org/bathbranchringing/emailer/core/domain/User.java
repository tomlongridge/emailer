package org.bathbranchringing.emailer.core.domain;

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

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table
public class User implements UserDetails {

    private static final long serialVersionUID = -8767822713228805716L;

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
	
	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
	private List<UserRole> roles;
	
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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getUsername() {
        return emailAddress;
    }

    @Override
    public boolean isAccountNonExpired() {
        return enabled;
    }

    @Override
    public boolean isAccountNonLocked() {
        return enabled;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return enabled;
    }
}
