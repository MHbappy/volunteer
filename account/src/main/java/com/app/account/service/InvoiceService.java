package com.app.account.service;

import com.app.account.dto.PromotionStatus;
import com.app.account.enumuration.InvoiceType;
import com.app.account.model.Invoice;
import com.app.account.model.VolunteerInfo;
import com.app.account.repository.InvoiceRepository;
import com.app.account.repository.VolunteerInfoRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class InvoiceService {

    private final Logger log = LoggerFactory.getLogger(InvoiceService.class);
    private final InvoiceRepository invoiceRepository;
    private final VolunteerInfoRepository volunteerInfoRepository;

    
    public Invoice save(Invoice invoice) {
        log.debug("Request to save Invoice : {}", invoice);
        return invoiceRepository.save(invoice);
    }

    
    public Invoice updateInvoiceStatus(Long invoiceId, InvoiceType invoiceType) {
        log.debug("Request to update Invoice : {}", invoiceId);

        Optional<Invoice> invoice = invoiceRepository.findById(invoiceId);
        if (!invoice.isPresent()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invoice not found");
        }
        invoice.get().setInvoiceType(invoiceType);
        return invoiceRepository.save(invoice.get());
    }

    public Boolean payment(Long invoiceId, Double amount) {
        Optional<Invoice> invoice = invoiceRepository.findById(invoiceId);
        if (!invoice.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invoice not found");
        }

        if (!invoice.get().getAmount().equals(amount)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You have to exact amount");
        }

        invoice.get().setInvoiceType(InvoiceType.PAID);
        invoiceRepository.save(invoice.get());
        return true;
    }

    
    public Optional<Invoice> partialUpdate(Invoice invoice) {
        log.debug("Request to partially update Invoice : {}", invoice);

        return invoiceRepository
            .findById(invoice.getId())
            .map(existingInvoice -> {
                if (invoice.getName() != null) {
                    existingInvoice.setName(invoice.getName());
                }
                if (invoice.getAmount() != null) {
                    existingInvoice.setAmount(invoice.getAmount());
                }
                if (invoice.getInvoiceType() != null) {
                    existingInvoice.setInvoiceType(invoice.getInvoiceType());
                }

                return existingInvoice;
            })
            .map(invoiceRepository::save);
    }

    
    @Transactional(readOnly = true)
    public List<Invoice> findAll() {
        log.debug("Request to get all Invoices");
        return invoiceRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Invoice> findAllInvoiceByVolunteerId(Long volunteerId) {
        log.debug("Request to get all Invoices");
        VolunteerInfo volunteerInfo = volunteerInfoRepository.findByVolunteerId(volunteerId);
        if (volunteerInfo == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Volunteer not found");
        }
        List<Invoice> allVolunteerInfoByVolunteerInfo = invoiceRepository.findAllByVolunteerInfo(volunteerInfo);
        if (allVolunteerInfoByVolunteerInfo == null){
            return new ArrayList<>();
        }
        return allVolunteerInfoByVolunteerInfo;
    }

    
    @Transactional(readOnly = true)
    public Optional<Invoice> findOne(Long id) {
        log.debug("Request to get Invoice : {}", id);
        return invoiceRepository.findById(id);
    }

    
    public void delete(Long id) {
        log.debug("Request to delete Invoice : {}", id);
        invoiceRepository.deleteById(id);
    }
}
