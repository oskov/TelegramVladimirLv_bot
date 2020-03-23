package com.warlodya.telegavladimirbot.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@Scope
public class CovidService {
    private static final String url = "https://www.delfi.lv/";
    private static final String regex = "<div class=\"label\">Saslimušie Latvijā<\\/div>\\W*<div class=\"val\">(?<count>\\d+)<\\/div>";
    private static final Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
    @Autowired
    private UrlLoaderService urlLoaderService;

    public int getCovidCount() {
        String html = urlLoaderService.load(url);

        Matcher matcher = pattern.matcher(html);
        int count = 0;

        if (matcher.find()) {
            count = Integer.parseInt(matcher.group("count"));;
        }

        return count;
    }
}
