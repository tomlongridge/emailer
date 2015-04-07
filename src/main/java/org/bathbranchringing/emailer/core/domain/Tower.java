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
	
	@Column(length = 20)
	private String doveId;
	
	@Column(length = 100)
	private String dedication;

	@Column(length = 100)
	private String area;

	@Column(nullable = false, length = 100)
	private String town;
	
	@ManyToOne
	@JoinColumn(name = "countyId")
	private County county;
	
	@Column(nullable = false)
	private short numBells;
	
	@Column(nullable = false)
	private short tenorWeightCwt;
	
	@Column(nullable = false)
	private short tenorWeightQtrs;
	
	@Column(nullable = false)
	private short tenorWeightLbs;

	public long getId() {
		return id;
	}

	public String getDoveId() {
		return doveId;
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
	
	public short getNumBells() {
		return numBells;
	}

	public short getTenorWeightCwt() {
		return tenorWeightCwt;
	}

	public short getTenorWeightQtrs() {
		return tenorWeightQtrs;
	}

	public short getTenorWeightLbs() {
		return tenorWeightLbs;
	}

	public String getDisplayName() {
		StringBuilder name = new StringBuilder();
		if (dedication != null) {
			name.append(dedication);
			name.append(", "); 
		}
		if (area != null) {
			name.append(area);
			name.append(", "); 
		}
		name.append(town);
		return name.toString();
	}
	
	public String getTenorWeight() {
		if ((tenorWeightLbs == 0) && (tenorWeightQtrs == 0)) {
			return tenorWeightCwt + " cwt";
		} else {
			return String.format("%d-%d-%d", tenorWeightCwt, tenorWeightQtrs, tenorWeightLbs);
		}
	}

}
