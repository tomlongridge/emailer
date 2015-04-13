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

import org.hibernate.annotations.Type;

@Entity
@Table
public class Notice {

	@Column(name = "noticeId")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(nullable = false, length = 512)
	private String heading;
	
	@Column(nullable = false)
	@Type(type="text")
	private String content;
	
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

	public long getId() {
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
}
