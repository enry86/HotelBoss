package cc.co.enricosartori.hotelboss.webclient.client.ui.mainwidget;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import cc.co.enricosartori.hotelboss.dto.Extra;
import cc.co.enricosartori.hotelboss.dto.Purchase;
import cc.co.enricosartori.hotelboss.webclient.client.purchase.PurController;
import cc.co.enricosartori.hotelboss.webclient.client.purchase.PurModel;
import cc.co.enricosartori.hotelboss.webclient.client.ui.widgets.ViewTable;

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
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.datepicker.client.DatePicker;

public class PurView extends Composite implements MainWidget {
	interface Binder extends UiBinder<DockLayoutPanel,PurView> { }
	private static final Binder binder = GWT.create(Binder.class);
	DockLayoutPanel dock;
	
	@UiField VerticalPanel cont;
	private ViewTable table  = new ViewTable (new String[] {"Data", "Extra", "Quantità"});;
	private Button sear_butt, save_butt, close_butt;
	private ListBox extra_lb = new ListBox();
	private TextBox room_tb, qty_tb;
	private DatePicker date_dp;
	private HorizontalPanel f_pan;
	private VerticalPanel t_pan;
	
	private ArrayList<Extra> extras = new ArrayList<Extra> ();
	
	private PurController p_cont;
	private PurModel p_mod;
	
	private boolean running = false;
	
	private AsyncCallback<List<Extra>> extra_callback = new AsyncCallback<List<Extra>> () {
		public void onFailure(Throwable caught) {
			show_message ("Errore di comunicazione con il server!");
		}

		public void onSuccess(List<Extra> result) {
			update_extras (result);
			populate_extras ();
		}
	};
	
	
	@Override 
	public void init() {
		dock = binder.createAndBindUi(this);
		p_mod = new PurModel ();
		p_cont = new PurController (this, p_mod);
		p_mod.fetch_extras (extra_callback);
		t_pan = setup_table_panel ();
		f_pan = setup_fields_panel ();
		cont.add (setup_search_panel ());
		cont.add (t_pan);
		cont.add (f_pan);
		running = true;
		set_editable (false);
		initWidget(dock);
	}

	@Override
	public boolean is_running() {
		return this.running;
	}
	
	public void update_list () {
		p_cont.search_room(room_tb.getValue ());
	}
	
	public void update () {
		p_mod.fetch_extras(extra_callback);
	}
	
	public void fill_table (List<Purchase> l) {
		Iterator<Purchase> i = l.iterator();
		ArrayList<String> row = new ArrayList<String>();
		DateTimeFormat df = DateTimeFormat.getFormat("dd/MM/yyyy");
		table.reset();
		while (i.hasNext()) {
			Purchase p = i.next();
			row.add(df.format(p.getDate()));
			row.add(retrieve_name(p.getId()));
			row.add(Integer.toString(p.getQty()));
			table.add_row(row);
			GWT.log("Row added");
			row.clear();
		}
	}
	
	public void set_editable (boolean enabled) {
		sear_butt.setEnabled(!enabled);
		save_butt.setEnabled(enabled);
		close_butt.setEnabled(enabled);
		room_tb.setEnabled(!enabled);
		f_pan.setVisible(enabled);
		t_pan.setVisible(enabled);
	}

	public void reset_fields (boolean room) {
		date_dp.setValue(new java.util.Date());
		qty_tb.setValue("");
		extra_lb.setSelectedIndex(0);
		if (room) {
			room_tb.setValue("");
			table.reset();
		}
	}
	
	public Purchase get_pur () {
		Purchase p = new Purchase ();
		try {
			p.setDate(new Date(date_dp.getValue().getTime()));
			p.setQty(Integer.parseInt(qty_tb.getValue()));
			p.setExtra_id(extras.get(extra_lb.getSelectedIndex()).getId());
			p.setRoom(Integer.parseInt(room_tb.getValue()));
			p.setStatus("NEW");
		}
		catch (Exception e) {
			show_message ("Errore nei dati inseriti!");
			p = null;
		}
		return p;
	}
	
	private String retrieve_name (int id) {
		boolean found = false;
		String res = "";
		Iterator<Extra> i = extras.iterator();
		while (i.hasNext() && !found) {
			Extra tmp = i.next();
			if (tmp.getId() == id) {
				res = tmp.getName();
				found = true;
			}
		}
		return res;
	}
	
	private void update_extras (List<Extra> l) {
		Iterator <Extra> i  = l.iterator();
		extras.clear();
		while (i.hasNext()) {
			extras.add(i.next());
		}
	}
	
	private HorizontalPanel setup_search_panel () {
		HorizontalPanel res = new HorizontalPanel();
		room_tb = new TextBox();
		sear_butt = new Button ("Cerca");
		res.add(new Label("Stanza: "));
		res.add(room_tb);
		res.add(sear_butt);
		sear_butt.addClickHandler(new ClickHandler () {
			public void onClick(ClickEvent event) {
				p_cont.search_room (room_tb.getValue());
			}
		});
		return res;
	}
	
	private VerticalPanel setup_table_panel () {
		VerticalPanel res = new VerticalPanel ();
		table.setVisible(true);
		res.add(new Label ("Extra della stanza:"));
		res.add(table);
		return res;
	}
	
	private HorizontalPanel setup_fields_panel () {
		HorizontalPanel res = new HorizontalPanel ();
		VerticalPanel fp = new VerticalPanel ();
		VerticalPanel bp = new VerticalPanel ();
		VerticalPanel dp = new VerticalPanel ();
		save_butt = new Button("Salva");
		close_butt = new Button("Chiudi");
		date_dp = new DatePicker ();
		qty_tb = new TextBox ();
		extra_lb.setVisibleItemCount(8);
		populate_extras();
		
		date_dp.setValue(new java.util.Date());
		dp.add (new Label ("Data:"));
		dp.add (date_dp);
		res.add(dp);
		
		fp.add (new Label("Extra:"));
		fp.add (extra_lb);
		fp.add (new Label("Quantità"));
		fp.add (qty_tb);
		res.add(fp);
		
		save_butt.addClickHandler(new ClickHandler () {
			public void onClick(ClickEvent event) {
				p_cont.save_pur ();
			}
		});
		close_butt.addClickHandler(new ClickHandler () {
			public void onClick(ClickEvent event) {
				p_cont.close ();
			}
		});
		bp.add (save_butt);
		bp.add (close_butt);
		res.add(bp);
		return res;
	}
	
	private void populate_extras () {
		Iterator<Extra> i = extras.iterator();
		extra_lb.clear();
		while (i.hasNext()) {
			Extra tmp = i.next();
			extra_lb.addItem(tmp.getId() + " " + tmp.getName());
		}
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
