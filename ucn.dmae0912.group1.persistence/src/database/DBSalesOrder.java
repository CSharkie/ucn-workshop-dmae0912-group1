package database;

import java.util.ArrayList;

import model.Customer;
import model.SalesOrder;
import model.Invoice;
import java.sql.*;

public class DBSalesOrder implements IFDBSalesOrder {

	private Connection con;

	/** Creates a new instance of DBSalesOrder */
	public DBSalesOrder() {
		con = DbConnection.getInstance().getDBcon();
	}

	public ArrayList<SalesOrder> getAllSalesOrdersByCustomerId(int customerId,
			boolean retrieveAssociation) {
		String wClause = " CustomerId = '" + customerId + "'";
		return miscWhere(wClause, retrieveAssociation);
	}

	public ArrayList<SalesOrder> getAllSalesOrders(boolean retriveAssociation) {
		String wClause = "";
		return miscWhere(wClause, retriveAssociation);
	}

	public SalesOrder getSaleOrderById(int salesOrderId,
			boolean retriveAssociation) {
		String wClause = " salesOrderId = '" + salesOrderId + "'";
		return singleWhere(wClause, retriveAssociation);
	}

	@Override
	public int insertSalesOrder(SalesOrder salesOrder) throws Exception {
		int nextId = getMax
				.getMaxId("Select max(salesOrderId) from SalesOrder");
		nextId = nextId + 1;
		salesOrder.setSalesOrderId(nextId);
		System.out.println("next ID = " + nextId);

		int rc = -1;
		java.sql.Date date = new java.sql.Date(salesOrder.getDate().getTime());
		java.sql.Date deliveryDate = new java.sql.Date(salesOrder
				.getDeliveryDate().getTime());
		int deliveryStatus = 0;
		if (salesOrder.getDeliveryStatus())
			deliveryStatus = 1;
		String query = "INSERT INTO SalesOrder(salesOrderId, customerId, date, deliveryDate, deliveryStatus, InvoiceNo)  VALUES('"
				+ salesOrder.getSalesOrderId()
				+ "','"
				+ salesOrder.getCustomer().getCustomerId()
				+ "','"
				+ date
				+ "','"
				+ deliveryDate
				+ "','"
				+ deliveryStatus
				+ "','"
				+ salesOrder.getInvoice().getInvoiceNo() + "');";

		System.out.println("insert : " + query);
		try { // insert new SalesOrder
			Statement stmt = con.createStatement();
			stmt.setQueryTimeout(5);
			rc = stmt.executeUpdate(query);
			stmt.close();
		}// end try
		catch (SQLException ex) {
			System.out.println("SalesOrder is not inserted");
			throw new Exception("SalesOrder is not inserted");
		}
		return (rc);
	}

	@Override
	public int updateSalesOrder(SalesOrder salesOrder) {
		SalesOrder salesOrderObj = salesOrder;
		int rc = -1;

		String query = "UPDATE SalesOrder SET " + "date ='"
				+ salesOrderObj.getDate() + "', " + "delivery date ='"
				+ salesOrderObj.getDeliveryDate() + "', "
				+ "delivery status ='" + salesOrderObj.getDeliveryStatus()
				+ "' " + " WHERE SalesOrderId = '"
				+ salesOrderObj.getSalesOrderId() + "'";
		System.out.println("Update query:" + query);
		try { // update SalesOrder
			Statement stmt = con.createStatement();
			stmt.setQueryTimeout(5);
			rc = stmt.executeUpdate(query);

			stmt.close();
		}// end try
		catch (Exception ex) {
			System.out.println("Update exception in SalesOrder db: " + ex);
		}
		return (rc);
	}

	public int deleteSalesOrder(int salesOrderId) {
		int rc = -1;

		String query = "DELETE FROM SalesORder WHERE ID = '" + salesOrderId
				+ "'";
		System.out.println(query);
		try { // delete from SalesOrder
			Statement stmt = con.createStatement();
			stmt.setQueryTimeout(5);
			rc = stmt.executeUpdate(query);
			stmt.close();
		}// end try
		catch (Exception ex) {
			System.out.println("Delete exception in SalesOrder db: " + ex);
		}
		return (rc);
	}

	// private methods
	// michWere is used whenever we want to select more than one SalesOrder

