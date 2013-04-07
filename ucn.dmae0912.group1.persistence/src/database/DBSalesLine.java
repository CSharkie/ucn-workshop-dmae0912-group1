package database;
import model.SalesLine;
import model.Product;

import java.sql.*;
import java.util.ArrayList;

public class DBSalesLine<Int> implements IFDBSalesLine{
	private Connection con;

	/** Creates a new instance of DBSalesLine */
	public DBSalesLine() {
		con = DbConnection.getInstance().getDBcon();
	}
	// Implements the methods from the interface
			// get all SalesLines
			public ArrayList<SalesLine> getAllSalesLines(boolean retrieveAssociation) {
				return miscWhere("", retrieveAssociation);
			}

			// get one SalesLine having the Id
			public SalesLine searchSalesLineId(int salesLineId,
					boolean retrieveAssociation) {
				String wClause = "  SalesLine Id: = '" + salesLineId + "'";
				return singleWhere(wClause, retrieveAssociation);
			}

			// insert a new SalesLine
			public int insertSalesLine(SalesLine saleLn) throws Exception { // call to get
																		// the next SalesLine
																		// id
				int nextId = getMax.getMaxId("Select max(salesLineId) from SalesLine");
				nextId = nextId + 1;
				System.out.println("next ID = " + nextId);

				int rc = -1;
				String query = "INSERT INTO SalesLine(salesLineId, amount)  VALUES('"
						+ nextId
						+ "','"
						+ saleLn.getAmount() + "','";
						
				System.out.println("insert : " + query);
				try { // insert new SalesLine 
					Statement stmt = con.createStatement();
					stmt.setQueryTimeout(5);
					rc = stmt.executeUpdate(query);
					stmt.close();
				}// end try
				catch (SQLException ex) {
					System.out.println("SalesLine is not inserted");
					throw new Exception("SalesLine is not inserted");
				}
				return (rc);
			}

			@Override
			public int updateSalesLine(SalesLine salesLn) {
				SalesLine salesLnObj = salesLn;
				int rc = -1;

				String query = "UPDATE SalesLine SET " + "amount ='" + salesLnObj.getAmount() 
						+ "' " + " WHERE SalesLineId = '" + salesLnObj.getSalesLineId()
						+ "'";
				System.out.println("Update query:" + query);
				try { // update SalesLine
					Statement stmt = con.createStatement();
					stmt.setQueryTimeout(5);
					rc = stmt.executeUpdate(query);

					stmt.close();
				}// end try
				catch (Exception ex) {
					System.out.println("Update exception in SalesLine db: " + ex);
				}
				return (rc);
			}

			public int delete(Int SalesLineId) {
				int rc = -1;

				String query = "DELETE FROM SalesLine WHERE No = '" + SalesLineId + "'";
				System.out.println(query);
				try { // delete from SalesLine
					Statement stmt = con.createStatement();
					stmt.setQueryTimeout(5);
					rc = stmt.executeUpdate(query);
					stmt.close();
				}// end try
				catch (Exception ex) {
					System.out.println("Delete exception in SalesLine db: " + ex);
				}
				return (rc);
			}

			// private methods
			// michWere is used whenever we want to select more than one SalesLine

			private ArrayList<SalesLine> miscWhere(String wClause,
					boolean retrieveAssociation) {
				ResultSet results;
				ArrayList<SalesLine> list = new ArrayList<SalesLine>();

				String query = buildQuery(wClause);

				try { // read the SalesLine from the database
					Statement stmt = con.createStatement();
					stmt.setQueryTimeout(5);
					results = stmt.executeQuery(query);

					while (results.next()) {
						SalesLine salesLnObj = new SalesLine();
						salesLnObj = buildSalesLine(results);
						if (retrieveAssociation) {
							IFDBProducts DBProdObj = new DBProduct();
	                        Product prodObj = DBProdObj.searchProductId(salesLnObj.getProduct().getProductId(), false);
	                        System.out.println("Product is selected ");
	                        salesLnObj.setProduct(prodObj);
							System.out.println("SalesLines are selected");
						}
						list.add(salesLnObj);
					}// end while
					stmt.close();

				}// end try
				catch (Exception e) {
					System.out.println("Query exception - select: " + e);
					e.printStackTrace();
				}
				return list;
			}

			// SingleWhere is used when we only select one SalesLine
			private SalesLine singleWhere(String wClause, boolean retrieveAssociation) {
				ResultSet results;
				SalesLine salesLnObj = new SalesLine();

				String query = buildQuery(wClause);
				System.out.println(query);
				try { // read the SalesLine from the database
					Statement stmt = con.createStatement();
					stmt.setQueryTimeout(5);
					results = stmt.executeQuery(query);

					if (results.next()) {
						salesLnObj = buildSalesLine(results);
						stmt.close();
						if (retrieveAssociation) {
							IFDBProducts DBProdObj = new DBProduct();
	                        Product prodObj = DBProdObj.searchProductId(salesLnObj.getProduct().getProductId(), false);
	                        salesLnObj.setProduct(prodObj);
	                        System.out.println("Product is selected ");
						}
						
					} else { // no SalesLine was found
						salesLnObj = null;
					}
				}// end try
				catch (Exception e) {
					System.out.println("Query exception: " + e);
				}
				return salesLnObj;
			}

			// method to build the query
			private String buildQuery(String wClause) {
				String query = "SELECT SalesLineId, amount, FROM SalesLine";

				if (wClause.length() > 0)
					query = query + " WHERE " + wClause;

				return query;
			}

			// method to build a SalesLine object
			private SalesLine buildSalesLine(ResultSet results) {
				SalesLine salesLnObj = new SalesLine();
				try { // the columns from the table SalesLine are used
					salesLnObj.setSalesLineId(results.getInt("salesLineId"));
					salesLnObj.setAmount(results.getInt("amount"));
					
				} catch (Exception e) {
					System.out.println("error in building the SalesLine object");
				}
				return salesLnObj;
}
}
			