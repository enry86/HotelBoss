package cc.co.enricosartori.hotelboss.core;

import java.util.List;

import javax.ejb.Remote;

import cc.co.enricosartori.hotelboss.dto.Period;
import cc.co.enricosartori.hotelboss.dto.Purchase;
import cc.co.enricosartori.hotelboss.dto.Totals;

@Remote
public interface CheckoutRemote {
	public void add_purchase (List<Purchase> p);
	public void remove_purchase (Purchase p);
	public List<Purchase> get_purchase ();
	public List<Period> get_periods (int room);
	public float get_total_pur ();
	public Totals get_totals ();
	public void checkout (int room);
	public void cancel ();
}
