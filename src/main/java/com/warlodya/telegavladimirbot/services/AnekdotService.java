package com.warlodya.telegavladimirbot.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@Scope
public class AnekdotService {
    private static final String url = "https://nekdo.ru/random/";

    private static final String regex = "<div class=\"text\" id=\"\\d*\">(?<anekdot>[\\W\\s]+)<\\/div>";
    private static final Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);

    private final UrlLoaderService urlLoaderService;

    @Autowired
    public AnekdotService(UrlLoaderService urlLoaderService) {
        this.urlLoaderService = urlLoaderService;
    }

    public String getAnekdot() {
        String html = urlLoaderService.load(url);

        Matcher matcher = pattern.matcher(html);

        if (matcher.find()) {
            return matcher.group("anekdot");
        }

        return "Hui tebe";
    }
}
