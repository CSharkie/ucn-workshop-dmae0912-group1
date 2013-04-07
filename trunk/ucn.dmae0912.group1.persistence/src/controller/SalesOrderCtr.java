package controller;

import model.SalesOrder;
import database.*;

import java.util.ArrayList;
import java.util.Date;

public class SalesOrderCtr {
		
		public SalesOrderCtr(){
			
		}
		public ArrayList<SalesOrder> getAllSalesOrdersByCustomerId(int customerId){
			IFDBSalesOrder dbSalesOrder = new DBSalesOrder();
			return dbSalesOrder.getAllSalesOrdersByCustomerId(customerId, true);
		}
		
		public ArrayList<SalesOrder> getAllSalesOrders(){
			IFDBSalesOrder dbsalesOrder=new DBSalesOrder();
			ArrayList<SalesOrder> allsalesOrder=new ArrayList<SalesOrder>();
			allsalesOrder=dbsalesOrder.getAllSalesOrders(false);
			return allsalesOrder;
		}
		public SalesOrder getSaleOrderById(int salesOrderId){
			IFDBSalesOrder dbsalesOrder=new DBSalesOrder();
			return dbsalesOrder.getSaleOrderById(salesOrderId, true);
		}
		public int updateSalesOrder(int salesOrderId, Date date, int amount, Date deliveryDate, Boolean deliveryStatus){
			IFDBSalesOrder dbsalesOrder=new DBSalesOrder();
			SalesOrder salesOrder=new SalesOrder(salesOrderId, date, amount, deliveryDate, deliveryStatus);
			return dbsalesOrder.updateSalesOrder(salesOrder);
		}
		
		public void insertSalesOrder(int salesOrderId, Date date, int amount, Date deliveryDate, Boolean deliveryStatus){
			SalesOrder salesOrderObj=new SalesOrder(salesOrderId, date, amount, deliveryDate, deliveryStatus);
			
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
		}
}
