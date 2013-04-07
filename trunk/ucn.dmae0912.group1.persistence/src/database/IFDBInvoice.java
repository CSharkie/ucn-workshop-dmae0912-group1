package database;
import java.util.ArrayList;

import model.Invoice;


public interface IFDBInvoice {
	
	// get all Invoices
		public ArrayList<Invoice> getAllInvoices(boolean retrieveAssociation);
	// get one Invoice by InvoiceNo;
		public Invoice searchInvoiceByNo(int invoiceNo, boolean retrieveAssociation);
	//insert a new Invoice;
		public int insertInvoice(Invoice inv) throws Exception;
	//update information about an Invoice
		public int updateInvoice(Invoice inv);
}
