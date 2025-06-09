package com.shc.alumni.springboot.entity;

import java.time.LocalDate;
//import java.util.Arrays;
//import java.util.Objects;

import javax.persistence.*;

@Entity
@Table(name = "alumni_register")
public class AlumniRegisterEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;
    public String fullname;
    public String fathersname;
    public String nationality;
    public LocalDate dob;
    public String gender;
    public String house_flat_number;
    public String street_name;
    public String city;
    public String state;
    public String postal_code;
    public String landmark;
    public String area;
    public String address_type;
    public String shift;
    public String department;
    public String degree_obtained;
    @Column(name = "shc_stay_from")
    public String shcStayFrom;
    @Column(name = "shc_stay_to")
    public String shcStayTo;
    public String marital_status;
    public LocalDate anniversary_year;
    public String whatsappno;
    public String phoneno;
    public String emailaddress;
    public String empstatus;
    public String jobdesig;
    public String officephoneno;
    public String officeemail;
    public String fieldofexpert;

    @Transient
    private String base64Photograph;
    
    
    
    @Column(name = "file_path", nullable = true)
    private String filePath;
    
    public String getFilePath() {
        if (filePath != null && (filePath.contains("\\") || filePath.contains("/"))) {
            return filePath.substring(filePath.lastIndexOf("/") + 1); // Extract filename
        }
        return filePath;
    }


    public void setFilePath(String filePath) {
        if (filePath != null) {
            this.filePath = filePath.substring(filePath.lastIndexOf("/") + 1);
        }
    }


	public String getShcStayFrom() {
		return shcStayFrom;
	}





	public void setShcStayFrom(String shc_stay_from) {
		this.shcStayFrom = shc_stay_from;
	}





	public String getShcStayTo() {
		return shcStayTo;
	}





	public void setShcStayTo(String shcStayTo) {
		this.shcStayTo = shcStayTo;
	}





	public String getShift() {
		return shift;
	}





	public void setShift(String shift) {
		this.shift = shift;
	}





	public int getId() {
		return id;
	}





	public void setId(int id) {
		this.id = id;
	}





	public String getFullname() {
		return fullname;
	}





	public void setFullname(String fullname) {
		this.fullname = fullname;
	}





	public String getFathersname() {
		return fathersname;
	}





	public void setFathersname(String fathersname) {
		this.fathersname = fathersname;
	}





	public String getNationality() {
		return nationality;
	}





	public void setNationality(String nationality) {
		this.nationality = nationality;
	}





	public LocalDate getDob() {
		return dob;
	}





	public void setDob(LocalDate dob) {
		this.dob = dob;
	}





	public String getGender() {
		return gender;
	}





	public void setGender(String gender) {
		this.gender = gender;
	}





	public String getHouse_flat_number() {
		return house_flat_number;
	}





	public void setHouse_flat_number(String house_flat_number) {
		this.house_flat_number = house_flat_number;
	}





	public String getStreet_name() {
		return street_name;
	}





	public void setStreet_name(String street_name) {
		this.street_name = street_name;
	}





	public String getCity() {
		return city;
	}





	public void setCity(String city) {
		this.city = city;
	}





	public String getState() {
		return state;
	}





	public void setState(String state) {
		this.state = state;
	}





	public String getPostal_code() {
		return postal_code;
	}





	public void setPostal_code(String postal_code) {
		this.postal_code = postal_code;
	}





	public String getLandmark() {
		return landmark;
	}





	public void setLandmark(String landmark) {
		this.landmark = landmark;
	}





	public String getArea() {
		return area;
	}





	public void setArea(String area) {
		this.area = area;
	}





	public String getAddress_type() {
		return address_type;
	}





	public void setAddress_type(String address_type) {
		this.address_type = address_type;
	}





	public String getDepartment() {
		return department;
	}





	public void setDepartment(String department) {
		this.department = department;
	}





	public String getDegree_obtained() {
		return degree_obtained;
	}





	public void setDegree_obtained(String degree_obtained) {
		this.degree_obtained = degree_obtained;
	}











	public String getMarital_status() {
		return marital_status;
	}





	public void setMarital_status(String marital_status) {
		this.marital_status = marital_status;
	}





	public LocalDate getAnniversary_year() {
		return anniversary_year;
	}





	public void setAnniversary_year(LocalDate anniversary_year) {
		this.anniversary_year = anniversary_year;
	}





	public String getWhatsappno() {
		return whatsappno;
	}





	public void setWhatsappno(String whatsappno) {
		this.whatsappno = whatsappno;
	}





	public String getPhoneno() {
		return phoneno;
	}





	public void setPhoneno(String phoneno) {
		this.phoneno = phoneno;
	}





	public String getEmailaddress() {
		return emailaddress;
	}





	public void setEmailaddress(String emailaddress) {
		this.emailaddress = emailaddress;
	}





	public String getEmpstatus() {
		return empstatus;
	}





	public void setEmpstatus(String empstatus) {
		this.empstatus = empstatus;
	}





	public String getJobdesig() {
		return jobdesig;
	}





	public void setJobdesig(String jobdesig) {
		this.jobdesig = jobdesig;
	}





	public String getOfficephoneno() {
		return officephoneno;
	}





	public void setOfficephoneno(String officephoneno) {
		this.officephoneno = officephoneno;
	}

	public String getOfficeemail() {
		return officeemail;
	}

	public void setOfficeemail(String officeemail) {
		this.officeemail = officeemail;
	}

	public String getFieldofexpert() {
		return fieldofexpert;
	}

	public void setFieldofexpert(String fieldofexpert) {
		this.fieldofexpert = fieldofexpert;
	}


	public String getBase64Photograph() {
		return base64Photograph;
	}

	public void setBase64Photograph(String base64Photograph) {
		this.base64Photograph = base64Photograph;
	}


	public AlumniRegisterEntity() {
	}
    
    

}
