package com.bta.profiles.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Entity
@Table(name = "AccountHolders")
public class AccountHolder {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long accountHolderId;
	
	@NotBlank(message = "fullName cannot be empty")
	private String fullName;
	
	@NotBlank(message = "mobileNumber cannot be empty")
	@Pattern(regexp = "[1-9][0-9]{9}", message = "Only ten digits are expected for mobileNumber")
	private String mobileNumber;
	
	@NotBlank(message = "EmailId cannot be empty")
	@Email(message = "A valid emailId expected")
	private String emailId;
	
	public AccountHolder() {
		
	}
	
	public AccountHolder(Long accountHolderId, @NotBlank(message = "fullName is mandatory") String fullName,
			@NotBlank(message = "mobileNumber is mandate") @Pattern(regexp = "[1-9][0-9] {9}", message = "Only ten digits are expected for mobile") String mobileNumber,
			@NotBlank(message = "emailId is mandate") @Email(message = "A valid emailId expected") String emailId) {
		super();
		this.accountHolderId = accountHolderId;
		this.fullName = fullName;
		this.mobileNumber = mobileNumber;
		this.emailId = emailId;
	}

	public Long getAccountHolderId() {
		return accountHolderId;
	}

	public void setAccountHolderId(Long accountHolderId) {
		this.accountHolderId = accountHolderId;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
}
