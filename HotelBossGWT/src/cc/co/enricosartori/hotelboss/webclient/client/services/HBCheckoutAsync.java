package cc.co.enricosartori.hotelboss.webclient.client.services;


import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

import cc.co.enricosartori.hotelboss.dto.Purchase;

public interface HBCheckoutAsync {
	public void add_purchase (List<Purchase> p, AsyncCallback<Void> cb);
	public void remove_purchase (Purchase p, AsyncCallback<Void> cb);
	public void get_purchase (AsyncCallback<List<Purchase>> cb);
	public void get_total_pur (AsyncCallback<Float> cb);
	public void cancel (AsyncCallback<Void> cb);
}
