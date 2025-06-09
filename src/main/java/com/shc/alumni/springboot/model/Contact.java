package com.shc.alumni.springboot.model;


public class Contact {
	

	    private Long id;
	    private String emailAddress;
	    private String fullName;
	    private String addinfo;
	    private String phoneNo;
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
		public Contact() {
		}
}