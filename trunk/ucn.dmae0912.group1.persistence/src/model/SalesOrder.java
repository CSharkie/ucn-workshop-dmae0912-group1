package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;


public class SalesOrder {
	
	int salesOrderId;
	Date date;
	int amount;
	Date deliveryDate;
	Boolean deliveryStatus;   // false=under delivery(undelivered) || true=delivered
	ArrayList<SalesLine> saleLines;
	
	public SalesOrder(int salesOrderId, Date date, int amount, Date deliveryDate, Boolean deliveryStatus){
		this.salesOrderId=salesOrderId;
		this.date=date;
		this.amount=amount;
		this.deliveryDate=deliveryDate;
		this.deliveryStatus=deliveryStatus;
		saleLines=new ArrayList<SalesLine>();
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

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
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
		System.out.println("Amount: " + amount);
		System.out.println("Delivery Date: " + deliveryDate);
		if(deliveryStatus=false)
		System.out.println("Delivery Status: undelivered/under delivery");
		else
		System.out.println("Delivery Status: delivered");
	}
}
