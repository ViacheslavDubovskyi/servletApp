package com.example.demo;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/isNotAvailable")
public class IsNotAvailable extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        int id = BookRepository.idOfTheBook(request);
        BookRepository.isNotAvailable(id);
        response.sendRedirect("viewIsNotAvailableServlet");
    }
}
