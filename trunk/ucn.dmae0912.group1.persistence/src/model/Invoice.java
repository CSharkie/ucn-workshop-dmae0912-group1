package model;

import java.util.Date;
import java.util.ArrayList;
import java.util.Iterator;

public class Invoice {
	int invoiceNo;
	Date paymentDate;
	double price;
	ArrayList<SalesOrder> salesOrders;
	
	public Invoice(int invoiceNo, Date paymentDate, int price) {
		this.invoiceNo = invoiceNo;
		this.paymentDate = paymentDate;
		this.price = price;
		salesOrders=new ArrayList<SalesOrder>();
	}
	public Invoice(){
		
	}
	public Invoice(int invoiceNo) {
		this.invoiceNo = invoiceNo;
	}
	public void addSalesOrder(SalesOrder salesOrder){
		salesOrders.add(salesOrder);
	}
	public void removeSale(SalesOrder saleOrder) {
		salesOrders.remove(saleOrder);
	}
	
	public SalesOrder getSalesOrder(int ID) throws RealException {  
		SalesOrder saleOrder = null;
		        boolean found = false;
		        Iterator<SalesOrder> it = salesOrders.iterator();
		        while(it.hasNext() && !found)
		        {
		        	SalesOrder orders = it.next();
		            if(orders.getSalesOrderId() == ID)
		            {
		            	saleOrder = orders;
		                found = true;
		            } 
		        }
		        if(!found) throw new RealException("Order was not found");
		        return saleOrder;
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

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
	public void print(){
		System.out.println("Invoice Number: " + invoiceNo);
		System.out.println("Payment Date: " + paymentDate);
		System.out.println("Price: " + price);
	}

	
}
