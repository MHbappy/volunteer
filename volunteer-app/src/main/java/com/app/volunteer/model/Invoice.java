package com.app.volunteer.model;

import com.app.volunteer.enumuration.INVOICE_TYPE;
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

    @Column(name = "name")
    private String name;

    @Column(name = "amount")
    private Double amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "invoice_type")
    private INVOICE_TYPE invoiceType;
}
