package com.bov.vitali.training.data.database.entity;

public class Address {
    private String street;
    private String state;
    private String city;
    private int postCode;

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getPostCode() {
        return postCode;
    }

    public void setPostCode(int postCode) {
        this.postCode = postCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Address address = (Address) o;

        if (postCode != address.postCode) return false;
        if (street != null ? !street.equals(address.street) : address.street != null) return false;
        if (state != null ? !state.equals(address.state) : address.state != null) return false;
        return city != null ? city.equals(address.city) : address.city == null;
    }

    @Override
    public int hashCode() {
        int result = street != null ? street.hashCode() : 0;
        result = 31 * result + (state != null ? state.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + postCode;
        return result;
    }
}