package controller;

import java.util.ArrayList;

import model.*;
import database.*;

public class SuppliersCtr {
	
	public SuppliersCtr() {
		
	}
	
	public ArrayList<Supplier> findAllSuppliers() {
		IFDBSuppliers dbSuppliers = new DBSupplier();
	    ArrayList<Supplier> allSuppliers = new ArrayList<Supplier>();
	    allSuppliers = dbSuppliers.getAllSuppliers(false);
	    return allSuppliers;
	}
	
	public Supplier findByName(String name) {
		IFDBSuppliers dbSuppliers = new DBSupplier();
		return dbSuppliers.searchSupplierName(name, true);
	}
	
	public Supplier findById(int id) {
		IFDBSuppliers dbSuppliers = new DBSupplier();
		return dbSuppliers.searchSupplierId(id, true);
	}
	
	public int updateSupplier(String name, String address, String country, String phoneNo, String email)
    {
		IFDBSuppliers dbSuppliers = new DBSupplier();
		Supplier supplier = new Supplier();
        supplier.setName(name);
        supplier.setAddress(address);
        supplier.setCountry(country);
        supplier.setEmail(email);
        supplier.setPhoneNo(phoneNo);
        return  dbSuppliers.updateSupplier(supplier);
    }
	
	public void insertNew(String name, String address, String country, String phoneNo, String email)
    {    
		Supplier supObj = new Supplier();
		supObj.setName(name);
		supObj.setAddress(address);
		supObj.setCountry(country);
		supObj.setEmail(email);
		supObj.setPhoneNo(phoneNo);
  
		try{
			DbConnection.startTransaction();
			DBSupplier dbSup = new DBSupplier();
			dbSup.insertSupplier(supObj);
			DbConnection.commitTransaction();
		}
		catch(Exception e)
		{
			DbConnection.rollbackTransaction();
		}
    }

}
