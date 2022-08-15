package com.example.demo;

import com.example.demo.interceptor.Logged;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Map;

@Slf4j
@WebServlet("/viewByIDServlet")
public class ViewByIDServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {

        PrintWriter out = BookRepository.getWriter(response);

        int id = BookRepository.idOfTheBook(request);
        Book book = BookRepository.getBookById(id);

        SaveServlet saveServlet = new SaveServlet();
        Map<Integer, Book> usersMap = saveServlet.putUserToMap(book);

        isExist(usersMap, out, book, id);
    }

    @Logged
    private void isExist(Map<Integer, Book> usersMap, PrintWriter out, Book book, int id) {
        if (usersMap.containsKey(id)) {
            out.print(book);
            log.info("getBookById() - end: " + book);
        } else {
            out.print("No book with such ID!");
            log.info("No record with such ID");
        }
        out.close();
    }
}