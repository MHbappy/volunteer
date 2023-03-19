package com.app.account.service;

import com.app.account.enumuration.InvoiceFor;
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

    /**
     * Create a invoice
     * @param invoice
     * @return a invoice
     */
    @Transactional
    public Invoice save(Invoice invoice) {
        log.debug("Request to save Invoice : {}", invoice);
        VolunteerInfo volunteerInfo = null;

        //Get volunteer from volunteer Id
        if (invoice.getVolunteerInfo() != null && invoice.getVolunteerInfo().getVolunteerId() != null){
            volunteerInfo = volunteerInfoRepository.findByVolunteerId(invoice.getVolunteerInfo().getVolunteerId());
            invoice.setVolunteerInfo(volunteerInfo);
        }

        //if ther have not volunteer then it will throw a error with message
        if (invoice.getVolunteerInfo().getVolunteerId() == null || invoice.getVolunteerInfo().getId().equals(0)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Volunteer not found");
        }

        //If exist invoice then no need to add this invoice again
        if (volunteerInfo != null){
            List<Invoice> invoiceList = invoiceRepository.findAllByVolunteerInfoAndBookCourseId(volunteerInfo, invoice.getBookCourseId());
            if (!invoiceList.isEmpty()){
                return null;
            }
        }
        return invoiceRepository.save(invoice);
    }

    /**
     * pay to a invoice by invoiceNo and amount
     * @param invoiceNo
     * @param amount
     * @return a boolean value
     */
    public Boolean payment(String invoiceNo, Double amount) {
        Optional<Invoice> invoice = invoiceRepository.findByInvoiceNo(invoiceNo);
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

    /**
     * Get all invoice
     * @return list of invoice
     */
    @Transactional(readOnly = true)
    public List<Invoice> findAll() {
        log.debug("Request to get all Invoices");
        return invoiceRepository.findAll();
    }

    /**
     * Get all invoice by volunteerId
     * @param volunteerId
     * @return List Of Invoice
     */
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

    /**
     * Get the eligibility for graduate
     * @param volunteerId
     * @return a boolean value
     */
    @Transactional(readOnly = true)
    public Boolean isEligibleForGraduate(Long volunteerId) {
        log.debug("Request to get all Invoices");
        VolunteerInfo volunteerInfo = volunteerInfoRepository.findByVolunteerId(volunteerId);
        if (volunteerInfo == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Volunteer not found");
        }

        //Get all invoice by the course type. If he/she not get any course then a error will be throw
        List<Invoice> allVolunteerInfoByVolunteerInfo = invoiceRepository.findAllByVolunteerInfoAndInvoiceFor(volunteerInfo, InvoiceFor.COURSE);
        if (allVolunteerInfoByVolunteerInfo.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You didn't get any course");
        }

        //If any pending invoice for course then throw a error
        List<Invoice> allVolunteerInfoByVolunteerInfoInvoiceListForCourse = invoiceRepository.findAllByVolunteerInfoAndInvoiceForAndInvoiceType(volunteerInfo, InvoiceFor.COURSE, InvoiceType.PENDING);
        if (!allVolunteerInfoByVolunteerInfoInvoiceListForCourse.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You have to pay for your course");
        }

        //If any pending invoice for book then throw a error
        List<Invoice> allVolunteerInfoByVolunteerInfoInvoiceListForBook = invoiceRepository.findAllByVolunteerInfoAndInvoiceForAndInvoiceType(volunteerInfo, InvoiceFor.BOOK, InvoiceType.PENDING);
        if (!allVolunteerInfoByVolunteerInfoInvoiceListForBook.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You have to pay fine for book");
        }
        return true;
    }

    /**
     *  find invoice
     * @param volunteerId
     * @param invoiceNo
     * @return a invoice
     */
    @Transactional(readOnly = true)
    public Invoice findInvoiceByVolunteerIdAndInvoiceNo(Long volunteerId, String invoiceNo) {
        log.debug("Request to get all Invoices");
        VolunteerInfo volunteerInfo = volunteerInfoRepository.findByVolunteerId(volunteerId);
        if (volunteerInfo == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Volunteer not found");
        }
        Invoice allVolunteerInfoByVolunteerInfo = invoiceRepository.findAllByVolunteerInfoAndInvoiceNo(volunteerInfo, invoiceNo);

        return allVolunteerInfoByVolunteerInfo;
    }

    /**
     * find invoice
     * @param id
     * @return a invoice
     */
    @Transactional(readOnly = true)
    public Optional<Invoice> findOne(Long id) {
        log.debug("Request to get Invoice : {}", id);
        return invoiceRepository.findById(id);
    }
}
