package cc.co.enricosartori.hotelboss.webclient.client.services;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import cc.co.enricosartori.hotelboss.dto.Purchase;

@RemoteServiceRelativePath("hbCheckout")
public interface HBCheckout extends RemoteService {
	public void add_purchase (List<Purchase> p);
	public void remove_purchase (Purchase p);
	public List<Purchase> get_purchase ();
	public float get_total_pur ();
	public void cancel ();
}
