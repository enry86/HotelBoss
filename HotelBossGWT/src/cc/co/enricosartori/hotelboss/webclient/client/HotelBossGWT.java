package cc.co.enricosartori.hotelboss.webclient.client;


import cc.co.enricosartori.hotelboss.webclient.client.ui.Content;
import cc.co.enricosartori.hotelboss.webclient.client.ui.Header;
import cc.co.enricosartori.hotelboss.webclient.client.ui.Navigation;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.RootLayoutPanel;



/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class HotelBossGWT implements EntryPoint {
	
	interface Binder extends UiBinder<DockLayoutPanel, HotelBossGWT> { }
	public static final Binder binder = GWT.create(Binder.class);
	public DockLayoutPanel dock;

	@UiField Header header;
	@UiField(provided=true) 
	final Content content = new Content();
	@UiField(provided=true)
	final Navigation navigation = new Navigation(content);
	
	public void onModuleLoad () {
		dock = binder.createAndBindUi(this);
		Window.enableScrolling(false);
		Window.setMargin("0px");
		RootLayoutPanel root = RootLayoutPanel.get();
		root.add(dock);
	}
}
