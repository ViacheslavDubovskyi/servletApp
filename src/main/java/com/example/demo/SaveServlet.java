package com.example.demo;

import com.example.demo.interceptor.Logged;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@WebServlet("/saveServlet")
public class SaveServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {

        response.setCharacterEncoding("UTF-8");
        PrintWriter out = BookRepository.getWriter(response);

        Book book = new Book();
        BookRepository.setBookInformation(request, book);

        int status = BookRepository.save(book);
        printStatus(book, status, out);
    }

    public Map<Integer, Book> putBookToMap(Book book) {
        Map<Integer, Book> bookMap = new HashMap<>();
        int bookID = book.getId();
        String bookTitle = book.getTitle();
        String bookAuthor = book.getAuthor();
        String bookYear = book.getYear();
        bookMap.put(bookID, new Book(bookTitle, bookAuthor, bookYear));
        return bookMap;
    }

    @Logged
    private void printStatus(Book book, int status, PrintWriter out) {
        if (status > 0) {
            out.print("Record saved successfully!" + '\n');
            out.print("Title: " + book.getTitle() + '\n');
            out.print("Author: " + book.getAuthor() + '\n');
            out.print("Year: " + book.getYear() + '\n');
            log.info("save() - end: " + book);
        } else {
            log.info("Unable to save record");
            out.println("Sorry! unable to save record");
        }
        out.close();
    }
}