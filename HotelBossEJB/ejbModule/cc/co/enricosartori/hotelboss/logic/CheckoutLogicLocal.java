package cc.co.enricosartori.hotelboss.logic;

import java.util.Collection;

import javax.ejb.Local;

import cc.co.enricosartori.hotelboss.dto.Purchase;

@Local
public interface CheckoutLogicLocal {
	public float get_total_pur (Collection<Purchase> c);
}
