package cc.co.enricosartori.hotelboss.webclient.client.arrivals;

import com.google.gwt.user.client.rpc.AsyncCallback;

import cc.co.enricosartori.hotelboss.dto.Customer;
import cc.co.enricosartori.hotelboss.webclient.client.ui.mainwidget.Arrivals;

public class ArrController {
	private ArrModel model;
	private Arrivals view;
	
	private AsyncCallback<Boolean> store_call = new AsyncCallback<Boolean> () {
		public void onFailure(Throwable caught) {
			view.show_message("Errore di comunicazione col server!");
		}

		public void onSuccess(Boolean result) {
			if (result)	view.show_message("Cliente salvato correttamente");
			else view.show_message("Errore nel salvataggio, stanza già occupata?");
		}
	};
	
	public ArrController (Arrivals view, ArrModel model) {
		this.model = model;
		this.view = view;
	}
	
	public void save_customer () {
		Customer c = view.get_customer();
		if (c != null) {
			model.save_customer (c, store_call);
			view.reset_fields ();
		}
	}
	
	public void cancel () {
		view.reset_fields();
	}
}
