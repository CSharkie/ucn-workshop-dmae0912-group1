package database;

import model.*;

import java.util.ArrayList;

public interface IFDBSuppliers {
	
	// get all Suppliers
	public ArrayList<Supplier> getAllSuppliers(boolean retreiveAssociation);
	// get one Supplier by SupplierId
	public Supplier searchSupplierId(int supplierId, boolean retreiveAssociation);
	//find one Supplier having the name
    public ArrayList<Supplier> searchSupplierName(String name, boolean retriveAssociation);
    
    //insert a new Supplier
    public int insertSupplier(Supplier sup) throws Exception;
    //update information about an Supplier
    public int updateSupplier(Supplier sup);
    
    public int deleteSupplier(int supplierId);
}
