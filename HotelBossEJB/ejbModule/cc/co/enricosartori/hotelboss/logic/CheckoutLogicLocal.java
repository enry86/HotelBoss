package cc.co.enricosartori.hotelboss.logic;

import java.util.Collection;
import java.util.List;

import javax.ejb.Local;

import cc.co.enricosartori.hotelboss.dto.Period;
import cc.co.enricosartori.hotelboss.dto.Purchase;

@Local
public interface CheckoutLogicLocal {
	public float get_total_pur (Collection<Purchase> c);
	public List<Period> get_periods (int room);
}
