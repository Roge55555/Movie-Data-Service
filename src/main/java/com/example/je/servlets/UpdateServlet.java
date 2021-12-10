package com.example.je.servlets;

import com.example.je.dao.Films;
import com.example.je.services.FilmService;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;

@WebServlet(name = "updateServlet", value = "/update")
public class UpdateServlet extends HttpServlet {
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(req.getInputStream(), StandardCharsets.UTF_8));
        StringBuilder inputLine = new StringBuilder();
        while (in.ready())
            inputLine.append(in.readLine());
        ObjectMapper mapper = new ObjectMapper();
        try {
            FilmService.updateFilm(mapper.readValue(inputLine.toString(), Films.class));
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}
