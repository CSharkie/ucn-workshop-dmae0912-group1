package model;

import java.util.ArrayList;

public class Customer {
	
	int customerId;
	String name;
	String address;
	int zipCode;
	String city;
	String phoneNo;
	ArrayList<SalesOrder> saleOrders;
	
	public Customer(int customerId, String name, String address, int zipCode, String city, String phoneNo){
		this.customerId=customerId;
		this.name=name;
		this.address=address;
		this.zipCode=zipCode;
		this.city=city;
		this.phoneNo=phoneNo;
		saleOrders=new ArrayList<SalesOrder>();
	}
	public void addSale(SalesOrder saleOrder){
		saleOrders.add(saleOrder);
	}
	public int getCustomerId() {
		return customerId;
	}
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
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
	public int getZipCode() {
		return zipCode;
	}
	public void setZipCode(int zipCode) {
		this.zipCode = zipCode;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	public void print(){
		System.out.println("Customer Id: " + customerId);
		System.out.println("Name: " + name);
		System.out.println("Address: " + address);
		System.out.println("ZipCode: " + zipCode);
		System.out.println("City: " + city);
		System.out.println("phoneNo: " + phoneNo);
		
	}

}
