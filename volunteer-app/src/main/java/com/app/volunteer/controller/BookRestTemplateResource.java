package com.app.volunteer.controller;


import com.app.volunteer.dto.library.Book;
import com.app.volunteer.dto.library.BookRequesetDto;
import com.app.volunteer.service.BookRestService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;


/**
 * This class is responsible to connect with library server
 */
@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class BookRestTemplateResource {
    public final BookRestService bookRestService;

    /**
     * save book if anyone borrow a book
     * @param bookRequesetDto
     * @return a book object
     */
    @PostMapping("/save-book")
    public Book saveBook(@RequestBody BookRequesetDto bookRequesetDto){
        return bookRestService.saveBook(bookRequesetDto);
    }

    /**
     * get all books with volunteer.
     * @return list of books
     */
    @GetMapping("/get-books")
    public List<Book> getBookList(){
        return bookRestService.getAllBooksByVolunteerId();
    }
}
