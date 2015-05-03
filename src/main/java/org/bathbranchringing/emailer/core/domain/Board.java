package org.bathbranchringing.emailer.core.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

@Entity
@Table
@Inheritance(strategy = InheritanceType.JOINED)
public class Board {
    
    @Column(name = "boardId")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

    @Column(nullable = false, length = 50, unique = true)
    private String identifier;

	@ManyToMany
	@JoinTable(name = "Affiliates",
	           joinColumns = @JoinColumn(name = "affiliate"),
	           inverseJoinColumns = @JoinColumn(name = "towerGroup"))
	private List<Group> affiliatedTo;
    
    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("role ASC")
    private List<Membership> members;
    
    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Subscriber> subscribers;
    
    public Long getId() {
		return id;
	}
    
    public String getIdentifier() {
        return identifier;
    }
    
    public List<Group> getAffiliatedTo() {
        return affiliatedTo;
    }
    
    public void setId(Long id) {
        this.id = id;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public void setAffiliatedTo(List<Group> affiliatedTo) {
        this.affiliatedTo = affiliatedTo;
    }
    
    public boolean isGroup() {
        return false;
    }
    
    public String getDisplayName() {
        return identifier;
    }
    
    public List<Membership> getMembers() {
        return members;
    }
    
    public void setMembers(List<Membership> members) {
        this.members = members;
    }

    public List<Subscriber> getSubscribers() {
        return subscribers;
    }

    public void setSubscribers(List<Subscriber> subscribers) {
        this.subscribers = subscribers;
    }
    
    public boolean isSubscribed(final User user) {
        
        for (Subscriber s : subscribers) {
            if (s.getUser().getId() == user.getId()) {
                return true;
            }
        }
        return false;
        
    }
    
    public boolean isMember(final User user) {
        
        for (Membership m : members) {
            if ((m.getJoined() != null) && (m.getUser().getId() == user.getId())) {
                return true;
            }
        }
        return false;
        
    }

}
