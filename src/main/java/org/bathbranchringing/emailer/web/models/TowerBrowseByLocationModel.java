package org.bathbranchringing.emailer.web.models;

import java.util.List;

import org.bathbranchringing.emailer.core.domain.Country;
import org.bathbranchringing.emailer.core.domain.County;
import org.bathbranchringing.emailer.core.domain.Tower;

public class TowerBrowseByLocationModel {

    private List<Country> countries;
    
    private String selectedCountryName;
    
    private List<County> counties;
    
    private String selectedCountyName;
    
    public List<Country> getCountries() {
        return countries;
    }

    public void setCountries(List<Country> countries) {
        this.countries = countries;
    }

    public String getSelectedCountryName() {
        return selectedCountryName;
    }

    public void setSelectedCountryName(String selectedCountryName) {
        this.selectedCountryName = selectedCountryName;
    }

    public List<County> getCounties() {
        return counties;
    }

    public void setCounties(List<County> counties) {
        this.counties = counties;
    }

    public String getSelectedCountyName() {
        return selectedCountyName;
    }

    public void setSelectedCountyName(String selectedCountyName) {
        this.selectedCountyName = selectedCountyName;
    }

    public List<Tower> getTowers() {
        return towers;
    }

    public void setTowers(List<Tower> towers) {
        this.towers = towers;
    }

    private List<Tower> towers;
    
}
