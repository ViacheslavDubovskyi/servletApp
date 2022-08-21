package com.example.demo.genres;

import com.example.demo.Book;
import com.example.demo.BookRepository;
import com.example.demo.SaveServlet;
import com.example.demo.interceptor.Logged;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import static com.example.demo.BookRepository.genreOfTheBook;
import static com.example.demo.BookRepository.getBookById;

@Slf4j
@WebServlet("/viewBooksByGenre")
public class ViewBooksByGenre extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) {

        PrintWriter out = BookRepository.getWriter(response);
        String genre = genreOfTheBook(request);
        List<Book> listGenre = BookRepository.getBooksByGenre(genre);
        isExist(listGenre, out);
    }

    @Logged
    private void isExist(List<Book> listGenre, PrintWriter out) {
        for (Book element : listGenre) {
            out.print(element);
        }
        log.info("getBooksByGenre() - end: status - OK");
        out.close();
    }
}