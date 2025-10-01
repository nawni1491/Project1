package com.stock.userproflie.model;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

public class UserProfileDto {
	
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	@NotEmpty(message = "FullName is required")
    private String fullname;
	
	@NotEmpty(message ="Email is required")
	private String email;
	
	@NotEmpty(message ="Phone is required")
	@Pattern(regexp = "^[0-9],{7,15}$",message = "Phone must be digits (7-15 characters)")
	private String phone;
	
	@NotEmpty(message ="Address is required")
	private String address;
	
	private MultipartFile imagePath;
	private String gender;

	// ----Getter and Setter -----
	public String getFullName() {
		return fullname;
	}

	public void setFullName(String fullname) {
		this.fullname = fullname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public MultipartFile getImagePath() {
		return imagePath;
	}

	public void setImageFile(MultipartFile imagePath) {
		this.imagePath = imagePath;
	}

	public String getGender() {
		// TODO Auto-generated method stub
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}
	
	
	
	
}
