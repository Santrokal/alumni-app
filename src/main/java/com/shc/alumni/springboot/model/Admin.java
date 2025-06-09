package com.shc.alumni.springboot.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Objects;
import java.util.Base64;

import javax.persistence.Column;
import javax.persistence.Lob;

public class Admin implements Serializable {
    
    private static final long serialVersionUID = 1L;

    // Fields for AdminEntity
    private Long id;
    private String fullName;
    private String emailAddress;
    private LocalDate dob; // You may prefer to use Date or LocalDate for real applications
    private String phoneNo;
    
    @Lob
    @Column(length = 10485760)
    private byte[] image; // Store raw image data as byte array

    // Default constructor
    public Admin() {}

    // Getter and setter methods for other fields
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    // Getter and setter for image field
    public byte[] getImage() {
        return image;
    }

    // This method allows setting image data directly as byte array
    public void setImage(byte[] image) {
        this.image = image;
    }

    // This method is used to retrieve the image as a Base64 string for rendering in JSP
    public String getBase64Image() {
        if (this.image != null) {
            return Base64.getEncoder().encodeToString(this.image); // Convert byte[] to Base64 string
        }
        return null; // Return null if image is not set
    }

    // Override hashCode() and equals() methods
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Arrays.hashCode(image);
        result = prime * result + Objects.hash(dob, emailAddress, fullName, id, phoneNo);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Admin other = (Admin) obj;
        return Objects.equals(dob, other.dob) && Objects.equals(emailAddress, other.emailAddress)
                && Objects.equals(fullName, other.fullName) && Objects.equals(id, other.id)
                && Arrays.equals(image, other.image) && Objects.equals(phoneNo, other.phoneNo);
    }

    // Constructor to initialize Admin with all fields
    public Admin(Long id, String fullName, String emailAddress, LocalDate dob, String phoneNo, byte[] image) {
        super();
        this.id = id;
        this.fullName = fullName;
        this.emailAddress = emailAddress;
        this.dob = dob;
        this.phoneNo = phoneNo;
        this.image = image;
    }
}
