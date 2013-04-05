package model;

import java.util.Date;

public class Invoice {
	int invoiceNo;
	Date paymentDate;
	int amount;
	int price;
	SalesOrder saleOrder;
	
	public Invoice(int invoiceNo, Date paymentDate, int amount, int price, SalesOrder saleOrder) {
		this.invoiceNo = invoiceNo;
		this.paymentDate = paymentDate;
		this.amount = amount;
		this.price = price;
		this.saleOrder=saleOrder;
	}
	public int getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(int invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	public Date getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}
	public SalesOrder getSaleOrder() {
		return saleOrder;
	}
	public void setSaleOrder(SalesOrder saleOrder) {
		this.saleOrder = saleOrder;
	}
	public void print(){
		System.out.println("Invoice Number: " + invoiceNo);
		System.out.println("Payment Date: " + paymentDate);
		System.out.println("Amount: " + amount);
		System.out.println("Price: " + price);
		saleOrder.print();
	}

	
}
