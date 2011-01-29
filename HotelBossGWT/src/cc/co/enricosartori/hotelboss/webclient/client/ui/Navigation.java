package cc.co.enricosartori.hotelboss.webclient.client.ui;

import cc.co.enricosartori.hotelboss.webclient.client.ui.mainwidget.ConfExtra;
import cc.co.enricosartori.hotelboss.webclient.client.ui.mainwidget.ConfPrices;
import cc.co.enricosartori.hotelboss.webclient.client.ui.mainwidget.ConfRed;
import cc.co.enricosartori.hotelboss.webclient.client.ui.nav.Menu;
import cc.co.enricosartori.hotelboss.webclient.client.ui.nav.Menu.Entry;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.ResizeComposite;
import com.google.gwt.user.client.ui.StackLayoutPanel;

public class Navigation extends ResizeComposite {
	interface Binder extends UiBinder<StackLayoutPanel, Navigation> { }
	public static final Binder binder = GWT.create(Binder.class);
	
	@UiField(provided=true) 
	final Menu recep;
	@UiField(provided=true) 
	final Menu extra;
	@UiField(provided=true) 
	final Menu pren;
	@UiField(provided=true)
	final Menu bianc;
	@UiField(provided=true) 
	final Menu conf;
	
	
	public Navigation(Content cont) {
		Entry[] r = {
				new Entry("Arrivi", null),
				new Entry("Partenze", null),
				new Entry("ISTAT", null)	
		};
		
		Entry[] b = {
				new Entry("Cambi del giorno", null)		
		};
		
		Entry[] p = {
				new Entry("Nuova Prenotazione", null),
				new Entry("Modifica Prenotazioni", null),
				new Entry("Planner", null)
		};
		
		Entry[] c = {
				new Entry("Prezzi", new ConfPrices()),
				new Entry("Sconti", new ConfRed()),
				//new Entry("Camere", null),
				new Entry("Extra", new ConfExtra()),
				//new Entry("Cambi biancheria", null)
		};
		
		Entry[] e = {
				new Entry("Modifica extra", null)
		};
		
		bianc = new Menu(b, cont);
		recep = new Menu(r, cont);
		pren = new Menu(p, cont);
		conf = new Menu(c, cont);
		extra = new Menu(e, cont);
		initWidget(binder.createAndBindUi(this));
	}

}
