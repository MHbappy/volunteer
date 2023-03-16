package com.app.account.controller;

import com.app.account.enumuration.InvoiceType;
import com.app.account.model.Invoice;
import com.app.account.repository.InvoiceRepository;
import com.app.account.service.InvoiceService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class InvoiceResource {

    private final Logger log = LoggerFactory.getLogger(InvoiceResource.class);

    private final InvoiceService invoiceService;

    private final InvoiceRepository invoiceRepository;

    @PostMapping("/invoices")
    public ResponseEntity<Invoice> createInvoice(@RequestBody Invoice invoice) throws URISyntaxException {
        log.debug("REST request to save Invoice : {}", invoice);
        if (invoice.getId() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A new invoice cannot already have an ID");
        }
        Invoice result = invoiceService.save(invoice);
        return ResponseEntity.ok(result);
    }


    @GetMapping("/volunteer-invoices")
    public List<Invoice> getAllUserInvoices(@RequestParam("volunteerId") Long volunteerId) {
        log.debug("REST request to get all Invoices");
        return invoiceService.findAllInvoiceByVolunteerId(volunteerId);
    }

    @GetMapping("/graduate-eligibility")
    public Boolean getStudentGraduateEligibility(@RequestParam("volunteerId") Long volunteerId) {
        log.debug("REST request to get all Invoices");
        return invoiceService.isEligibleForGraduate(volunteerId);
    }



    @GetMapping("/volunteer-invoices-by-invoice-id-and-volunteer-id")
    public Invoice getAllUserInvoicesByInvoiceIdAndVolunteerId(@RequestParam("volunteerId") Long volunteerId, @RequestParam("invoiceNo") String invoiceNo) {
        log.debug("REST request to get all Invoices");
        return invoiceService.findInvoiceByVolunteerIdAndInvoiceNo(volunteerId, invoiceNo);
    }

    @PostMapping("/payment")
    public ResponseEntity<Boolean> payment(@RequestParam("invoiceNo") String invoiceNo, @RequestParam("amount") Double amount) {
        log.debug("REST request to save Invoice : {}", invoiceNo);
        return ResponseEntity.ok(invoiceService.payment(invoiceNo, amount));
    }

    @GetMapping("/invoices")
    public List<Invoice> getAllInvoices() {
        log.debug("REST request to get all Invoices");
        return invoiceService.findAll();
    }

    @GetMapping("/invoices/{id}")
    public ResponseEntity<Invoice> getInvoice(@PathVariable Long id) {
        log.debug("REST request to get Invoice : {}", id);
        Optional<Invoice> invoice = invoiceService.findOne(id);
        return ResponseEntity.ok(invoice.get());
    }
}
