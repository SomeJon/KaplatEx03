package com.assignment.kaplatex03jonatanshaya.controller.book;

import com.assignment.kaplatex03jonatanshaya.controller.Controller;
import com.assignment.kaplatex03jonatanshaya.data.ServerData;
import com.assignment.kaplatex03jonatanshaya.data.book.Book;
import com.assignment.kaplatex03jonatanshaya.dto.DtoBook;
import com.assignment.kaplatex03jonatanshaya.dto.DtoResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping("/book")
public class BookController implements Controller {
    private final ServerData Data;

    public BookController(ServerData data) {
        Data = data;
    }

    @PostMapping
    public ResponseEntity<DtoResponse<Integer>> addBook(@RequestBody DtoBook book) {
        int result;
        String errorMsg;
        HttpStatusCode status;

        try{
            Book addedBook = Data.addBook(book);
            result = addedBook.getId();
            errorMsg = NO_ERROR;
            status = HttpStatus.OK;
        } catch (ResponseStatusException e) {
            result = 0;
            errorMsg = e.getReason();
            status = e.getStatusCode();
        }

        DtoResponse<Integer> bodyResponse = new DtoResponse<>(result, errorMsg);
        return ResponseEntity.status(status).body(bodyResponse);
    }

    @GetMapping
    public ResponseEntity<DtoResponse<DtoBook>> getABook(@RequestParam int id) {
        Optional<Book> book = Data.getBookById(id);
        String errorMsg;
        DtoBook returnedBook = null;
        HttpStatusCode status;

        if (book.isPresent()) {
                errorMsg = NO_ERROR;
                returnedBook = new DtoBook(book.get());
                status = HttpStatus.OK;
        }
        else{
            errorMsg = "Error: no such Book with id " + id;
            //“Error: no such Book with id <book number>”
            status = HttpStatus.NOT_FOUND;
        }
        DtoResponse<DtoBook> ret = new DtoResponse<>(returnedBook, errorMsg);
        return ResponseEntity.status(status).body(ret);
    }

    @PutMapping
    public ResponseEntity<DtoResponse<Integer>> updateABookPrice(@RequestParam int id, @RequestParam int price) {
        Optional<Book> book = Data.getBookById(id);
        String errorMsg;
        int oldPrice = 0;
        HttpStatusCode status;

        if (book.isPresent() && price > 0) {
            errorMsg = NO_ERROR;
            oldPrice = book.get().setPrice(price);
            status = HttpStatus.OK;
        }
        else if (book.isPresent()) {
            errorMsg = "Error: no such Book with id " + id;
            //Error: no such Book with id <book number>
            status = HttpStatus.NOT_FOUND;
        }
        else{
            errorMsg = "Error: price update for book [" + id + "] must be a positive integer";
            //“Error: price update for book [<book number>] must be a positive integer”
            status = HttpStatus.CONFLICT;
        }

        DtoResponse<Integer> bodyResponse = new DtoResponse<>(oldPrice, errorMsg);
        return ResponseEntity.status(status).body(bodyResponse);
    }

    @DeleteMapping
    public ResponseEntity<DtoResponse<Integer>> deleteABook(@RequestParam int id) {
        int leftBooks = 0;
        String errorMsg;
        HttpStatusCode status;

        try{
            leftBooks = Data.deleteBookById(id);
            errorMsg = NO_ERROR;
            status = HttpStatus.OK;
        }catch (ResponseStatusException e) {
            errorMsg = e.getReason();
            status = e.getStatusCode();
        }

        DtoResponse<Integer> bodyResponse = new DtoResponse<>(leftBooks, errorMsg);
        return ResponseEntity.status(status).body(bodyResponse);
    }
}
