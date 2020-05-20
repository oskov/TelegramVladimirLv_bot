package com.warlodya.telegavladimirbot.models;

public class Covid19Data {
    private final String date;
    private final int confirmed;
    private final int deaths;
    private final int recovered;

    public Covid19Data(String date, int confirmed, int deaths, int recovered) {
        this.date = date;
        this.confirmed = confirmed;
        this.deaths = deaths;
        this.recovered = recovered;
    }

    public String getDate() {
        return date;
    }

    public int getConfirmed() {
        return confirmed;
    }

    public int getDeaths() {
        return deaths;
    }

    public int getRecovered() {
        return recovered;
    }

    @Override
    public String toString() {
        return "Date = " + date + ", confirmed = " + confirmed + ", deaths = " + deaths;
    }
}
