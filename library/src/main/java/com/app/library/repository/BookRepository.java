package com.app.library.repository;

import com.app.library.model.Book;
import com.app.library.model.VolunteerInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findAllByVolunteerInfo(VolunteerInfo volunteerInfo);

}
