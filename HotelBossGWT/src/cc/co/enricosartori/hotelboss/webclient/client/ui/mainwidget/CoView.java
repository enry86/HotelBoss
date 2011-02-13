package cc.co.enricosartori.hotelboss.webclient.client.ui.mainwidget;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import cc.co.enricosartori.hotelboss.dto.Extra;
import cc.co.enricosartori.hotelboss.dto.Period;
import cc.co.enricosartori.hotelboss.dto.Purchase;
import cc.co.enricosartori.hotelboss.dto.Totals;
import cc.co.enricosartori.hotelboss.webclient.client.checkout.CoController;
import cc.co.enricosartori.hotelboss.webclient.client.checkout.CoModel;
import cc.co.enricosartori.hotelboss.webclient.client.ui.widgets.ViewTable;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class CoView extends Composite implements MainWidget {
	public interface Binder extends UiBinder<DockLayoutPanel, CoView> {} 
	private static final Binder binder = GWT.create(Binder.class);
	DockLayoutPanel dock;
	
	@UiField
	HorizontalPanel sear;
	@UiField
	VerticalPanel cont;
	@UiField
	HorizontalPanel butt;
	
	private VerticalPanel pur_panel;
	private VerticalPanel bill_panel;
	private VerticalPanel tot_panel;
	private Button sear_butt, close_butt, ok_butt, rem_butt;
	private ListBox pur_lb = new ListBox ();
	private TextBox room_tb;
	private Label tot_lab;
	private HandlerRegistration ok_hr;
	
	private ViewTable per_tab = new ViewTable (new String [] {"Periodo", "Giorni", "Prezzo"});
	private ViewTable tot_tab = new ViewTable (new String [] {"Descrizone", "Importo"});
	
	private CoModel mod;
	private CoController con;
	private HashMap <String, Purchase> purs;
	private HashMap <Integer, String> extras;
	private boolean running = false;
	
	private AsyncCallback<List<Extra>> extra_cb = new AsyncCallback<List<Extra>> () {
		public void onFailure(Throwable caught) {
			show_message("Errore di comunicazione con il server");
		}
		public void onSuccess(List<Extra> result) {
			update_extras(result);
		}
	};
	
	private AsyncCallback<Float> tpur_cb = new AsyncCallback<Float> () {
		public void onFailure(Throwable caught) {
			show_message("Errore di comunicazione con il server");
		}

		public void onSuccess(Float result) {
			NumberFormat nf = NumberFormat.getFormat("########.00");
			tot_lab.setText("Totale Extra: " + nf.format(result));
			con.select_bill ();
		}
	};
	
	
	private AsyncCallback<Totals> tot_cb = new AsyncCallback<Totals> () {
		public void onFailure(Throwable caught) {
			show_message("Errore di comunicazione con il server");
		}

		public void onSuccess(Totals result) {
			populate_tot_tab (result);
			set_checkout_mode ();
		}
	};
	
	private AsyncCallback<List<Period>> per_cb = new AsyncCallback<List<Period>> () {
		public void onFailure(Throwable caught) {
			show_message ("Errore di comunicazione con il server");
		}

		public void onSuccess(List<Period> result) {
			populate_pers_tab (result);
		}
	};
	
	private ClickHandler pur_handler = new ClickHandler () {
		public void onClick(ClickEvent event) {
			con.pur_accept ();
		}
	};
	
	private ClickHandler bill_handler = new ClickHandler () {
		public void onClick(ClickEvent event) {
			con.bill_accept ();
		}
	};
	
	private ClickHandler co_handler = new ClickHandler () {
		public void onClick(ClickEvent event) {
			con.checkout ();
		}
	};
	
	@Override
	public void init() {
		dock = binder.createAndBindUi (this);
		mod = new CoModel ();
		con = new CoController (this, mod);
		purs = new HashMap<String, Purchase> ();
		extras = new HashMap<Integer, String> ();
		mod.fetch_extras (extra_cb);
		setup_search_pan ();
		pur_panel = setup_pur_panel ();
		bill_panel = setup_bill_panel ();
		tot_panel = setup_tot_panel ();
		cont.add(pur_panel);
		cont.add(bill_panel);
		cont.add(tot_panel);
		setup_butt_panel ();
		set_search_mode ();
		running = true;
		initWidget (dock);
	}

	@Override
	public boolean is_running() {
		return running;
	}

	@Override
	public void update() {
		
	}
	
	public void populate_pur_lb (List<Purchase> l) {
		Iterator<Purchase> i = l.iterator();
		DateTimeFormat df = DateTimeFormat.getFormat("dd/MM/yyyy");
		pur_lb.clear();
		while (i.hasNext()) {
			Purchase tmp = i.next();
			String date = df.format(tmp.getDate());
			String id = tmp.getId() + ": " + date + ", " + tmp.getQty() + " " + retrieve_name (tmp.getExtra_id());
			purs.put(id, tmp);
			pur_lb.addItem(id);
		}
	}
	
	public void update_extras (List<Extra> l) {
		extras.clear();
		Iterator<Extra> i = l.iterator();
		while (i.hasNext()) {
			Extra tmp = i.next();
			extras.put(tmp.getId(), tmp.getName());
		}
	}
	
	public void update_periods_tab () {
		mod.fetch_periods (Integer.parseInt(room_tb.getValue()), per_cb);
	}
	
	public void populate_pers_tab (List<Period> l) {
		Iterator<Period> i = l.iterator();
		ArrayList<String> row = new ArrayList<String> ();
		DateTimeFormat df = DateTimeFormat.getFormat("dd/MM/yyyy");
		NumberFormat nf = NumberFormat.getFormat("#########.00");
		while (i.hasNext()) {
			Period tmp = i.next();
			String sd = df.format(tmp.getD_start());
			String ed = df.format(tmp.getD_end());
			row.add(sd + " - " + ed);
			row.add(Integer.toString(tmp.getDays()));
			row.add(nf.format(tmp.getPrice()));
			per_tab.add_row(row);
			row.clear();
		}
	}
	
	public void set_search_mode () {
		pur_panel.setVisible(false);
		bill_panel.setVisible(false);
		tot_panel.setVisible(false);
		butt.setVisible(false);
	}
	
	public void set_pur_mode () {
		pur_panel.setVisible(true);
		bill_panel.setVisible(false);
		tot_panel.setVisible(false);
		butt.setVisible(true);
		ok_hr.removeHandler();
		ok_hr = ok_butt.addClickHandler(pur_handler);
	}
	
	public void set_bill_mode () {
		pur_panel.setVisible(false);
		bill_panel.setVisible(true);
		tot_panel.setVisible(false);
		butt.setVisible(true);
		ok_hr.removeHandler();
		ok_hr = ok_butt.addClickHandler(bill_handler);
	}
	
	public void set_checkout_mode () {
		pur_panel.setVisible(false);
		bill_panel.setVisible(false);
		tot_panel.setVisible(true);
		butt.setVisible(true);
		ok_butt.setText("Checkout");
		ok_hr.removeHandler();
		ok_hr = ok_butt.addClickHandler(co_handler);
	}
	
	public void set_editable (boolean enabled) {
		room_tb.setEnabled(!enabled);
		sear_butt.setEnabled(!enabled);
	}
	
	public String get_room () {
		return room_tb.getValue();
	}
	
	private String retrieve_name (int extraid) {
		return extras.get(extraid);
	}
	
	public void update_tot_pur () {
		mod.get_total_pur (tpur_cb);
	}
	
	public void update_totals () {
		mod.fetch_totals (tot_cb);
	}
	
	public void populate_tot_tab (Totals tot) {
		ArrayList<String> row = new ArrayList<String> ();
		NumberFormat nf = NumberFormat.getFormat("########.00");
		row.add("Tot Extra");
		row.add(nf.format(tot.getExtra()));
		tot_tab.add_row(row);
		row.clear();
		row.add("Tot Pensione");
		row.add(nf.format(tot.getPens()));
		tot_tab.add_row(row);
		row.clear();
		row.add("Totale");
		row.add(nf.format(tot.getTotal()));
		tot_tab.add_row(row);
		row.clear();
	}
	
	public void reset_fields () {
		room_tb.setValue ("");
		pur_lb.clear();
		purs.clear();
		per_tab.reset();
		tot_tab.reset();
	}
	
	public void remove_pur () {
		String id = pur_lb.getItemText(pur_lb.getSelectedIndex());
		purs.remove(id);
		pur_lb.removeItem(pur_lb.getSelectedIndex());
	}
	
	public Collection<Purchase> get_purs () {
		return purs.values();
	}
	
	private void setup_search_pan () {
		room_tb = new TextBox ();
		sear_butt = new Button ("Cerca");
		sear.add(new Label("Stanza: "));
		sear.add(room_tb);
		sear.add(sear_butt);
		sear_butt.addClickHandler(new ClickHandler () {
			public void onClick(ClickEvent event) {
				con.search_room();
			}
		});
	}
	
	private VerticalPanel setup_pur_panel () {
		VerticalPanel res = new VerticalPanel();
		rem_butt = new Button ("Elimina");
		tot_lab = new Label ("Totale: 0.00");
		pur_lb.setVisibleItemCount(15);
		res.add (new Label ("Consumazioni Camera:"));
		res.add (pur_lb);
		res.add (rem_butt);
		res.add (tot_lab);
		rem_butt.addClickHandler(new ClickHandler () {
			public void onClick(ClickEvent event) {
				con.remove_pur ();
			}
		});
		return res;
	}
	
	private void setup_butt_panel () {
		ok_butt = new Button ("Prosegui");
		close_butt = new Button ("Chiudi");
		butt.add(close_butt);
		butt.add(ok_butt);
		ok_hr = ok_butt.addClickHandler(pur_handler);
		close_butt.addClickHandler(new ClickHandler () {
			public void onClick(ClickEvent event) {
				con.close ();
			}
		});
	}
	
	private VerticalPanel setup_bill_panel () {
		VerticalPanel res = new VerticalPanel ();
		res.add (tot_lab);
		res.add (per_tab);
		return res;
	}
	
	private VerticalPanel setup_tot_panel () {
		VerticalPanel res = new VerticalPanel ();
		res.add(tot_tab);
		return res;
	}
	
	public void show_message (String message) {
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
		err.setText("Message");
		err.setAnimationEnabled(true);
		err.center();
		err.show();
	}
	
}
