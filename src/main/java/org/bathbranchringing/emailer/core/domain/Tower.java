package org.bathbranchringing.emailer.core.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table
@PrimaryKeyJoinColumn(name = "boardId")
public class Tower extends Board {

    @Column(length = 100)
    private String dedication;

    @Column(length = 100)
    private String area;

    @Column(nullable = false, length = 100)
    private String town;
    
    @ManyToOne
    @JoinColumn(name = "county")
    private County county;
    
    @Column(nullable = false)
    private short numBells;
    
    @Column(nullable = false)
    private short tenorWeightCwt;
    
    @Column(nullable = false)
    private short tenorWeightQtrs;
    
    @Column(nullable = false)
    private short tenorWeightLbs;

	public void setDedication(String dedication) {
        this.dedication = dedication;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public void setCounty(County county) {
        this.county = county;
    }

    public void setNumBells(short numBells) {
        this.numBells = numBells;
    }

    public void setTenorWeightCwt(short tenorWeightCwt) {
        this.tenorWeightCwt = tenorWeightCwt;
    }

    public void setTenorWeightQtrs(short tenorWeightQtrs) {
        this.tenorWeightQtrs = tenorWeightQtrs;
    }

    public void setTenorWeightLbs(short tenorWeightLbs) {
        this.tenorWeightLbs = tenorWeightLbs;
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
	
	public String getTenorWeight() {
		if ((tenorWeightLbs == 0) && (tenorWeightQtrs == 0)) {
			return tenorWeightCwt + " cwt";
		} else {
			return String.format("%d-%d-%d", tenorWeightCwt, tenorWeightQtrs, tenorWeightLbs);
		}
	}
	
	public boolean isGroup() {
	    return false;
	}

    public String getLocation() {
        StringBuilder name = new StringBuilder();
        if (area != null) {
            name.append(area);
            name.append(", "); 
        }
        name.append(town);
        return name.toString();
    }

    public String getDisplayName() {
        StringBuilder name = new StringBuilder();
        if (dedication != null) {
            name.append(dedication);
            name.append(", "); 
        }
        name.append(getLocation());
        return name.toString();
    }

}
