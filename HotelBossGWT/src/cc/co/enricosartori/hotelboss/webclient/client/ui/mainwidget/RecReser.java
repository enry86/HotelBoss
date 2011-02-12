package cc.co.enricosartori.hotelboss.webclient.client.ui.mainwidget;

import java.util.ArrayList;
import java.sql.Date;
import java.util.HashMap;
import java.util.Iterator;


import cc.co.enricosartori.hotelboss.dto.Reservation;
import cc.co.enricosartori.hotelboss.webclient.client.reser.ReserController;
import cc.co.enricosartori.hotelboss.webclient.client.reser.ReserModel;
import cc.co.enricosartori.hotelboss.webclient.client.ui.widgets.GenericTable;
import cc.co.enricosartori.hotelboss.webclient.client.ui.widgets.GenericTable.Listener;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
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
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.datepicker.client.DatePicker;

public class RecReser extends Composite implements MainWidget {
	interface Binder extends UiBinder<DockLayoutPanel, RecReser> {}
	private static final Binder binder = GWT.create(Binder.class);
	DockLayoutPanel dock;

	private boolean running = false;
	private ReserController r_cont;
	private ReserModel r_mod;
	
	@UiField HorizontalPanel cont;
	@UiField (provided=true)
	final GenericTable table = new GenericTable (new String []
		{"Data arrivo",
		 "Data partenza",
		 "Stanza",
		 "Cliente",
		 "Note"
		});
	
	private HashMap<Integer, Reservation> hm_table;
	
	private AsyncCallback<Void> callback = new AsyncCallback<Void> () {

		public void onFailure(Throwable caught) {
			show_error ("Server Error...");
		}

		public void onSuccess(Void result) {
			Iterator<Reservation> i = r_mod.get_iterator();
			while (i.hasNext()) {
				Reservation r = i.next();
				add_entry (r);
			}
		}
	};
	
	private DatePicker arr_dp, dep_dp;
	private TextBox cust_tb, room_tb;
	private TextArea note_ta;
	private Button new_butt, save_butt, dele_butt, canc_butt;
	
	public RecReser () {}
	
	@Override
	public void init() {
		dock = binder.createAndBindUi (this);
		r_mod = new ReserModel ();
		r_cont = new ReserController (this, r_mod);
		hm_table = new HashMap<Integer, Reservation> ();
		update_table ();
		arr_dp = new DatePicker ();
		dep_dp = new DatePicker ();
		cont.add (setup_dp_panel (arr_dp, "Data di arrivo:"));
		cont.add (setup_dp_panel (dep_dp, "Data di partenza:"));
		cont.add (setup_fields_panel ());
		cont.add (setup_butt_panel ());
		set_editable (false);
		running = true;
		initWidget (dock);
	}
	
	public void update () {
		update_table ();
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
		r_mod.fetch_reservations (callback);
	}
	
	public void reset_table () {
		hm_table.clear ();
		table.reset ();
	}
	
	public Reservation get_reservation () {
		Reservation r = new Reservation();
		r.setDate_arr (new Date (arr_dp.getValue().getTime()));
		r.setDate_dep (new Date (dep_dp.getValue().getTime()));
		r.setRoom(room_tb.getValue());
		r.setCustomer(cust_tb.getValue());
		r.setNote(note_ta.getValue());
		return r;
	}
	
	public Reservation show_reservation (int index) {
		Reservation r = hm_table.get(index);
		update_fields (r);
		set_editable (true);
		return r;
	}
	
	public void add_entry (Reservation r) {
		ArrayList<String> r_tab = new ArrayList<String> ();
		DateTimeFormat df = DateTimeFormat.getFormat("dd/MM/yyyy");
		r_tab.add (df.format (r.getDate_arr ()));
		r_tab.add (df.format (r.getDate_dep ()));
		r_tab.add (r.getRoom ());
		r_tab.add (r.getCustomer ());
		r_tab.add (r.getNote ());
		int index = table.add_row(r_tab);
		hm_table.put(index, r);
	}
	
	private void update_fields (Reservation r) {
		arr_dp.setValue (r.getDate_arr ());
		dep_dp.setValue (r.getDate_dep ());
		room_tb.setValue (r.getRoom ());
		cust_tb.setValue(r.getCustomer());
		note_ta.setValue(r.getNote());
	}
	
	public void reset_fields () {
		arr_dp.setValue(new java.util.Date());
		dep_dp.setValue(new java.util.Date());
		room_tb.setValue("");
		cust_tb.setValue("");
		note_ta.setValue("");
	}
	
	private VerticalPanel setup_dp_panel (DatePicker dp, String title) {
		VerticalPanel res = new VerticalPanel ();
		dp.setValue(new java.util.Date());
		res.add(new Label(title));
		res.add(dp);
		return res;
	}
	
	private VerticalPanel setup_fields_panel () {
		VerticalPanel res = new VerticalPanel ();
		room_tb = new TextBox ();
		cust_tb = new TextBox ();
		note_ta = new TextArea ();
		res.add (new Label ("Stanza:"));
		res.add (room_tb);
		res.add (new Label ("Cliente:"));
		res.add (cust_tb);
		res.add (new Label ("Note:"));
		res.add (note_ta);
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
				r_cont.new_reserv();
			}
		});

		save_butt.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				r_cont.save_reserv();
			}
		});
		
		canc_butt.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				r_cont.cancel();
			}
		});
		
		dele_butt.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				r_cont.dele_reserv();
			}
		});
		res.add(new_butt);
		res.add(save_butt);
		res.add(dele_butt);
		res.add(canc_butt);
		return res;
	}
	
	public void set_editable (boolean enabled) {
		room_tb.setEnabled(enabled);
		cust_tb.setEnabled(enabled);
		note_ta.setEnabled(enabled);
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
