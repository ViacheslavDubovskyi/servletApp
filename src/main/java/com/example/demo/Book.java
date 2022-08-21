package com.example.demo;

import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Book {

    private int id;
    private String title;
    private String author;
    private String year;
    private Boolean isAvailable = Boolean.TRUE;
    private Boolean isUpdated = Boolean.FALSE;
    private String genre;

    public Book(String title, String author, String year) {
        this.title = title;
        this.author = author;
        this.year = year;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", year=" + year + '\'' +
                ", isAvailable=" + isAvailable + '\'' +
                ", isUpdated=" + isUpdated + '\'' +
                ", genre=" + genre + '\'' +
                '}' + '\n';
    }
}
