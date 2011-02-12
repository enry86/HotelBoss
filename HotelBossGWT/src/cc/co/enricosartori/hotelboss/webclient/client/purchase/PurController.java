package cc.co.enricosartori.hotelboss.webclient.client.purchase;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

import cc.co.enricosartori.hotelboss.dto.Purchase;
import cc.co.enricosartori.hotelboss.webclient.client.ui.mainwidget.PurView;

public class PurController {
	private PurView view;
	private PurModel model;
	
	private AsyncCallback<List<Purchase>> search_cb = new AsyncCallback<List<Purchase>> () {
		public void onFailure(Throwable caught) {
			view.show_message("Errore di comunicazione con il server");
		}

		public void onSuccess(List<Purchase> result) {
			if (result != null) {
				view.fill_table (result);
				view.set_editable(true);
			}
			else
				view.show_message("Camera ancora libera");
		}
	};
	
	private AsyncCallback<Boolean> save_cb = new AsyncCallback<Boolean> () {
		public void onFailure(Throwable caught) {
			view.show_message("Errore di comunicazione con il Server!");
		}

		public void onSuccess(Boolean result) {
			if (result) {
				view.show_message("Extra salvato correttamente");
				view.reset_fields(false);
				view.update_list ();
			}
			else {
				view.show_message("Salvataggio non riuscito. Camera ancora libera?");
			}
		}
	};
	
	
	public PurController (PurView view, PurModel model) {
		this.view = view;
		this.model = model;
	}
	
	public void search_room (String room) {
		try {
			int r = Integer.parseInt(room);
			model.search_room (r, search_cb);
		}
		catch (Exception e) {
			view.show_message("Valore non valido");
		}
	}
	
	public void save_pur () {
		Purchase p = view.get_pur();
		if (p != null) {
			model.save_pur (p, save_cb);
		}
	}
	
	public void close () {
		view.reset_fields (true);
		view.set_editable(false);
	}
	
}
