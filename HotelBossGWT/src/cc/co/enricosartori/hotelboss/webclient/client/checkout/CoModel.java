package cc.co.enricosartori.hotelboss.webclient.client.checkout;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import cc.co.enricosartori.hotelboss.dto.Extra;
import cc.co.enricosartori.hotelboss.dto.Period;
import cc.co.enricosartori.hotelboss.dto.Purchase;
import cc.co.enricosartori.hotelboss.dto.Totals;
import cc.co.enricosartori.hotelboss.webclient.client.services.HBCheckout;
import cc.co.enricosartori.hotelboss.webclient.client.services.HBCheckoutAsync;
import cc.co.enricosartori.hotelboss.webclient.client.services.HBConfiguration;
import cc.co.enricosartori.hotelboss.webclient.client.services.HBConfigurationAsync;
import cc.co.enricosartori.hotelboss.webclient.client.services.HBReception;
import cc.co.enricosartori.hotelboss.webclient.client.services.HBReceptionAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class CoModel {
	private HBReceptionAsync hbr = GWT.create(HBReception.class);
	private HBConfigurationAsync hbc = GWT.create(HBConfiguration.class);
	private HBCheckoutAsync hbco = GWT.create(HBCheckout.class);
	
	
	public void fetch_extras (AsyncCallback <List<Extra>> cb) { 
		hbc.get_extras(cb);
	}
	
	public void search_room (int room, AsyncCallback<List<Purchase>> cb) {
		hbr.get_pur_room(room, cb);
	}
	
	public void save_purs (Collection<Purchase> c, AsyncCallback<Void> cb) {
		GWT.log(Integer.toString(c.size()) + " extra da salvare");
		ArrayList<Purchase> l = new ArrayList<Purchase> ();
		Iterator<Purchase> i = c.iterator();
		while (i.hasNext()) {
			l.add(i.next());
		}
		hbco.add_purchase(l, cb);
	}

	public void get_total_pur (AsyncCallback<Float> cb) {
		hbco.get_total_pur(cb);
	}
	
	public void fetch_periods (int room, AsyncCallback<List<Period>> cb) {
		hbco.get_periods(room, cb);
	}
	
	public void close (AsyncCallback<Void> cb) {
		hbco.cancel(cb);
	}
	
	public void fetch_totals (AsyncCallback<Totals> cb) {
		hbco.get_totals(cb);
	}
	
	public void checkout (int room, AsyncCallback<Void> cb) {
		hbco.checkout(room, cb);
	}
}
