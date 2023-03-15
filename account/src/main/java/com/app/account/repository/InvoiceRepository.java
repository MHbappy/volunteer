package com.app.account.repository;

import com.app.account.model.Invoice;
import com.app.account.model.VolunteerInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA repository for the Invoice entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    List<Invoice> findAllByVolunteerInfo(VolunteerInfo volunteerInfo);
}
