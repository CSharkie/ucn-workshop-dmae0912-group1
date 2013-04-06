package controller;

import model.Customer;
import database.*;

import java.util.ArrayList;

public class CustomerCtr {
	
	public CustomerCtr(){
		
	}
	public ArrayList<Customer> getAllCustomers(){
		IFDBCustomer dbCust=new DBCustomer();
		ArrayList<Customer> allCust=new ArrayList<Customer>();
		allCust=dbCust.getAllCustomers(false);
		return allCust;
	}
	public Customer searchCustomerByName(String name){
		IFDBCustomer dbCust=new DBCustomer();
		return dbCust.searchCustomerByName(name, true);
	}
	public Customer searchCustomerById(int customerId){
		IFDBCustomer dbCust=new DBCustomer();
		return dbCust.searchCustomerById(customerId, true);
	}
	public int updateCustomer(int customerId, String name, String address, int zipCode, String city, String phoneNo){
		IFDBCustomer dbCust=new DBCustomer();
		Customer cust=new Customer(customerId, name, address, zipCode, city, phoneNo);
		/*cust.setCustomerId(customerId);
		cust.setName(name);
		cust.setAddress(address);
		cust.setZipCode(zipCode);
		cust.setCity(city);
		cust.setPhoneNo(phoneNo); */
		return dbCust.updateCustomer(cust);
	}
	
	public void insertCustomer(int customerId, String name, String address, int zipCode, String city, String phoneNo){
		Customer custObj=new Customer(customerId, name, address, zipCode, city, phoneNo);
		
		try{
			DbConnection.startTransaction();
			DBCustomer dbCust=new DBCustomer();
			dbCust.insertCustomer(custObj);
			DbConnection.commitTransaction();
		}
		catch(Exception e)
		{
			DbConnection.rollbackTransaction();
		}
	}
}
