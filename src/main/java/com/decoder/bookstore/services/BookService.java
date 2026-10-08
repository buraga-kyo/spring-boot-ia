package com.decoder.bookstore.services;

import com.decoder.bookstore.dtos.BookRecordDto;
import com.decoder.bookstore.models.BookModel;
import com.decoder.bookstore.repositories.BookRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<BookModel> findAll() {
        return bookRepository.findAll();
    }

    public Optional<BookModel> findById(UUID id) {
        return bookRepository.findById(id);
    }

    public void save(BookRecordDto bookRecordDto) {
        BookModel bookModel = new BookModel();
        BeanUtils.copyProperties(bookRecordDto, bookModel);
        bookRepository.save(bookModel);
    }

    public void update(UUID id, BookRecordDto bookRecordDto) {
        BookModel bookModel = bookRepository.findById(id).orElseThrow();
        BeanUtils.copyProperties(bookRecordDto, bookModel);
        bookRepository.save(bookModel);
    }

    public void delete(UUID id) {
        bookRepository.deleteById(id);
    }
}
