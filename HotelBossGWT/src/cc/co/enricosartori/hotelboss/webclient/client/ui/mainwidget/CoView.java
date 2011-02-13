package cc.co.enricosartori.hotelboss.webclient.client.ui.mainwidget;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import cc.co.enricosartori.hotelboss.dto.Extra;
import cc.co.enricosartori.hotelboss.dto.Purchase;
import cc.co.enricosartori.hotelboss.webclient.client.checkout.CoController;
import cc.co.enricosartori.hotelboss.webclient.client.checkout.CoModel;
import cc.co.enricosartori.hotelboss.webclient.client.ui.widgets.ViewTable;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
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
	private Button sear_butt, close_butt, ok_butt, rem_butt;
	private ListBox pur_lb = new ListBox ();
	private TextBox room_tb;
	private Label tot_lab;
	
	private ViewTable per_tab = new ViewTable (new String [] {"Periodo", "Giorni", "Prezzo"});
	
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
			NumberFormat nf = NumberFormat.getFormat("########,00");
			tot_lab.setText("Totale Extra: " + nf.format(result));
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
		cont.add(pur_panel);
		cont.add(bill_panel);
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
	
	public void set_search_mode () {
		pur_panel.setVisible(false);
		bill_panel.setVisible(false);
		butt.setVisible(false);
	}
	
	public void set_pur_mode () {
		pur_panel.setVisible(true);
		bill_panel.setVisible(false);
		butt.setVisible(true);
	}
	
	public void set_bill_mode () {
		pur_panel.setVisible(false);
		bill_panel.setVisible(true);
		butt.setVisible(true);
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
	
	public void reset_fields () {
		room_tb.setValue ("");
		pur_lb.clear();
		purs.clear();
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
		ok_butt.addClickHandler(new ClickHandler () {
			public void onClick(ClickEvent event) {
				con.pur_accept ();
			}
		});
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
