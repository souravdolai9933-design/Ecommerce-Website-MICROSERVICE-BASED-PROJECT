package com.example.demo.Dto;

public class ShippingAddressDTO {
	
	
	    private Long addid;
	    private Long rootUserId;
	    
	    private String receiverName;
	    private Long receiverPhnNo;
	    
	    private String houseno;
	    private String city;
	    private String state;
	    private String country;
	    private Integer zipcode;
	    private boolean isdefault;
		public Long getAddid() {
			return addid;
		}
		public void setAddid(Long addid) {
			this.addid = addid;
		}
		public Long getRootUserId() {
			return rootUserId;
		}
		public void setRootUserId(Long rootUserId) {
			this.rootUserId = rootUserId;
		}
		public String getReceiverName() {
			return receiverName;
		}
		public void setReceiverName(String receiverName) {
			this.receiverName = receiverName;
		}
		public Long getReceiverPhnNo() {
			return receiverPhnNo;
		}
		public void setReceiverPhnNo(Long receiverPhnNo) {
			this.receiverPhnNo = receiverPhnNo;
		}
		public String getHouseno() {
			return houseno;
		}
		public void setHouseno(String houseno) {
			this.houseno = houseno;
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
		public String getCountry() {
			return country;
		}
		public void setCountry(String country) {
			this.country = country;
		}
		public Integer getZipcode() {
			return zipcode;
		}
		public void setZipcode(Integer zipcode) {
			this.zipcode = zipcode;
		}
		public boolean isIsdefault() {
			return isdefault;
		}
		public void setIsdefault(boolean isdefault) {
			this.isdefault = isdefault;
		}
		@Override
		public String toString() {
			return "ShippingAddressDTO [addid=" + addid + ", rootUserId=" + rootUserId + ", receiverName="
					+ receiverName + ", receiverPhnNo=" + receiverPhnNo + ", houseno=" + houseno + ", city=" + city
					+ ", state=" + state + ", country=" + country + ", zipcode=" + zipcode + ", isdefault=" + isdefault
					+ "]";
		}
	    
	    

}
