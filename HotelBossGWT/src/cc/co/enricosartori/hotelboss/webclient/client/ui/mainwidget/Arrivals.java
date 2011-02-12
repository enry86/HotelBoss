package cc.co.enricosartori.hotelboss.webclient.client.ui.mainwidget;


import java.sql.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import cc.co.enricosartori.hotelboss.dto.Customer;
import cc.co.enricosartori.hotelboss.dto.Reduction;
import cc.co.enricosartori.hotelboss.webclient.client.arrivals.ArrController;
import cc.co.enricosartori.hotelboss.webclient.client.arrivals.ArrModel;

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
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.datepicker.client.DatePicker;

public class Arrivals extends Composite implements MainWidget {
	private boolean running = false;
	interface Binder extends UiBinder<DockLayoutPanel,Arrivals> { }
	private static final Binder binder = GWT.create(Binder.class);
	DockLayoutPanel dock;
	
	@UiField HorizontalPanel cont;

	private TextBox room_tb, name_tb, peop_tb;
	private ListBox trat_lb;
	private ListBox disc_lb;
	private Button save_butt, canc_butt;
	private DatePicker arr_dp;
	
	private final String[] TREATS = {"Pensione Completa", "Mezza Pensione", "B&B"};
	private HashMap<Integer, Reduction> reds;
	
	private ArrController a_cont;
	private ArrModel a_mod;
	
	private AsyncCallback<List<Reduction>> call = new AsyncCallback<List<Reduction>> () {
		public void onFailure(Throwable caught) {
			update_disc (null);
			show_message ("Errore di comuncazione con il server");
		}
		public void onSuccess(List<Reduction> result) {
			update_disc (result);
		}
	};
	
	public Arrivals() {
		GWT.log("Carico arrivals");
	}
	
	@Override
	public void init() {
		dock = binder.createAndBindUi(this);
		a_mod = new ArrModel();
		a_cont = new ArrController(this, a_mod);
		reds = new HashMap<Integer, Reduction> ();
		disc_lb = new ListBox();
		update ();
		cont.add (setup_dp_panel ());
		cont.add (setup_fields_panel ());
		cont.add (setup_lb_panel ());
		cont.add (setup_butt_panel ());
		running = true;
		initWidget(dock);
	}
	
	@Override
	public boolean is_running() {
		return running;
	}
	
	public void update () {
		reds.clear();
		disc_lb.clear();
		a_mod.get_reductions (call);
	}
	
	public void update_disc (List<Reduction> l) {
		int r = 1;
		Reduction vr = new Reduction();
		vr.setId(-1);
		vr.setDescr("Nessuno sconto");
		disc_lb.addItem(vr.getDescr());
		reds.put(0, vr);
		if (l != null) {
			Iterator<Reduction> i = l.iterator();
			while (i.hasNext()) {
				Reduction tmp = i.next();
				disc_lb.addItem(tmp.getDescr());
				reds.put(r, tmp);
				r++;
			}
		}
	}
	
	public void reset_fields () {
		arr_dp.setValue(new java.util.Date());
		room_tb.setValue("");
		name_tb.setValue("");
		peop_tb.setValue("");
		trat_lb.setSelectedIndex(0);
		disc_lb.setSelectedIndex(0);
		
	}
	
	private VerticalPanel setup_dp_panel () {
		VerticalPanel res = new VerticalPanel (); 
		arr_dp = new DatePicker();
		arr_dp.setValue(new java.util.Date());
		arr_dp.setTitle("Data di arrivo:");
		res.add(arr_dp);
		return res;
	}
	
	private VerticalPanel setup_fields_panel () {
		VerticalPanel res = new VerticalPanel ();
		room_tb = new TextBox ();
		name_tb = new TextBox ();
		peop_tb = new TextBox ();
		res.add(new Label ("Camera:"));
		res.add(room_tb);
		res.add(new Label ("Cliente:"));
		res.add(name_tb);
		res.add(new Label ("Numero Persone"));
		res.add(peop_tb);
		return res;
	}
	
	private VerticalPanel setup_lb_panel () {
		VerticalPanel res = new VerticalPanel ();
		trat_lb = new ListBox ();
		for (int i = 0; i < TREATS.length; i++) {
			trat_lb.addItem(TREATS[i]);
		}
		res.add(new Label("Trattamento:"));
		res.add(trat_lb);
		res.add(new Label("Sconto:"));
		res.add(disc_lb);
		return res;
	}
	
	private VerticalPanel setup_butt_panel () {
		VerticalPanel res = new VerticalPanel ();
		save_butt = new Button ("Salva");
		canc_butt = new Button ("Annulla");
		res.add(save_butt);
		res.add(canc_butt);
		save_butt.addClickHandler (new ClickHandler () {
			public void onClick(ClickEvent event) {
				a_cont.save_customer();
			}
		});
		canc_butt.addClickHandler (new ClickHandler () {
			public void onClick(ClickEvent event) {
				a_cont.cancel();
			}
		});
		return res;
	}
	
	public Customer get_customer () {
		Customer c = new Customer ();
		try {
			c.setRoom(Integer.parseInt(room_tb.getValue()));
			c.setPeople(Integer.parseInt(peop_tb.getValue()));
			c.setDate_arr(new Date(arr_dp.getValue().getTime()));
			c.setTreatment(trat_lb.getSelectedIndex());
			c.setDiscount(reds.get(disc_lb.getSelectedIndex()).getId());
			c.setName(name_tb.getValue());
			c.setStatus("NEW");
		}
		catch (Exception e) {
			show_message ("Errore nei dati inseriti");
			c = null;
		}
		
		return c;
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
