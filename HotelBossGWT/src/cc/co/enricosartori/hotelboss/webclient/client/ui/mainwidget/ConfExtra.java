package cc.co.enricosartori.hotelboss.webclient.client.ui.mainwidget;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import cc.co.enricosartori.hotelboss.webclient.client.extra.ExtraController;
import cc.co.enricosartori.hotelboss.webclient.client.extra.ExtraModel;
import cc.co.enricosartori.hotelboss.webclient.client.ui.widgets.GenericTable;

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
	
	private ExtraModel e_mod;
	private ExtraController e_con;
	
	private Button new_butt, save_butt, dele_butt, canc_butt;
	private TextBox name_tf, price_tf;
	
	public ConfExtra () {}
	
	public void init() {
		dock = binder.createAndBindUi(this);
		e_mod = new ExtraModel ();
		e_con = new ExtraController (this, e_mod);
		content.add(setup_fields_pan ());
		content.add(setup_butt_pan ());
		initWidget(dock);
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
				
			}
		});
		save_butt.addClickHandler(new ClickHandler () {
			public void onClick(ClickEvent event) {
				
			}
		});
		dele_butt.addClickHandler(new ClickHandler () {
			public void onClick(ClickEvent event) {
				
			}
		});
		canc_butt.addClickHandler(new ClickHandler () {
			public void onClick(ClickEvent event) {
				
			}
		});
		return res;
	}
	
	
	
	
	
	
	
}
