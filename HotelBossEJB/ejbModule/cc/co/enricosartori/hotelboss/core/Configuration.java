package cc.co.enricosartori.hotelboss.core;

import java.util.Iterator;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import cc.co.enricosartori.hotelboss.dao.PriceDAOLocal;
import cc.co.enricosartori.hotelboss.dao.ReductionDAOLocal;
import cc.co.enricosartori.hotelboss.dto.Price;
import cc.co.enricosartori.hotelboss.dto.Reduction;

/**
 * Session Bean implementation class Configuration
 */
@Stateless
public class Configuration implements ConfigurationRemote {
	@EJB
	PriceDAOLocal price_dao;
	@EJB
	ReductionDAOLocal red_dao;
    /**
     * Default constructor. 
     */
    public Configuration() {}

	@Override
	public List<Price> get_pricelist() {
		System.out.println("Retrieving pricelist");
		return price_dao.get_pricelist();
	}
	
	public void store_pricelist(List<Price> pl) {
		Iterator<Price> i = pl.iterator();
		while (i.hasNext()){
			Price tmp = i.next();
			if (tmp.getState().compareTo("NEW") == 0) {
				price_dao.insert_price(tmp);
			}
			else if (tmp.getState().compareTo("UPDATED") == 0) {
				price_dao.update_price(tmp);
			}
			else if (tmp.getState().compareTo("DELETED") == 0) {
				price_dao.delete_price(tmp);
			}
		}
	}
	
	
	public List<Reduction>  get_reductions () {
		return red_dao.get_reductions();
	}
	
	
	public void store_reductions (List<Reduction> rl) {
		Iterator<Reduction> i = rl.iterator();
		while (i.hasNext()) {
			Reduction tmp = i.next();
			if (tmp.getState().equals("NEW")) {
				red_dao.insert_red (tmp);
			}
			else if (tmp.getState().equals("UPDATED")) {
				red_dao.update_red (tmp);
			}
			else if (tmp.getState().equals("DELETED")) {
				red_dao.delete_red (tmp);
			}
		}
	}
	
	
	
	
}
