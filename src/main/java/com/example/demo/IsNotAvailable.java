package com.example.demo;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/isNotAvailable")
public class IsNotAvailable extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {

        int id = BookRepository.idOfTheBook(request);
        BookRepository.isNotAvailable(id);
        try {
            response.sendRedirect("viewIsNotAvailableServlet");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
