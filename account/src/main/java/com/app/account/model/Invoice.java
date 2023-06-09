package com.app.account.model;

import com.app.account.enumuration.InvoiceFor;
import com.app.account.enumuration.InvoiceType;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * A Invoice.
 */
@Entity
@Table(name = "invoice")
@Data
public class Invoice implements Serializable {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    String invoiceNo;

    @Column(name = "name")
    private String name;

    @Column(name = "amount")
    private Double amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "invoice_type")
    private InvoiceType invoiceType;

    @Enumerated(EnumType.STRING)
    @Column(name = "invoice_for")
    private InvoiceFor invoiceFor;

    private Long bookCourseId;

    @ManyToOne
    private VolunteerInfo volunteerInfo;
}
