package com.example.demo;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/putServlet")
public class PutServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {

        PrintWriter out = BookRepository.getWriter(response);
        int id = BookRepository.idForBook(request);

        Book book = new Book();
        book.setId(id);
        BookRepository.setBookInformation(request, book);

        int status = BookRepository.update(book);
        printStatus(book, status, id, out);
    }

    private void printStatus(Book book, int status, int id, PrintWriter out) {
        try {
            if (status > 0) {
                out.println("Record is successfully update!");
                out.println("New parameters for book with ID " + id + ":");
                out.print("Title: " + book.getTitle() + '\n');
                out.print("Author: " + book.getAuthor() + '\n');
                out.print("Year: " + book.getYear() + '\n');
            } else {
                throw new IOException();
            }
        } catch (IOException e) {
            out.println("Sorry! unable to update record");
        } finally {
            out.close();
        }
    }
}