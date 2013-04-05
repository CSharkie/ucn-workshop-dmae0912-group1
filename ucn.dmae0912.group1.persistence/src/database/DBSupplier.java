package database;

import model.*;

import java.sql.*;
import java.util.ArrayList;

public class DBSupplier implements IFDBSuppliers {
	private Connection con;
	
	public DBSupplier() {
		con = DbConnection1.getInstance().getDBcon();
	}

	@Override
	public ArrayList<Supplier> getAllSuppliers(boolean retreiveAssociation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Supplier searchSupplierId(int id, boolean retreiveAssociation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Supplier searchSupplierName(String name, boolean retriveAssociation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int insertSupplier(Supplier sup) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateSupplier(Supplier sup) {
		// TODO Auto-generated method stub
		return 0;
	}

}
