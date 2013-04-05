package database;

import model.*;

import java.sql.*;
import java.util.ArrayList;

public class DBProduct implements IFDBProducts {
	private Connection con;
	
	public DBProduct() {
		con = DbConnection1.getInstance().getDBcon();
	}

	@Override
	public ArrayList<Product> getAllProducts(boolean retreiveAssociation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Product searchProductId(int id, boolean retreiveAssociation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Product searchProductName(String name, boolean retriveAssociation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int insertProduct(Product prod) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateProduct(Product prod) {
		// TODO Auto-generated method stub
		return 0;
	}

}
