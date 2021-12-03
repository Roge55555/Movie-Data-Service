package com.example.JE.dao;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class Countries {

    @JsonProperty("country")
    private String country;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Countries countries = (Countries) o;
        return Objects.equals(country, countries.country);
    }

    @Override
    public int hashCode() {
        return Objects.hash(country);
    }

    public Countries(String country) {
        this.country = country;
    }

    public Countries() {
    }
}
