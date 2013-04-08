package controller;

import java.util.ArrayList;

import model.*;
import database.*;

public class ProductsCtr {

	public ProductsCtr() {
		
	}
	
	public ArrayList<Product> findAllProducts() {
		IFDBProducts dbProducts = new DBProduct();
	    ArrayList<Product> allProducts = new ArrayList<Product>();
	    allProducts = dbProducts.getAllProducts(false);
	    return allProducts;
	}
	
	public ArrayList<Product> findByName(String name) {
		IFDBProducts dbProducts = new DBProduct();
		return dbProducts.searchProductName(name, true);
	}
	
	public Product findById(int productId) {
		IFDBProducts dbProducts = new DBProduct();
		return dbProducts.searchProductId(productId, true);
	}
	
	public int updateProduct(int productId, Supplier supplier, String name, double purchasePrice, double salePrice, double rentPrice, String countryOfOrigin, int minStock)
    {
		IFDBProducts dbProducts = new DBProduct();
		Product product = new Product(productId, supplier, name, purchasePrice, salePrice, rentPrice, countryOfOrigin, minStock);
        return dbProducts.updateProduct(product);
    }
	
	public int insertProduct(String name, Supplier supplier, double purchasePrice, double salePrice, double rentPrice, String countryOfOrigin, int minStock)
    {    
		Product prodObj = new Product(name, supplier, purchasePrice, salePrice, rentPrice, countryOfOrigin, minStock);
  
		try{
			DbConnection.startTransaction();
			DBProduct dbProd = new DBProduct();
			dbProd.insertProduct(prodObj);
			DbConnection.commitTransaction();
		}
		catch(Exception e)
		{
			DbConnection.rollbackTransaction();
		}
		return prodObj.getProductId();
    }

	public void removeProduct(int productId) {
		IFDBProducts dbProducts = new DBProduct();
		dbProducts.deleteProduct(productId);
	}
}
