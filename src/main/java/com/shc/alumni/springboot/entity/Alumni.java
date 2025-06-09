package com.shc.alumni.springboot.entity;


import javax.persistence.*;

@Entity
@Table(name = "alumni")
public class Alumni {
    @Id
    private String memberId;
    private String membershipType; // "life" or "regular"
    private boolean isActive;

    // Getters and Setters
    public String getMemberId() { return memberId; }
    public void setMemberId(String memberId) { this.memberId = memberId; }
    public String getMembershipType() { return membershipType; }
    public void setMembershipType(String membershipType) { this.membershipType = membershipType; }
    public boolean isActive() { return isActive; }
    public void setActive(boolean active) { isActive = active; }
}