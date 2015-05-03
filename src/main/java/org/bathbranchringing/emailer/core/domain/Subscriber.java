package org.bathbranchringing.emailer.core.domain;

import java.util.Date;

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
public class Subscriber {

    @Column(name = "subscriptionId")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@ManyToOne
	@JoinColumn(name = "userId")
	private User user;
    
    @ManyToOne
    @JoinColumn(name = "boardId")
    private Board board;
    
    @Column
    private Date subscribed;

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

    public Board getBoard() {
        return board;
    }

    public Date getSubscribed() {
        return subscribed;
    }

    public void setSubscribed(Date subscribed) {
        this.subscribed = subscribed;
    }
    
}
