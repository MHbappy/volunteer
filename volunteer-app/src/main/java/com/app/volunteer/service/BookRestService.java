package com.app.volunteer.service;

import com.app.volunteer.config.Constraints;
import com.app.volunteer.dto.VolunteerInfo;
import com.app.volunteer.dto.account.Invoice;
import com.app.volunteer.dto.library.Book;
import com.app.volunteer.dto.library.BookRequesetDto;
import com.app.volunteer.enumuration.BookStatus;
import com.app.volunteer.model.Volunteer;
import com.app.volunteer.security.JwtTokenProvider;
import lombok.AllArgsConstructor;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class BookRestService {

    private final RestTemplate restTemplate;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;

    public Book saveBook(BookRequesetDto bookRequesetDto){
        Volunteer volunteer = userService.getVolunteerByCurrentUser();
        Book book = new Book();
        book.setName(bookRequesetDto.getName());
        book.setBorrowedDateTime(LocalDateTime.now());
        book.setBookStatus(BookStatus.BORROWED);
        book.setIsbn(bookRequesetDto.getIsbn() == null ? "" : bookRequesetDto.getIsbn());
        book.setReturnDateTIme(LocalDateTime.now().plusWeeks(2));
        book.setIsActive(true);
        book.setVolunteerInfo(new VolunteerInfo(null, volunteer.getId()));
        try {
            String url = Constraints.LIBRARY_URL + "/api/books";
            System.out.println(url);

            Book book1 = restTemplate.postForObject(Constraints.LIBRARY_URL + "/api/books", book, Book.class);
            return book1;
        }catch (HttpStatusCodeException ex){
            ex.printStackTrace();
            JsonParser springParser = JsonParserFactory.getJsonParser();
            Map< String, Object > map = springParser.parseMap(ex.getResponseBodyAsString());
            String message = (String) map.get("message");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, message);
        }
    }


    public List<Book> getAllBooksByVolunteerId(){
        Boolean isRoleAdmin = jwtTokenProvider.isRoleExist("ROLE_ADMIN");
        Volunteer volunteer = userService.getVolunteerByCurrentUser();
        if (volunteer == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Volunteer not found");
        }
        List<Book> books = new ArrayList<>();
        if (isRoleAdmin){
            books = restTemplate.getForObject(Constraints.LIBRARY_URL + "/api/books", List.class);
        }else {
            books = restTemplate.getForObject(Constraints.LIBRARY_URL + "/api/books-by-volunteer?volunteerId="+volunteer.getId(), List.class);
        }
        return books;
    }

}
