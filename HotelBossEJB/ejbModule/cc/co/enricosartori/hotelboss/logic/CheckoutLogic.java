package cc.co.enricosartori.hotelboss.logic;

import java.util.Collection;
import java.util.Iterator;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import cc.co.enricosartori.hotelboss.dao.ExtraDAOLocal;
import cc.co.enricosartori.hotelboss.dto.Purchase;

@Stateless
public class CheckoutLogic implements CheckoutLogicLocal {
	@EJB
	private ExtraDAOLocal e_dao;
	
	@Override
	public float get_total_pur(Collection<Purchase> c) {
		float tot = 0.0f;
		Iterator<Purchase> i = c.iterator();
		while (i.hasNext()) {
			Purchase tmp = i.next();
			tot += (e_dao.get_price (tmp.getExtra_id()) * tmp.getQty());
		}
		return tot;
	}
	
}
