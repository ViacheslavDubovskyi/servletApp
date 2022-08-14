package com.example.demo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookRepository {

    /*public static void main(String[] args) {
        getConnection();

        Employee employee = new Employee();

        employee.setName("oleg");
        employee.setEmail(" ");
        employee.setCountry(" ");
        save(employee);
    }*/

    public static Connection getConnection() {
        Connection connection = null;
        String url = "jdbc:postgresql://localhost:5432/library";
        String user = "postgres";
        String password = "tranquilo22";

        try {
            connection = DriverManager.getConnection(url, user, password);
            if (connection != null) {
                System.out.println("Connected to the PostgreSQL server successfully.");
            } else {
                System.out.println("Failed to make connection!");
            }

        } catch (SQLException sqlException) {
            System.out.println(sqlException);
        }
        return connection;
    }

    public static int save(Book book) {

        int status = 0;

        try {
            PreparedStatement ps = connection().prepareStatement("insert into books(title,author,year) values (?,?,?)");
            setBookIntoTable(ps, book);

            status = ps.executeUpdate();
            connection().close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return status;
    }

    public static int update(Book book) {

        int status = 0;

        try {
            PreparedStatement ps = connection().prepareStatement("update books set title=?,author=?,year=? where id=?");
            setBookIntoTable(ps, book);
            ps.setInt(4, book.getId());

            status = ps.executeUpdate();
            connection().close();

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return status;
    }

    public static int delete(int id) {

        int status = 0;

        try {
            PreparedStatement ps = connection().prepareStatement("delete from books where id=?");
            ps.setInt(1, id);
            status = ps.executeUpdate();
            connection().close();

        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return status;
    }

    public static Book getBookById(int id) {

        Book book = new Book();

        try {
            PreparedStatement ps = connection().prepareStatement("select * from books where id=?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                getResultSet(rs, book);
            }
            connection().close();

        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return book;
    }

    public static List<Book> getAllBooks() {

        List<Book> listBooks = new ArrayList<>();

        try {
            PreparedStatement ps = connection().prepareStatement("select * from books");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Book book = new Book();
                getResultSet(rs, book);
                listBooks.add(book);
            }
            connection().close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listBooks;
    }

    public static Connection connection() {
        return getConnection();
    }

    public static void setBookIntoTable(PreparedStatement ps, Book book) {
        try {
            ps.setString(1, book.getTitle());
            ps.setString(2, book.getAuthor());
            ps.setString(3, book.getYear());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void getResultSet(ResultSet rs, Book book) {
        try {
            book.setId(rs.getInt(1));
            book.setTitle(rs.getString(2));
            book.setAuthor(rs.getString(3));
            book.setYear(rs.getString(4));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static PrintWriter getWriter(HttpServletResponse response) {
        response.setContentType("text/plain");
        PrintWriter out;
        try {
            out = response.getWriter();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return out;
    }

    public static void setBookInformation(HttpServletRequest request, Book book) {
        String title = request.getParameter("title");
        String author = request.getParameter("author");
        String year = request.getParameter("year");
        book.setTitle(title);
        book.setAuthor(author);
        book.setYear(year);
    }

    public static int idForBook(HttpServletRequest request) {
        String sid = request.getParameter("id");
        return Integer.parseInt(sid);
    }
}