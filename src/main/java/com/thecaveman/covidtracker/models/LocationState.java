package com.thecaveman.covidtracker.models;

public class LocationState {
    private String state;
    private String country;
    private int latestCases;
    private int diffFromLastDay;

    public int getDiffFromLastDay() {
        return diffFromLastDay;
    }

    public void setDiffFromLastDay(int diffFromLastDay) {
        this.diffFromLastDay = diffFromLastDay;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getLatestCases() {
        return latestCases;
    }

    public void setLatestCases(int latestCases) {
        this.latestCases = latestCases;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "LocationState{" +
                "state='" + state + '\'' +
                ", country='" + country + '\'' +
                ", latestCases=" + latestCases +
                '}';
    }
}
