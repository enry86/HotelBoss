package cc.co.enricosartori.hotelboss.webclient.client.dashboard;

import java.sql.Date;
import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

import cc.co.enricosartori.hotelboss.dto.Reservation;
import cc.co.enricosartori.hotelboss.webclient.client.ui.mainwidget.DashBoard;

public class DashController {
	private DashModel model;
	private DashBoard view;
	
	private AsyncCallback<List<Reservation>> arr_call = new AsyncCallback<List<Reservation>> () {
		public void onFailure(Throwable caught) {
			view.show_error ("Errore di comunicazione con il server");
		}

		public void onSuccess(List<Reservation> result) {
			view.reset_arr ();
			view.show_arr (result);
		}
	};
	
	private AsyncCallback<List<Reservation>> dep_call = new AsyncCallback<List<Reservation>> () {
		public void onFailure(Throwable caught) {
			view.show_error ("Errore di comunicazione con il server");
		}

		public void onSuccess(List<Reservation> result) {
			view.reset_dep ();
			view.show_dep (result);
		}
	};
	
	
	public DashController (DashBoard view, DashModel model) {
		this.view = view;
		this.model = model;
	}
	
	public void update_lists () {
		Date d = view.get_date();
		model.update_arr (d, arr_call);
		model.update_dep (d, dep_call);
	}
}
