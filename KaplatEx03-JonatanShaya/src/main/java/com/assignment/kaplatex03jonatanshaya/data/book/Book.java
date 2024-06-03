package com.assignment.kaplatex03jonatanshaya.data.book;

import com.assignment.kaplatex03jonatanshaya.dto.DtoBook;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Book {
    private static final int minYear = 1940;
    private static final int maxYear = 2100;
    private static int NextId = 1;
    private final int Id;
    private final String Title;
    private final String Author;
    private final int PrintYear;
    private int Price;
    private final List<Genre> Genres;


    public Book(String title, String author, int printYear, int price, List<String> genres) {
        checkValidity(printYear, price);

        Id = NextId;
        NextId++;
        Title = title;
        Author = author;
        PrintYear = printYear;
        Price = price;
        Genres = new ArrayList<>();

        for(String genre : genres){
            Genre toEnter = Genre.valueOf(genre.toUpperCase());
            Genres.add(toEnter);
        }
    }

    public Book(DtoBook book){
        this(book.getTitle(), book.getAuthor(), book.getYear(), book.getPrice(), book.getGenres());
    }

    private void checkValidity(int printYear, int price){
        if(printYear < minYear || printYear > maxYear){
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Error: Can’t create new Book that its year [" +
                    printYear + "] is not in the accepted range [1940 -> 2100]");
            //“Error: Can’t create new Book that its year [<book’s year>] is not in the accepted range [1940 -> 2100]”
        }
        if(price < 1){
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Error: Can’t create new Book with negative price");
            //“Error: Can’t create new Book with negative price”
        }
    }

    public int getId() {
        return Id;
    }

    public String getTitle() {
        return Title;
    }

    public String getAuthor() {
        return Author;
    }

    public int getPrintYear() {
        return PrintYear;
    }

    public int getPrice() {
        return Price;
    }

    public List<String> getGenres() {
        List<String> genres = new ArrayList<>();

        for(Genre genre : Genres){
            genres.add(genre.name());
        }

        return genres;
    }

    public int setPrice(int i_Price) {
        if(i_Price < 1){
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Error: price update for book [" + Id + "] must be a positive integer");
            //“Error: price update for book [<book number>] must be a positive integer”
        }

        int old = Price;

        Price = i_Price;

        return old;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(Title.toUpperCase(), book.Title.toUpperCase());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(Title.toUpperCase());
    }

    public boolean equalsTitle(String title) {
        return Title.equalsIgnoreCase(title);
    }


}
