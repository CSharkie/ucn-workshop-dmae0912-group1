package gui;

import controller.ProductsCtr;
import controller.SuppliersCtr;
import model.Product;
import model.Supplier;

import java.util.ArrayList;

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

public class ProductGUI extends Composite {
	
	ProductsCtr prodCtr;
	SuppliersCtr supplierCtr;
	
	// Tables
	private Table table;
	
	// Text Fields
	private Text txt_id;
	private Text txt_name;
	private Text txt_PurchasePrice;
	private Text txt_SalePrice;
	private Text txt_RentPrice;
	private Text txt_CountryOfOrigin;
	private Text txt_minStock;
	private Text search_name;

	private Button btn_delete;
	private Button btn_save;
	private Button btn_edit;
	private Button btn_create;
	private Text txt_supplier;
	
	public ProductGUI(Composite parent, int style) {
		super(parent, style);
		this.setLayout(new BorderLayout(0, 0));
		
		prodCtr = new ProductsCtr();
		
		supplierCtr = new SuppliersCtr();

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
		search_name.setLayoutData(new RowData(116, SWT.DEFAULT));

		Button btn_search = new Button(composite_8, SWT.NONE);
		btn_search.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String name = search_name.getText();
				showSearchedProducts(name);
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

		TableColumn tblclmnName = new TableColumn(table, SWT.NONE);
		tblclmnName.setWidth(100);
		tblclmnName.setText("Name");
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
				int productId = 0;
				String name = null;
				double PurchasePrice = 0.0;
				double SalePrice = 0.0;
				double RentPrice = 0.0;
				String CountryOfOrigin = null;
				int minStock = 0;
				int supplierId = 0;
				Supplier supplier = null;
				try {
					productId = Integer.parseInt(txt_id.getText());
				} catch (NumberFormatException ex) {
					create = true;
					boolean error = false;
					try {
						name = txt_name.getText();
						PurchasePrice = Double.parseDouble(txt_PurchasePrice.getText());
						SalePrice = Double.parseDouble(txt_SalePrice.getText());
						RentPrice = Double.parseDouble(txt_RentPrice.getText());
						CountryOfOrigin = txt_CountryOfOrigin.getText();
						minStock = Integer.parseInt(txt_minStock.getText());
						supplierId = Integer.parseInt(txt_supplier.getText());
						supplier = supplierCtr.findById(supplierId);
						
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
							productId = prodCtr.insertProduct(name, supplier, PurchasePrice, SalePrice, RentPrice, CountryOfOrigin, minStock);
						} catch (Exception ex1) {
							ok = false;
							MessageBox box = new MessageBox(getShell(), 0);
							box.setText("Error");
							box.setMessage("There was an error. Please try again");
							box.open();
						}
						if (ok) {
							showAllProducts();
							showProduct(productId);
						}
					}
				}
				if (!create) {
					boolean error = false;
					try {
						name = txt_name.getText();
						PurchasePrice = Double.parseDouble(txt_PurchasePrice.getText());
						SalePrice = Double.parseDouble(txt_SalePrice.getText());
						RentPrice = Double.parseDouble(txt_RentPrice.getText());
						CountryOfOrigin = txt_CountryOfOrigin.getText();
						minStock = Integer.parseInt(txt_minStock.getText());
						supplierId = Integer.parseInt(txt_supplier.getText());
						supplier = supplierCtr.findById(supplierId);
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
							prodCtr.updateProduct(productId, supplier, name, PurchasePrice, SalePrice, RentPrice, CountryOfOrigin, minStock);
						} catch (Exception ex1) {
							MessageBox box = new MessageBox(getShell(), 0);
							box.setText("Error");
							box.setMessage("There was an error. Please try again");
							box.open();
							ok = false;
						}
						if (ok) {
							showAllProducts();
							showProduct(productId);
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
				int productId = 0;
				boolean error = false;
				try {
					productId = Integer.parseInt(txt_id.getText());
				} catch (NumberFormatException ex) {
					error = true;
					MessageBox box = new MessageBox(getShell(), 0);
					box.setText("Error");
					box.setMessage("There was an error. Please try again");
					box.open();
				}
				if (!error) {
					try {
						prodCtr.removeProduct(productId);
					} catch (Exception ex) {
						MessageBox box = new MessageBox(getShell(), 0);
						box.setText("Error");
						box.setMessage("There was an error. Please try again");
						box.open();
					}
					showAllProducts();
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
				btn_delete.setEnabled(true);
				btn_edit.setEnabled(false);
				btn_save.setEnabled(true);
				btn_create.setEnabled(false);
				
				txt_id.setEditable(false);
				txt_supplier.setEditable(true);
				txt_name.setEditable(true);
				txt_PurchasePrice.setEditable(true);
				txt_SalePrice.setEditable(true);
				txt_RentPrice.setEditable(true);
				txt_CountryOfOrigin.setEditable(true);
				txt_minStock.setEditable(true);
				search_name.setEditable(true);
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
				txt_supplier.setEditable(true);
				txt_name.setEditable(true);
				txt_PurchasePrice.setEditable(true);
				txt_SalePrice.setEditable(true);
				txt_RentPrice.setEditable(true);
				txt_CountryOfOrigin.setEditable(true);
				txt_minStock.setEditable(true);
				search_name.setEditable(true);
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
		GridData gd_txt_id = new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1);
		gd_txt_id.widthHint = 203;
		txt_id.setEditable(false);
		txt_id.setLayoutData(gd_txt_id);
		
		Label lblSupplier = new Label(composite_7, SWT.NONE);
		lblSupplier.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		lblSupplier.setText("Supplier:");
		
		txt_supplier = new Text(composite_7, SWT.BORDER);
		txt_supplier.setEditable(false);
		txt_supplier.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		txt_supplier.setEditable(false);

		Label lblName = new Label(composite_7, SWT.NONE);
		lblName.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false,
				false, 1, 1));
		lblName.setText("Name:");

