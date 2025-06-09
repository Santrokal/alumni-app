package com.shc.alumni.springboot.model;

import java.time.LocalDateTime;
import java.util.Objects;


public class Membership {
	
	public int id;
    public String payment_id;
    public String member_id;
    public String emailaddress;
    public String fullname;
    public LocalDateTime createdAt;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPayment_id() {
		return payment_id;
	}
	public void setPayment_id(String payment_id) {
		this.payment_id = payment_id;
	}
	public String getMember_id() {
		return member_id;
	}
	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}
	public String getEmailaddress() {
		return emailaddress;
	}
	public void setEmailaddress(String emailaddress) {
		this.emailaddress = emailaddress;
	}
	public String getFullname() {
		return fullname;
	}
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	@Override
	public int hashCode() {
		return Objects.hash(createdAt, emailaddress, fullname, id, member_id, payment_id);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Membership other = (Membership) obj;
		return Objects.equals(createdAt, other.createdAt) && Objects.equals(emailaddress, other.emailaddress)
				&& Objects.equals(fullname, other.fullname) && id == other.id
				&& Objects.equals(member_id, other.member_id) && Objects.equals(payment_id, other.payment_id);
	}
	public Membership() {
	}
	@Override
	public String toString() {
		return "Membership [id=" + id + ", payment_id=" + payment_id + ", member_id=" + member_id + ", emailaddress="
				+ emailaddress + ", fullname=" + fullname + ", createdAt=" + createdAt + "]";
	}
    
    
    

}
