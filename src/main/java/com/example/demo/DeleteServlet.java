package com.example.demo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/deleteServlet")
public class DeleteServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) {

        String sid = request.getParameter("id");
        int id = Integer.parseInt(sid);
        EmployeeRepository.delete(id);

        try {
            response.sendRedirect("viewServlet");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
