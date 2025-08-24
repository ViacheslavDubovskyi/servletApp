package com.example.demo;

import com.example.demo.interceptor.Logged;
import lombok.extern.slf4j.Slf4j;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

@Slf4j
@WebServlet("/viewByIDServlet")
public class ViewByIDServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {

        PrintWriter out = BookRepository.getWriter(response);

        int id = BookRepository.idOfTheBook(request);
        Book book = BookRepository.getBookById(id);

        isExist(out, book, id);
    }

    @Logged
    private void isExist(PrintWriter out, Book book, int id) {
        if (book != null) {
            out.print(book);
            log.info("getBookById() - end: {}", book);
        } else {
            out.print("No book with such ID!");
            log.info("No record with such ID");
        }
        out.close();
    }
}