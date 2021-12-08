package com.example.je.servlets;

import com.example.je.dao.Pages;
import com.example.je.services.LoadService;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.annotation.WebServlet;
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

@WebServlet(name = "loadServlet", value = "/load")
public class LoadServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int page = 0;
        int end;
        Pages pages = new Pages();

        do {
            page++;

            URL urldemo = new URL("https://kinopoiskapiunofficial.tech/api/v2.2/films/top?type=TOP_250_BEST_FILMS&page=" + page);
            URLConnection yc = urldemo.openConnection();
            yc.setRequestProperty("X-API-KEY", "2c2c61fe-07c5-47d2-9ce0-f3f6ad187b7e");

            BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream(), StandardCharsets.UTF_8));
            String inputLine = in.readLine();
            ObjectMapper mapper = new ObjectMapper();
            pages.addFilms(mapper.readValue(inputLine, Pages.class).getFilms(), mapper.readValue(inputLine, Pages.class).getPagesCount());

            end = pages.getPagesCount().intValue();
        } while (page < end);

        pages.setFilms(pages.getFilms().stream().distinct().collect(Collectors.toList()));

        try {
            LoadService.loadData(pages);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

    }
}

