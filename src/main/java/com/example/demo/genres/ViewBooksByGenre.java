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
import java.util.List;

import static com.example.demo.BookRepository.genreOfTheBook;

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
        log.info("getBooksByGenre() - end: {}, status - OK", listGenre);
        out.close();
    }
}