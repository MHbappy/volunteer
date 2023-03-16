package com.app.volunteer.controller;

import com.app.volunteer.dto.account.Invoice;
import com.app.volunteer.model.Volunteer;
import com.app.volunteer.service.AccountRestService;
import com.app.volunteer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AccountRestTemplateResource {

    @Autowired
    private AccountRestService accountRestService;

    @Autowired
    private UserService userService;

    @GetMapping("/invoices")
    public List<Invoice> getAllInvoiceByVolunteer(){
        Volunteer volunteer = userService.getVolunteerByCurrentUser();
        return accountRestService.getInvoiceListByVolunteerId(volunteer.getId());
    }

    @GetMapping("/invoicesByUserInvoiceNo")
    public Invoice getAllInvoiceByVolunteerAndInvoiceNo(@RequestParam("invoiceNo") String invoiceNo){
        Volunteer volunteer = userService.getVolunteerByCurrentUser();
        return accountRestService.getInvoiceByVolunteerIdAndId(volunteer.getId(), invoiceNo);
    }

    @PostMapping("/payment")
    public Boolean getPayment(@RequestParam("amount") Double amount, @RequestParam("invoiceNo") String invoiceNo){
        return accountRestService.getPayment(amount, invoiceNo);
    }

    @GetMapping("/is-graduate")
    public Boolean isGraduate(){
        Volunteer volunteer = userService.getVolunteerByCurrentUser();
        return accountRestService.isPermitForGraduate(volunteer.getId());
    }

}
