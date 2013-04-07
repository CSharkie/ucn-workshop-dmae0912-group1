package database;

import java.util.ArrayList;
import model.SalesOrder;

public interface IFDBSalesOrder {
	public ArrayList<SalesOrder> getAllSalesOrdersByCustomerId(int customerId, boolean retrieveAssociation);
	public ArrayList<SalesOrder> getAllSalesOrders(boolean retrieveAssociation);
	public SalesOrder getSaleOrderById(int salesOrderId, boolean retrieveAssociation);
	public int insertSalesOrder(SalesOrder salesOrder) throws Exception;
	public int updateSalesOrder(SalesOrder salesOrder);
	public int deleteSalesOrder(int salesOrderId);
}
