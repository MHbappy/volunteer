package com.app.volunteer.service;

import com.app.volunteer.model.Invoice;
import com.app.volunteer.repository.InvoiceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class InvoiceService   {

    private final Logger log = LoggerFactory.getLogger(InvoiceService.class);

    private final InvoiceRepository invoiceRepository;

    public InvoiceService(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    
    public Invoice save(Invoice invoice) {
        log.debug("Request to save Invoice : {}", invoice);
        return invoiceRepository.save(invoice);
    }

    
    public Invoice update(Invoice invoice) {
        log.debug("Request to update Invoice : {}", invoice);
        return invoiceRepository.save(invoice);
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
    public Optional<Invoice> findOne(Long id) {
        log.debug("Request to get Invoice : {}", id);
        return invoiceRepository.findById(id);
    }

    
    public void delete(Long id) {
        log.debug("Request to delete Invoice : {}", id);
        invoiceRepository.deleteById(id);
    }
}
