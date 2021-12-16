package com.example.je.servlets;

import com.example.je.model.Page;
import com.example.je.services.FilmService;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.stream.Collectors;

public class LoadServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int pageNumber = 0;
        int end;
        Page page = new Page();

        do {
            pageNumber++;

            URL urldemo = new URL("https://kinopoiskapiunofficial.tech/api/v2.2/films/top?type=TOP_250_BEST_FILMS&page=" + pageNumber);
            URLConnection yc = urldemo.openConnection();
            yc.setRequestProperty("X-API-KEY", "2c2c61fe-07c5-47d2-9ce0-f3f6ad187b7e");

            BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream(), StandardCharsets.UTF_8));
            String inputLine = in.readLine();
            ObjectMapper mapper = new ObjectMapper();
            page.addFilms(mapper.readValue(inputLine, Page.class).getFilms(), mapper.readValue(inputLine, Page.class).getPagesCount());

            end = page.getPagesCount().intValue();
        } while (pageNumber < end);

        page.setFilms(page.getFilms().stream().distinct().collect(Collectors.toList()));

        try {
            FilmService.saveFilms(page.getFilms());
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}

