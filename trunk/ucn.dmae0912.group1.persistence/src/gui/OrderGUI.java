package gui;

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
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import swing2swt.layout.FlowLayout;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.layout.GridData;

public class OrderGUI extends Composite {
	
	// Tables
	private Table table;
	
	// Text Fields
	private Text txt_orderID;
	private Text txt_date;
	private Text txt_amount;
	private Text txt_deliveryDate;
	private Text txt_deliveryStatus;
	private Text search_name;

	public OrderGUI(Composite parent, int style) {
		super(parent, style);
		this.setLayout(new BorderLayout(0, 0));

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

		final Button btn_delete = new Button(composite_5, SWT.CENTER);
		final Button btn_save = new Button(composite_5, SWT.CENTER);
		final Button btn_edit = new Button(composite_5, SWT.CENTER);
		final Button btn_create = new Button(composite_5, SWT.CENTER);
		
		btn_delete.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				btn_delete.setEnabled(true);
				btn_edit.setEnabled(false);
				btn_save.setEnabled(false);
				btn_create.setEnabled(false);
			}
		});
		btn_delete.setLayoutData(new RowData(75, 50));
		btn_delete.setText("DELETE");
		btn_delete.setEnabled(false);

		
		btn_save.setLayoutData(new RowData(75, 50));
		btn_save.setText("SAVE");
		btn_save.setEnabled(false);

		
		btn_edit.setLayoutData(new RowData(75, 50));
		btn_edit.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				btn_delete.setEnabled(true);
				btn_edit.setEnabled(false);
				btn_save.setEnabled(true);
				btn_create.setEnabled(false);
				
				txt_orderID.setEditable(true);
				txt_date.setEditable(true);
				txt_amount.setEditable(true);
				txt_deliveryDate.setEditable(true);
				txt_deliveryStatus.setEditable(true);
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
				
				txt_orderID.setEditable(true);
				txt_date.setEditable(true);
				txt_amount.setEditable(true);
				txt_deliveryDate.setEditable(true);
				txt_deliveryStatus.setEditable(true);
				search_name.setEditable(true);
			}
		});
		btn_create.setText("CREATE");

		Composite composite_6 = new Composite(composite_3, SWT.NONE);
		composite_6.setLayoutData(BorderLayout.CENTER);
		composite_6.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 40));

		Composite composite_7 = new Composite(composite_6, SWT.NONE);
		composite_7.setLayout(new GridLayout(2, false));

		Label lblorderID = new Label(composite_7, SWT.NONE);
		lblorderID.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false,
				1, 1));
		lblorderID.setBounds(0, 0, 55, 15);
		lblorderID.setText("Order ID:");

		txt_orderID = new Text(composite_7, SWT.BORDER);
		GridData gd_txt_orderID = new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1);
		gd_txt_orderID.widthHint = 203;
		txt_orderID.setEditable(false);
		txt_orderID.setLayoutData(gd_txt_orderID);

		Label lblDate = new Label(composite_7, SWT.NONE);
		lblDate.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false,
				false, 1, 1));
		lblDate.setText("Date:");

		txt_date = new Text(composite_7, SWT.BORDER);
		GridData gd_txt_date = new GridData(SWT.LEFT, SWT.CENTER, true, false, 1,
				1);
		gd_txt_date.widthHint = 203;
		txt_date.setEditable(false);
		txt_date.setLayoutData(gd_txt_date);

		Label lblAmount = new Label(composite_7, SWT.NONE);
		lblAmount.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false,
				false, 1, 1));
		lblAmount.setText("Amount:");

		txt_amount = new Text(composite_7, SWT.BORDER);
		GridData gd_txt_amount = new GridData(SWT.LEFT, SWT.CENTER, true, false, 1,
				1);
		gd_txt_amount.widthHint = 203;
		txt_amount.setEditable(false);
		txt_amount.setLayoutData(gd_txt_amount);

		Label lblDeliveryDate = new Label(composite_7, SWT.NONE);
		lblDeliveryDate.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false,
				false, 1, 1));
		lblDeliveryDate.setText("Delivery Date:");

		txt_deliveryDate = new Text(composite_7, SWT.BORDER);
		GridData gd_txt_deliveryDate = new GridData(SWT.LEFT, SWT.CENTER, true, false, 1,
				1);
		gd_txt_deliveryDate.widthHint = 203;
		txt_deliveryDate.setEditable(false);
		txt_deliveryDate.setLayoutData(gd_txt_deliveryDate);

		Label lblDeliveryStatus = new Label(composite_7, SWT.NONE);
		lblDeliveryStatus.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false,
				false, 1, 1));
		lblDeliveryStatus.setText("Delivery Status:");

		txt_deliveryStatus = new Text(composite_7, SWT.BORDER);
		GridData gd_txt_deliveryStatus = new GridData(SWT.LEFT, SWT.CENTER, true, false, 1,
				1);
		gd_txt_deliveryStatus.widthHint = 203;
		txt_deliveryStatus.setEditable(false);
		txt_deliveryStatus.setLayoutData(gd_txt_deliveryStatus);

	}
}