package cc.co.enricosartori.hotelboss.webclient.client.ui.mainwidget;

import java.util.ArrayList;
import java.util.Date;
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
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
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
	
	private PriceController p_cont; 
	private PriceModel p_mod;
	private TextBox fb_tb, hb_tb, bb_tb;
	private Button new_b, save_b, canc_b, dele_b;
	
	private DatePicker dp_in;
	private DatePicker dp_out;
	
	private HashMap<Integer, Price> hm_table;
	
	private AsyncCallback<Void> callback = new AsyncCallback<Void>() {
			public void onFailure (Throwable caught) {
				//price_l.setText("Server error...");
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
		content.add(setup_dp_panel(dp_in, "Data di arrivo"));
		content.add(setup_dp_panel(dp_out, "Data di partenza"));
		content.add(setup_fields_panel());
		content.add(setup_butt_panel());
		set_editable(false);
		initWidget(dock);
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
	
	public Price get_price (int index) {
		return hm_table.get(index);
	}
	
	public void add_entry (Price p) {
		ArrayList<String> p_tab = new ArrayList<String>();
		p_tab.add(p.getStart_d().toString());
		p_tab.add(p.getStart_d().toString());
		p_tab.add(Double.toString(p.getFb()));
		p_tab.add(Double.toString(p.getHb()));
		p_tab.add(Double.toString(p.getBb()));
		int index = table.add_row(p_tab);
		hm_table.put(new Integer(index), p);
	}
	
	private VerticalPanel setup_dp_panel (DatePicker dp, String title) {
		VerticalPanel res = new VerticalPanel();
		Label l_tit = new Label(title);
		dp = new DatePicker();
		dp.setValue(new Date());
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
				p_cont.dele_price(new Price());
			}
		});
		res.add(new_b);
		res.add(save_b);
		res.add(dele_b);
		res.add(canc_b);
		return res;
	}
}
