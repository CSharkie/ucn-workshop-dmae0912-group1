package database;

import model.Invoice;

import java.sql.*;
import java.util.ArrayList;

public class DBInvoice implements IFDBInvoice {
	private Connection con;

	/** Creates a new instance of DBInvoice */
	public DBInvoice() {
		con = DbConnection.getInstance().getDBcon();
	}

	// Implements the methods from the interface
	// get all Invoices
	public ArrayList<Invoice> getAllInvoices(boolean retrieveAssociation) {
		return miscWhere("", retrieveAssociation);
	}

	// get one Invoice having the No
	public Invoice searchInvoiceByNo(int invoiceNo, boolean retrieveAssociation) {
		String wClause = " invoiceNo = '" + invoiceNo + "'";
		return singleWhere(wClause, retrieveAssociation);
	}

	// insert a new Invoice
	public int insertInvoice(Invoice inv) throws Exception { // call to get
																// the next
																// invoice
																// number
		int nextId = getMax.getMaxId("Select max(invoiceNo) from Invoice");
		nextId = nextId + 1;
		inv.setInvoiceNo(nextId);
		System.out.println("next ID = " + nextId);

		int rc = -1;
		String query = "INSERT INTO Invoice(invoiceNo, price)  VALUES('"
				+ inv.getInvoiceNo() + "','" + inv.getPrice() + "');";

		System.out.println("insert : " + query);
		try { // insert new Invoice
			Statement stmt = con.createStatement();
			stmt.setQueryTimeout(5);
			rc = stmt.executeUpdate(query);
			stmt.close();
		}// end try
		catch (SQLException ex) {
			System.out.println("Invoice is not inserted");
			throw new Exception("Invoice is not inserted");
		}
		return (rc);
	}

	@Override
	public int updateInvoice(Invoice inv) {
		Invoice invObj = inv;
		int rc = -1;
		
		String payment = "";
		if (invObj.getPaymentDate() != null) {
			java.sql.Date date = new java.sql.Date(invObj.getPaymentDate()
					.getTime());
			payment = "paymentDate ='" + date + "', ";
		}

		String query = "UPDATE Invoice SET " + payment
				+ "price ='" + invObj.getPrice() + "' "
				+ " WHERE invoiceNo = '" + invObj.getInvoiceNo() + "'";
		System.out.println("Update query:" + query);
		try { // update Invoice
			Statement stmt = con.createStatement();
			stmt.setQueryTimeout(5);
			rc = stmt.executeUpdate(query);

			stmt.close();
		}// end try
		catch (Exception ex) {
			System.out.println("Update exception in Invoice db: " + ex);
		}
		return (rc);
	}

	public int delete(int InvoiceNo) {
		int rc = -1;

		String query = "DELETE FROM Invoice WHERE No = '" + InvoiceNo + "'";
		System.out.println(query);
		try { // delete from Invoice
			Statement stmt = con.createStatement();
			stmt.setQueryTimeout(5);
			rc = stmt.executeUpdate(query);
			stmt.close();
		}// end try
		catch (Exception ex) {
			System.out.println("Delete exception in Invoice db: " + ex);
		}
		return (rc);
	}

	// private methods
	// michWere is used whenever we want to select more than one Invoice

	private ArrayList<Invoice> miscWhere(String wClause,
			boolean retrieveAssociation) {
		ResultSet results;
		ArrayList<Invoice> list = new ArrayList<Invoice>();

		String query = buildQuery(wClause);

		try { // read the Invoice from the database
			Statement stmt = con.createStatement();
			stmt.setQueryTimeout(5);
			results = stmt.executeQuery(query);

			while (results.next()) {
				Invoice invObj = new Invoice();
				invObj = buildInvoice(results);
				list.add(invObj);
			}// end while
			stmt.close();

		}// end try
		catch (Exception e) {
			System.out.println("Query exception - select: " + e);
			e.printStackTrace();
		}
		return list;
	}

	// SingleWhere is used when we only select one Invoice
	private Invoice singleWhere(String wClause, boolean retrieveAssociation) {
		ResultSet results;
		Invoice invObj = new Invoice();

		String query = buildQuery(wClause);
		System.out.println(query);
		try { // read the Invoice from the database
			Statement stmt = con.createStatement();
			stmt.setQueryTimeout(5);
			results = stmt.executeQuery(query);

			if (results.next()) {
				invObj = buildInvoice(results);
				stmt.close();

			} else { // no Invoice was found
				invObj = null;
			}
		}// end try
		catch (Exception e) {
			System.out.println("Query exception: " + e);
		}
		return invObj;
	}

	// method to build the query
	private String buildQuery(String wClause) {
		String query = "SELECT invoiceNo, paymentDate, price FROM Invoice";

		if (wClause.length() > 0)
			query = query + " WHERE " + wClause;

		return query;
	}

	// method to build an Invoice object
	private Invoice buildInvoice(ResultSet results) {
		Invoice invObj = new Invoice();
		try { // the columns from the table invoice are used
			invObj.setInvoiceNo(results.getInt("invoiceNo"));
			invObj.setPaymentDate(results.getDate("paymentDate"));
			invObj.setPrice(results.getInt("price"));

		} catch (Exception e) {
			System.out.println("error in building the Invoice object");
		}
		return invObj;
	}
}