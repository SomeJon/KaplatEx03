package com.assignment.kaplatex03jonatanshaya.dto;

import com.assignment.kaplatex03jonatanshaya.data.book.Book;

import java.util.List;

public class DtoBook {
    private final Integer id;
    private final String title;
    private final String author;
    private final int price;
    private final int year;
    private final List<String> genres;

    public DtoBook(String title, String author, int price, int year, List<String> genres) {
        this.id = 0;
        this.title = title;
        this.author = author;
        this.price = price;
        this.year = year;
        this.genres = genres;
    }

    public DtoBook(Book book) {
        this.id = book.getId();
        this.title = book.getTitle();
        this.author = book.getAuthor();
        this.price = book.getPrice();
        this.year = book.getPrintYear();
        this.genres = book.getGenres();
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getPrice() {
        return price;
    }

    public int getYear() {
        return year;
    }

    public List<String> getGenres() {
        return genres;
    }
}
