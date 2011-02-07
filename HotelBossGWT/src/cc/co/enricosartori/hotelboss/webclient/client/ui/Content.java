package cc.co.enricosartori.hotelboss.webclient.client.ui;

import cc.co.enricosartori.hotelboss.webclient.client.ui.mainwidget.DashBoard;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DeckPanel;
import com.google.gwt.user.client.ui.Widget;

public class Content extends Composite {
	private Widget current;
	private DeckPanel main;
	
	public Content () {
		current = DashBoard.get_instance();
		main = new DeckPanel();
		main.add(current);
		int index = main.getWidgetIndex(current);
		main.showWidget(index);
		this.initWidget(main);
	}
	
	public void change_wid (Widget w) {
			main.add(w);
			int new_i = main.getWidgetIndex(w);
			int old_i = main.getVisibleWidget();
			main.showWidget(new_i);
			main.remove(old_i);
			current = w;
	}
	
	public boolean is_loaded (Widget w) {
		return current == w;
	}
}

