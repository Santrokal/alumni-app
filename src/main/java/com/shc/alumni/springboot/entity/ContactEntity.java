package com.shc.alumni.springboot.entity;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "contact_us")
public class ContactEntity {
	
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;
	    private String emailAddress;
	    private String fullName;
	    @Column(nullable = false, columnDefinition = "TEXT")
	    private String addinfo;
	    private String phoneNo;
	    @Column(nullable = false)
	    private boolean skipped = false;
	    
	    
	    
		public boolean isSkipped() {
			return skipped;
		}
		public void setSkipped(boolean skipped) {
			this.skipped = skipped;
		}
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public String getEmailAddress() {
			return emailAddress;
		}
		public void setEmailAddress(String emailAddress) {
			this.emailAddress = emailAddress;
		}
		public String getFullName() {
			return fullName;
		}
		public void setFullName(String fullName) {
			this.fullName = fullName;
		}
		public String getAddinfo() {
			return addinfo;
		}
		public void setAddinfo(String addinfo) {
			this.addinfo = addinfo;
		}
		public String getPhoneNo() {
			return phoneNo;
		}
		public void setPhoneNo(String phoneNo) {
			this.phoneNo = phoneNo;
		}
		public ContactEntity() {

		}
		@Override
		public int hashCode() {
			return Objects.hash(addinfo, emailAddress, fullName, id, phoneNo);
		}
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			ContactEntity other = (ContactEntity) obj;
			return Objects.equals(addinfo, other.addinfo) && Objects.equals(emailAddress, other.emailAddress)
					&& Objects.equals(fullName, other.fullName) && Objects.equals(id, other.id)
					&& Objects.equals(phoneNo, other.phoneNo);
		}
		@Override
		public String toString() {
			return "ContactEntity [id=" + id + ", emailAddress=" + emailAddress + ", fullName=" + fullName
					+ ", addinfo=" + addinfo + ", phoneNo=" + phoneNo + "]";
		}
		
	    
	    

}
