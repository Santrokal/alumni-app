package com.shc.alumni.springboot.model;

import java.time.LocalDate;
import java.util.Objects;




public class AlumniRegister {
	
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
    public LocalDate shc_stay_from;
    public LocalDate shc_stay_to;
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
    
    
	public String getShift() {
		return shift;
	}


	public void setShift(String shift) {
		this.shift = shift;
	}


	@Override
	public int hashCode() {
		return Objects.hash(address_type, anniversary_year, area, city, degree_obtained, department, dob, emailaddress,
				empstatus, fathersname, fieldofexpert, fullname, gender, house_flat_number, id, jobdesig, landmark,
				marital_status, nationality, officeemail, officephoneno, phoneno, postal_code, shc_stay_from,
				shc_stay_to, shift, state, street_name, whatsappno);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AlumniRegister other = (AlumniRegister) obj;
		return Objects.equals(address_type, other.address_type)
				&& Objects.equals(anniversary_year, other.anniversary_year) && Objects.equals(area, other.area)
				&& Objects.equals(city, other.city) && Objects.equals(degree_obtained, other.degree_obtained)
				&& Objects.equals(department, other.department) && Objects.equals(dob, other.dob)
				&& Objects.equals(emailaddress, other.emailaddress) && Objects.equals(empstatus, other.empstatus)
				&& Objects.equals(fathersname, other.fathersname) && Objects.equals(fieldofexpert, other.fieldofexpert)
				&& Objects.equals(fullname, other.fullname) && Objects.equals(gender, other.gender)
				&& Objects.equals(house_flat_number, other.house_flat_number) && id == other.id
				&& Objects.equals(jobdesig, other.jobdesig) && Objects.equals(landmark, other.landmark)
				&& Objects.equals(marital_status, other.marital_status)
				&& Objects.equals(nationality, other.nationality) && Objects.equals(officeemail, other.officeemail)
				&& Objects.equals(officephoneno, other.officephoneno) && Objects.equals(phoneno, other.phoneno)
				&& Objects.equals(postal_code, other.postal_code) && Objects.equals(shc_stay_from, other.shc_stay_from)
				&& Objects.equals(shc_stay_to, other.shc_stay_to) && Objects.equals(shift, other.shift)
				&& Objects.equals(state, other.state) && Objects.equals(street_name, other.street_name)
				&& Objects.equals(whatsappno, other.whatsappno);
	}


	@Override
	public String toString() {
		return "AlumniRegister [id=" + id + ", fullname=" + fullname + ", fathersname=" + fathersname + ", nationality="
				+ nationality + ", dob=" + dob + ", gender=" + gender + ", house_flat_number=" + house_flat_number
				+ ", street_name=" + street_name + ", city=" + city + ", state=" + state + ", postal_code="
				+ postal_code + ", landmark=" + landmark + ", area=" + area + ", address_type=" + address_type
				+ ", shift=" + shift + ", department=" + department + ", degree_obtained=" + degree_obtained
				+ ", shc_stay_from=" + shc_stay_from + ", shc_stay_to=" + shc_stay_to + ", marital_status="
				+ marital_status + ", anniversary_year=" + anniversary_year + ", whatsappno=" + whatsappno
				+ ", phoneno=" + phoneno + ", emailaddress=" + emailaddress + ", empstatus=" + empstatus + ", jobdesig="
				+ jobdesig + ", officephoneno=" + officephoneno + ", officeemail=" + officeemail + ", fieldofexpert="
				+ fieldofexpert + "]";
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


	public LocalDate getShc_stay_from() {
		return shc_stay_from;
	}


	public void setShc_stay_from(LocalDate shc_stay_from) {
		this.shc_stay_from = shc_stay_from;
	}


	public LocalDate getShc_stay_to() {
		return shc_stay_to;
	}


	public void setShc_stay_to(LocalDate shc_stay_to) {
		this.shc_stay_to = shc_stay_to;
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


	public AlumniRegister() {

	}

    
    
}
