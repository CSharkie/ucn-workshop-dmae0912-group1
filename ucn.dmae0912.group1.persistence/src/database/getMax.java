package database;

import java.sql.*;

public class getMax {
	
	 public getMax() {
		 
	    }
	    //getMax is for the primary key in the table   
	    public static int getMaxId(String query){
	 ResultSet results;
	 int id = -1;
	 try{ 
	    Statement stmt = DbConnection.getInstance().getDBcon().createStatement();
	    results = stmt.executeQuery(query);
	    if( results.next() ){
	   id = results.getInt(1);
	     }
	     }//end try 
	 catch(Exception e){
	     System.out.println("Query exception: Error in reading maxid" + e);
	 }
	 return id;
	}

}