	private ArrayList<SalesOrder> miscWhere(String wClause,
			boolean retrieveAssociation) {
		ResultSet results;
		ArrayList<SalesOrder> list = new ArrayList<SalesOrder>();

		String query = buildQuery(wClause);

		try { // read the SalesOrder from the database
			Statement stmt = con.createStatement();
			stmt.setQueryTimeout(5);
			results = stmt.executeQuery(query);

			while (results.next()) {
				SalesOrder salesOrderObj = new SalesOrder();
				salesOrderObj = buildSalesOrder(results);
				list.add(salesOrderObj);
			}
			stmt.close();
			if (retrieveAssociation) {
				for (SalesOrder salesOrderObj : list) {
					// customer
					IFDBCustomer dbCustObj = new DBCustomer();
					Customer custObj = dbCustObj.searchCustomerById(
							salesOrderObj.getCustomer().getCustomerId(), false);
					System.out.println("Customer is selected ");
					salesOrderObj.setCustomer(custObj);
					// invoice
					IFDBInvoice dbInvObj = new DBInvoice();
					Invoice invObj = dbInvObj.searchInvoiceByNo(salesOrderObj
							.getInvoice().getInvoiceNo(), false);
					System.out.println("Invoice is selected ");
					salesOrderObj.setInvoice(invObj);
				}
			}
		}// end try
		catch (Exception e) {
			System.out.println("Query exception - select: " + e);
			e.printStackTrace();
		}
		return list;
	}

	// Single where is used when we only select one SalesOrder
	private SalesOrder singleWhere(String wClause, boolean retrieveAssociation) {
		ResultSet results;
		SalesOrder salesOrderObj = new SalesOrder();

		String query = buildQuery(wClause);
		System.out.println(query);
		try { // read the SalesOrder from the database
			Statement stmt = con.createStatement();
			stmt.setQueryTimeout(5);
			results = stmt.executeQuery(query);

			if (results.next()) {
				salesOrderObj = buildSalesOrder(results);
				stmt.close();
				if (retrieveAssociation) {
					// customer
					IFDBCustomer dbCustObj = new DBCustomer();
					Customer custObj = dbCustObj.searchCustomerById(
							salesOrderObj.getCustomer().getCustomerId(), false);
					System.out.println("Customer is selected ");
					salesOrderObj.setCustomer(custObj);
					// invoice
					IFDBInvoice dbInvObj = new DBInvoice();
					Invoice invObj = dbInvObj.searchInvoiceByNo(salesOrderObj
							.getInvoice().getInvoiceNo(), false);
					System.out.println("Invoice is selected ");
					salesOrderObj.setInvoice(invObj);
				}
			} else { // no SalesOrder was found
				salesOrderObj = null;
			}
		}// end try
		catch (Exception e) {
			System.out.println("Query exception: " + e);
		}
		return salesOrderObj;
	}

	// method to build the query
	private String buildQuery(String wClause) {
		String query = "SELECT salesOrderId, date, deliveryDate, deliveryStatus, customerId, invoiceNo FROM SalesOrder";

		if (wClause.length() > 0)
			query = query + " WHERE " + wClause;

		return query;
	}

	// method to build an SalesOrder object
	private SalesOrder buildSalesOrder(ResultSet results) {
		SalesOrder salesOrderObj = new SalesOrder();
		try { // the columns from the table SalesOrder are used
			salesOrderObj.setSalesOrderId(results.getInt("SalesOrderId"));
			salesOrderObj.setDate(results.getDate("date"));
			salesOrderObj.setDeliveryDate(results.getDate("deliveryDate"));
			salesOrderObj.setDeliveryStatus(results
					.getBoolean("deliveryStatus"));
			salesOrderObj
					.setCustomer(new Customer(results.getInt("customerId")));
			salesOrderObj.setInvoice(new Invoice(results.getInt("invoiceNo")));
		} catch (Exception e) {
			System.out.println("error in building the SalesOrder object");
		}
		return salesOrderObj;
	}

	@Override
	public int deleteOrder(int orderId) {
		int rc = -1;

		String query = "DELETE FROM SalesOrder WHERE SalesOrderId = '"
				+ orderId + "'";
		System.out.println(query);
		try { // delete from SalesOrder
			Statement stmt = con.createStatement();
			stmt.setQueryTimeout(5);
			rc = stmt.executeUpdate(query);
			stmt.close();
		}// end try
		catch (Exception ex) {
			System.out.println("Delete exception in SalesOrder db: " + ex);
		}
		return (rc);
	}
}
