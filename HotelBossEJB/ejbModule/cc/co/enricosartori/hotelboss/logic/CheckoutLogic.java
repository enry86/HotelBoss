package cc.co.enricosartori.hotelboss.logic;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import cc.co.enricosartori.hotelboss.dao.CustomerDAOLocal;
import cc.co.enricosartori.hotelboss.dao.ExtraDAOLocal;
import cc.co.enricosartori.hotelboss.dao.PriceDAOLocal;
import cc.co.enricosartori.hotelboss.dao.ReductionDAOLocal;
import cc.co.enricosartori.hotelboss.dto.Customer;
import cc.co.enricosartori.hotelboss.dto.Period;
import cc.co.enricosartori.hotelboss.dto.Price;
import cc.co.enricosartori.hotelboss.dto.Purchase;
import cc.co.enricosartori.hotelboss.dto.Reduction;

@Stateless
public class CheckoutLogic implements CheckoutLogicLocal {
	@EJB
	private ExtraDAOLocal e_dao;
	@EJB
	private CustomerDAOLocal c_dao;
	@EJB
	private ReductionDAOLocal r_dao;
	@EJB
	private PriceDAOLocal p_dao;
	
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

	@Override
	public List<Period> get_periods (int room) {
		ArrayList<Price> prices = new ArrayList<Price> ();
		ArrayList<Period> res = null;
		Date d = new Date (new java.util.Date().getTime());
		Customer c = c_dao.get_customer(room);
		Reduction r = r_dao.get_reduction(c.getDiscount());
		Price compl_pr = p_dao.get_compl_price (c.getDate_arr(), d);
		if (compl_pr != null) {
			prices.add(compl_pr);
		}
		else {
			Price first = p_dao.get_chunk_price(c.getDate_arr());
			Price last = p_dao.get_chunk_price(d);
			List<Price> list = p_dao.get_inter_price(c.getDate_arr(), d);
			prices.add(first);
			Iterator<Price> i = list.iterator();
			while (i.hasNext()) {
				prices.add(i.next());
			}
			prices.add(last);
		}
		res = compute_periods (prices, d, c, r);
		return res;
	}
	
	
	private ArrayList<Period> compute_periods (ArrayList<Price> l, Date dep, Customer cus, Reduction red) {
		ArrayList<Period> res = new ArrayList<Period> (l.size());
		for (int i = 0; i < l.size(); i++) {
			Price curr = l.get(i);
			Period tmp = new Period ();
			Date start = curr.getStart_d(); 
			Date end = curr.getEnd_d();
			if (i == 0) {
				start = cus.getDate_arr();
			}
			if (i == l.size() - 1) {
				end = dep;
			}
			float price = get_price (curr, cus.getTreatment(), red);
			int days = get_days (start, end);
			tmp.setD_start(start);
			tmp.setD_end(end);
			tmp.setPrice(price);
			tmp.setDays(days);
			res.add(tmp);
		}
		return res;
	}
	
	
	private float get_price (Price p, int treat, Reduction r) {
		float base = 0.0f;
		float var = 0.0f;
		float res = 0.0f;
		if (treat == 0) base = p.getFb();
		else if (treat == 1) base = p.getHb();
		else if (treat == 2) base = p.getBb();
		if (r.isPerc()) {
			var = base * (r.getVal() / 100.0f);
		}
		else {
			var = r.getVal();
		}
		
		if (r.getRed_type() == 0) res = base - var;
		else if (r.getRed_type() == 1) res = base + var;
		else if (r.getRed_type() == 2) res = var;
		
		return res;
	}
	
	private int get_days (Date s, Date e) {
		int day = 24 * 60 * 60 * 1000;
		long sl = s.getTime();
		long el = e.getTime();
		int diff = (int) (el - sl);
		return diff / day;		
	}
}
