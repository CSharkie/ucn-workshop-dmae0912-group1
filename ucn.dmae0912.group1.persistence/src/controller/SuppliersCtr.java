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
	
	public ArrayList<Supplier> findByName(String name) {
		IFDBSuppliers dbSuppliers = new DBSupplier();
		return dbSuppliers.searchSupplierName(name, true);
	}
	
	public Supplier findById(int supplierId) {
		IFDBSuppliers dbSuppliers = new DBSupplier();
		return dbSuppliers.searchSupplierId(supplierId, true);
	}
	
	public int updateSupplier(int supplierId, String name, String address, String country, String phoneNo, String email)
    {
		IFDBSuppliers dbSuppliers = new DBSupplier();
		Supplier supplier = new Supplier(supplierId, name, address, country, phoneNo, email);
        return  dbSuppliers.updateSupplier(supplier);
    }
	
	public int insertCustomer(String name, String address, String country, String phoneNo, String email)
    {    
		Supplier supObj = new Supplier(name, address, country, phoneNo, email);
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
		return supObj.getSupplierId();
    }
	
	public void removeSupplier(int supplierId) {
		IFDBSuppliers dbSuppliers=new DBSupplier();
		dbSuppliers.deleteSupplier(supplierId);
	}

}
