package com.warlodya.telegavladimirbot.services;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.warlodya.telegavladimirbot.models.Covid19Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@Scope
public class CovidService {
    @Autowired
    private UrlLoaderService urlLoaderService;

    private String covidJson;
    private Map<String, List<Covid19Data>> covidData;

    private void loadCovidData() {
        covidJson = urlLoaderService.load("https://pomber.github.io/covid19/timeseries.json");
    }

    private void getData() {
        if (covidJson == null) {
            loadCovidData();
        }

        Type mapType = new TypeToken<Map<String, List<Covid19Data>>>() {
        }.getType();
        covidData = new Gson().fromJson(covidJson, mapType);
    }

    private Map<String, List<Covid19Data>> getCovidData() {
        if (covidData == null) {
            getData();
        }
        return covidData;
    }

    public String getTopConfirmed() {
        Map<String, List<Covid19Data>> data = getCovidData();
        List<Map.Entry<String, List<Covid19Data>>> set = data.entrySet().stream()
                .sorted(Comparator.comparingInt(stringListEntry -> stringListEntry.getValue().get(stringListEntry.getValue().size() - 1).getConfirmed() * (-1)))
                .limit(10).collect(Collectors.toList());
        StringBuilder sb = new StringBuilder();
        set.forEach(stringListEntry -> sb.append(stringListEntry.getKey()).append(" : ")
                .append(stringListEntry.getValue().get(stringListEntry.getValue().size() - 1)).append(System.lineSeparator()));
        return sb.toString();
    }

    public String getTopDeaths() {
        Map<String, List<Covid19Data>> data = getCovidData();
        List<Map.Entry<String, List<Covid19Data>>> set = data.entrySet().stream()
                .sorted(Comparator.comparingInt(stringListEntry -> stringListEntry.getValue().get(stringListEntry.getValue().size() - 1).getDeaths() * (-1)))
                .limit(10).collect(Collectors.toList());
        StringBuilder sb = new StringBuilder();
        set.forEach(stringListEntry -> sb.append(stringListEntry.getKey()).append(" : ")
                .append(stringListEntry.getValue().get(stringListEntry.getValue().size() - 1)).append(System.lineSeparator()));
        return sb.toString();
    }

    public Set<String> getCountrySet() {
        return getCovidData().keySet();
    }

    private List<Covid19Data> getCovidDataForCountry(String countryName) {
        List<Covid19Data> list = getCovidData().get(countryName);
        return list;
    }

    public String generateWeekReportForCountry(String countryName) {
        StringBuilder sb = new StringBuilder();
        List<Covid19Data> list = getCovidDataForCountry(countryName);
        if (list == null) {
            return "Unknown country";
        }
        sb.append("Report for ").append(countryName).append("\n");
        list.stream().skip(Math.max(0, list.size() - 7)).forEach(covid19Data -> sb.append(covid19Data).append(System.lineSeparator()));
        return sb.toString();
    }

    public String getCovidCountForCountry(String countryName) {
        List<Covid19Data> list = getCovidDataForCountry(countryName);
        if (list == null) {
            return "Unknown country";
        }
        Covid19Data lastDayData = list.get(list.size() - 1);
        int increment = lastDayData.getConfirmed() - list.get(list.size() - 2).getConfirmed();
        return "Date " + lastDayData.getDate() + "\nStats for " + countryName + ": \nConfirmed: " + lastDayData.getConfirmed() + "\nIncrement: " + increment + "\nDeaths: " + lastDayData.getDeaths();
    }
}
