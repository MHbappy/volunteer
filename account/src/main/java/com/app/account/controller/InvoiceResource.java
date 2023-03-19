package com.app.account.controller;

import com.app.account.model.Invoice;
import com.app.account.service.InvoiceService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class InvoiceResource {
    private final Logger log = LoggerFactory.getLogger(InvoiceResource.class);

    private final InvoiceService invoiceService;

    /**
     * Create Invoice with library and volunteer application
     * @param invoice
     * @return
     * Invoice Object
     * @throws URISyntaxException
     */
    @PostMapping("/invoices")
    public ResponseEntity<Invoice> createInvoice(@RequestBody Invoice invoice) {
        log.debug("REST request to save Invoice : {}", invoice);
        if (invoice.getId() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A new invoice cannot already have an ID");
        }
        Invoice result = invoiceService.save(invoice);
        return ResponseEntity.ok(result);
    }

    /**
     * Getting all volunteer invoice with there id
     * @param volunteerId
     * @return List of Invoice
     */
    @GetMapping("/volunteer-invoices")
    public List<Invoice> getAllUserInvoices(@RequestParam("volunteerId") Long volunteerId) {
        log.debug("REST request to get all Invoices");
        return invoiceService.findAllInvoiceByVolunteerId(volunteerId);
    }

    /**
     * Check is this volunteer eligible for graduate
     * @param volunteerId
     * @return
     */
    @GetMapping("/graduate-eligibility")
    public Boolean getStudentGraduateEligibility(@RequestParam("volunteerId") Long volunteerId) {
        log.debug("REST request to get all Invoices");
        return invoiceService.isEligibleForGraduate(volunteerId);
    }

    /**
     * Get Invoice by there volunteer id and invoiceNo
     * @param volunteerId
     * @param invoiceNo
     * @return a Invoice
     */
    @GetMapping("/volunteer-invoices-by-invoice-id-and-volunteer-id")
    public Invoice getAllUserInvoicesByInvoiceIdAndVolunteerId(@RequestParam("volunteerId") Long volunteerId, @RequestParam("invoiceNo") String invoiceNo) {
        log.debug("REST request to get all Invoices");
        return invoiceService.findInvoiceByVolunteerIdAndInvoiceNo(volunteerId, invoiceNo);
    }

    /**
     * Pay for the invoice
     * @param invoiceNo
     * @param amount
     * @return boolean value
     */
    @PostMapping("/payment")
    public ResponseEntity<Boolean> payment(@RequestParam("invoiceNo") String invoiceNo, @RequestParam("amount") Double amount) {
        log.debug("REST request to save Invoice : {}", invoiceNo);
        return ResponseEntity.ok(invoiceService.payment(invoiceNo, amount));
    }

    /**
     * get all invoice list
     * @return list of inocice
     */
    @GetMapping("/invoices")
    public List<Invoice> getAllInvoices() {
        log.debug("REST request to get all Invoices");
        return invoiceService.findAll();
    }


    /**
     * Invoice by invoice ID
     * @param id
     * @return a invoice
     */
    @GetMapping("/invoices/{id}")
    public ResponseEntity<Invoice> getInvoice(@PathVariable Long id) {
        log.debug("REST request to get Invoice : {}", id);
        Optional<Invoice> invoice = invoiceService.findOne(id);
        return ResponseEntity.ok(invoice.get());
    }
}
