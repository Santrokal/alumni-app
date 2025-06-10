package com.shc.alumni.springboot.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "bill_pdf_entity")
public class BillPdfEntity {

    @Id
    @Column(name = "id", unique = true, nullable = false, length = 50)
    private String id;

    @Column(name = "member_id", nullable = false)
    private String memberId;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "phoneNumber", nullable = false)
    private String phoneNumber;

    @Column(name = "payment_id", unique = true, nullable = false)
    private String paymentId;

    @Column(name = "status", nullable = false)
    private String status; // "PAID" or "UNPAID"

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date", nullable = false)
    private Date date;

    @Lob
    @Column(name = "pdf_data", nullable = false, columnDefinition = "LONGBLOB")
    private byte[] pdfData;

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getMemberId() { return memberId; }
    public void setMemberId(String memberId) { this.memberId = memberId; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getPaymentId() { return paymentId; }
    public void setPaymentId(String paymentId) { this.paymentId = paymentId; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }

    public byte[] getPdfData() { return pdfData; }
    public void setPdfData(byte[] pdfData) { this.pdfData = pdfData; }

}
