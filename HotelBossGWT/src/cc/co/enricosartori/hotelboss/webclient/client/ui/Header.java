package cc.co.enricosartori.hotelboss.webclient.client.ui;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;


public class Header extends Composite{
	private HorizontalPanel hp;
	private Label lab;
	
	public Header() {
		lab = new Label("HotelBoss");
		hp = new HorizontalPanel();
		hp.add(lab);
		initWidget(hp);
	}
}
