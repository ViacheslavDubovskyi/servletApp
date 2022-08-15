package com.example.demo;

import com.example.demo.interceptor.Logged;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
@WebServlet("/deleteServlet")
public class DeleteServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) {

        int id = BookRepository.idOfTheBook(request);
        BookRepository.delete(id);
        printDeletingMessage(response, id);
    }

    @Logged
    private void printDeletingMessage(HttpServletResponse response, int id) {
        response.setContentType("text/html");
        PrintWriter out;
        try {
            out = response.getWriter();
            out.println("Deleting book by ID " + id + " was successful");
            log.info("delete() - end: book with ID " + id + "was deleted");

        } catch (IOException e) {
            log.info("Unable to delete record. IOException has appear");
            throw new RuntimeException(e);
        }
        out.close();
    }
}
