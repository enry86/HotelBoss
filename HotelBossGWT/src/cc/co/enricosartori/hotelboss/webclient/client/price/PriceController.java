package cc.co.enricosartori.hotelboss.webclient.client.price;

import com.google.gwt.core.client.GWT;

import cc.co.enricosartori.hotelboss.dto.Price;
import cc.co.enricosartori.hotelboss.webclient.client.ui.mainwidget.ConfPrices;
import cc.co.enricosartori.hotelboss.webclient.client.ui.widgets.GenericTable.Listener;

public class PriceController {
	private PriceModel model;
	private ConfPrices view;
	
	public class TableCallback implements Listener {
		@Override
		public void row_selected(int index) {
			select_price(index);			
		}
		
	}
	
	public PriceController (PriceModel model, ConfPrices view) {
		this.model = model;
		this.view = view;
		this.view.set_table_callback(new TableCallback());
	}
	
	
	public void new_price () {
		model.new_price();
		view.set_editable(true);
	}
	
	public void cancel () {
		model.cancel();
		view.set_editable(false);
	}
	
	public void edit_price (Price p) {
		model.edit_price(p);
		view.set_editable(true);
	}
	
	public void save_price () {
		model.save_price();
		view.set_editable(false);
	}
	
	public void dele_price (Price p) {
		model.dele_price(p);
		view.set_editable(false);
	}
	
	public void select_price (int index) {
		Price p = view.get_price(index);
		model.select_price(p);
		GWT.log(Integer.toString(index));
	}
	
}

