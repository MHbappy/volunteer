package com.app.library.service;


import com.app.library.model.Book;
import com.app.library.model.VolunteerInfo;
import com.app.library.repository.BookRepository;
import com.app.library.repository.VolunteerInfoRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;


@Service
@Transactional
@AllArgsConstructor
public class BookService {

    private final Logger log = LoggerFactory.getLogger(BookService.class);
    private final BookRepository bookRepository;
    private final VolunteerInfoRepository volunteerInfoRepository;

    public Book save(Book book) {
        log.debug("Request to save Book : {}", book);
        return bookRepository.save(book);
    }

    public Book update(Book book) {
        log.debug("Request to update Book : {}", book);
        return bookRepository.save(book);
    }

    @Transactional(readOnly = true)
    public List<Book> findAll() {
        log.debug("Request to get all Books");
        return bookRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Book> findAllByVolunteerId(Long volunteerId) {
        VolunteerInfo volunteerInfo = volunteerInfoRepository.findByVolunteerId(volunteerId);
        if (volunteerInfo == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Volunteer not found");
        }
        log.debug("Request to get all volunteer wise Books");
        return bookRepository.findAllByVolunteerInfo(volunteerInfo);
    }

    @Transactional(readOnly = true)
    public Optional<Book> findOne(Long id) {
        log.debug("Request to get Book : {}", id);
        return bookRepository.findById(id);
    }

    public void delete(Long id) {
        log.debug("Request to delete Book : {}", id);
        bookRepository.deleteById(id);
    }
}
