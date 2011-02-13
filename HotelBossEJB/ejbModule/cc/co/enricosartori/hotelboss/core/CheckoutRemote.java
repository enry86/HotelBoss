package cc.co.enricosartori.hotelboss.core;

import java.util.List;

import javax.ejb.Remote;

import cc.co.enricosartori.hotelboss.dto.Purchase;

@Remote
public interface CheckoutRemote {
	public void add_purchase (List<Purchase> p);
	public void remove_purchase (Purchase p);
	public List<Purchase> get_purchase ();
	public float get_total_pur ();
	public void cancel ();
}
