package cc.co.enricosartori.hotelboss.webclient.client.extra;



import com.google.gwt.user.client.rpc.AsyncCallback;

import cc.co.enricosartori.hotelboss.dto.Extra;
import cc.co.enricosartori.hotelboss.webclient.client.ui.mainwidget.ConfExtra;
import cc.co.enricosartori.hotelboss.webclient.client.ui.widgets.GenericTable.Listener;

public class ExtraController {
	private ConfExtra view;
	private ExtraModel model;
	
	public class TableCallback implements Listener {

		public void row_selected(int index) {
			select_extra (index);
		}
		
	}
	
	
	private AsyncCallback<Void> store_callback = new AsyncCallback<Void> () {

		public void onFailure(Throwable caught) {
			view.show_error("Errore di comunicazione col server");
		}

		public void onSuccess(Void result) {
			view.update_table();
		}
		
	};
	
	public ExtraController (ConfExtra view, ExtraModel model) {
		this.view = view;
		this.model = model;
		this.view.set_table_callback(new TableCallback ());
	}
	
	public void new_extra () {
		model.new_extra ();
		view.set_editable(true);
	}
	
	public void cancel () {
		model.cancel ();
		view.reset_fields();
		view.set_editable(false);
	}
	
	public void save_extra () {
		Extra e = view.get_extra();
		if (e != null) {
			view.reset_fields ();
			model.edit_extra (e, store_callback);
			view.set_editable(false);
		}
	}

	public void dele_extra () {
		model.dele_extra (store_callback);
		view.reset_fields();
		view.set_editable(false);
	}
	
	public void select_extra (int index) {
		Extra e = view.show_extra(index);
		model.select_extra (e);
	}
}
