package database;

import java.util.ArrayList;

import model.Customer;
import model.SalesOrder;
import java.sql.*;

import com.sun.org.apache.xml.internal.utils.StringToIntTable;

import sun.io.Converters;


public class DBSalesOrder implements IFDBSalesOrder  {

	private Connection con;

	/** Creates a new instance of DBSalesOrder */
	public DBSalesOrder() {
		con = DbConnection.getInstance().getDBcon();
	}

	public ArrayList<SalesOrder> getAllSalesOrdersByCustomerId(int customerId, boolean retrieveAssociation){
		return miscWhere(customerId, retrieveAssociation);		
	}
	
	public ArrayList<SalesOrder> getAllSalesOrders(boolean retriveAssociation) {
		return miscWhere("", retriveAssociation);
	}


	public SalesOrder getSaleOrderById(int customerId,
			boolean retriveAssociation) {
		String wClause = "  Customer ID: = '" + customerId + "'";
		return singleWhere(wClause, retriveAssociation);
	}

	@Override
	public int insertSalesOrder(SalesOrder salesOrder) throws Exception {
		int nextId = getMax.getMaxId("Select max(salesOrderId) from SalesOrder");
		nextId = nextId + 1;
		System.out.println("next ID = " + nextId);

		int rc = -1;
		String query = "INSERT INTO SalesOrder(salesOrderId, date, amount, deliveryDate, deliveryStatus)  VALUES('"
				+ nextId
				+ "','"
				+ salesOrder.getDate()
				+ "','"
				+ salesOrder.getAmount()
				+ "','"
				+ salesOrder.getDeliveryDate()
				+ "','"
				+ salesOrder.getDeliveryStatus()
				+ "','";

		System.out.println("insert : " + query);
		try { // insert new SalesOrder + dependent
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

		String query = "UPDATE SalesOrder SET " + "date ='" + salesOrderObj.getDate()
				+ "', " + "amount ='" + salesOrderObj.getAmount() + "', "
				+ "delivery date ='" + salesOrderObj.getDeliveryDate() + "', " + "delivery status ='"
				+ salesOrderObj.getDeliveryStatus()
				+ "' " + " WHERE SalesOrderId = '" + salesOrderObj.getSalesOrderId()
				+ "'";
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

		String query = "DELETE FROM SalesORder WHERE ID = '" + salesOrderId + "'";
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
					if (retrieveAssociation) {
						IFDBSalesLine salesLine = new DBSalesLine();
						ArrayList<SalesLine> salesLines = salesLine.getAllSalesLinesBySalesLineId(salesLineObj.getSalesLineId(), false);
						salesLineObj.setSalesLines(salesLine);
						System.out.println("Lines are selected");
					}
					list.add(salesOrderObj);
				}// end while
				stmt.close();

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
						IFDBSalesLine salesLine = new DBSalesLine();
						ArrayList<SalesLine> salesLines = salesLine.getAllSalesLinesBySalesLineId(salesLineObj.getSalesLineId(), false);
						salesLineObj.setSalesLines(salesLine);
						System.out.println("Lines are selected");
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
			String query = "SELECT salesOrderId, date, amount, deliveryDate, deliveryStatus  FROM SalesOrder";

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
				salesOrderObj.setAmount(results.getInt("amount"));
				salesOrderObj.setDeliveryDate(results.getDate("deliveryDate"));
				salesOrderObj.setDeliveryStatus(results.getBoolean("deliveryStatus"));
			} catch (Exception e) {
				System.out.println("error in building the SalesOrder object");
			}
			return salesOrderObj;
		}
}
