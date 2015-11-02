package org.bathbranchringing.emailer.core.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(
        uniqueConstraints =
            @UniqueConstraint(columnNames = {"name","country"}, name = "UniqueCountyInCountry")
)
public class County {

	@Column(name = "countyId")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(nullable = false, length = 100)
	private String name;

	@ManyToOne
	@JoinColumn(name = "country")
	private Country country;
	
	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Country getCountry() {
		return country;
	}

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

}
