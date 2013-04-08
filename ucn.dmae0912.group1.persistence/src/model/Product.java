package model;

public class Product {
	
	int productId;
	String name;
	double purchasePrice;
	double salePrice;
	double rentPrice;
	String countryOfOrigin;
	int minStock;
	Supplier supplier;
	
	public Product(String name, double purchasePrice, double salePrice, double rentPrice, String countryOfOrigin, int minStock) {
		this.name = name;
		this.purchasePrice = purchasePrice;
		this.salePrice = salePrice;
		this.rentPrice = rentPrice;
		this.countryOfOrigin = countryOfOrigin;
		this.minStock = minStock;
	}
	
	public Product() {
		
	}

	public Product(String name, Supplier supplier, double purchasePrice, double salePrice, double rentPrice, String countryOfOrigin, int minStock) {
		this.name = name;
		this.purchasePrice = purchasePrice;
		this.salePrice = salePrice;
		this.rentPrice = rentPrice;
		this.countryOfOrigin = countryOfOrigin;
		this.minStock = minStock;
		this.supplier = supplier;
	}

	public Product(int id) {
		this.productId = id;
	}

	public Product(int productId, Supplier supplier, String name, double purchasePrice,
			double salePrice, double rentPrice, String countryOfOrigin,
			int minStock) {
		this.productId=productId;
		this.supplier=supplier;
		this.name = name;
		this.purchasePrice = purchasePrice;
		this.salePrice = salePrice;
		this.rentPrice = rentPrice;
		this.countryOfOrigin = countryOfOrigin;
		this.minStock = minStock;
	}

	public int getProductId() {
		return productId;
	}
	
	public void setProductId(int productId) {
		this.productId = productId;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public double getPurchasePrice() {
		return purchasePrice;
	}
	
	public void setPurchasePrice(double purchasePrice) {
		this.purchasePrice = purchasePrice;
	}
	
	public double getSalePrice() {
		return salePrice;
	}
	
	public void setSalePrice(double salePrice) {
		this.salePrice = salePrice;
	}
	
	public double getRentPrice() {
		return rentPrice;
	}
	
	public void setRentPrice(double rentPrice) {
		this.rentPrice = rentPrice;
	}
	
	public String getCountryOfOrigin() {
		return countryOfOrigin;
	}
	
	public void setCountryOfOrigin(String countryOfOrigin) {
		this.countryOfOrigin = countryOfOrigin;
	}
	
	public int getMinStock() {
		return minStock;
	}
	
	public void setMinStock(int minStock) {
		this.minStock = minStock;
	}
	
	public Supplier getSupplier() {
		return supplier;
	}
	
	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

}
