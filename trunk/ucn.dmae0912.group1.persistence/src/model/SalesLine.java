package model;


public class SalesLine {
	
	int amount;
	int salesLineId;
	private Product prod;
	
	public SalesLine(int amount, int salesLineId){
		this.amount=amount;
		this.salesLineId=salesLineId;
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
}
