package database;

import model.Product;

import java.sql.*;
import java.util.ArrayList;

public class DBProduct implements IFDBProducts {
	private Connection con;
	
	public DBProduct() {
		con = DbConnection.getInstance().getDBcon();
	}
	
	public ArrayList<Product> getAllProducts(boolean retreiveAssociation) {
		return miscWhere("", retreiveAssociation);
	}

	public Product searchProductId(int productId, boolean retreiveAssociation) {
		String wClause = " ProductId = '" + productId + "'";
        return singleWhere(wClause, retreiveAssociation);
	}

	public ArrayList<Product> searchProductName(String name, boolean retriveAssociation) {
		String wClause = "Name LIKE '%" + name + "%'";
        System.out.println("Product " + wClause);
        return miscWhere(wClause, retriveAssociation);
	}

	public int insertProduct(Product prod) throws Exception {
		int nextId = getMax.getMaxId("Select max(productId) from Product");
        nextId = nextId + 1;
        System.out.println("next ID = " +  nextId);
        
        prod.setProductId(nextId);
       int rp = -1;
	   String query="INSERT INTO Product(productId, name, purchasePrice, salePrice, rentPrice, minStock, countryOfOrigin, productType)  VALUES('"
			   + prod.getProductId()
			   + "','" 
			   + prod.getName() 
			   + "','" 
			   + prod.getPurchasePrice() 
			   + "','" 
			   + prod.getSalePrice() 
			   + "','" 
			   + prod.getRentPrice() 
			   + "','" 
			   + prod.getMinStock() 
			   + "','" 
			   + prod.getCountryOfOrigin() 
			   + "','" 
			   + 1 // This is the product type a default number until future implement of the function in the GUI
			   + "');" ;

       System.out.println("insert : " + query);
      try{ // insert new Product
          Statement stmt = con.createStatement();
          stmt.setQueryTimeout(5);
     	  rp = stmt.executeUpdate(query);
          stmt.close();
      }//end try
       catch(SQLException ex){
          System.out.println("Product is not inserted");
          throw new Exception ("Product is not inserted");
       }
       return(rp);
    }

	public int updateProduct(Product prod) {
		Product prodObj  = prod;
		int rp=-1;
	
		String query="UPDATE Product SET " 
		+ "name ='" 
		+ prodObj.getName() 
		+ "', " 
		+ "purchasePrice ='" 
		+ prodObj.getPurchasePrice() 
		+ "', " 
		+ "salePrice ='" 
		+ prodObj.getSalePrice() 
		+ "', " 
		+ "rentPrice ='" 
		+ prodObj.getRentPrice() 
		+ "', " 
		+  "minStock ='" 
		+ prodObj.getMinStock() 
		+ "', " 
		+ "countryOfOrigin ='" 
		+ prodObj.getCountryOfOrigin() 
		+ "'" 
		+ " WHERE productId = '" 
		+ prodObj.getProductId() 
		+ "'";
	            System.out.println("Update query:" + query);
			try{ // update Product
	 		Statement stmt = con.createStatement();
	 		stmt.setQueryTimeout(5);
	 	 	rp = stmt.executeUpdate(query);
	
	 	 	stmt.close();
		}//slut try
	 	catch(Exception ex){
	 	 	System.out.println("Update exception in Product db: "+ex);
	  	}
		return(rp);
		}
	
	public int deleteProduct(int productId)
	{
               int rp=-1;
	  
	  	String query="DELETE FROM Product WHERE productId = '" +
	  			productId + "'";
                System.out.println(query);
	  	try{ // delete from Product
	 		Statement stmt = con.createStatement();
	 		stmt.setQueryTimeout(5);
	 	  	rp = stmt.executeUpdate(query);
	 	  	stmt.close();
  		}//end try	
   	        catch(Exception ex){
	 	  	System.out.println("Delete exception in Product db: "+ex);
   	        }
		return(rp);
	}
	
	private ArrayList<Product> miscWhere(String wClause, boolean retrieveAssociation)
	{
            ResultSet results;
	    ArrayList<Product> list = new ArrayList<Product>();	
		
	    String query =  buildQuery(wClause);
  
            try{ // read the Product from the database
		Statement stmt = con.createStatement();
	 	stmt.setQueryTimeout(5);
	 	results = stmt.executeQuery(query);
	 	
	
		while( results.next() ){
	     	 Product prodObj = new Product();
		 prodObj = buildProduct(results);
                 list.add(prodObj);	
		}//end while
                 stmt.close();     
			
		}//end try	
	 	catch(Exception e){
	 		System.out.println("Query exception - select: "+e);
			e.printStackTrace();
	 	}
		return list;
	}
	
	//Singelwhere is used when we only select one Product 	
	private Product singleWhere(String wClause, boolean retrieveAssociation)
	{
		ResultSet results;
		Product prodObj = new Product();
                
	        String query =  buildQuery(wClause);
                System.out.println(query);
		try{ // read the Product from the database
	 		Statement stmt = con.createStatement();
	 		stmt.setQueryTimeout(5);
	 		results = stmt.executeQuery(query);
	 		
	 		if( results.next() ){
                            prodObj = buildProduct(results);
                            //assocaition is to be build
                            stmt.close();
                 }
                        else{ //no Product was found
                            prodObj = null;
                        }
		}//end try	
	 	catch(Exception e){
	 		System.out.println("Query exception: "+e);
	 	}
		return prodObj;
	}
	//method to build the query
	private String buildQuery(String wClause)
	{
	    String query="SELECT productId, name, purchasePrice, salePrice, rentPrice, countryOfOrigin, minStock  FROM Product";
		
		if (wClause.length()>0)
			query=query+" WHERE "+ wClause;
			
		return query;
	}
	//method to build an Product object
	private Product buildProduct(ResultSet results)
      {   Product prodObj = new Product();
          try{ // the columns from the table Product  are used
                prodObj.setProductId(results.getInt("productId"));
                prodObj.setName(results.getString("name"));
                prodObj.setPurchasePrice(results.getDouble("purchasePrice"));
                prodObj.setSalePrice(results.getDouble("salePrice"));
                prodObj.setRentPrice(results.getDouble("rentPrice"));
                prodObj.setCountryOfOrigin(results.getString("countryOfOrigin"));
                prodObj.setMinStock(results.getInt("minStock"));
          }
         catch(Exception e)
         {
             System.out.println("error in building the Product object");
         }
         return prodObj;
      }

}
