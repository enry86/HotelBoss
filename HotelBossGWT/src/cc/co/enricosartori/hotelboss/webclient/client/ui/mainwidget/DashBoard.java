package cc.co.enricosartori.hotelboss.webclient.client.ui.mainwidget;

import java.sql.Date;
import java.util.Iterator;
import java.util.List;

import cc.co.enricosartori.hotelboss.dto.Reservation;
import cc.co.enricosartori.hotelboss.webclient.client.dashboard.DashController;
import cc.co.enricosartori.hotelboss.webclient.client.dashboard.DashModel;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.datepicker.client.DatePicker;

public class DashBoard extends Composite implements MainWidget {
	interface Binder extends UiBinder<DockLayoutPanel, DashBoard> { }
	private final static Binder binder = GWT.create(Binder.class);
	private DatePicker date;	
	private boolean running;
	private static  DashBoard instance;
	
	@UiField HorizontalPanel hp;
	@UiField DisclosurePanel dis_left;
	@UiField DisclosurePanel dis_right;
	
	private DashController cont;
	private DashModel model;
	
	public static DashBoard get_instance () {
		if (instance == null) return new DashBoard();
		else return instance;
	}
	
	private DashBoard () {
		initWidget(binder.createAndBindUi(this));
		model = new DashModel ();
		cont = new DashController (this, model);
		setup_gui ();
		running = true;
		instance = this;
		cont.update_lists();
	}
	

	@Override
	public void init() {}


	@Override
	public boolean is_running() {
		return running;
	}
	
	private void setup_gui () {
		date = new DatePicker();
		date.setValue(new java.util.Date());
		date.addValueChangeHandler(new ValueChangeHandler<java.util.Date> () {
			public void onValueChange(ValueChangeEvent<java.util.Date> event) {
				cont.update_lists ();
			}
		});
		dis_left.setAnimationEnabled(true);
		dis_right.setAnimationEnabled(true);
		dis_left.setOpen(true);
		dis_right.setOpen(true);
		hp.insert(date, 0);
	}
	
	public Date get_date () {
		java.util.Date d = date.getValue();
		return new Date(d.getTime());
	}
	
	public void show_arr (List<Reservation> l) {
		fill_list (l, dis_left, "Nessun Arrivo");
	}
	
	public void show_dep (List<Reservation> l) {
		fill_list (l, dis_right, "Nessuna Partenza");
	}
	
	public void reset_arr () {
		reset_panel (dis_left);
	}
	
	public void reset_dep () {
		reset_panel (dis_right);
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
	
	private void fill_list (List<Reservation> l, DisclosurePanel p, String void_mex) {
		Iterator<Reservation> i = l.iterator();
		VerticalPanel vp = new VerticalPanel ();
		while (i.hasNext()) {
			Reservation tmp = i.next();
			Label lab = new Label(tmp.getRoom() + " - " + tmp.getCustomer());
			vp.add(lab);
		}
		if (l.size() == 0) {
			Label lab = new Label(void_mex);
			vp.add(lab);
		}
		p.add(vp);
	}
	
	private void reset_panel (DisclosurePanel p) {
		p.clear();
	}
}
