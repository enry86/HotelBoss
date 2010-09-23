package cc.co.enricosartori.hotelboss.core;

import java.util.Iterator;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import cc.co.enricosartori.hotelboss.dao.PriceDAOLocal;
import cc.co.enricosartori.hotelboss.dto.Price;

/**
 * Session Bean implementation class Configuration
 */
@Stateless
public class Configuration implements ConfigurationRemote {
	@EJB
	PriceDAOLocal price_dao;
    /**
     * Default constructor. 
     */
    public Configuration() {
        // TODO Auto-generated constructor stub
    }

	@Override
	public List<Price> get_pricelist() {
		System.out.println("Retrieving pricelist");
		return price_dao.get_pricelist();
	}
	
	public void store_pricelist(List<Price> pl) {
		System.out.println("Storing pricelist");
		Iterator<Price> i = pl.iterator();
		while (i.hasNext()){
			Price tmp = i.next();
			if (tmp.getState().compareTo("NEW") == 0) price_dao.insert_price(tmp);
			else if (tmp.getState().compareTo("UPDATED") == 0) price_dao.update_price(tmp);
			else if (tmp.getState().compareTo("DELETED") == 0) price_dao.delete_price(tmp);
		}
	}
}
