package com.example.JE.servlets;

import com.example.JE.services.ReadService;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.stream.Collectors;

@WebServlet(name = "getServlet", value = "/get")
public class ReadServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Long getId = Long.valueOf(req.getReader().lines().collect(Collectors.toList()).get(0));

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        try {
            String filmJsonString = new Gson().toJson(ReadService.getFilm(getId));
            resp.getWriter().write(filmJsonString);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}
