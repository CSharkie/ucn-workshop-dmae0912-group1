package database;

import java.util.ArrayList;

import model.SalesOrder;

public interface IFDBSalesOrder {
	public ArrayList<SalesOrder> getAllSalesOrdersByCustomerId(int customerId, boolean retrieveAssociation);
}
