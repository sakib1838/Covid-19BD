package com.example.covid_19bd;

public class CountryEntity {
    String Country;
    String Cases;
    String todayCases;
    String Deaths;
    String todayDeaths;
    String recovered;
    String active;
    String critical;
    String caseperMillion;

    public String getCountry() {
        return Country;
    }

    public String getCases() {
        return Cases;
    }

    public String getTodayCases() {
        return todayCases;
    }

    public String getDeaths() {
        return Deaths;
    }

    public String getTodayDeaths() {
        return todayDeaths;
    }

    public String getRecovered() {
        return recovered;
    }

    public String getActive() {
        return active;
    }

    public String getCritical() {
        return critical;
    }

    public String getCaseperMillion() {
        return caseperMillion;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public void setCases(String cases) {
        Cases = cases;
    }

    public void setTodayCases(String todayCases) {
        this.todayCases = todayCases;
    }

    public void setDeaths(String deaths) {
        Deaths = deaths;
    }

    public void setTodayDeaths(String todayDeaths) {
        this.todayDeaths = todayDeaths;
    }

    public void setRecovered(String recovered) {
        this.recovered = recovered;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public void setCritical(String critical) {
        this.critical = critical;
    }

    public void setCaseperMillion(String caseperMillion) {
        this.caseperMillion = caseperMillion;
    }
}
