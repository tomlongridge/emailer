package org.bathbranchringing.emailer.core.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(name = "boardId")
public class Group extends Board {
    
	@Column(nullable = false, length = 100)
	private String name;
	
	@ManyToMany(mappedBy = "affiliatedTo")
	private List<Board> affiliates;
	
	public String getName() {
		return name;
	}

	public List<Board> getAffiliates() {
        return affiliates;
    }
	
	public boolean isGroup() {
	    return true;
	}
	
	public String getDisplayName() {
	    return name;
	}
}
