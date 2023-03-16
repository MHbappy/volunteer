package com.app.account.repository;

import com.app.account.enumuration.InvoiceFor;
import com.app.account.enumuration.InvoiceType;
import com.app.account.model.Invoice;
import com.app.account.model.VolunteerInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for the Invoice entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    List<Invoice> findAllByVolunteerInfo(VolunteerInfo volunteerInfo);
    List<Invoice> findAllByVolunteerInfoAndInvoiceFor(VolunteerInfo volunteerInfo, InvoiceFor invoiceFor);
    List<Invoice> findAllByVolunteerInfoAndInvoiceForAndInvoiceType(VolunteerInfo volunteerInfo, InvoiceFor invoiceFor, InvoiceType invoiceType);
    Invoice findAllByVolunteerInfoAndInvoiceNo(VolunteerInfo volunteerInfo, String invoiceNo);
    Optional<Invoice> findByInvoiceNo(String invoiceNo);
    List<Invoice> findAllByVolunteerInfoAndBookCourseId(VolunteerInfo volunteerInfo, Long courseOrBookId);
}
