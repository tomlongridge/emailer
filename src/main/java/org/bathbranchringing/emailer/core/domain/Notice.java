package org.bathbranchringing.emailer.core.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

@Entity
@Table
@Inheritance(strategy = InheritanceType.JOINED)
public class Notice {

    @Column(name = "noticeId")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, length = 512)
	private String heading;
	
	@Column(nullable = false)
	@Type(type="text")
	private String content;
    
    @Column(length = 512)
    private String link;
	
	@ManyToOne
	@JoinColumn(name = "board")
	private Board board;
	
	@ManyToOne
	@JoinColumn(name = "createdBy")
	private User createdBy;
	
	@Column(nullable = false)
	private Date creationDate;
	
	@ManyToOne
	@JoinColumn(name = "lastModifiedBy")
	private User lastModifiedBy;

	@Column(nullable = false)
	private Date modificationDate;

	public Long getId() {
		return id;
	}

	public String getHeading() {
		return heading;
	}

	public String getContent() {
		return content;
	}
	
	public Board getBoard() {
		return board;
	}

	public User getCreatedBy() {
		return createdBy;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public User getLastModifiedBy() {
		return lastModifiedBy;
	}

	public Date getModificationDate() {
		return modificationDate;
	}

    public void setId(Long id) {
        this.id = id;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public void setLastModifiedBy(User lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public void setModificationDate(Date modificationDate) {
        this.modificationDate = modificationDate;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
