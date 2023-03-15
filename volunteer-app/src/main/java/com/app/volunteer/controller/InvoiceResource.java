package com.app.volunteer.controller;

import com.app.volunteer.model.Invoice;
import com.app.volunteer.repository.InvoiceRepository;
import com.app.volunteer.service.InvoiceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
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
public class InvoiceResource {

    private final Logger log = LoggerFactory.getLogger(InvoiceResource.class);

    private final InvoiceService invoiceService;

    private final InvoiceRepository invoiceRepository;

    public InvoiceResource(InvoiceService invoiceService, InvoiceRepository invoiceRepository) {
        this.invoiceService = invoiceService;
        this.invoiceRepository = invoiceRepository;
    }

    @PostMapping("/invoices")
    public ResponseEntity<Invoice> createInvoice(@RequestBody Invoice invoice) throws URISyntaxException {
        log.debug("REST request to save Invoice : {}", invoice);
        if (invoice.getId() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A new invoice cannot already have an ID");
        }
        Invoice result = invoiceService.save(invoice);
        return ResponseEntity
            .created(new URI("/api/invoices/" + result.getId()))
            .body(result);
    }

    @PutMapping("/invoices/{id}")
    public ResponseEntity<Invoice> updateInvoice(@PathVariable(value = "id", required = false) final Long id, @RequestBody Invoice invoice)
        throws URISyntaxException {
        log.debug("REST request to update Invoice : {}, {}", id, invoice);
        if (invoice.getId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid id");
        }
        if (!Objects.equals(id, invoice.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid id");
        }
        if (!invoiceRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Entity not found");
        }

        Invoice result = invoiceService.update(invoice);
        return ResponseEntity
            .ok()
            .body(result);
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

    @DeleteMapping("/invoices/{id}")
    public ResponseEntity<Void> deleteInvoice(@PathVariable Long id) {
        log.debug("REST request to delete Invoice : {}", id);
        invoiceService.delete(id);
        return ResponseEntity
            .noContent()
            .build();
    }
}
