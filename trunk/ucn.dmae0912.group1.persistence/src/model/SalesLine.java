package model;

public class SalesLine {
	
	int amount;
	int salesLineId;
	
	public SalesLine(int amount, int salesLineId){
		this.amount=amount;
		this.salesLineId=salesLineId;
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
	public void print(){
		System.out.println("Amount: " + amount);
		System.out.println("Sales Line Id: " + salesLineId);
	}
}
