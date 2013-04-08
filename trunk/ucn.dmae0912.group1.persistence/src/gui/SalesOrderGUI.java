package gui;

import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;

import controller.SalesOrderCtr;

import model.SalesOrder;

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
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import swing2swt.layout.FlowLayout;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.graphics.Point;

public class SalesOrderGUI extends Composite {

	SalesOrderCtr salesOrderCtr;

	// Tables
	private Table table;

	// Text Fields
	private Text txt_id;
	private Text txt_customer;
	private Text txt_date;
	private Text txt_deliveryDate;
	private Text txt_invoiceId;
	private Text search_id;

	private Button btn_delete;
	private Button btn_save;
	private Button btn_edit;
	private Button btn_create;
	private Table table_1;
	private Text txt_ProdId;
	private Text txt_Quantity;

	public SalesOrderGUI(Composite parent, int style) {
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

		Label lbl_id = new Label(composite_8, SWT.NONE);
		lbl_id.setText("Customer id:");

		search_id = new Text(composite_8, SWT.BORDER);
		search_id.setLayoutData(new RowData(197, SWT.DEFAULT));

		Button btn_search = new Button(composite_8, SWT.NONE);
		btn_search.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				boolean error = false;
				int id = 0;
				try {
					id = Integer.parseInt(search_id.getText());
				} catch (Exception ex) {
					error = true;
					// TODO: handle exception
				}
				if (!error)
					showSearchedSalesOrders(id);
			}
		});
		btn_search.setText("Search");

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

		TableColumn tblclmnCustomer = new TableColumn(table, SWT.NONE);
		tblclmnCustomer.setWidth(100);
		tblclmnCustomer.setText("Customer");
		
		TableColumn tblclmnDate = new TableColumn(table, SWT.NONE);
		tblclmnDate.setWidth(100);
		tblclmnDate.setText("Date");
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

		btn_delete = new Button(composite_5, SWT.CENTER);
		btn_save = new Button(composite_5, SWT.CENTER);
		btn_save.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				boolean create = false;
				int salesOrderId = 0;
				int customerId = 0;
				Date date = null;
				Date deliveryDate = null;
				try {
					salesOrderId = Integer.parseInt(txt_id.getText());
				} catch (NumberFormatException ex) {
					create = true;
					boolean error = false;
					try {
						date = new SimpleDateFormat("dd.MM.yyyy").parse(txt_date.getText());
						customerId = Integer.parseInt(txt_customer.getText());
						deliveryDate = new SimpleDateFormat("dd.MM.yyyy").parse(txt_deliveryDate.getText());
					} catch (Exception exc) {
						MessageBox box = new MessageBox(getShell(), 0);
						box.setText("Error");
						box.setMessage("There was an error. Please try again");
						box.open();
						error = true;
					}
					if (!error) {
						boolean ok = true;
						try {
							salesOrderId = salesOrderCtr.insertSalesOrder(date, customerId, deliveryDate);
									} catch (Exception ex1) {
							ok = false;
							MessageBox box = new MessageBox(getShell(), 0);
							box.setText("Error");
							box.setMessage("There was an error. Please try again");
							box.open();
						}
						if (ok) {
							//showAllSalesOrders();
							showSalesOrder(salesOrderId);
						}
					}
				}
				if (!create) {
					boolean error = false;
					try {
						date = new SimpleDateFormat("dd.MM.yyyy").parse(txt_date.getText());
						customerId = Integer.parseInt(txt_customer.getText());
						deliveryDate = new SimpleDateFormat("dd.MM.yyyy").parse(txt_deliveryDate.getText());
					} catch (Exception exc) {
						MessageBox box = new MessageBox(getShell(), 0);
						box.setText("Error");
						box.setMessage("There was an error. Please try again");
						box.open();
						error = true;
					}
					if (!error) {
						boolean ok = true;
						try {
							salesOrderCtr.updateSalesOrder(salesOrderId, customerId, date, deliveryDate);
						} catch (Exception ex1) {
							MessageBox box = new MessageBox(getShell(), 0);
							box.setText("Error");
							box.setMessage("There was an error. Please try again");
							box.open();
							ok = false;
						}
						if (ok) {
							showAllSalesOrders();
							showSalesOrder(salesOrderId);
						}
					}

				}
			}
		});
		btn_edit = new Button(composite_5, SWT.CENTER);
		btn_create = new Button(composite_5, SWT.CENTER);

		btn_delete.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				int orderId = 0;
				boolean error = false;
				try {
					orderId = Integer.parseInt(txt_id.getText());
				} catch (NumberFormatException ex) {
					error = true;
					MessageBox box = new MessageBox(getShell(), 0);
					box.setText("Error");
					box.setMessage("There was an error. Please try again");
					box.open();
				}
				if (!error) {
					try {
						salesOrderCtr.removeOrder(orderId);
					} catch (Exception ex) {
						MessageBox box = new MessageBox(getShell(), 0);
						box.setText("Error");
						box.setMessage("There was an error. Please try again");
						box.open();
					}
					showAllSalesOrders();
					resetFields();
					btn_delete.setEnabled(false);
					btn_edit.setEnabled(false);
					btn_save.setEnabled(false);
					btn_create.setEnabled(true);
				}
			}
		});
		btn_delete.setLayoutData(new RowData(75, 50));
		btn_delete.setText("DELETE");
		btn_delete.setEnabled(false);

		btn_save.setLayoutData(new RowData(75, 50));
		btn_save.setText("SAVE");
		btn_save.setEnabled(false);

		btn_edit.setLayoutData(new RowData(75, 50));
		btn_edit.setEnabled(false);
		btn_edit.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				btn_delete.setEnabled(false);
				btn_edit.setEnabled(false);
				btn_save.setEnabled(true);
				btn_create.setEnabled(false);

				txt_id.setEditable(false);
				txt_customer.setEditable(true);
				txt_date.setEditable(true);
				txt_deliveryDate.setEditable(true);
				txt_invoiceId.setEditable(true);
				search_id.setEditable(true);
			}
		});
		btn_edit.setText("EDIT");

		btn_create.setLayoutData(new RowData(75, 50));
		btn_create.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				btn_delete.setEnabled(false);
				btn_edit.setEnabled(false);
				btn_save.setEnabled(true);
				btn_create.setEnabled(false);

				resetFields();

				txt_id.setEditable(false);
				txt_customer.setEditable(true);
				txt_date.setEditable(true);
				txt_deliveryDate.setEditable(true);
				txt_invoiceId.setEditable(true);
				search_id.setEditable(true);
			}
		});
		btn_create.setText("CREATE");

		Composite composite_6 = new Composite(composite_3, SWT.NONE);
		composite_6.setLayoutData(BorderLayout.CENTER);
		composite_6.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 40));

		Composite composite_7 = new Composite(composite_6, SWT.NONE);
		composite_7.setLayout(new GridLayout(2, false));

		Label lblId = new Label(composite_7, SWT.NONE);
		lblId.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false,
				1, 1));
		lblId.setBounds(0, 0, 55, 15);
		lblId.setText("ID:");

		txt_id = new Text(composite_7, SWT.BORDER);
		GridData gd_txt_id = new GridData(SWT.LEFT, SWT.CENTER, true, false, 1,
				1);
		gd_txt_id.widthHint = 203;
		txt_id.setEditable(false);
		txt_id.setLayoutData(gd_txt_id);

		Label lblCustomer = new Label(composite_7, SWT.NONE);
		lblCustomer.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false,
				false, 1, 1));
		lblCustomer.setText("Customer:");

		txt_customer = new Text(composite_7, SWT.BORDER);
		GridData gd_txt_customer = new GridData(SWT.LEFT, SWT.CENTER, true, false,
				1, 1);
		gd_txt_customer.widthHint = 203;
		txt_customer.setEditable(false);
		txt_customer.setLayoutData(gd_txt_customer);

		Label lblDate = new Label(composite_7, SWT.NONE);
		lblDate.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false,
				false, 1, 1));
		lblDate.setText("Date:");

		txt_date = new Text(composite_7, SWT.BORDER);
		GridData gd_txt_date = new GridData(SWT.LEFT, SWT.CENTER, true,
				false, 1, 1);
		gd_txt_date.widthHint = 203;
		txt_date.setEditable(false);
		txt_date.setLayoutData(gd_txt_date);

		Label lblDeliveryDate = new Label(composite_7, SWT.NONE);
		lblDeliveryDate.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false,
				false, 1, 1));
		lblDeliveryDate.setText("DeliveryDate:");

		txt_deliveryDate = new Text(composite_7, SWT.BORDER);
		GridData gd_txt_deliveryDate = new GridData(SWT.LEFT, SWT.CENTER, true,
				false, 1, 1);
		gd_txt_deliveryDate.widthHint = 203;
		txt_deliveryDate.setEditable(false);
		txt_deliveryDate.setLayoutData(gd_txt_deliveryDate);

		Label lblDeliveryStatus = new Label(composite_7, SWT.NONE);
		lblDeliveryStatus.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false,
				false, 1, 1));
		lblDeliveryStatus.setText("Delivery Status:");
		
		Button btn_deliveryStatus = new Button(composite_7, SWT.CHECK);
		btn_deliveryStatus.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		btn_deliveryStatus.setText("Delivered");

		Label lblInvoiceId = new Label(composite_7, SWT.NONE);
		lblInvoiceId.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false,
				false, 1, 1));
		lblInvoiceId.setText("Invoice ID:");

		txt_invoiceId = new Text(composite_7, SWT.BORDER);
		GridData gd_txt_invoiceId = new GridData(SWT.LEFT, SWT.CENTER, true,
				false, 1, 1);
		gd_txt_invoiceId.widthHint = 203;
		txt_invoiceId.setEditable(false);
		txt_invoiceId.setLayoutData(gd_txt_invoiceId);
		
		Composite composite = new Composite(composite_6, SWT.NONE);
		RowLayout rl_composite = new RowLayout(SWT.VERTICAL);
		rl_composite.center = true;
		composite.setLayout(rl_composite);
		
		Composite composite_10 = new Composite(composite, SWT.NONE);
		composite_10.setLayoutData(new RowData(SWT.DEFAULT, 130));
		composite_10.setLayout(new RowLayout(SWT.HORIZONTAL));
		
		ScrolledComposite scrolledComposite_1 = new ScrolledComposite(composite_10, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		scrolledComposite_1.setLayoutData(new RowData(SWT.DEFAULT, 106));
		scrolledComposite_1.setExpandVertical(true);
		scrolledComposite_1.setExpandHorizontal(true);
		
		table_1 = new Table(scrolledComposite_1, SWT.BORDER | SWT.FULL_SELECTION);
		table_1.setLinesVisible(true);
		table_1.setItemCount(0);
		table_1.setHeaderVisible(true);
		
		TableColumn tbl2clmnId = new TableColumn(table_1, SWT.NONE);
		tbl2clmnId.setWidth(50);
		tbl2clmnId.setText("ID");
		
		TableColumn tbl2clmnProduct = new TableColumn(table_1, SWT.NONE);
		tbl2clmnProduct.setWidth(100);
		tbl2clmnProduct.setText("Product");
		
		TableColumn tbl2clmnAmount = new TableColumn(table_1, SWT.NONE);
		tbl2clmnAmount.setWidth(70);
		tbl2clmnAmount.setText("Amount");
		
		TableColumn tbl2clmnPrice = new TableColumn(table_1, SWT.NONE);
		tbl2clmnPrice.setWidth(100);
		tbl2clmnPrice.setText("Price");
		scrolledComposite_1.setContent(table_1);
		scrolledComposite_1.setMinSize(table_1.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		scrolledComposite_1.setMinSize(new Point(321, 45));
		
		Composite composite_1 = new Composite(composite, SWT.NONE);
		RowLayout rl_composite_1 = new RowLayout(SWT.HORIZONTAL);
		rl_composite_1.center = true;
		composite_1.setLayout(rl_composite_1);
		
		Label lblProdId = new Label(composite_1, SWT.NONE);
		lblProdId.setAlignment(SWT.CENTER);
		lblProdId.setText("Product ID:");
		
		txt_ProdId = new Text(composite_1, SWT.BORDER);
		txt_ProdId.setLayoutData(new RowData(43, SWT.DEFAULT));
		
		Label lblQuantity = new Label(composite_1, SWT.NONE);
		lblQuantity.setText("Quantity:");
		lblQuantity.setAlignment(SWT.CENTER);
		
		txt_Quantity = new Text(composite_1, SWT.BORDER);
		
		Button btnAdd = new Button(composite_1, SWT.NONE);
		btnAdd.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				int salesOrderId = 0;
				int productId = 0;
				int quantity = 0;
				try
				{
					salesOrderId = Integer.parseInt(txt_id.getText());
					productId = Integer.parseInt(txt_ProdId.getText());
					quantity = Integer.parseInt(txt_Quantity.getText());
				}
				catch(NumberFormatException ex)
				{
					
				}
				salesOrderCtr.addSalesLine(salesOrderId, productId, quantity);
			}
		});
		btnAdd.setText("Add");
		btn_create.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				// TODO add salesline
			}
		});
		
		Button btnDel = new Button(composite_1, SWT.NONE);
		btnDel.setText("Delete");
		btnDel.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				// TODO delete salesline
			}
		});

		table.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				int id = Integer.parseInt(table.getItem(
						table.getSelectionIndex()).getText(0));
				showSalesOrder(id);
			}
		});

		showAllSalesOrders();

	}

	private void showAllSalesOrders() {
		table.clearAll();
		table.setItemCount(0);
		ArrayList<SalesOrder> salesOrders = salesOrderCtr.getAllSalesOrders();
		for (SalesOrder salesOrder : salesOrders) {
			TableItem item = new TableItem(table, SWT.NONE);
			item.setText(0, String.valueOf(salesOrder.getSalesOrderId()));
			item.setText(1, salesOrder.getCustomer().getName());
			item.setText(2, salesOrder.getDate().toString());
		}

	}

	private void showSearchedSalesOrders(int customerId) {
		table.clearAll();
		table.setItemCount(0);
		ArrayList<SalesOrder> salesOrders = salesOrderCtr
				.getAllSalesOrdersByCustomerId(customerId);
		for (SalesOrder salesOrder : salesOrders) {
			TableItem item = new TableItem(table, SWT.NONE);
			item.setText(0, String.valueOf(salesOrder.getSalesOrderId()));
			item.setText(1, salesOrder.getCustomer().getName());
			item.setText(2, salesOrder.getDate().toString());
		}

	}

	private void showSalesOrder(int id) {
		SalesOrder salesOrder = salesOrderCtr.getSaleOrderById(id);
		txt_id.setText(String.valueOf(salesOrder.getSalesOrderId()));

		txt_customer.setText(salesOrder.getCustomer().getName());
		txt_date.setText(salesOrder.getDate().toString());
		txt_invoiceId.setText(String.valueOf(salesOrder.getInvoice().getInvoiceNo()));
		txt_deliveryDate.setText(salesOrder.getDeliveryDate().toString());

		txt_id.setEditable(false);
		txt_customer.setEditable(false);
		txt_date.setEditable(false);
		txt_invoiceId.setEditable(false);
		txt_deliveryDate.setEditable(false);

		btn_create.setEnabled(true);
		btn_edit.setEnabled(true);
		btn_delete.setEnabled(true);
		btn_save.setEnabled(false);
	}

	public void resetFields() {
		txt_id.setText("");
		txt_customer.setText("");
		txt_date.setText("");
		txt_deliveryDate.setText("");
		txt_invoiceId.setText("");
		search_id.setText("");
	}
}
