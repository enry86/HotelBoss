package cc.co.enricosartori.hotelboss.webclient.client.ui.nav;

import cc.co.enricosartori.hotelboss.webclient.client.ui.Content;
import cc.co.enricosartori.hotelboss.webclient.client.ui.mainwidget.MainWidget;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class Menu extends Composite {
	
	public static class Entry {
		String name;
		MainWidget widget;
		
		public Entry (String name, MainWidget widget) {
			this.name = name;
			this.widget = widget;
		}
	}
	
	interface Binder extends UiBinder<Widget, Menu> { }
	interface Style extends CssResource {
		String item();
	}

	private static final Binder binder = GWT.create(Binder.class);
	private Content cont;
	public Entry[] entries = null;
	@UiField ComplexPanel fp;
	@UiField Style style;
	
	
	public Menu(Entry[] entries, Content cont) {
		initWidget(binder.createAndBindUi(this));
		this.cont = cont;
		this.entries = entries;
		for (int i = 0; i < entries.length; i++) {
			addEntry(entries[i]);
		}
	}
	
	private void addEntry (final Entry e) {
		final Anchor link = new Anchor(e.name);
		fp.add(link);
		link.setStyleName(style.item());

		link.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent ev) {
				if (e.widget != null) {
					e.widget.init();
					cont.change_wid((Widget) e.widget);
				}
			}
		});
	}
	
	
	
	

}
