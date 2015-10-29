package org.bathbranchringing.emailer.core.domain;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
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
	@NotBlank(message = "You must provide your first name")
	private String firstName;
	
	@Column(nullable = false, length = 100)
	@NotBlank(message = "You must provide your surname")
	private String surname;
	
	@Column(nullable = false, length = 150, unique = true)
	@Email(message = "Invalid email address")
	@NotBlank(message = "An email address must be provided")
	private String emailAddress;
	
	@Column(nullable = false, length = 60)
    @Length(min = 6, message = "A password of at least {min} characters must be provided")
	private String password;
	
	@Transient
    private String confirmPassword;
    
    @Column(nullable = false)
    private Date creationDate;

    @Column(nullable = false)
    private Date modificationDate;

    @Column
	private boolean enabled;
    
    @OneToMany(mappedBy = "user")
    private List<UserRole> roles;
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Membership> membership;
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Subscriber> subscriptions;
	
	public List<Subscriber> getSubscriptions() {
        return subscriptions;
    }

    public void setSubscriptions(List<Subscriber> subscriptions) {
        this.subscriptions = subscriptions;
    }

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
	
	public List<UserRole> getRoles() {
        return roles;
    }

    public void setRoles(List<UserRole> roles) {
        this.roles = roles;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void setMembership(List<Membership> committee) {
        this.membership = committee;
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
    
    public List<Membership> getMembership() {
        return membership;
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
    
    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(Date modificationDate) {
        this.modificationDate = modificationDate;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
