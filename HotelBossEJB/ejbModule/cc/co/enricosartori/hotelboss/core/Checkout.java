package cc.co.enricosartori.hotelboss.core;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateful;

import cc.co.enricosartori.hotelboss.dto.Period;
import cc.co.enricosartori.hotelboss.dto.Purchase;
import cc.co.enricosartori.hotelboss.logic.CheckoutLogicLocal;

@Stateful
public class Checkout implements CheckoutRemote {
	@EJB
	private CheckoutLogicLocal colog;
	
	private HashMap<Integer,Purchase> purchases = new HashMap<Integer, Purchase> ();
	private ArrayList<Period> periods = new ArrayList<Period> ();
	
	public void add_purchase (List<Purchase> p) {
		System.out.println(purchases.size());
		purchases.clear();
		Iterator<Purchase> i = p.iterator();
		while (i.hasNext()) {
			Purchase tmp = i.next();
			purchases.put(tmp.getId(), tmp);
		}
	}
	
	public void remove_purchase (Purchase p) {
		purchases.remove(p.getId());
	}
	
	public List<Purchase> get_purchase () {
		ArrayList<Purchase> l = new ArrayList<Purchase>();
		Collection<Purchase> c = purchases.values();
		Iterator<Purchase> i = c.iterator();
		while (i.hasNext()) l.add(i.next());
		return l;
	}
	
	public float get_total_pur () {
		return colog.get_total_pur (purchases.values());
	}
	
	public void cancel () {
		purchases.clear();
		periods.clear();
	}

	@Override
	public List<Period> get_periods(int room) {
		List<Period> l = colog.get_periods(room);
		Iterator<Period> i = l.iterator();
		while (i.hasNext()) {
			periods.add(i.next());
		}
		return l;
	}
}
