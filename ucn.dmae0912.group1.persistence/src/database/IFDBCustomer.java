package database;

import model.Customer;
import java.util.ArrayList;

public interface IFDBCustomer {
	//getAllCustomers
	public ArrayList<Customer> getAllCustomers(boolean retrieveAssociation);
    //get one customer by ID
	public Customer searchCustomerById(int customerId, boolean retrieveAssociation);
	//get one employee by name
	public ArrayList<Customer> searchCustomersByName(String name, boolean retrieveAssociation);
	
	//insert a new customer
	public int insertCustomer(Customer cust) throws Exception;
	//update information about a customer;
	public int updateCustomer(Customer cust);
	
	public int deleteCustomer(int customerId);
}
