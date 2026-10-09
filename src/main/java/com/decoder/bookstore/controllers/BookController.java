package com.decoder.bookstore.controllers;

import com.decoder.bookstore.dtos.BookRecordDto;
import com.decoder.bookstore.models.BookModel;
import com.decoder.bookstore.services.BookService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping(version = "v1")
    public ResponseEntity<List<BookModel>> getAllBooks() {
        List<BookModel> books = bookService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(books);
    }

    @GetMapping(value = "/{id}", version = "v1")
    public ResponseEntity<BookModel> getOneBook(@PathVariable UUID id) {
        Optional<BookModel> book = bookService.findById(id);
        if (book.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.status(HttpStatus.OK).body(book.get());
    }

    @PostMapping(version = "v1")
    public ResponseEntity<BookModel> saveBook(@RequestBody @Valid BookRecordDto bookRecordDto) {
        BookModel bookModel = bookService.save(bookRecordDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(bookModel);
    }

    @PutMapping(value = "/{id}", version = "v1")
    public ResponseEntity<Void> updateBook(@PathVariable UUID id, @RequestBody @Valid BookRecordDto bookRecordDto) {
        if (bookService.findById(id).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        bookService.update(id, bookRecordDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping(value = "/{id}", version = "v1")
    public ResponseEntity<Void> deleteBook(@PathVariable UUID id) {
        if (bookService.findById(id).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            /* esse codigo foi gerado diferente do da aula também
            * o da aula foi return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book not found"); */
        }

        bookService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
