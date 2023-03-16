package com.app.library.dto.account;

import com.app.library.enumuration.InvoiceFor;
import com.app.library.enumuration.InvoiceType;
import lombok.Data;

@Data
public class Invoice {
    private Long id;
    private String name;
    private Double amount;
    private InvoiceType invoiceType;
    private InvoiceFor invoiceFor;
    private Long bookCourseId;
    private VolunteerInfo volunteerInfo;
    private String invoiceNo;
}
