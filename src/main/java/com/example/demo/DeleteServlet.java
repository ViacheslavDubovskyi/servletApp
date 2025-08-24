package com.example.demo;

import com.example.demo.interceptor.Logged;
import lombok.extern.slf4j.Slf4j;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

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
        try (PrintWriter out = response.getWriter()) {
            response.setContentType("text/html");

            out.println("Deleting book by ID " + id + " was successful");
            log.info("delete() - end: book with ID {} was deleted", id);

        } catch (IOException e) {
            log.info("Unable to delete record. IOException has appear");
            throw new RuntimeException(e);
        }
    }
}
