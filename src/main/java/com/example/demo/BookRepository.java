package com.example.demo;

import com.example.demo.interceptor.Logged;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class BookRepository {

    @Logged
    public static Connection getConnection() {
        Connection connection = null;
        String url = "jdbc:postgresql://localhost:5432/library";
        String user = "postgres";
        String password = "tranquilo22";

        try {
            log.info("getConnection() - start: connecting the PostgreSQL in process");
            connection = DriverManager.getConnection(url, user, password);
            if (connection != null) {
                log.info("Connected to the PostgreSQL server successfully!");
            } else {
                log.info("Failed to make connection!");
            }

        } catch (SQLException sqlException) {
            System.out.println(sqlException);
            log.info("Something went wrong. SQLException appears.");
        }
        return connection;
    }

    @Logged
    public static int save(Book book) {

        int status = 0;

        try {
            log.info("save() - start");
            Connection connection = getConnection();
            PreparedStatement ps = connection.prepareStatement("insert into books(title,author,year) values (?,?,?)");
            setBookIntoTable(ps, book);

            status = ps.executeUpdate();
            connection.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
            log.info("Something went wrong. SQLException appears.");
        }
        return status;
    }

    @Logged
    public static int saveGenre(Book book) {

        int status = 0;

        try {
            log.info("saveGenre() - start");
            Connection connection = getConnection();
            PreparedStatement ps = connection.prepareStatement("insert into genres(book_id, genre) values (?,?)");
            setGenreIntoTable(ps, book);

            status = ps.executeUpdate();
            connection.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
            log.info("Something went wrong. SQLException appears.");
        }
        return status;
    }

    @Logged
    public static int update(Book book) {

        int status = 0;

        try {
            log.info("update() - start: update record by ID");
            Connection connection = getConnection();
            PreparedStatement ps = connection.prepareStatement("update books set title=?,author=?,year=? where id=?");
            setBookIntoTable(ps, book);
            ps.setInt(4, book.getId());

            status = ps.executeUpdate();
            connection.close();

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            log.info("Something went wrong. SQLException appears.");
        }
        return status;
    }

    @Logged
    public static int delete(int id) {

        int status = 0;

        try {
            log.info("delete() - start: book ID: " + id);
            Connection connection = getConnection();
            PreparedStatement ps = connection.prepareStatement("delete from books where id=?");
            ps.setInt(1, id);

            status = ps.executeUpdate();
            connection.close();

        } catch (SQLException exception) {
            exception.printStackTrace();
            log.info("Something went wrong. SQLException appears.");
        }
        return status;
    }

    public static int isNotAvailable(int id) {

        int status = 0;

        try {
            log.info("isNotAvailable() - start: book ID: " + id);
            Connection connection = getConnection();
            PreparedStatement ps = connection.prepareStatement("UPDATE books SET is_available = FALSE where id = ?");
            ps.setInt(1, id);

            status = ps.executeUpdate();
            connection.close();

        } catch (SQLException exception) {
            exception.printStackTrace();
            log.info("Something went wrong. SQLException appears.");
        }
        return status;
    }

    @Logged
    public static Book getBookById(int id) {

        Book book = new Book();

        try {
            log.info("getBookById() - start: book ID: " + id);
            Connection connection = getConnection();
            PreparedStatement ps = connection.prepareStatement("select * from books where id=?");
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                getBookFromTheTable(rs, book);
            }
            connection.close();

        } catch (SQLException exception) {
            exception.printStackTrace();
            log.info("Something went wrong. SQLException appears.");
        }
        return book;
    }

    @Logged
    public static List<Book> getAllBooks() {

        List<Book> listBooks = new ArrayList<>();

        try {
            log.info("getAllBooks() - start");
            Connection connection = getConnection();
            PreparedStatement ps = connection.prepareStatement("select * from books");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Book book = new Book();
                getBookFromTheTable(rs, book);
                listBooks.add(book);
            }
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
            log.info("Something went wrong. SQLException appears.");
        }
        return listBooks;
    }

    public static List<Book> getAllBooksGenre() {

        List<Book> listBooks = new ArrayList<>();

        try {
            log.info("getAllBooksGenre() - start");
            Connection connection = getConnection();
            PreparedStatement ps = connection.prepareStatement("select * from books LEFT JOIN genres ON books.id=genres.book_id");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Book book = new Book();
                getBookFromTheTable(rs, book);
                listBooks.add(book);
            }
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
            log.info("Something went wrong. SQLException appears.");
        }
        return listBooks;
    }

    public static List<Book> getBooksWithGenre() {

        List<Book> listBooks = new ArrayList<>();

        try {
            log.info("getBooksWithGenre() - start");
            Connection connection = getConnection();
            PreparedStatement ps = connection.prepareStatement("select * from books LEFT JOIN genres ON books.id=genres.book_id WHERE genre is not null");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Book book = new Book();
                getBookFromTheTable(rs, book);
                listBooks.add(book);
            }
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
            log.info("Something went wrong. SQLException appears.");
        }
        return listBooks;
    }

    public static List<Book> getBooksByGenre(String genre) {

        List<Book> listBooks = new ArrayList<>();

        try {
            log.info("getBooksWithGenre() - start");
            Connection connection = getConnection();
            PreparedStatement ps = connection.prepareStatement("select * from books LEFT JOIN genres ON books.id=genres.book_id WHERE genre=?");
            ps.setString(1, genre);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Book book = new Book();
                getBookFromTheTable(rs, book);
                listBooks.add(book);
            }
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
            log.info("Something went wrong. SQLException appears.");
        }
        return listBooks;
    }

    @Logged
    public static List<Book> getAllBooksIsNotAvailable() {

        List<Book> listBooks = new ArrayList<>();

        try {
            log.info("getAllBooksIsAvailable() - start");
            Connection connection = getConnection();
            PreparedStatement ps = connection.prepareStatement("select * from books where is_available = FALSE");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Book book = new Book();
                getBookFromTheTable(rs, book);
                listBooks.add(book);
            }
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
            log.info("Something went wrong. SQLException appears.");
        }
        return listBooks;
    }


    public static void setBookIntoTable(PreparedStatement ps, Book book) {
        try {
            ps.setString(1, book.getTitle());
            ps.setString(2, book.getAuthor());
            ps.setString(3, book.getYear());
        } catch (SQLException e) {
            e.printStackTrace();
            log.info("Something went wrong. SQLException appears.");
        }
    }

    public static void setGenreIntoTable(PreparedStatement ps, Book book) {
        try {
            ps.setInt(1, book.getId());
            ps.setString(2, book.getGenre());
        } catch (SQLException e) {
            e.printStackTrace();
            log.info("Something went wrong. SQLException appears.");
        }
    }

    public static void getBookFromTheTable(ResultSet rs, Book book) {
        try {
            book.setId(rs.getInt(1));
            book.setTitle(rs.getString(2));
            book.setAuthor(rs.getString(3));
            book.setYear(rs.getString(4));
            book.setIsAvailable(rs.getBoolean(5));
            book.setGenre(rs.getString(8));
        } catch (SQLException e) {
            e.printStackTrace();
            log.info("Something went wrong. SQLException appears.");
        }
    }

    public static void setBookInformation(HttpServletRequest request, Book book) {
        String title = request.getParameter("title");
        String author = request.getParameter("author");
        String year = request.getParameter("year");
        book.setTitle(title);
        book.setAuthor(author);
        book.setYear(year);
    }

    public static void setGenreFromTheTable(HttpServletRequest request, Book book) {
        String bookId = request.getParameter("book_id");
        String genre = request.getParameter("genre");
        book.setId(Integer.parseInt(bookId));
        book.setGenre(genre);
    }

    public static PrintWriter getWriter(HttpServletResponse response) {
        response.setContentType("text/plain");
        PrintWriter out;
        try {
            out = response.getWriter();
        } catch (IOException e) {
            log.info("Something went wrong. IOException has appear");
            throw new RuntimeException(e);
        }
        return out;
    }

    public static int idOfTheBook(HttpServletRequest request) {
        String sid = request.getParameter("id");
        return Integer.parseInt(sid);
    }

    public static String genreOfTheBook(HttpServletRequest request) {
        return request.getParameter("genre");
    }
}