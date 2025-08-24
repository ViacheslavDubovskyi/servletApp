package com.example.demo.genres;

import com.example.demo.Book;
import com.example.demo.BookRepository;
import com.example.demo.interceptor.Logged;
import lombok.extern.slf4j.Slf4j;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

@Slf4j
@WebServlet("/saveGenreServlet")
public class SaveGenre extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {

        response.setCharacterEncoding("UTF-8");
        PrintWriter out = BookRepository.getWriter(response);

        Book book = new Book();
        BookRepository.setGenreIntoTheTableGenres(request,book);

        int status = BookRepository.saveGenre(book);
        printStatus(book, status, out);
    }

    @Logged
    private void printStatus(Book book, int status, PrintWriter out) {
        if (status > 0) {
            out.print("Record saved successfully!" + '\n');
            log.info("saveGenre() - end: {}", book);
        } else {
            log.info("Unable to save record");
            out.println("Sorry! unable to save record");
        }
        out.close();
    }
}
