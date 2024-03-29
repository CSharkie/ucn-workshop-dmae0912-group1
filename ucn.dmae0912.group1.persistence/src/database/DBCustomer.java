package database;

import model.Customer;
import java.sql.*;
import java.util.ArrayList;

public class DBCustomer implements IFDBCustomer {
	private Connection con;

	/** Creates a new instance of DBCustomer */
	public DBCustomer() {
		con = DbConnection.getInstance().getDBcon();
	}

	// Implements the methods from the interface
	// get all Customers
	public ArrayList<Customer> getAllCustomers(boolean retriveAssociation) {
		return miscWhere("", retriveAssociation);
	}
	
	// get one Customer having the ID
	public Customer searchCustomerById(int customerId,
			boolean retriveAssociation) {
		String wClause = " CustomerId = '" + customerId + "'";
		return singleWhere(wClause, retriveAssociation);
	}

	// find one Customer having the name
	public ArrayList<Customer> searchCustomersByName(String name, boolean retriveAssociation) {
		String wClause = "Name LIKE '%" + name + "%'";
		System.out.println("Customer " + wClause);
		return miscWhere(wClause, retriveAssociation);
	}

	// insert a new Customer
	public int insertCustomer(Customer cust) throws Exception { // call to get
																// the next Id
																// number
		int nextId = getMax.getMaxId("Select max(customerId) from Customer");
		nextId = nextId + 1;
		System.out.println("next ID = " + nextId);
		cust.setCustomerId(nextId);
		int rc = -1;
		String query = "INSERT INTO Customer(customerId, name, address, zipCode, city, phoneNo)  VALUES('"
				+ cust.getCustomerId()
				+ "','"
				+ cust.getName()
				+ "','"
				+ cust.getAddress()
				+ "','"
				+ cust.getZipCode()
				+ "','"
				+ cust.getCity()
				+ "','"
				+ cust.getPhoneNo() + "');";

		System.out.println("insert : " + query);
		try { // insert new Customer
			Statement stmt = con.createStatement();
			stmt.setQueryTimeout(5);
			rc = stmt.executeUpdate(query);
			stmt.close();
		}// end try
		catch (SQLException ex) {
			System.out.println("Customer is not inserted");
			throw new Exception("Customer is not inserted");
		}
		return (rc);
	}

	@Override
	public int updateCustomer(Customer cust) {
		Customer custObj = cust;
		int rc = -1;

		String query = "UPDATE Customer SET " + "name ='" + custObj.getName()
				+ "', " + "address ='" + custObj.getAddress() + "', "
				+ "zipCode ='" + custObj.getZipCode() + "', " + "city ='"
				+ custObj.getCity() + "', " + "phoneNo = '" + custObj.getPhoneNo()
				+ "' " + " WHERE customerId = '" + custObj.getCustomerId()
				+ "'";
		System.out.println("Update query:" + query);
		try { // update Customer
			Statement stmt = con.createStatement();
			stmt.setQueryTimeout(5);
			rc = stmt.executeUpdate(query);

			stmt.close();
		}// slut try
		catch (Exception ex) {
			System.out.println("Update exception in Customer db: " + ex);
		}
		return (rc);
	}

	public int deleteCustomer(int customerId) {
		int rc = -1;

		String query = "DELETE FROM Customer WHERE customerId = '" + customerId + "'";
		System.out.println(query);
		try { // delete from Customer
			Statement stmt = con.createStatement();
			stmt.setQueryTimeout(5);
			rc = stmt.executeUpdate(query);
			stmt.close();
		}// end try
		catch (Exception ex) {
			System.out.println("Delete exception in Customer db: " + ex);
		}
		return (rc);
	}

	// private methods
	// michWere is used whenever we want to select more than one Customer

	private ArrayList<Customer> miscWhere(String wClause,
			boolean retrieveAssociation) {
		ResultSet results;
		ArrayList<Customer> list = new ArrayList<Customer>();

		String query = buildQuery(wClause);

		try { // read the Customer from the database
			Statement stmt = con.createStatement();
			stmt.setQueryTimeout(5);
			results = stmt.executeQuery(query);

			while (results.next()) {
				Customer custObj = new Customer();
				custObj = buildCustomer(results);
				/*
				if (retrieveAssociation) {
					IFDBSalesOrder salesOrders = new DBSalesOrder();
					ArrayList<SalesOrder> orders = salesOrders.getAllSalesOrdersByCustomerId(custObj.getCustomerId(), false);
					custObj.setSalesOrders(orders);
					System.out.println("Orders are selected");
				}
				*/
				list.add(custObj);
			}// end while
			stmt.close();

		}// end try
		catch (Exception e) {
			System.out.println("Query exception - select: " + e);
			e.printStackTrace();
		}
		return list;
	}

	// Single where is used when we only select one Customer
	private Customer singleWhere(String wClause, boolean retrieveAssociation) {
		ResultSet results;
		Customer custObj = new Customer();

		String query = buildQuery(wClause);
		System.out.println(query);
		try { // read the Customer from the database
			Statement stmt = con.createStatement();
			stmt.setQueryTimeout(5);
			results = stmt.executeQuery(query);

			if (results.next()) {
				custObj = buildCustomer(results);
				stmt.close();
				/*
				if (retrieveAssociation) {
					IFDBSalesOrder salesOrders = new DBSalesOrder();
					ArrayList<SalesOrder> orders = salesOrders.getAllSalesOrdersByCustomerId(custObj.getCustomerId(), false);
					custObj.setSalesOrders(orders);
					System.out.println("Orders are selected");
				}
				*/
			} else { // no Customer was found
				custObj = null;
			}
		}// end try
		catch (Exception e) {
			System.out.println("Query exception: " + e);
		}
		return custObj;
	}

	// method to build the query
	private String buildQuery(String wClause) {
		String query = "SELECT customerId, name, address, zipCode, city, phoneNo  FROM Customer";

		if (wClause.length() > 0)
			query = query + " WHERE " + wClause;

		return query;
	}

	// method to build an Customer object
	private Customer buildCustomer(ResultSet results) {
		Customer custObj = new Customer();
		try { // the columns from the table customer are used
			custObj.setCustomerId(results.getInt("customerId"));
			custObj.setName(results.getString("name"));
			custObj.setAddress(results.getString("address"));
			custObj.setZipCode(results.getInt("zipCode"));
			custObj.setCity(results.getString("city"));
			custObj.setPhoneNo(results.getString("phoneNo"));
		} catch (Exception e) {
			System.out.println("error in building the Customer object");
		}
		return custObj;
	}

}
