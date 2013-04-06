package database;

import model.Supplier;

import java.sql.*;
import java.util.ArrayList;

public class DBSupplier implements IFDBSuppliers {
	private Connection con;
	
	public DBSupplier() {
		con = DbConnection1.getInstance().getDBcon();
	}

	public ArrayList<Supplier> getAllSuppliers(boolean retreiveAssociation) {
		return miscWhere("", retreiveAssociation);
	}

	public Supplier searchSupplierId(int supplierId, boolean retreiveAssociation) {
		String wClause = "  Supplier ID: = '" + supplierId + "'";
        return singleWhere(wClause, retreiveAssociation);
	}

	public Supplier searchSupplierName(String name, boolean retriveAssociation) {
		String wClause = "Name: " + name + ",";
        System.out.println("Supplier " + wClause);
        return singleWhere(wClause, retriveAssociation);
	}

	public int insertSupplier(Supplier sup) throws Exception {
		int nextId = getMax.getMaxId("Select max(supplierId) from Supplier");
        nextId = nextId + 1;
        System.out.println("next ID = " +  nextId);
  
       int rs = -1;
	   String query="INSERT INTO Supplier(supplierId, name, address, country, phoneNo, email)  VALUES('"+
			nextId + "','" +
			sup.getName() + "','" +
			sup.getAddress() + "','" +
			sup.getCountry() + "','" +
			sup.getPhoneNo() + "','" +
			sup.getEmail() + "','" ;

       System.out.println("insert : " + query);
      try{ // insert new Supplier +  dependent
          Statement stmt = con.createStatement();
          stmt.setQueryTimeout(5);
     	  rs = stmt.executeUpdate(query);
          stmt.close();
      }//end try
       catch(SQLException ex){
          System.out.println("Supplier is not inserted");
          throw new Exception ("Supplier is not inserted");
       }
       return(rs);
    }

	public int updateSupplier(Supplier sup) {
		Supplier supObj  = sup;
		int rs=-1;
	
		String query="UPDATE Supplier SET "+
		 	  "name ='"+ supObj.getName()+"', "+
		 	  "address ='"+ supObj.getAddress() + "', " +
	                      "country ='"+ supObj.getCountry() + "', " +
	                      "phoneNo ='"+ supObj.getPhoneNo() + ", " +
	                      "email ='" + supObj.getEmail() + "' " +
		          " WHERE supplierId = '"+ supObj.getSupplierId() + "'";
	            System.out.println("Update query:" + query);
			try{ // update Supplier
	 		Statement stmt = con.createStatement();
	 		stmt.setQueryTimeout(5);
	 	 	rs = stmt.executeUpdate(query);
	
	 	 	stmt.close();
		}//slut try
	 	catch(Exception ex){
	 	 	System.out.println("Update exception in Supplier db: "+ex);
	  	}
		return(rs);
		}
	
	public int delete(String supplierId)
	{
               int rs=-1;
	  
	  	String query="DELETE FROM Supplier WHERE ID = '" +
	  			supplierId + "'";
                System.out.println(query);
	  	try{ // delete from Supplier
	 		Statement stmt = con.createStatement();
	 		stmt.setQueryTimeout(5);
	 	  	rs = stmt.executeUpdate(query);
	 	  	stmt.close();
  		}//end try	
   	        catch(Exception ex){
	 	  	System.out.println("Delete exception in Supplier db: "+ex);
   	        }
		return(rs);
	}
	
	private ArrayList<Supplier> miscWhere(String wClause, boolean retrieveAssociation)
	{
            ResultSet results;
	    ArrayList<Supplier> list = new ArrayList<Supplier>();	
		
	    String query =  buildQuery(wClause);
  
            try{ // read the Supplier from the database
		Statement stmt = con.createStatement();
	 	stmt.setQueryTimeout(5);
	 	results = stmt.executeQuery(query);
	 	
	
		while( results.next() ){
	     	 Supplier supObj = new Supplier();
		 supObj = buildSupplier(results);	
                 list.add(supObj);	
		}//end while
                 stmt.close();     
			
		}//end try	
	 	catch(Exception e){
	 		System.out.println("Query exception - select: "+e);
			e.printStackTrace();
	 	}
		return list;
	}
	
	//Singelwhere is used when we only select one Supplier 	
	private Supplier singleWhere(String wClause, boolean retrieveAssociation)
	{
		ResultSet results;
		Supplier supObj = new Supplier();
                
	        String query =  buildQuery(wClause);
                System.out.println(query);
		try{ // read the Supplier from the database
	 		Statement stmt = con.createStatement();
	 		stmt.setQueryTimeout(5);
	 		results = stmt.executeQuery(query);
	 		
	 		if( results.next() ){
                            supObj = buildSupplier(results);
                            //assocaition is to be build
                            stmt.close();
                 }
                        else{ //no Supplier was found
                            supObj = null;
                        }
		}//end try	
	 	catch(Exception e){
	 		System.out.println("Query exception: "+e);
	 	}
		return supObj;
	}
	//method to build the query
	private String buildQuery(String wClause)
	{
	    String query="SELECT supplierId, name, address, country, phoneNo, email  FROM Supplier";
		
		if (wClause.length()>0)
			query=query+" WHERE "+ wClause;
			
		return query;
	}
	//method to build an Supplier object
	private Supplier buildSupplier(ResultSet results)
      {   Supplier supObj = new Supplier();
          try{ // the columns from the table Supplier  are used
                supObj.setSupplierId(results.getInt("supplierId"));
                supObj.setName(results.getString("name"));
                supObj.setAddress(results.getString("address"));
                supObj.setCountry(results.getString("country"));
                supObj.setPhoneNo(results.getString("phoneNo"));
                supObj.setEmail(results.getString("email"));
          }
         catch(Exception e)
         {
             System.out.println("error in building the Supplier object");
         }
         return supObj;
      }

}
