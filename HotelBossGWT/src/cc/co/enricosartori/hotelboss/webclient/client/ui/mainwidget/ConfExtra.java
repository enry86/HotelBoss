package cc.co.enricosartori.hotelboss.webclient.client.ui.mainwidget;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
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

import cc.co.enricosartori.hotelboss.dto.Extra;
import cc.co.enricosartori.hotelboss.webclient.client.extra.ExtraController;
import cc.co.enricosartori.hotelboss.webclient.client.extra.ExtraModel;
import cc.co.enricosartori.hotelboss.webclient.client.ui.widgets.GenericTable;
import cc.co.enricosartori.hotelboss.webclient.client.ui.widgets.GenericTable.Listener;

public class ConfExtra extends Composite implements MainWidget {
	interface Binder extends UiBinder<DockLayoutPanel,ConfExtra> { }
	private static final Binder binder = GWT.create(Binder.class);
	DockLayoutPanel dock;
	
	@UiField HorizontalPanel content;
	@UiField(provided=true)
	final GenericTable table = new GenericTable(new String[]
			{"Nome",
			"Prezzo"}
			);
	
	private boolean running = false;
	private ExtraModel e_mod;
	private ExtraController e_cont;
	
	private Button new_butt, save_butt, dele_butt, canc_butt;
	private TextBox name_tf, price_tf;
	
	private HashMap<Integer, Extra> hm_table;
	
	private AsyncCallback<Void> callback = new AsyncCallback<Void> () {
		public void onFailure (Throwable caught) {
			show_error("Errore di comunicazione con il server");
		}
		public void onSuccess (Void v) {
			Iterator<Extra> i = e_mod.get_iterator();
			while (i.hasNext()) {
				Extra e = i.next();
				add_entry(e);
			}
		}
	};
	
	
	public ConfExtra () {}
	
	public void init() {
		dock = binder.createAndBindUi(this);
		e_mod = new ExtraModel ();
		e_cont = new ExtraController (this, e_mod);
		hm_table = new HashMap<Integer, Extra> ();
		update_table ();
		content.add(setup_fields_pan ());
		content.add(setup_butt_pan ());
		set_editable(false);
		running = true;
		initWidget(dock);
	}
	
	public void update () {
		update_table();
	}
	
	public boolean is_running() {
		return running;
	}
	
	public void set_table_callback (Listener l) {
		table.set_listener(l);
	}
	
	public void update_table () {
		reset_table();
		e_mod.fetch_extras (callback);
	}
	
	public void reset_table () {
		hm_table.clear();
		table.reset();
	}
	
	public Extra get_extra () {
		Extra res = new Extra ();
		res.setName (name_tf.getText ());
		try {
			res.setPrice (get_float (price_tf.getText ()));
		}
		catch (Exception e) {
			show_error ("Errore nei dati inseriti");
			res = null;
		}
		return res;
	}
	
	
	public Extra show_extra (int index) {
		Extra sel = hm_table.get(index);
		update_fields (sel);
		set_editable (true);
		return sel;
	}
	
	
	public void add_entry (Extra e) {
		ArrayList<String> e_tab = new ArrayList<String> ();
		NumberFormat nf = NumberFormat.getFormat("########.00");
		e_tab.add(e.getName());
		e_tab.add(nf.format(e.getPrice()));
		int index = table.add_row(e_tab);
		hm_table.put(new Integer(index), e);
	}
	
	
	private void update_fields (Extra e) {
		NumberFormat nf = NumberFormat.getFormat("########.00");
		name_tf.setText(e.getName ());
		price_tf.setText (nf.format(e.getPrice ()));
	}
	
	
	public void reset_fields () {
		name_tf.setText("");
		price_tf.setText("");
	}
	
	
	public float get_float (String s) {
		s = s.replace(',', '.');
		float d = Float.parseFloat(s);
		return d;
	}
	
	
	public void set_editable (boolean enabled) {
		name_tf.setEnabled (enabled);
		price_tf.setEnabled (enabled);
		save_butt.setEnabled (enabled);
		new_butt.setEnabled (!enabled);
		dele_butt.setEnabled (enabled);
		canc_butt.setEnabled (enabled);
	}
	
	private VerticalPanel setup_fields_pan () {
		VerticalPanel res = new VerticalPanel ();
		name_tf = new TextBox ();
		price_tf = new TextBox ();
		res.add(new Label("Nome:"));
		res.add(name_tf);
		res.add(new Label("Prezzo:"));
		res.add(price_tf);
		return res;
	}
	
	
	private VerticalPanel setup_butt_pan () {
		VerticalPanel res =  new VerticalPanel ();
		new_butt = new Button ("Nuovo");
		save_butt = new Button ("Salva");
		dele_butt = new Button ("Elimina");
		canc_butt = new Button ("Annulla");
		res.add(new_butt);
		res.add(save_butt);
		res.add(dele_butt);
		res.add(canc_butt);
		new_butt.addClickHandler(new ClickHandler () {
			public void onClick(ClickEvent event) {
				e_cont.new_extra ();	
			}
		});
		save_butt.addClickHandler(new ClickHandler () {
			public void onClick(ClickEvent event) {
				e_cont.save_extra ();
			}
		});
		dele_butt.addClickHandler(new ClickHandler () {
			public void onClick(ClickEvent event) {
				e_cont.dele_extra ();
			}
		});
		canc_butt.addClickHandler(new ClickHandler () {
			public void onClick(ClickEvent event) {
				e_cont.cancel ();
			}
		});
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
