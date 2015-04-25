package org.bathbranchringing.emailer.core.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;

@Entity
@Table
public class CommitteeMember implements GrantedAuthority {

    private static final long serialVersionUID = -5296383611739147502L;

    @Column(name = "committeeMemberId")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@ManyToOne
	@JoinColumn(name = "userId")
	private User user;
    
    @ManyToOne
    @JoinColumn(name = "boardId")
    private Board board;
	
	@Column(nullable = false, length = 50)
	private String role;

    public long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }
    
    public Board getBoard() {
        return board;
    }

    public String getRole() {
        return role;
    }

    @Override
    public String getAuthority() {
        return role;
    }
	
}
