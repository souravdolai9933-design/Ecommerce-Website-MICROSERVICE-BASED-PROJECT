package com.example.demo.OrderDto;

import java.util.List;

 

public class CustomerDTO {
	
	    private Long cusid;

	    private String name;
	    private String password;
	    private Long phoneno;
		public Long getCusid() {
			return cusid;
		}
		public void setCusid(Long cusid) {
			this.cusid = cusid;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
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
		@Override
		public String toString() {
			return "CustomerDTO [cusid=" + cusid + ", name=" + name + ", password=" + password + ", phoneno=" + phoneno
					+ "]";
		}

}
