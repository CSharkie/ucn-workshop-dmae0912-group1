package database;

import model.*;

import java.sql.*;
import java.util.ArrayList;

public class DBCustomer implements IFDBCustomer {
	private Connection con;
	
	public DBCustomer() {
		con = DbConnection1.getInstance().getDBcon();
	}

	@Override
	public ArrayList<Customer> getAllCustomers(boolean retrieveAssociation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Customer searchCustomerById(int customerId,
			boolean retrieveAssociation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Customer searchCustomerByName(String name,
			boolean retrieveAssociation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int insertCustomer(Customer cust) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateCustomer(Customer cust) {
		// TODO Auto-generated method stub
		return 0;
	}

}
