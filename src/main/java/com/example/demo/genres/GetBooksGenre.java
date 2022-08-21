package com.example.demo.genres;

import com.example.demo.Book;
import com.example.demo.BookRepository;
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
@WebServlet("/viewBooksGenre")
public class GetBooksGenre extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) {

        PrintWriter out = BookRepository.getWriter(response);
        List<Book> list = BookRepository.getAllBooksGenre();
        printAllBooksGenre(list, out);
    }

    private void getBooks(List<Book> list, PrintWriter out) {
        for (Book book : list) {
            out.print(book);
        }
    }

    @Logged
    private void printAllBooksGenre(List<Book> list, PrintWriter out) {
        try {
            getBooks(list, out);
            if (list.isEmpty()) {
                log.info("IOException has appear: the table is empty");
                throw new IOException();
            }
            log.info("getAllBooksGenre() - end: status - OK");
        } catch (IOException e) {
            out.println("The table is Empty!");
        } finally {
            out.close();
        }
    }
}
