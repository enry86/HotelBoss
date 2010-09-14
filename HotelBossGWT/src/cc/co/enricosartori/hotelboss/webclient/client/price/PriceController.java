package cc.co.enricosartori.hotelboss.webclient.client.price;

import cc.co.enricosartori.hotelboss.dto.Price;
import cc.co.enricosartori.hotelboss.webclient.client.ui.mainwidget.ConfPrices;

public class PriceController {
	private PriceModel model;
	private ConfPrices view;
	
	public PriceController (PriceModel model, ConfPrices view) {
		this.model = model;
		this.view = view;
	}
	
	
	public void new_price () {
		model.new_price();
		view.set_editable(true);
	}
	
	public void cancel () {
		model.cancel();
		view.set_editable(false);
	}
	
	public void edit_price (Price p) {
		model.edit_price(p);
		view.set_editable(true);
	}
	
	public void save_price () {
		model.save_price();
		view.set_editable(false);
	}
	
	public void dele_price (Price p) {
		model.dele_price(p);
		view.set_editable(false);
	}
	
}

