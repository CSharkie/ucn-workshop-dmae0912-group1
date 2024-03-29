package database;

import model.*;

import java.util.ArrayList;

public interface IFDBProducts {

	// get all Products
	public ArrayList<Product> getAllProducts(boolean retreiveAssociation);
	// get one Product by ProductId
	public Product searchProductId(int productId, boolean retreiveAssociation);
	//find one Product having the name
	public ArrayList<Product> searchProductName(String name, boolean retriveAssociation);
	    
	//insert a new Product
	public int insertProduct(Product prod) throws Exception;
	//update information about an Product
	public int updateProduct(Product prod);

	public int deleteProduct(int productId);
}
