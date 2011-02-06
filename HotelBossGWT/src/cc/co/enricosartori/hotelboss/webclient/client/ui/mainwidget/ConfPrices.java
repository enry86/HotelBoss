package cc.co.enricosartori.hotelboss.webclient.client.ui.mainwidget;


import java.util.ArrayList;
import java.sql.Date;
import java.util.HashMap;
import java.util.Iterator;

import cc.co.enricosartori.hotelboss.dto.Price;
import cc.co.enricosartori.hotelboss.webclient.client.price.PriceController;
import cc.co.enricosartori.hotelboss.webclient.client.price.PriceModel;
import cc.co.enricosartori.hotelboss.webclient.client.ui.widgets.GenericTable;
import cc.co.enricosartori.hotelboss.webclient.client.ui.widgets.GenericTable.Listener;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
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
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.datepicker.client.DatePicker;

public class ConfPrices extends Composite implements MainWidget {
	interface Binder extends UiBinder<DockLayoutPanel,ConfPrices> { }
	private static final Binder binder = GWT.create(Binder.class);
	DockLayoutPanel dock;
	
	@UiField HorizontalPanel content;
	@UiField(provided=true)
	final GenericTable table = new GenericTable(new String[]
			{"Data Inizio", 
			 "Data fine",
			 "Pens. Compl.",
			 "Mezza Pens.",
			 "Pernottamento"}
			);
	
	private boolean running = false;
	private PriceController p_cont; 
	private PriceModel p_mod;
	
	private TextBox fb_tb, hb_tb, bb_tb;
	private Button new_b, save_b, canc_b, dele_b;
	
	private DatePicker dp_in;
	private DatePicker dp_out;
	
	private HashMap<Integer, Price> hm_table;
	
	private AsyncCallback<Void> callback = new AsyncCallback<Void>() {
			public void onFailure (Throwable caught) {
				show_error("Server error...");
			}
			public void onSuccess (Void v) {
				Iterator<Price> i = p_mod.get_iterator();
				while (i.hasNext()) {
					Price p = i.next();
					add_entry(p);
				}
			}
	};
	
	public ConfPrices () {

	}
	
	public void init () {
		dock = binder.createAndBindUi(this);
		p_mod = new PriceModel();
		p_cont = new PriceController(p_mod, this);
		hm_table = new HashMap<Integer, Price>();
		update_table();
		dp_in = new DatePicker();
		dp_out = new DatePicker();
		content.add(setup_dp_panel(dp_in, "Data di arrivo"));
		content.add(setup_dp_panel(dp_out, "Data di partenza"));
		content.add(setup_fields_panel());
		content.add(setup_butt_panel());
		set_editable(false);
		running = true;
		initWidget(dock);
	}
	
	public boolean is_running() {
		return running;
	}
	
	public void set_table_callback (Listener l) {
		table.set_listener(l);
	}
	
	public void update_table () {
		reset_table();
		p_mod.fetch_pricelist(callback);
	}
	
	public void reset_table () {
		hm_table.clear();
		table.reset();
	}

	
	public Price get_price () {
		Price res = new Price();
		res.setStart_d(new Date (dp_in.getValue().getTime()));
		res.setEnd_d(new Date (dp_out.getValue().getTime()));
		try {
			res.setFb(get_float(fb_tb.getText()));
			res.setHb(get_float(hb_tb.getText()));
			res.setBb(get_float(bb_tb.getText()));
		} catch (Exception e) {
			show_error("Errore nei dati inseriti");
			return null;
		}
		return res;
	}
	
	public Price show_price (int index) {
		Price sel = hm_table.get(index);
		update_fields(sel);
		set_editable(true);
		return sel;
	}
	
	public void add_entry (Price p) {
		ArrayList<String> p_tab = new ArrayList<String>();
		DateTimeFormat df = DateTimeFormat.getFormat("dd/MM/yyyy");
		NumberFormat nf = NumberFormat.getFormat("#########.00");
		p_tab.add(df.format(p.getStart_d()));
		p_tab.add(df.format(p.getEnd_d()));
		p_tab.add(nf.format(p.getFb()));
		p_tab.add(nf.format(p.getHb()));
		p_tab.add(nf.format(p.getBb()));
		int index = table.add_row(p_tab);
		hm_table.put(new Integer(index), p);
	}
	
	private void update_fields (Price p) {
		NumberFormat nf = NumberFormat.getFormat("#########.00");
		dp_in.setValue(p.getStart_d());
		dp_out.setValue(p.getEnd_d());
		fb_tb.setText(nf.format(p.getFb()));
		hb_tb.setText(nf.format(p.getHb()));
		bb_tb.setText(nf.format(p.getBb()));
	}
	
	public void reset_fields () {
		dp_in.setValue(new java.util.Date());
		dp_out.setValue(new java.util.Date());
		fb_tb.setText("");
		hb_tb.setText("");
		bb_tb.setText("");
	}
	
	private float get_float (String s) {
		s = s.replace(',', '.');
		float d = Float.parseFloat(s);
		return d;
	}
	
	private VerticalPanel setup_dp_panel (DatePicker dp, String title) {
		VerticalPanel res = new VerticalPanel();
		Label l_tit = new Label(title);
		dp.setValue(new java.util.Date());
		dp.setTitle(title);
		res.add(l_tit);
		res.add(dp);
		return res;
	}
	
	private VerticalPanel setup_fields_panel () {
		VerticalPanel res = new VerticalPanel();
		fb_tb = new TextBox();
		hb_tb = new TextBox();
		bb_tb = new TextBox();
		res.add(new Label("Pensione Completa:"));
		res.add(fb_tb);
		res.add(new Label("Mezza Pensione:"));
		res.add(hb_tb);
		res.add(new Label("Pernottamento:"));
		res.add(bb_tb);
		return res;
	}
	
	public void set_editable(boolean enable) {
		fb_tb.setEnabled(enable);
		hb_tb.setEnabled(enable);
		bb_tb.setEnabled(enable);
		save_b.setEnabled(enable);
		new_b.setEnabled(!enable);
		dele_b.setEnabled(enable);
		canc_b.setEnabled(enable);
	}
	
	private VerticalPanel setup_butt_panel () {
		VerticalPanel res = new VerticalPanel();
		new_b = new Button();
		save_b = new Button();
		canc_b = new Button();
		dele_b = new Button();
		
		new_b.setText("Nuovo");
		save_b.setText("Salva");
		canc_b.setText("Annulla");
		dele_b.setText("Elimina");
		
		new_b.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				p_cont.new_price();
			}
		});

		save_b.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				p_cont.save_price();
			}
		});
		
		canc_b.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				p_cont.cancel();
			}
		});
		
		dele_b.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				p_cont.dele_price();
			}
		});
		res.add(new_b);
		res.add(save_b);
		res.add(dele_b);
		res.add(canc_b);
		return res;
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
