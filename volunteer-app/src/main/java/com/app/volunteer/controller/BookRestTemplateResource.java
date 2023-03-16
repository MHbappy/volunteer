package com.app.volunteer.controller;


import com.app.volunteer.dto.library.Book;
import com.app.volunteer.dto.library.BookRequesetDto;
import com.app.volunteer.service.BookRestService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class BookRestTemplateResource {
    public final BookRestService bookRestService;

    @PostMapping("/save-book")
    public Book saveBook(@RequestBody BookRequesetDto bookRequesetDto){
        return bookRestService.saveBook(bookRequesetDto);
    }

    @PostMapping("/get-books")
    public List<Book> getBookList(){
        return bookRestService.getAllBooksByVolunteerId();
    }
}
