package com.example.demo;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

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

    public Map<Integer, Book> putUserToMap(Book book) {
        Map<Integer, Book> usersMap = new HashMap<>();
        int empID = book.getId();
        String bookTitle = book.getTitle();
        String bookAuthor = book.getAuthor();
        String bookYear = book.getYear();
        usersMap.put(empID, new Book(bookTitle, bookAuthor, bookYear));
        return usersMap;
    }

    private void printStatus(Book book, int status, PrintWriter out) {
        if (status > 0) {
            out.print("Record saved successfully!" + '\n');
            out.print("Title: " + book.getTitle() + '\n');
            out.print("Author: " + book.getAuthor() + '\n');
            out.print("Year: " + book.getYear() + '\n');
        } else {
            out.println("Sorry! unable to save record");
        }
        out.close();
    }
}