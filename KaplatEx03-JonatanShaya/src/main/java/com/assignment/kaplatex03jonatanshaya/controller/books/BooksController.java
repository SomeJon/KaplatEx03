package com.assignment.kaplatex03jonatanshaya.controller.books;

import com.assignment.kaplatex03jonatanshaya.controller.Controller;
import com.assignment.kaplatex03jonatanshaya.data.ServerData;
import com.assignment.kaplatex03jonatanshaya.dto.DtoBook;
import com.assignment.kaplatex03jonatanshaya.dto.DtoResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/books")
public class BooksController implements Controller {
    private final ServerData Data;

    public BooksController(ServerData data) {
        Data = data;
    }

    @GetMapping
    public ResponseEntity<DtoResponse<List<DtoBook>>> getBooksData(
            @RequestParam("author") Optional<String> author,
            @RequestParam("price-bigger-than") Optional<Integer> priceBiggerThen,
            @RequestParam("price-less-than") Optional<Integer> priceSmallerThen,
            @RequestParam("year-bigger-than") Optional<Integer> yearBiggerThen,
            @RequestParam("year-less-than") Optional<Integer> yearSmallerThen,
            @RequestParam("genres") Optional<List<String>> genres) {
        List<DtoBook> books = Data.getBooks(author, priceBiggerThen,
                priceSmallerThen, yearBiggerThen, yearSmallerThen, genres);

        DtoResponse<List<DtoBook>> response = new DtoResponse<>(books, NO_ERROR);

        return ResponseEntity.ok(response);
    }
}