		txt_name = new Text(composite_7, SWT.BORDER);
		GridData gd_txt_name = new GridData(SWT.LEFT, SWT.CENTER, true, false, 1,
				1);
		gd_txt_name.widthHint = 203;
		txt_name.setEditable(false);
		txt_name.setLayoutData(gd_txt_name);

		Label lblPurchasePrice = new Label(composite_7, SWT.NONE);
		lblPurchasePrice.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false,
				false, 1, 1));
		lblPurchasePrice.setText("Purchase Price:");

		txt_PurchasePrice = new Text(composite_7, SWT.BORDER);
		GridData gd_txt_PurchasePrice = new GridData(SWT.LEFT, SWT.CENTER, true, false, 1,
				1);
		gd_txt_PurchasePrice.widthHint = 203;
		txt_PurchasePrice.setEditable(false);
		txt_PurchasePrice.setLayoutData(gd_txt_PurchasePrice);

		Label lblSalePrice = new Label(composite_7, SWT.NONE);
		lblSalePrice.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false,
				false, 1, 1));
		lblSalePrice.setText("Sale Price:");

		txt_SalePrice = new Text(composite_7, SWT.BORDER);
		GridData gd_txt_SalePrice = new GridData(SWT.LEFT, SWT.CENTER, true, false, 1,
				1);
		gd_txt_SalePrice.widthHint = 203;
		txt_SalePrice.setEditable(false);
		txt_SalePrice.setLayoutData(gd_txt_SalePrice);

		Label lblRentPrice = new Label(composite_7, SWT.NONE);
		lblRentPrice.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false,
				false, 1, 1));
		lblRentPrice.setText("Rent Price:");

		txt_RentPrice = new Text(composite_7, SWT.BORDER);
		GridData gd_txt_RentPrice = new GridData(SWT.LEFT, SWT.CENTER, true, false, 1,
				1);
		gd_txt_RentPrice.widthHint = 203;
		txt_RentPrice.setEditable(false);
		txt_RentPrice.setLayoutData(gd_txt_RentPrice);
		
		
		Label lblCountryOfOrigin = new Label(composite_7, SWT.NONE);
		lblCountryOfOrigin.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false,
				false, 1, 1));
		lblCountryOfOrigin.setText("Country Of Origin:");

		txt_CountryOfOrigin = new Text(composite_7, SWT.BORDER);
		GridData gd_txt_countryOfOrigin = new GridData(SWT.LEFT, SWT.CENTER, true, false, 1,
				1);
		gd_txt_countryOfOrigin.widthHint = 203;
		txt_CountryOfOrigin.setEditable(false);
		txt_CountryOfOrigin.setLayoutData(gd_txt_countryOfOrigin);
		
		Label lblMinStock = new Label(composite_7, SWT.NONE);
		lblMinStock.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false,
				false, 1, 1));
		lblMinStock.setText("Min Stock:");

		txt_minStock = new Text(composite_7, SWT.BORDER);
		GridData gd_txt_minStock = new GridData(SWT.LEFT, SWT.CENTER, true, false, 1,
				1);
		gd_txt_minStock.widthHint = 203;
		txt_minStock.setEditable(false);
		txt_minStock.setLayoutData(gd_txt_minStock);

		table.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				int productId = Integer.parseInt(table.getItem(
						table.getSelectionIndex()).getText(0));
				showProduct(productId);
			}
		});

		showAllProducts();
		
	}
	
	private void showAllProducts() {
		table.clearAll();
		table.setItemCount(0);
		ArrayList<Product> products = prodCtr.findAllProducts();
		for (Product product : products) {
			TableItem item = new TableItem(table, SWT.NONE);
			item.setText(0, String.valueOf(product.getProductId()));
			item.setText(1, product.getName());
		}
	}

	private void showSearchedProducts(String name) {
		table.clearAll();
		table.setItemCount(0);
		ArrayList<Product> products = prodCtr.findByName(name);
		for (Product product : products) {
			TableItem item = new TableItem(table, SWT.NONE);
			item.setText(0, String.valueOf(product.getProductId()));
			item.setText(1, product.getName());
		}

	}

	private void showProduct(int productId) {
		Product product = prodCtr.findById(productId);
		txt_id.setText(String.valueOf(product.getProductId()));
		txt_name.setText(product.getName());
		txt_PurchasePrice.setText(String.valueOf(product.getPurchasePrice()));
		txt_SalePrice.setText(String.valueOf(product.getSalePrice()));
		txt_RentPrice.setText(String.valueOf(product.getRentPrice()));
		txt_CountryOfOrigin.setText(product.getCountryOfOrigin());
		txt_minStock.setText(String.valueOf(product.getMinStock()));
		txt_supplier.setText(String.valueOf(product.getSupplier().getSupplierId()));

		txt_id.setEditable(false);
		txt_supplier.setEditable(false);
		txt_name.setEditable(false);
		txt_PurchasePrice.setEditable(false);
		txt_SalePrice.setEditable(false);
		txt_RentPrice.setEditable(false);
		txt_CountryOfOrigin.setEditable(false);
		txt_minStock.setEditable(false);

		btn_create.setEnabled(true);
		btn_edit.setEnabled(true);
		btn_delete.setEnabled(true);
		btn_save.setEnabled(false);
	}

	public void resetFields() {
		txt_supplier.setText("");
		txt_id.setText("");
		txt_name.setText("");
		txt_PurchasePrice.setText("");
		txt_SalePrice.setText("");
		txt_RentPrice.setText("");
		txt_CountryOfOrigin.setText("");
		txt_minStock.setText("");
		search_name.setText("");
	}
	
}