package gui;

import java.util.ArrayList;
import java.util.Date;

import controller.SalesOrderCtr;

import model.Invoice;

import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.FillLayout;
import swing2swt.layout.BorderLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import swing2swt.layout.FlowLayout;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.layout.GridData;

public class InvoiceGUI extends Composite {

	SalesOrderCtr salesOrderCtr;

	// Tables
	private Table table;

	// Text Fields
	private Text txt_no;
	private Text txt_paymentDate;
	private Text txt_price;
	private Text search_name;
	private Button btn_confirmPayment;

	public InvoiceGUI(Composite parent, int style) {
		super(parent, style);
		this.setLayout(new BorderLayout(0, 0));

		salesOrderCtr = new SalesOrderCtr();

		Composite composite_2 = new Composite(this, SWT.NONE);
		composite_2.setLayoutData(BorderLayout.WEST);
		composite_2.setLayout(new BorderLayout(0, 0));

		Composite composite_8 = new Composite(composite_2, SWT.NONE);
		composite_8.setLayoutData(BorderLayout.NORTH);
		RowLayout rl_composite_8 = new RowLayout(SWT.HORIZONTAL);
		rl_composite_8.center = true;
		composite_8.setLayout(rl_composite_8);

		Label lbl_name = new Label(composite_8, SWT.NONE);
		lbl_name.setText("Name:");

		search_name = new Text(composite_8, SWT.BORDER);
		search_name.setLayoutData(new RowData(77, SWT.DEFAULT));

		Button btn_search = new Button(composite_8, SWT.NONE);
		btn_search.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				int invoiceNo = 0;
				boolean error = false;
				try {
					invoiceNo = Integer.parseInt(search_name.getText());
				} catch (NumberFormatException ex) {
					error = true;
					// TODO exception
				}
				if (!error)
					showSearchedInvoices(invoiceNo);
			}
		});
		btn_search.setText("Search");
		
		Button btn_refresh = new Button(composite_8, SWT.NONE);
		btn_refresh.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				showAllInvoices();
			}
		});
		btn_refresh.setText("Refresh");

		Composite composite_9 = new Composite(composite_2, SWT.NONE);
		composite_9.setLayout(new FillLayout(SWT.HORIZONTAL));

		ScrolledComposite scrolledComposite = new ScrolledComposite(
				composite_9, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		scrolledComposite.setExpandHorizontal(true);
		scrolledComposite.setExpandVertical(true);

		table = new Table(scrolledComposite, SWT.BORDER | SWT.FULL_SELECTION);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		TableColumn tblclmnId = new TableColumn(table, SWT.NONE);
		tblclmnId.setWidth(100);
		tblclmnId.setText("ID");

		TableColumn tblclmnPrice = new TableColumn(table, SWT.NONE);
		tblclmnPrice.setWidth(100);
		tblclmnPrice.setText("Price");
		scrolledComposite.setContent(table);
		scrolledComposite.setMinSize(table
				.computeSize(SWT.DEFAULT, SWT.DEFAULT));

		Composite composite_3 = new Composite(this, SWT.NONE);
		composite_3.setLayout(new BorderLayout(0, 0));

		Composite composite_4 = new Composite(composite_3, SWT.NONE);
		composite_4.setLayoutData(BorderLayout.SOUTH);
		composite_4.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		Composite composite_5 = new Composite(composite_4, SWT.NONE);
		composite_5.setLayout(new RowLayout(SWT.HORIZONTAL));
		btn_confirmPayment = new Button(composite_5, SWT.CENTER);
		btn_confirmPayment.setLayoutData(new RowData(172, 50));
		btn_confirmPayment.setEnabled(false);
		btn_confirmPayment.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Date date = new Date();
				int invoiceNo = 0;
				boolean error = false;
				try {
					invoiceNo = Integer.parseInt(txt_no.getText());
				} catch (NumberFormatException ex) {
					error = true;
					// TODO exception
				}
				if (!error) {
					salesOrderCtr.confirmInvoicePayment(invoiceNo, date);
					showInvoice(invoiceNo);
				}
			}
		});
		btn_confirmPayment.setText("CONFIRM PAYMENT");

		Composite composite_6 = new Composite(composite_3, SWT.NONE);
		composite_6.setLayoutData(BorderLayout.CENTER);
		composite_6.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 40));

		Composite composite_7 = new Composite(composite_6, SWT.NONE);
		composite_7.setLayout(new GridLayout(2, false));

		Label lblNo = new Label(composite_7, SWT.NONE);
		lblNo.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false,
				1, 1));
		lblNo.setBounds(0, 0, 55, 15);
		lblNo.setText("Invoice No:");

		txt_no = new Text(composite_7, SWT.BORDER);
		GridData gd_txt_no = new GridData(SWT.LEFT, SWT.CENTER, true, false, 1,
				1);
		gd_txt_no.widthHint = 203;
		txt_no.setEditable(false);
		txt_no.setLayoutData(gd_txt_no);

		Label lblPaymentDate = new Label(composite_7, SWT.NONE);
		lblPaymentDate.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER,
				false, false, 1, 1));
		lblPaymentDate.setText("Payment Date:");

		txt_paymentDate = new Text(composite_7, SWT.BORDER);
		GridData gd_txt_paymentDate = new GridData(SWT.LEFT, SWT.CENTER, true,
				false, 1, 1);
		gd_txt_paymentDate.widthHint = 203;
		txt_paymentDate.setEditable(false);
		txt_paymentDate.setLayoutData(gd_txt_paymentDate);

		Label lblPrice = new Label(composite_7, SWT.NONE);
		lblPrice.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false,
				false, 1, 1));
		lblPrice.setText("Price:");

		txt_price = new Text(composite_7, SWT.BORDER);
		GridData gd_txt_price = new GridData(SWT.LEFT, SWT.CENTER, true, false,
				1, 1);
		gd_txt_price.widthHint = 203;
		txt_price.setEditable(false);
		txt_price.setLayoutData(gd_txt_price);

		table.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				int id = Integer.parseInt(table.getItem(
						table.getSelectionIndex()).getText(0));
				showInvoice(id);
			}
		});

		showAllInvoices();

	}

	private void showAllInvoices() {
		table.clearAll();
		table.setItemCount(0);
		ArrayList<Invoice> invoices = salesOrderCtr.getAllInvoices();
		for (Invoice invoice : invoices) {
			TableItem item = new TableItem(table, SWT.NONE);
			item.setText(0, String.valueOf(invoice.getInvoiceNo()));
			item.setText(1, String.valueOf(invoice.getPrice()));
		}

	}

	private void showSearchedInvoices(int invoiceNo) {
		table.clearAll();
		table.setItemCount(0);
		Invoice invoice = salesOrderCtr.getInvoiceByNo(invoiceNo);
		TableItem item = new TableItem(table, SWT.NONE);
		item.setText(0, String.valueOf(invoice.getInvoiceNo()));
		item.setText(1, String.valueOf(invoice.getPrice()));
	}

	private void showInvoice(int id) {
		Invoice invoice = salesOrderCtr.getInvoiceByNo(id);
		txt_no.setText(String.valueOf(invoice.getInvoiceNo()));
		if (invoice.getPaymentDate() != null) {
			txt_paymentDate.setText(invoice.getPaymentDate().toString());
			btn_confirmPayment.setEnabled(false);
		} else {
			txt_paymentDate.setText("");
			btn_confirmPayment.setEnabled(true);
		}
		txt_price.setText(String.valueOf(invoice.getPrice()));
		txt_no.setEditable(false);
		txt_paymentDate.setEditable(false);
		txt_price.setEditable(false);
	}

	public void resetFields() {
		txt_no.setText("");
		txt_paymentDate.setText("");
		txt_price.setText("");
	}
}
