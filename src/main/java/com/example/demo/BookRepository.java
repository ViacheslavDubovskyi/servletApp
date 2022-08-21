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

    //Set the connection with database
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

    //Save new book in database, in the table 'books'
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

    //Save genre in database, in the table 'genres', for book with specific ID;
    //foreign key 'book_id' (genres) references to the 'id'(books)
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

    //update already existing book in database by specific ID,
    //parameters 'title', 'author' and 'year' can be set
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

    //delete book with specific ID from the table 'books'
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

    //set 'false' into column 'is_available' in the table 'genres'
    //determine that the book is not available anymore
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

    //get book by specific ID form the 'books'
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

    //get all books that is in the database
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

    //get the list of all books and all parameters 'genre',
    //which was set in the table 'genres', including 'null' values
    @Logged
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

    //get the list of all books that have some genre,
    //which was set in the table 'genres'
    @Logged
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

    //get the list of all books which have specific genre
    @Logged
    public static List<Book> getBooksByGenre(String genre) {

        List<Book> listBooks = new ArrayList<>();

        try {
            log.info("getBooksByGenre() - start");
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

    //get the list of all books that are not available anymore
    @Logged
    public static List<Book> getAllBooksIsNotAvailable() {

        List<Book> listBooks = new ArrayList<>();

        try {
            log.info("getAllBooksIsNotAvailable() - start");
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

    //setting parameters for the new book into table 'books', is used in method save()
    @Logged
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

    //setting parameters into table 'genres', is used in method saveGenre()
    @Logged
    public static void setGenreIntoTable(PreparedStatement ps, Book book) {
        try {
            ps.setInt(1, book.getId());
            ps.setString(2, book.getGenre());
        } catch (SQLException e) {
            e.printStackTrace();
            log.info("Something went wrong. SQLException appears.");
        }
    }

    //get parameters from corresponding columns
    @Logged
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

    //setting parameters, which are updated in method update()
    public static void setBookInformation(HttpServletRequest request, Book book) {
        String title = request.getParameter("title");
        String author = request.getParameter("author");
        String year = request.getParameter("year");
        book.setTitle(title);
        book.setAuthor(author);
        book.setYear(year);
    }

    //setting parameters into the table the 'genres' in the method saveGenre()
    public static void setGenreIntoTheTableGenres(HttpServletRequest request, Book book) {
        String bookId = request.getParameter("book_id");
        String genre = request.getParameter("genre");
        book.setId(Integer.parseInt(bookId));
        book.setGenre(genre);
    }

    //getting PrintWriter object
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

    //requesting ID from the database of the already existing book
    public static int idOfTheBook(HttpServletRequest request) {
        String sid = request.getParameter("id");
        return Integer.parseInt(sid);
    }

    //requesting genre from the database from table 'genres'
    public static String genreOfTheBook(HttpServletRequest request) {
        return request.getParameter("genre");
    }
}