package com.example.demo;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/viewServlet")
public class ViewServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) {

        PrintWriter out = BookRepository.getWriter(response);
        List<Book> list = BookRepository.getAllBooks();
        printAllBooks(list, out);
    }

    private void getBooks(List<Book> list, PrintWriter out) {
        for (Book book : list) {
            out.print(book);
        }
    }

    private void printAllBooks(List<Book> list, PrintWriter out) {
        try {
            getBooks(list, out);
            if (list.isEmpty()) {
                throw new IOException();
            }
        } catch (IOException e) {
            out.println("The table is empty!");
        } finally {
            out.close();
        }
    }
}