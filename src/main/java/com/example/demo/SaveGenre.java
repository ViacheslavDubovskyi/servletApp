package com.example.demo;

import com.example.demo.interceptor.Logged;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

@Slf4j
@WebServlet("/saveGenreServlet")
public class SaveGenre extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {

        response.setCharacterEncoding("UTF-8");
        PrintWriter out = BookRepository.getWriter(response);

        Book book = new Book();
        BookRepository.setGenreFromTheTable(request,book);

        int status = BookRepository.saveGenre(book);
        printStatus(book, status, out);
    }

    @Logged
    private void printStatus(Book book, int status, PrintWriter out) {
        if (status > 0) {
            out.print("Record saved successfully!" + '\n');
            log.info("saveGenre() - end: " + book);
        } else {
            log.info("Unable to save record");
            out.println("Sorry! unable to save record");
        }
        out.close();
    }
}
