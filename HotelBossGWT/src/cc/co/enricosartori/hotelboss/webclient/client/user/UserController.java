package cc.co.enricosartori.hotelboss.webclient.client.user;


import com.google.gwt.user.client.rpc.AsyncCallback;

import cc.co.enricosartori.hotelboss.dto.User;
import cc.co.enricosartori.hotelboss.webclient.client.ui.mainwidget.ConfUser;
import cc.co.enricosartori.hotelboss.webclient.client.ui.widgets.GenericTable.Listener;

public class UserController {
	private ConfUser view;
	private UserModel model;
	
	public class TableCallback implements Listener {
		@Override
		public void row_selected(int index) {
			select_user (index);
		}
		
	}
	
	private AsyncCallback<Boolean> store_callback = new AsyncCallback<Boolean> () {

		@Override
		public void onFailure(Throwable caught) {
			view.show_error("Errore di connessione con il server");
		}

		@Override
		public void onSuccess(Boolean result) {
			if (result) {
				view.update_table();
			}
			else {
				view.show_error("Errore nell'inserimento, username già presente?");
			}
		}
	};
	
	public UserController (ConfUser view, UserModel model) {
		this.view = view;
		this.model = model;
		this.view.set_table_callback(new TableCallback());
	}
	
	public void new_user () {
		model.new_user ();
		view.set_editable(true);
	}
	
	public void cancel () {
		model.cancel ();
		view.reset_fields ();
		view.set_editable(false);
	}
	
	public void save_user () {
		User u = view.get_user();
		if (u != null) {
			view.reset_fields();
			model.edit_user (u, store_callback);
			view.set_editable(false);
		}
	}
	
	public void dele_user () {
		model.dele_user (store_callback);
		view.reset_fields();
		view.set_editable(false);
	}
	
	public void select_user (int index) {
		User u = view.show_user(index);
		model.select_user (u);
	}
}
