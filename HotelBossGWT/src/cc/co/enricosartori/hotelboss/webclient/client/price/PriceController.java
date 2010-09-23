package cc.co.enricosartori.hotelboss.webclient.client.price;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;

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
	
	private AsyncCallback<Void> store_callback = new AsyncCallback<Void>() {

		@Override
		public void onFailure(Throwable caught) {
			view.show_error("Errore di connessione col Server!");
		}

		@Override
		public void onSuccess(Void result) {
			view.update_table();
		}
		
	};
	
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
		view.reset_fields();
		view.set_editable(false);
	}
	
	public void save_price () {
		Price p = view.get_price();
		if (p != null) {
			view.reset_fields();
			model.edit_price(p, store_callback);
			view.set_editable(false);
		}
	}
	
	public void dele_price () {
		model.dele_price(store_callback);
		view.reset_fields();
		view.set_editable(false);
	}
	
	public void select_price (int index) {
		Price p = view.show_price(index);
		model.select_price(p);
		GWT.log(Integer.toString(index));
	}
	
}

