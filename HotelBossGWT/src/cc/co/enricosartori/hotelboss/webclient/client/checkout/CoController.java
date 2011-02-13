package cc.co.enricosartori.hotelboss.webclient.client.checkout;

import java.util.Collection;
import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

import cc.co.enricosartori.hotelboss.dto.Purchase;
import cc.co.enricosartori.hotelboss.webclient.client.ui.mainwidget.CoView;

public class CoController {
	private CoView view;
	private CoModel model;
	
	private AsyncCallback<List<Purchase>> search_cb = new AsyncCallback<List<Purchase>> () {
		public void onFailure(Throwable caught) {
			view.show_message("Errore di comunicazione con il server!");
		}

		public void onSuccess(List<Purchase> result) {
			if (result != null) {
				view.populate_pur_lb (result);
				view.set_pur_mode ();
				view.set_editable(true);
			}
			else view.show_message("Camera non occupata!");
		}
	};
	
	
	private AsyncCallback<Void> save_cb = new AsyncCallback<Void> () {
		public void onFailure(Throwable caught) {
			view.show_message ("Errore di comunicazione con il server: " + caught.getMessage());
		}

		public void onSuccess(Void result) {
			view.show_message ("Extra aggiunti al conto");
			view.update_tot_pur();
		}
	};
	
	
	private AsyncCallback<Void> canc_cb = new AsyncCallback<Void> () {
		@Override
		public void onFailure(Throwable caught) {
			view.show_message("Errore di comunicazione con il server");
		}

		@Override
		public void onSuccess(Void result) {
			view.reset_fields ();
			view.set_search_mode();
			view.set_editable(false);
		}
	};
	
	private AsyncCallback<Void> co_cb = new AsyncCallback<Void> () {
		public void onFailure(Throwable caught) {
			view.show_message("Errore di comunicazione con il server");
		}

		public void onSuccess(Void result) {
			view.show_message("Checkout del cliente eseguito con successo");
			view.reset_fields();
			view.set_search_mode();
			view.set_editable(false);
		}
		
	};
	
	
	public CoController (CoView view, CoModel model) {
		this.view = view;
		this.model = model;
	}
	
	public void search_room () {
		try {
			int room = Integer.parseInt(view.get_room());
			model.search_room (room, search_cb);
		}
		catch (Exception e){
			view.show_message ("Stanza non valida");
		}
	}
	
	public void remove_pur () {
		view.remove_pur();
	}
	
	public void close () {
		model.close (canc_cb);
	
	}
	
	public void pur_accept () {
		Collection<Purchase> l = view.get_purs ();
		model.save_purs (l, save_cb);
	}
	
	public void bill_accept () {
		view.update_totals ();
	}
	
	public void select_bill () {
		view.update_periods_tab ();
		view.set_bill_mode();
	}
	
	public void checkout () {
		String room = view.get_room ();
		model.checkout(Integer.parseInt(room), co_cb);
	}
}
