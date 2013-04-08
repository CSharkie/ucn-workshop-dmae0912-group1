package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;


public class SalesOrder {
	
	int salesOrderId;
	Date date;
	Date deliveryDate;
	Boolean deliveryStatus;   // false=under delivery(undelivered) || true=delivered
	Customer customer;
	Invoice invoice;
	ArrayList<SalesLine> saleLines;
	
	public SalesOrder(int salesOrderId, Customer customer, Date date, Date deliveryDate){
		this.salesOrderId=salesOrderId;
		this.customer = customer;
		this.date=date;
		this.deliveryDate=deliveryDate;
		this.deliveryStatus = false;
		saleLines=new ArrayList<SalesLine>();
	}
	public SalesOrder(Customer customer, Date date, Date deliveryDate, Invoice invoice){
		this.date=date;
		this.customer = customer;
		this.deliveryDate=deliveryDate;
		this.invoice=invoice;
		this.deliveryStatus = false;
		saleLines=new ArrayList<SalesLine>();
	}
	public SalesOrder(){
		
	}
	public void setCustomer(Customer customer){
		this.customer=customer;
	}
	public Customer getCustomer(){
		return customer;
	}
	public void setInvoice(Invoice invoice){
		this.invoice=invoice;
	}
	public Invoice getInvoice(){
		return invoice;
	}
	public void addSalesLine(SalesLine saleLine){
		saleLines.add(saleLine);
	}
	
	public void removeSalesLine(SalesLine saleLine) {
		saleLines.remove(saleLine);
	}
	
	public SalesLine getSalesLine(int ID) throws RealException {
		SalesLine saleLine = null;
        boolean found = false;
        Iterator<SalesLine> it = saleLines.iterator();
        while(it.hasNext() && !found)
        {
        	SalesLine lines = it.next();
            if(lines.getSalesLineId() == ID)
            {
            	saleLine = lines;
                found = true;
            } 
        }
        if(!found) throw new RealException("Sales Line was not found");
		return saleLine;
	}
	
	public int getSalesOrderId() {
		return salesOrderId;
	}

	public void setSalesOrderId(int salesOrderId) {
		this.salesOrderId = salesOrderId;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Date getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public Boolean getDeliveryStatus() {
		return deliveryStatus;
	}

	public void setDeliveryStatus(Boolean deliveryStatus) {
		this.deliveryStatus = deliveryStatus;
	}
	public void print(){
		System.out.println("Sales Order Id: " + salesOrderId);
		System.out.println("Date: " + date);
		System.out.println("Delivery Date: " + deliveryDate);
		if(deliveryStatus=false)
		System.out.println("Delivery Status: undelivered/under delivery");
		else
		System.out.println("Delivery Status: delivered");
	}
}
