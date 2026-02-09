package com.example.demo.RootcustomerDTO;

 

public class RegistrationRequestDTO {
	
	private Long id;
	private String name;
	private String email;	 
	private String password;
	private String confirmpassword;
	private Long phoneno;
	
	private boolean enable = true;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Long getPhoneno() {
		return phoneno;
	}

	public void setPhoneno(Long phoneno) {
		this.phoneno = phoneno;
	}

	public boolean isEnable() {
		return enable;
	}

	
	public String getConfirmpassword() {
		return confirmpassword;
	}

	public void setConfirmpassword(String confirmpassword) {
		this.confirmpassword = confirmpassword;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}
}
