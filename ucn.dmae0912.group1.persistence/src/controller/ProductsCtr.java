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
	
	public Product findByName(String name) {
		IFDBProducts dbProducts = new DBProduct();
		return dbProducts.searchProductName(name, true);
	}
	
	public Product findById(int id) {
		IFDBProducts dbProducts = new DBProduct();
		return dbProducts.searchProductId(id, true);
	}
	
	public int updateProduct(String name, double purchasePrice, double salePrice, double rentPrice, String countryOfOrigin, int minStock)
    {
		IFDBProducts dbProducts = new DBProduct();
		Product Product = new Product();
        Product.setName(name);
        Product.setCountryOfOrigin(countryOfOrigin);
        Product.setMinStock(minStock);
        Product.setPurchasePrice(purchasePrice);
        Product.setRentPrice(rentPrice);
        Product.setSalePrice(salePrice);
        return  dbProducts.updateProduct(Product);
    }
	
	public void insertNew(String name, double purchasePrice, double salePrice, double rentPrice, String countryOfOrigin, int minStock)
    {    
		Product ProdObj = new Product();
		ProdObj.setName(name);
		ProdObj.setCountryOfOrigin(countryOfOrigin);
		ProdObj.setMinStock(minStock);
		ProdObj.setPurchasePrice(purchasePrice);
		ProdObj.setRentPrice(rentPrice);
		ProdObj.setSalePrice(salePrice);
  
		try{
			DbConnection.startTransaction();
			DBProduct dbProd = new DBProduct();
			dbProd.insertProduct(ProdObj);
			DbConnection.commitTransaction();
		}
		catch(Exception e)
		{
			DbConnection.rollbackTransaction();
		}
    }
	
}
