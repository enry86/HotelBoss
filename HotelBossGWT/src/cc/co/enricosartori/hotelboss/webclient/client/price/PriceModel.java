package cc.co.enricosartori.hotelboss.webclient.client.price;

import java.util.ArrayList;
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
	
	public void edit_price (Price p, AsyncCallback<Void> call) {
		update_curr(p);
		if (curr_state != PriceState.NEW) {
			curr_state = PriceState.UPDATED;
		}
		save_price(call);
	}
	
	public void dele_price (AsyncCallback<Void> call) {
		pricelist.put(current, PriceState.DELETED);
		store_pricelist(call);
	}
	
	public void select_price (Price p) {
		current = p;
		curr_state = pricelist.get(p);
	}
	
	private void save_price (AsyncCallback<Void> call) {
		if (current != null) {
			pricelist.put(current, curr_state);
			store_pricelist(call);
		}
	}
	
	public void cancel () {
		this.current = null;
		this.curr_state = null;
	}
	
	private void store_pricelist(AsyncCallback<Void> call) {
		Iterator<Price> i = get_iterator();
		ArrayList<Price> l = new ArrayList<Price>();
		while (i.hasNext()) {
			Price p = i.next();
			p.setState(pricelist.get(p).toString());
			l.add(p);
		}
		send_pricelist(l, call);		
	}

	private void update_curr (Price p) {
		current.setStart_d(p.getStart_d());
		current.setEnd_d(p.getEnd_d());
		current.setFb(p.getFb());
		current.setHb(p.getHb());
		current.setBb(p.getBb());
	}
	
	public Iterator<Price> get_iterator () {
		Set<Price> s = pricelist.keySet();
		Iterator<Price> i = s.iterator();
		return i;
	}
	
	private void read_pricelist (List<Price> list) {
		if (list != null) {
			Iterator<Price> i = list.iterator();
			while (i.hasNext()) {
				Price tmp = (Price) i.next();
				pricelist.put(tmp, PriceState.STORED);
			}
		}
	}
	
	private void send_pricelist (List<Price> list, final AsyncCallback<Void> call) {
		hbconf.store_pricelist(list, new AsyncCallback<Void>() {

			@Override
			public void onFailure(Throwable caught) {
				GWT.log("SERVER ERROR");
				call.onFailure(caught);
			}

			@Override
			public void onSuccess(Void result) {
				call.onSuccess(result);
			}
			
		});
	}
	
	
	public void fetch_pricelist (final AsyncCallback<Void> callback) {
		hbconf.get_pricelist( 
				new AsyncCallback<List<Price>>() {
					public void onFailure(Throwable caught) {
						callback.onFailure(caught);
					}

					public void onSuccess(List<Price> result) {
						pricelist.clear();
						read_pricelist(result);
						callback.onSuccess(null);
					}
				});
	}
	
	
	
}
