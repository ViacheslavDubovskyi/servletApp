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
@WebServlet("/putServlet")
public class PutServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {

        PrintWriter out = BookRepository.getWriter(response);
        int id = BookRepository.idOfTheBook(request);

        Book book = new Book();
        book.setId(id);
        BookRepository.setBookInformation(request, book);

        int status = BookRepository.update(book);
        printStatus(book, status, id, out);
    }

    @Logged
    private void printStatus(Book book, int status, int id, PrintWriter out) {
        try {
            if (status > 0) {
                out.println("Record is successfully update!");
                out.println("New parameters for book with ID " + id + ":");
                out.print("Title: " + book.getTitle() + '\n');
                out.print("Author: " + book.getAuthor() + '\n');
                out.print("Year: " + book.getYear() + '\n');
                log.info("Record with ID " + id + " is successfully update!");
            } else {
                throw new IOException();
            }
        } catch (IOException e) {
            log.info("Unable to save record. IOException has appear");
            out.println("Sorry! unable to update record");
        } finally {
            out.close();
        }
    }
}