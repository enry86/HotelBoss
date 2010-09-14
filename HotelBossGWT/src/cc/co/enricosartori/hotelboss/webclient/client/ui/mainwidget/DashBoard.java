package cc.co.enricosartori.hotelboss.webclient.client.ui.mainwidget;

import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.datepicker.client.DatePicker;

public class DashBoard extends Composite{
	interface Binder extends UiBinder<DockLayoutPanel, DashBoard> { }
	private final static Binder binder = GWT.create(Binder.class);
	private DatePicker date;	
	
	
	@UiField HorizontalPanel hp;
	@UiField DisclosurePanel dis_left;
	@UiField DisclosurePanel dis_right;
	
	
	public DashBoard () {
		initWidget(binder.createAndBindUi(this));
		date = new DatePicker();
		date.setValue(new Date());
		dis_left.setAnimationEnabled(true);
		dis_right.setAnimationEnabled(true);
		dis_left.setOpen(true);
		dis_right.setOpen(true);
		hp.insert(date, 0);
	}
}
