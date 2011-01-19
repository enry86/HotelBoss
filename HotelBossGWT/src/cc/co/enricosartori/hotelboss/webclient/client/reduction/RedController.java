package cc.co.enricosartori.hotelboss.webclient.client.reduction;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;

import cc.co.enricosartori.hotelboss.dto.Price;
import cc.co.enricosartori.hotelboss.dto.Reduction;
import cc.co.enricosartori.hotelboss.webclient.client.ui.mainwidget.ConfRed;
import cc.co.enricosartori.hotelboss.webclient.client.ui.widgets.GenericTable.Listener;

public class RedController {

	private ConfRed view;
	private RedModel model;
	
	
	public class TableCallback implements Listener {
		@Override
		public void row_selected(int index) {
			select_red(index);			
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
	
	
	public RedController (ConfRed view, RedModel model) {
		this.model = model;
		this.view = view;
		this.view.set_table_callback(new TableCallback ());
	}
	
	
	public void select_red (int index) {
		Reduction r = view.show_red (index);
		model.select_red (r);
	}


	public void new_red() {
		model.new_red ();
		view.set_editable (true);
	}


	public void dele_red() {
		view.set_editable (false);
		model.dele_red (store_callback);
		view.reset_fields ();	
	}


	public void save_red() {
		Reduction r = view.get_reduction ();
		GWT.log(Integer.toString(r.getRed_type()));
		if (r != null) {
			view.set_editable (false);
			model.edit_price(r, store_callback);
			view.reset_fields ();
		}
	}
	
	
	public void cancel() {
		model.cancel ();
		view.set_editable (false);
	}
}
