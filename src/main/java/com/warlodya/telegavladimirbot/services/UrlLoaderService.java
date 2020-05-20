package com.warlodya.telegavladimirbot.services;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

@Component
@Scope
public class UrlLoaderService {

    public String load(String urlString) {
        StringBuilder sb = new StringBuilder();
        try {
            URL url = new URL(urlString);
            URLConnection conn = url.openConnection();

            // open the stream and put it into BufferedReader
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));

            String inputLine;
            while ((inputLine = br.readLine()) != null) {
                sb.append(inputLine);
                sb.append(System.lineSeparator());
            }
            br.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}
