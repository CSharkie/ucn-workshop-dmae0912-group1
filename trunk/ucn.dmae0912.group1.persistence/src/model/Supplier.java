package model;

public class Supplier {
	
	int supplierId;
	String name;
	String address;
	String country;
	String phoneNo;
	String email;

	public Supplier(int supplierId, String name, String address,
			String country, String phoneNo, String email) {
		this.supplierId = supplierId;
		this.name = name;
		this.address = address;
		this.country = country;
		this.phoneNo = phoneNo;
		this.email = email;
	}
	
	public int getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(int supplierId) {
		this.supplierId = supplierId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
