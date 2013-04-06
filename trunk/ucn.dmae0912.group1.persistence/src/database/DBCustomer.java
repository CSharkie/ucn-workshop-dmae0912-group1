package database;

import model.Customer;
import java.sql.*;
import java.util.ArrayList;

public class DBCustomer implements IFDBCustomer{
    private  Connection con;
    /** Creates a new instance of DBCustomer */
    public DBCustomer() {
      con = DbConnection1.getInstance().getDBcon();
    }
    
  //Implements the methods from the interface
    // get all Customers
    public ArrayList<Customer> getAllCustomers(boolean retriveAssociation)
    {
        return miscWhere("", retriveAssociation);
    }
    //get one Customer having the ID
    public Customer searchCustomerById(int customerId, boolean retriveAssociation)
    {   String wClause = "  Customer ID: = '" + customerId + "'";
        return singleWhere(wClause, retriveAssociation);
    }
    //find one Customer having the name
    public Customer searchCustomerByName(String name, boolean retriveAssociation)
    {
        String wClause = "Name: " + name + ",";
        System.out.println("Customer " + wClause);
        return singleWhere(wClause, retriveAssociation);
    }
     //insert a new Customer
    public int insertCustomer(Customer cust) throws Exception
    {  //call to get the next ssn number
        int nextId = GetMax.getMaxId("Select max(customerId) from Customer");
        nextId = nextId + 1;
        System.out.println("next ID = " +  nextId);
  
       int rc = -1;
	   String query="INSERT INTO Customer(customerId, name, address, zipCode, city, phoneNo)  VALUES('"+
			nextId + "','" +
			cust.getName() + "','" +
	  		cust.getAddress() + "','" +
			cust.getZipCode() + "','" +
            cust.getCity() + "','" +
			cust.getPhoneNo() + "','" ;

       System.out.println("insert : " + query);
      try{ // insert new Customer +  dependent
          Statement stmt = con.createStatement();
          stmt.setQueryTimeout(5);
     	  rc = stmt.executeUpdate(query);
          stmt.close();
      }//end try
       catch(SQLException ex){
          System.out.println("Customer is not inserted");
          throw new Exception ("Customer is not inserted");
       }
       return(rc);
    }
@Override
	public int updateCustomer(Customer cust)
	{
		
	}
	
	public int delete(String customerId)
	{
               int rc=-1;
	  
	  	String query="DELETE FROM Customer WHERE ID = '" +
				customerId + "'";
                System.out.println(query);
	  	try{ // delete from Customer
	 		Statement stmt = con.createStatement();
	 		stmt.setQueryTimeout(5);
	 	  	rc = stmt.executeUpdate(query);
	 	  	stmt.close();
  		}//end try	
   	        catch(Exception ex){
	 	  	System.out.println("Delete exception in Customer db: "+ex);
   	        }
		return(rc);
	}
	
	//private methods
    //michWere is used whenever we want to select more than one Customer
	
	 
	private ArrayList<Customer> miscWhere(String wClause, boolean retrieveAssociation)
	{
            ResultSet results;
	    ArrayList<Customer> list = new ArrayList<Customer>();	
		
	    String query =  buildQuery(wClause);
  
            try{ // read the Customer from the database
		Statement stmt = con.createStatement();
	 	stmt.setQueryTimeout(5);
	 	results = stmt.executeQuery(query);
	 	
	
		while( results.next() ){
	     	 Customer custObj = new Customer();
		 custObj = buildCustomer(results);	
                 list.add(custObj);	
		}//end while
                 stmt.close();     
                 if(retrieveAssociation)
                 {   //The supervisor and department is to be build as well
                     for(Customer custObj : list){
                        String superssn = custObj.getSupervisor().getSsn();
                        Customer supercust = singleWhere(" ssn = '" + superssn + "'",false);
                        custObj.setSupervisor(supercust);
                        System.out.println("Supervisor is seleceted");
                       // here the department has to be selected as well
                     }
                 }//end if   
			
		}//slut try	
	 	catch(Exception e){
	 		System.out.println("Query exception - select: "+e);
			e.printStackTrace();
	 	}
		return list;
	}
	
	//Singelwhere is used when we only select one Customer 	
	private Customer singleWhere(String wClause, boolean retrieveAssociation)
	{
		ResultSet results;
		Customer custObj = new Customer();
                
	        String query =  buildQuery(wClause);
                System.out.println(query);
		try{ // read the Customer from the database
	 		Statement stmt = con.createStatement();
	 		stmt.setQueryTimeout(5);
	 		results = stmt.executeQuery(query);
	 		
	 		if( results.next() ){
                            custObj = buildCustomer(results);
                            //assocaition is to be build
                            stmt.close();
                            if(retrieveAssociation)
                            {   //The supervisor and department is to be build as well
                                String superssn = custObj.getSupervisor().getSsn();
                                Customer supercust = singleWhere(" ssn = '" + superssn + "'",false);
                                custObj.setSupervisor(supercust);
                                System.out.println("Supervisor is seleceted");
                                IFDBDepartment dbDeptObj = new DBDepartment();
                                Department deptObj = dbDeptObj.findDepartment(custObj.getDept().getDnumber(), false);
                                System.out.println("Department is seleceted ");
                                custObj.setDepartment(deptObj);
                             
                       

                           
                           
                            }
			}
                        else{ //no Customer was found
                            custObj = null;
                        }
		}//end try	
	 	catch(Exception e){
	 		System.out.println("Query exception: "+e);
	 	}
		return custObj;
	}
	//method to build the query
	private String buildQuery(String wClause)
	{
	    String query="SELECT fname, minit,lname,ssn, address, bdate,sex, salary, superssn,dno  FROM Customer";
		
		if (wClause.length()>0)
			query=query+" WHERE "+ wClause;
			
		return query;
	}
	//method to build an Customer object
	private Customer buildCustomer(ResultSet results)
      {   Customer custObj = new Customer(0, null, null, 0, null, null);
          try{ // the columns from the table custlayee  are used
                custObj.setFname(results.getString("fname"));
                custObj.setMinit(results.getString("minit"));
                custObj.setLname(results.getString("lname"));
                custObj.setSsn(results.getString("ssn"));
                custObj.setBdate(results.getString("bdate"));
                custObj.setAddress(results.getString("address"));
                custObj.setSex(results.getString("sex"));
                custObj.setSalary(results.getDouble("salary"));
                custObj.setSupervisor(new Customer(results.getString("superssn")));
                custObj.setDepartment(new Department(results.getInt("dno")));
          }
         catch(Exception e)
         {
             System.out.println("error in building the Customer object");
         }
         return custObj;
      }

}  
    

