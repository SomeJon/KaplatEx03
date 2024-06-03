package com.assignment.kaplatex03jonatanshaya.data;

import com.assignment.kaplatex03jonatanshaya.data.book.Book;
import com.assignment.kaplatex03jonatanshaya.dto.DtoBook;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ServerData {
    private final List<Book> books;

    public ServerData() {
        books = new ArrayList<>();
    }

    public Book addBook(DtoBook book) {
        String title = book.getTitle();
        boolean bookExists = books.stream()
                        .anyMatch(b -> b.equalsTitle(title));

        if (bookExists) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Error: Book with the title [" + title + "] already exists in the system");
            //“Error: Book with the title [<book title>] already exists in the system”
        }

        Book newBook = new Book(book);
        books.add(newBook);

        return newBook;
    }

    public List<DtoBook> getBooks(Optional<String> author, Optional<Integer> priceBiggerThen,
            Optional<Integer> priceSmallerThen, Optional<Integer> yearBiggerThen,
            Optional<Integer> yearSmallerThen, Optional<List<String>> genres) {
        List<DtoBook> books = this.books.stream().map(DtoBook::new).toList();

        return books.stream()
                .filter(b -> author.map(s -> b.getAuthor().equalsIgnoreCase(s)).orElse(true))
                .filter(b -> priceBiggerThen.map(p -> b.getPrice() >= p).orElse(true))
                .filter(b -> priceSmallerThen.map(p -> b.getPrice() <= p).orElse(true))
                .filter(b -> yearBiggerThen.map(p -> b.getYear() >= p).orElse(true))
                .filter(b -> yearSmallerThen.map(p -> b.getYear() <= p).orElse(true))
                .filter(b -> genres.map(g -> b.getGenres().stream().anyMatch(g::contains)).orElse(true))
                .sorted((b1, b2) -> b1.getTitle().compareToIgnoreCase(b2.getTitle()))
                .toList();
    }

    public Optional<Book> getBookById(int id) {
        return books.stream().filter(b -> b.getId() == id).findFirst();
    }

    public int deleteBookById(int id) {
        Optional<Book> book = getBookById(id);

        if (book.isPresent()) {
            books.remove(book.get());
        }
        else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Error: no such Book with id " + id);
            //“Error: no such Book with id <book number>”
        }

        return books.size();
    }
}
