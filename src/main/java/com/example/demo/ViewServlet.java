package com.example.demo;

import com.example.demo.interceptor.Logged;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@Slf4j
@WebServlet("/viewServlet")
public class ViewServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) {

        PrintWriter out = BookRepository.getWriter(response);
        List<Book> list = BookRepository.getAllBooks();
        printAllEmployees(list, out);
    }

    private void getEmployees(List<Book> list, PrintWriter out) {
        for (Book book : list) {
            out.print(book);
        }
    }

    @Logged
    private void printAllEmployees(List<Book> list, PrintWriter out) {
        try {
            getEmployees(list, out);
            log.info("getAllBooks() - end: status - OK");
            if (list.isEmpty()) {
                log.info("IOException has appear: the table is empty");
                throw new IOException();
            }
        } catch (IOException e) {
            out.println("The table is Empty!");
        } finally {
            out.close();
        }
    }
}