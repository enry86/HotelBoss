package cc.co.enricosartori.hotelboss.webclient.client.services;


import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

import cc.co.enricosartori.hotelboss.dto.Period;
import cc.co.enricosartori.hotelboss.dto.Purchase;
import cc.co.enricosartori.hotelboss.dto.Totals;

public interface HBCheckoutAsync {
	public void add_purchase (List<Purchase> p, AsyncCallback<Void> cb);
	public void remove_purchase (Purchase p, AsyncCallback<Void> cb);
	public void get_purchase (AsyncCallback<List<Purchase>> cb);
	public void get_total_pur (AsyncCallback<Float> cb);
	public void get_totals (AsyncCallback<Totals> cb);
	public void checkout (int room, AsyncCallback<Void> cb);
	public void get_periods (int room, AsyncCallback<List<Period>> cb);
	public void cancel (AsyncCallback<Void> cb);
}
