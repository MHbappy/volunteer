package com.app.volunteer.controller;


import com.app.volunteer.model.Book;
import com.app.volunteer.repository.BookRepository;
import com.app.volunteer.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class BookResource {

    private final Logger log = LoggerFactory.getLogger(BookResource.class);

    private final BookService bookService;

    private final BookRepository bookRepository;

    public BookResource(BookService bookService, BookRepository bookRepository) {
        this.bookService = bookService;
        this.bookRepository = bookRepository;
    }


    @PostMapping("/books")
    public ResponseEntity<Book> createBook(@RequestBody Book book) throws URISyntaxException {
        log.debug("REST request to save Book : {}", book);
        if (book.getId() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A new book cannot already have an ID");
        }
        Book result = bookService.save(book);
        return ResponseEntity
            .created(new URI("/api/books/" + result.getId()))
            .body(result);
    }

    @PutMapping("/books/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable(value = "id", required = false) final Long id, @RequestBody Book book)
        throws URISyntaxException {
        log.debug("REST request to update Book : {}, {}", id, book);
        if (book.getId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid id");
        }
        if (!Objects.equals(id, book.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid id");
        }
        if (!bookRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Entity not found");
        }

        Book result = bookService.update(book);
        return ResponseEntity
            .ok()
            .body(result);
    }

    @GetMapping("/books")
    public List<Book> getAllBooks() {
        log.debug("REST request to get all Books");
        return bookService.findAll();
    }

    @GetMapping("/books/{id}")
    public ResponseEntity<Book> getBook(@PathVariable Long id) {
        log.debug("REST request to get Book : {}", id);
        Optional<Book> book = bookService.findOne(id);
        return ResponseEntity.ok(book.get());
    }

    @DeleteMapping("/books/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        log.debug("REST request to delete Book : {}", id);
        bookService.delete(id);
        return ResponseEntity
            .noContent()
            .build();
    }
}
