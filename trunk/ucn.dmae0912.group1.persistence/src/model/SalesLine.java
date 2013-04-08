package model;


public class SalesLine {
	
	private int amount;
	private int salesLineId;
	private Product prod;
	private SalesOrder order;
	
	public SalesLine(SalesOrder order, Product product, int amount){
		this.amount=amount;
		this.prod = product;
		this.order = order;
	}

	public SalesLine(){
	
	
	}
	
	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public int getSalesLineId() {
		return salesLineId;
	}

	public void setSalesLineId(int salesLineId) {
		this.salesLineId = salesLineId;
	}
	
	 public void setProduct(Product prod){
	    this.prod = prod;
	    }
	
	 public Product getProduct(){
		return prod;
	 }
	 
	 public void print(){
		System.out.println("Amount: " + amount);
		System.out.println("Sales Line Id: " + salesLineId);
	}

	public SalesOrder getOrder() {
		return order;
	}

	public void setOrder(SalesOrder order) {
		this.order = order;
	}
}
