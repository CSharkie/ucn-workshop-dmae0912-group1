package controller;

import model.Customer;
import model.Invoice;
import model.Product;
import model.SalesLine;
import model.SalesOrder;
import database.*;

import java.util.ArrayList;
import java.util.Date;

public class SalesOrderCtr {
		
		private CustomerCtr custCtr;
		private ProductsCtr prodCtr;
		
		public SalesOrderCtr(){
			custCtr = new CustomerCtr();		
			prodCtr = new ProductsCtr();
		}
		public ArrayList<SalesOrder> getAllSalesOrdersByCustomerId(int customerId){
			IFDBSalesOrder dbSalesOrder = new DBSalesOrder();
			return dbSalesOrder.getAllSalesOrdersByCustomerId(customerId, true);
		}
		
		public ArrayList<SalesOrder> getAllSalesOrders(){
			IFDBSalesOrder dbsalesOrder=new DBSalesOrder();
			ArrayList<SalesOrder> allsalesOrder=new ArrayList<SalesOrder>();
			allsalesOrder=dbsalesOrder.getAllSalesOrders(true);
			return allsalesOrder;
		}
		public SalesOrder getSaleOrderById(int salesOrderId){
			IFDBSalesOrder dbsalesOrder=new DBSalesOrder();
			return dbsalesOrder.getSaleOrderById(salesOrderId, true);
		}
		public int updateSalesOrder(int salesOrderId, int customerId, Date date, Date deliveryDate){
			Customer customer = custCtr.searchCustomerById(customerId);
			IFDBSalesOrder dbsalesOrder=new DBSalesOrder();
			SalesOrder salesOrder=new SalesOrder(salesOrderId, customer, date, deliveryDate);
			return dbsalesOrder.updateSalesOrder(salesOrder);
		}
		
		public int insertSalesOrder(Date date, int customerId, Date deliveryDate){		
			Customer customer = custCtr.searchCustomerById(customerId);
			Invoice invoice = getCurrentInvoice();
			SalesOrder salesOrderObj=new SalesOrder(customer, date, deliveryDate, invoice);
			try{
				DbConnection.startTransaction();
				DBSalesOrder dbsalesOrder=new DBSalesOrder();
				dbsalesOrder.insertSalesOrder(salesOrderObj);
				DbConnection.commitTransaction();
			}
			catch(Exception e)
			{
				DbConnection.rollbackTransaction();
			}
			return salesOrderObj.getSalesOrderId();
		}
		
		public void removeOrder(int orderId) {
			IFDBSalesOrder dbsalesOrder=new DBSalesOrder();
			dbsalesOrder.deleteOrder(orderId);
		}
		
		public Invoice getCurrentInvoice()
		{
			DBInvoice dbInvoice=new DBInvoice();
			Invoice invoice = new Invoice();
			try {
				dbInvoice.insertInvoice(invoice);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return invoice;
		}
		
		public ArrayList<Invoice> getAllInvoices() {
			IFDBInvoice dbInvoice=new DBInvoice();
			ArrayList<Invoice> allInvoices=new ArrayList<Invoice>();
			allInvoices=dbInvoice.getAllInvoices(false);
			return allInvoices;
		}
		public Invoice getInvoiceByNo(int invoiceNo) {
			IFDBInvoice dbInvoice=new DBInvoice();			
			Invoice invoice = dbInvoice.searchInvoiceByNo(invoiceNo, false);
			return invoice;
		}
		public int confirmInvoicePayment(int invoiceNo, Date date) {
			IFDBInvoice dbInvoice=new DBInvoice();
			Invoice invoice=dbInvoice.searchInvoiceByNo(invoiceNo, false);
			invoice.setPaymentDate(date);
			return dbInvoice.updateInvoice(invoice);
		}
		public ArrayList<SalesLine> getAllSalesLines(int id) {
			IFDBSalesLine dbSalesLine=new DBSalesLine();
			ArrayList<SalesLine> allSalesLines=new ArrayList<SalesLine>();
			allSalesLines=dbSalesLine.getAllSalesLinesBySalesOrderId(id, true);
			return allSalesLines;
		}
		public void addSalesLine(int salesOrderId, int productId, int quantity) {
			SalesOrder salesOrder = getSaleOrderById(salesOrderId);
			Product product = prodCtr.findById(productId);
			SalesLine salesLineObj=new SalesLine(salesOrder, product, quantity);
			boolean error = false;
			try{
				DbConnection.startTransaction();
				DBSalesLine dbsalesLine = new DBSalesLine();
				dbsalesLine.insertSalesLine(salesLineObj);
				DbConnection.commitTransaction();
			}
			catch(Exception e)
			{
				error = true;
				DbConnection.rollbackTransaction();
			}
			if(!error) 
			{
				IFDBInvoice dbInvoice=new DBInvoice();
				Invoice invoice  = salesOrder.getInvoice();
				double price = product.getSalePrice() * quantity;
				invoice.setPrice(invoice.getPrice() + price);
				dbInvoice.updateInvoice(invoice);
			}
		}		
		public void removeSalesLines(int salesLineId) {
			IFDBSalesLine dbSalesLine=new DBSalesLine();
			SalesLine salesLine = dbSalesLine.searchSalesLineId(salesLineId, true);
			SalesOrder salesOrder = salesLine.getOrder();
			Invoice invoice = salesOrder.getInvoice();
			Product product = salesLine.getProduct();
			boolean error = false;
			try{
				DbConnection.startTransaction();
				DBSalesLine dbsalesLine = new DBSalesLine();
				dbsalesLine.delete(salesLineId);
				DbConnection.commitTransaction();
			}
			catch(Exception e)
			{
				error = true;
				DbConnection.rollbackTransaction();
			}
			if(!error) 
			{
				IFDBInvoice dbInvoice=new DBInvoice();
				double price = product.getSalePrice() * salesLine.getAmount();
				invoice.setPrice(invoice.getPrice() - price);
				dbInvoice.updateInvoice(invoice);
			}
		}
}
