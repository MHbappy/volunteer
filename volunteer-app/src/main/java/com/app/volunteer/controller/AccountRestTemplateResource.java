package com.app.volunteer.controller;

import com.app.volunteer.dto.account.Invoice;
import com.app.volunteer.model.Volunteer;
import com.app.volunteer.service.AccountRestService;
import com.app.volunteer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * This class is responsible for connect with account application
 */
@RestController
@RequestMapping("/api")
public class AccountRestTemplateResource {

    @Autowired
    private AccountRestService accountRestService;

    @Autowired
    private UserService userService;

    /**
     * get all invoice by volunteer id
     * @return list of invoice
     */
    @GetMapping("/invoices")
    public List<Invoice> getAllInvoiceByVolunteer(){
        return accountRestService.getInvoiceListByVolunteerId();
    }

    /**
     * get  invoice by invoiceno and current unser
     * @param invoiceNo
     * @return single invoice
     */
    @GetMapping("/invoicesByUserInvoiceNo")
    public Invoice getAllInvoiceByVolunteerAndInvoiceNo(@RequestParam("invoiceNo") String invoiceNo){
        Volunteer volunteer = userService.getVolunteerByCurrentUser();
        return accountRestService.getInvoiceByVolunteerIdAndId(volunteer.getId(), invoiceNo);
    }

    /**
     * payment with invoice No
     * @param amount
     * @param invoiceNo
     * @return a boolean
     */
    @PostMapping("/payment")
    public Boolean getPayment(@RequestParam("amount") Double amount, @RequestParam("invoiceNo") String invoiceNo){
        return accountRestService.getPayment(amount, invoiceNo);
    }

    /**
     * eligibility check for gradudate
     * @return a boolean value
     */
    @GetMapping("/is-graduate")
    public Boolean isGraduate(){
        Volunteer volunteer = userService.getVolunteerByCurrentUser();
        return accountRestService.isPermitForGraduate(volunteer.getId());
    }
}