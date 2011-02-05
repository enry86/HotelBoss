package cc.co.enricosartori.hotelboss.webclient.client.reser;

import com.google.gwt.user.client.rpc.AsyncCallback;

import cc.co.enricosartori.hotelboss.dto.Reservation;
import cc.co.enricosartori.hotelboss.webclient.client.ui.mainwidget.RecReser;
import cc.co.enricosartori.hotelboss.webclient.client.ui.widgets.GenericTable.Listener;

public class ReserController {
	private RecReser view;
	private ReserModel model;
	
	public class TableCallback implements Listener {

		@Override
		public void row_selected(int index) {
			select_reserv (index);
		}
		
	}
	
	private AsyncCallback<Boolean> store_callback = new AsyncCallback<Boolean> () {

		public void onFailure(Throwable caught) {
			view.show_error("Errore di connessione con il server!");
		}

		public void onSuccess(Boolean result) {
			if (result) {
				view.update_table();
			}
			else {
				view.show_error("Prenotazione non valida, stanza già occupata?");
			}
		}
	};
	
	
	public ReserController (RecReser view, ReserModel model) {
		this.view = view;
		this.model = model;
		this.view.set_table_callback(new TableCallback());
	}
	
	public void new_reserv () {
		model.new_reserv ();
		view.set_editable(true);
	}
	
	public void cancel () {
		model.cancel ();
		view.reset_fields ();
		view.set_editable(false);
	}
	
	public void save_reserv () {
		Reservation r = view.get_reservation();
		if (r != null) {
			view.reset_fields();
			model.edit_reserv (r, store_callback);
			view.set_editable(false);
		}
	}
	
	public void dele_reserv () {
		model.dele_reserv(store_callback);
		view.reset_fields();
		view.set_editable(false);
	}
	
	public void select_reserv (int index) {
		Reservation r = view.show_reservation(index);
		model.select_reserv (r);
	}
}
