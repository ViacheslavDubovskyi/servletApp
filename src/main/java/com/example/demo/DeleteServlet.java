package com.example.demo;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/deleteServlet")
public class DeleteServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) {

        int id = BookRepository.idForBook(request);
        BookRepository.delete(id);
        printDeletingMessage(response, id);
    }

    private void printDeletingMessage(HttpServletResponse response, int id) {
        response.setContentType("text/html");
        PrintWriter out;
        try {
            out = response.getWriter();
            out.println("Deleting book by ID " + id + " was successful");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        out.close();
    }
}
