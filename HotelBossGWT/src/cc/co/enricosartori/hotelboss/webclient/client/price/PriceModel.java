package cc.co.enricosartori.hotelboss.webclient.client.price;

import java.util.Iterator;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;

import cc.co.enricosartori.hotelboss.dto.Price;
import cc.co.enricosartori.hotelboss.webclient.client.services.HBConfiguration;
import cc.co.enricosartori.hotelboss.webclient.client.services.HBConfigurationAsync;

public class PriceModel {
	private final HBConfigurationAsync hbconf = GWT.create(HBConfiguration.class);
	private enum PriceState {NEW, UPDATED, DELETED, STORED};
	private HashMap<Price, PriceState> pricelist = new HashMap<Price, PriceState>();
	private Price current;
	private PriceState curr_state;
	
	public PriceModel () {
		current = null;
		curr_state = null;
	}
	
	public void new_price () {
		Price p = new Price();
		current = p;
		curr_state = PriceState.NEW;
	}
	
	public void edit_price (Price p) {
		current = p;
		curr_state = PriceState.UPDATED;
	}
	
	public void dele_price (Price p) {
		if (pricelist.containsKey(p)) {
			pricelist.put(p, PriceState.DELETED);
			store_pricelist();
		}
	}
	
	public void save_price () {
		if (current != null) {
			pricelist.put(current, curr_state);
			store_pricelist();
		}
	}
	
	public void cancel () {
		this.current = null;
		this.curr_state = null;
	}
	
	private void store_pricelist() {
		//TODO
		GWT.log("Here i should put everithing on the server");
	}
	
	public Iterator<Price> get_iterator () {
		Set<Price> s = pricelist.keySet();
		Iterator<Price> i = s.iterator();
		return i;
	}
	
	private void read_pricelist (List<Price> list) {
		Iterator<Price> i = list.iterator();
		while (i.hasNext()) {
			Price tmp = (Price) i.next();
			pricelist.put(tmp, PriceState.STORED);
		}
	}
	
	public void fetch_pricelist (final AsyncCallback<Void> callback) {
		hbconf.get_pricelist( 
				new AsyncCallback<List<Price>>() {
					public void onFailure(Throwable caught) {
						callback.onFailure(caught);
						GWT.log("FAILURE!");
					}

					public void onSuccess(List<Price> result) {
						pricelist.clear();
						read_pricelist(result);
						GWT.log("SUCCESS!");
						callback.onSuccess(null);
					}
				});
	}
	
	
	
}
