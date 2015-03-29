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
public class Tower {

	@Column(name = "towerId")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(length = 100)
	private String dedication;

	@Column(length = 100)
	private String area;

	@Column(nullable = false, length = 100)
	private String town;
	
	@ManyToOne
	@JoinColumn(name = "countyId")
	private County county;

	public long getId() {
		return id;
	}

	public String getDedication() {
		return dedication;
	}

	public String getArea() {
		return area;
	}

	public String getTown() {
		return town;
	}

	public County getCounty() {
		return county;
	}

}
