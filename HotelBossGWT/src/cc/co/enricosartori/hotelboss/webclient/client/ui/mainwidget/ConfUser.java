package cc.co.enricosartori.hotelboss.webclient.client.ui.mainwidget;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import cc.co.enricosartori.hotelboss.dto.Price;
import cc.co.enricosartori.hotelboss.dto.User;
import cc.co.enricosartori.hotelboss.webclient.client.ui.widgets.GenericTable;
import cc.co.enricosartori.hotelboss.webclient.client.ui.widgets.GenericTable.Listener;
import cc.co.enricosartori.hotelboss.webclient.client.user.UserController;
import cc.co.enricosartori.hotelboss.webclient.client.user.UserModel;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ConfUser extends Composite implements MainWidget {
	interface Binder extends UiBinder<DockLayoutPanel,ConfUser> { }
	private static final Binder binder = GWT.create(Binder.class);
	DockLayoutPanel dock;
	
	private boolean running = false;
	private UserModel u_mod;
	private UserController u_cont;
	
	@UiField HorizontalPanel cont;
	@UiField(provided=true)
	final GenericTable table = new GenericTable (new String[]
			{"Username",
			 "Role",
			 "Nome",
			 "Cognome",
			 "Email"}
			);
	
	private HashMap<Integer, User> hm_table;
	
	private AsyncCallback<Void> callback = new AsyncCallback<Void>() {
		public void onFailure (Throwable caught) {
			show_error("Server error...");
		}
		public void onSuccess (Void v) {
			Iterator<User> i = u_mod.get_iterator();
			while (i.hasNext()) {
				User u = i.next();
				add_entry(u);
			}
		}
	};
	
	private TextBox user_tb, pass_tb, role_tb, name_tb, surn_tb, email_tb;
	private Button new_butt, save_butt, canc_butt, dele_butt;
	
	
	public ConfUser () {}
	
	@Override
	public void init() {
		dock = binder.createAndBindUi(this);
		u_mod = new UserModel();
		u_cont = new UserController(this, u_mod);
		hm_table = new HashMap<Integer, User> ();
		update_table ();
		cont.add (setup_fields_panel ());
		cont.add (setup_butt_panel ());
		set_editable (false);
		running = true;
		initWidget (dock);
	}

	@Override
	public boolean is_running() {
		return running;
	}
	
	public void set_table_callback (Listener l) {
		table.set_listener(l);
	}
	
	public void update_table () {
		reset_table ();
		u_mod.fetch_users (callback);
	}
	
	public void reset_table () {
		hm_table.clear();
		table.reset();
	}
	
	public User get_user () {
		User res = new User();
		res.setUsername(user_tb.getValue());
		res.setPasswd(pass_tb.getValue());
		res.setRole(role_tb.getValue());
		res.setName(name_tb.getValue());
		res.setSurname(surn_tb.getValue());
		res.setEmail(email_tb.getValue());
		return res;
	}
	
	public User show_user (int index) {
		User sel = hm_table.get(index);
		update_fields (sel);
		set_editable (true);
		return sel;
	}
	
	public void add_entry (User u) {
		ArrayList<String> u_tab = new ArrayList<String> ();
		u_tab.add(u.getUsername());
		u_tab.add(u.getRole());
		u_tab.add(u.getName());
		u_tab.add(u.getSurname());
		u_tab.add(u.getEmail());
		int index = table.add_row(u_tab);
		hm_table.put(new Integer(index), u);
	}
	
	private void update_fields (User u) {
		user_tb.setValue(u.getUsername());
		pass_tb.setValue(u.getPasswd());
		role_tb.setValue(u.getRole());
		name_tb.setValue(u.getName());
		surn_tb.setValue(u.getSurname());
		email_tb.setValue(u.getEmail());
	}
	
	public void reset_fields () {
		user_tb.setValue("");
		pass_tb.setValue("");
		role_tb.setValue("");
		name_tb.setValue("");
		surn_tb.setValue("");
		email_tb.setValue("");
	}
	
	private VerticalPanel setup_fields_panel () {
		VerticalPanel res = new VerticalPanel ();
		user_tb = new TextBox();
		pass_tb = new TextBox();
		role_tb = new TextBox();
		name_tb = new TextBox();
		surn_tb = new TextBox();
		email_tb = new TextBox();
		res.add(new Label("Username:"));
		res.add(user_tb);
		res.add(new Label("Password:"));
		res.add(pass_tb);
		res.add(new Label("Gruppo:"));
		res.add(role_tb);
		res.add(new Label("Nome:"));
		res.add(name_tb);
		res.add(new Label("Cognome:"));
		res.add(surn_tb);
		res.add(new Label("Email"));
		res.add(email_tb);
		return res;
	}
	
	private VerticalPanel setup_butt_panel () {
		VerticalPanel res = new VerticalPanel ();
		new_butt = new Button("Nuovo");
		save_butt = new Button("Salva");
		canc_butt = new Button("Annulla");
		dele_butt = new Button("Elimina");
		
		new_butt.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				u_cont.new_user();
			}
		});

		save_butt.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				u_cont.save_user();
			}
		});
		
		canc_butt.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				u_cont.cancel();
			}
		});
		
		dele_butt.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				u_cont.dele_user();
			}
		});
		res.add(new_butt);
		res.add(save_butt);
		res.add(dele_butt);
		res.add(canc_butt);
		return res;
	}
	
	public void set_editable (boolean enabled) {
		user_tb.setEnabled(enabled);
		pass_tb.setEnabled(enabled);
		role_tb.setEnabled(enabled);
		name_tb.setEnabled(enabled);
		surn_tb.setEnabled(enabled);
		email_tb.setEnabled(enabled);
		save_butt.setEnabled(enabled);
		new_butt.setEnabled(!enabled);
		dele_butt.setEnabled(enabled);
		canc_butt.setEnabled(enabled);
	}
	
	public void show_error(String message) {
		final DialogBox err = new DialogBox(true);
		VerticalPanel vp = new VerticalPanel();
		Label l = new Label(message);
		Button ok = new Button("OK");
		ok.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				err.hide();
			}
		});
		vp.add(l);
		vp.add(ok);
		err.add(vp);
		err.setText("Errore");
		err.setAnimationEnabled(true);
		err.show();
	}
}
