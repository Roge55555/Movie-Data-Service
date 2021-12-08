package com.example.je.servlets;

import com.example.je.services.DeleteService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.stream.Collectors;

@WebServlet(name = "deleteServlet", value = "/delete")
public class DeleteServlet extends HttpServlet {
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {

            Long delId = Long.valueOf(req.getReader().lines().collect(Collectors.toList()).get(0));
        try {
            DeleteService.deleting(delId);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

    }
}
