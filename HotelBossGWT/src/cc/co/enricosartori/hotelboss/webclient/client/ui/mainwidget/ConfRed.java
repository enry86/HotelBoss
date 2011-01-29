package cc.co.enricosartori.hotelboss.webclient.client.ui.mainwidget;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import cc.co.enricosartori.hotelboss.dto.Reduction;
import cc.co.enricosartori.hotelboss.webclient.client.reduction.RedController;
import cc.co.enricosartori.hotelboss.webclient.client.reduction.RedModel;
import cc.co.enricosartori.hotelboss.webclient.client.ui.widgets.GenericTable;
import cc.co.enricosartori.hotelboss.webclient.client.ui.widgets.GenericTable.Listener;

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
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ConfRed extends Composite implements MainWidget{
	
	interface Binder extends UiBinder<DockLayoutPanel,ConfRed> { }
	private static final Binder binder = GWT.create(Binder.class);
	DockLayoutPanel dock;
	
	@UiField HorizontalPanel content;
	@UiField(provided=true)
	final GenericTable table = new GenericTable(new String[]
			{"Descrizione", 
			 "Valore",
			 "Tipo",
			 "Percentuale"}
			);
	
	private final String[] RED_TYPES = new String[] {"Sconto", "Supplemento", "Tariffa"};
	private final String[] RED_PERC = new String[] {"Percentuale", "Valore ass."};
	private boolean running = false;
	
	
	private AsyncCallback<Void> callback = new AsyncCallback<Void>() {
		public void onFailure (Throwable caught) {
			//price_l.setText("Server error...");
		}
		public void onSuccess (Void v) {
			Iterator<Reduction> i = r_mod.get_iterator();
			while (i.hasNext()) {
				Reduction r = i.next();
				add_entry(r);
			}
		}
	};
	
	private HashMap<Integer, Reduction> red_table;
	private RedModel r_mod;
	private RedController r_cont;
	
	private TextBox des_tf, val_tf;
	private RadioButton ty_red_rb, ty_sup_rb, ty_tar_rb, per_t_rb, per_f_rb;
	private Button new_butt, save_butt, dele_butt, canc_butt;
	private final int RED_VAL = 0, SUP_VAL = 1, TAR_VAL = 2;
	
	
	public ConfRed () {}
	
		
	@Override
	public void init() {
		dock = binder.createAndBindUi(this);
		red_table = new HashMap<Integer, Reduction> ();
		r_mod = new RedModel();
		r_cont = new RedController (this, r_mod);
		update_table ();
		content.add (setup_fields_panel ());
		content.add (setup_butt_panel ());
		set_editable (false);
		running = true;
		initWidget(dock);
	}
	
	public boolean is_running() {
		return running;
	}
	
	private VerticalPanel setup_fields_panel () {
		VerticalPanel res = new VerticalPanel ();
		des_tf = new TextBox ();
		val_tf = new TextBox ();
		ty_red_rb = new RadioButton ("type", "Sconto");
		ty_sup_rb = new RadioButton ("type", "Supplemento");
		ty_tar_rb = new RadioButton ("type", "Tariffa");
		per_t_rb = new RadioButton ("perc", "Percentuale");
		per_f_rb = new RadioButton ("perc", "Valore ass.");
		
		res.add (new Label ("Descrizione:"));
		res.add (des_tf);
		res.add (new Label ("Valore:"));
		res.add (val_tf);
		res.add (new Label ("Tipologia:"));
		res.add (ty_red_rb);
		res.add (ty_sup_rb);
		res.add (ty_tar_rb);
		res.add (new Label ("Tipo Modifica:"));
		res.add (per_t_rb);
		res.add (per_f_rb);
		return res;
	}
	
	
	private VerticalPanel setup_butt_panel () {
		VerticalPanel res = new VerticalPanel ();
		new_butt = new Button ("Nuovo");
		save_butt = new Button ("Salva");
		dele_butt = new Button ("Elimina");
		canc_butt = new Button ("Annulla");
		
		new_butt.addClickHandler(new ClickHandler () {
			public void onClick(ClickEvent event) {
				r_cont.new_red();
			}
		});
		save_butt.addClickHandler(new ClickHandler () {
			public void onClick(ClickEvent event) {
				r_cont.save_red();
			}
		});
		dele_butt.addClickHandler(new ClickHandler () {
			public void onClick(ClickEvent event) {
				r_cont.dele_red();
			}
		});
		canc_butt.addClickHandler(new ClickHandler () {
			public void onClick(ClickEvent event) {
				r_cont.cancel();
			}
		});
		
		res.add(new_butt);
		res.add(save_butt);
		res.add(dele_butt);
		res.add(canc_butt);
		return res;
	}
	
	
	public Reduction show_red (int index) {
		Reduction r = red_table.get(index);
		update_fields (r);
		set_editable (true);
		return r;
	}
	
	
	private void update_fields (Reduction r) {
		int type = r.getRed_type ();
		boolean perc = r.isPerc();
		des_tf.setText(r.getDescr ());
		val_tf.setText(Float.toString(r.getVal()));
		
		if (type == RED_VAL) {
			ty_red_rb.setValue(true);
		}
		else if (type == SUP_VAL) {
			ty_sup_rb.setValue(true);
		}
		else if (type == TAR_VAL) {
			ty_tar_rb.setValue(true);
		}
		
		if (perc) {
			per_t_rb.setValue(true);
		}
		else {
			per_f_rb.setValue(true);
		}
		
	}
	
	
	public void reset_fields () {
		des_tf.setValue("");
		val_tf.setValue("");
		ty_red_rb.setValue(false);
		ty_sup_rb.setValue(false);
		ty_tar_rb.setValue(false);
		per_t_rb.setValue(false);
		per_f_rb.setValue(false);
	}
	
	
	public void set_editable (boolean val) {
		des_tf.setEnabled(val);
		val_tf.setEnabled(val);
		ty_red_rb.setEnabled(val);
		ty_sup_rb.setEnabled(val);
		ty_tar_rb.setEnabled(val);
		per_t_rb.setEnabled(val);
		per_f_rb.setEnabled(val);
		
		new_butt.setEnabled(!val);
		save_butt.setEnabled(val);
		dele_butt.setEnabled(val);
		canc_butt.setEnabled(val);
	}
	
	
	public void update_table () {
		reset_table ();
		r_mod.fetch_reductions (callback);
	}
	
	
	private void reset_table () {
		red_table.clear();
		table.reset();
	}
	
	
	public void set_table_callback (Listener l) {
		table.set_listener(l);
	}
	
	
	public void add_entry (Reduction r) {
		String type_s, perc_s;
		ArrayList<String> r_tab = new ArrayList<String> ();
		NumberFormat nf = NumberFormat.getFormat("########.00");
		type_s = RED_TYPES [r.getRed_type ()];
		if (r.isPerc()) perc_s = RED_PERC [0];
		else perc_s = RED_PERC[1];
		
		r_tab.add(r.getDescr());
		r_tab.add(nf.format(r.getVal()));
		r_tab.add(type_s);
		r_tab.add(perc_s);
		int index = table.add_row(r_tab);
		red_table.put (index, r);
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
	
	
	public Reduction get_reduction () {
		Reduction res = new Reduction ();
		int type = 0;		
		try {
			res.setDescr(des_tf.getValue());
			res.setVal(new Float(val_tf.getValue()));
			if (ty_red_rb.getValue()) type = RED_VAL;
			else if (ty_sup_rb.getValue()) type = SUP_VAL;
			else if (ty_tar_rb.getValue()) type = TAR_VAL;
			res.setRed_type(type);
			res.setPerc(per_t_rb.getValue());
		}
		catch (Exception e) {
			show_error ("Errore nei dati inseriti");
			return null;
		}
		return res;
	}
}
