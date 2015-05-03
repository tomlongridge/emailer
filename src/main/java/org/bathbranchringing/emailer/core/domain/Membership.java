package org.bathbranchringing.emailer.core.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table
public class Membership {

    @Column(name = "membershipId")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@ManyToOne
	@JoinColumn(name = "userId")
	private User user;
    
    @ManyToOne
    @JoinColumn(name = "boardId")
    private Board board;
    
    @Column(length = 50)
    private String role;

    @Column
    private Date joined;

    @OneToOne
    @JoinColumn(name = "approvedBy")
    private User approvedBy;
	
    public long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }
    
    public void setId(long id) {
        this.id = id;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public void setJoined(Date joined) {
        this.joined = joined;
    }

    public void setApprovedBy(User approvedBy) {
        this.approvedBy = approvedBy;
    }
    
    public User getApprovedBy() {
        return approvedBy;
    }
    
    public Date getJoined() {
        return joined;
    }

    public Board getBoard() {
        return board;
    }
    
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

}
