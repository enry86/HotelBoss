package cc.co.enricosartori.hotelboss.webclient.client.ui.widgets;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.FlexTable;

public class GenericTable extends Composite {
	interface Binder extends UiBinder<DockLayoutPanel, GenericTable> {}
	private static final Binder binder = GWT.create(Binder.class);
	private int cols;
	private int row;
	
	@UiField FlexTable header;
	@UiField FlexTable table;
	
	public GenericTable(String[] fields) {
		initWidget(binder.createAndBindUi(this));
		cols = fields.length;
		row = 0;
		int width = (int) 100.0 / fields.length;
		for (int i = 0; i < fields.length; i++) {
			header.getColumnFormatter().setWidth(i, Integer.toString(width) + "%");
			header.setText(0, i, fields[i]);
			table.getColumnFormatter().setWidth(i, Integer.toString(width) + "%");
		}
		
	}
	
	public void reset() {
		table.clear();
		row = 0;
	}
	
	public int add_row (ArrayList e) {
		for (int i = 0; i < cols; i++) {
			table.setText(row, i, (String) e.get(i));
		}
		return row++;
	}
}
