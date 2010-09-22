package cc.co.enricosartori.hotelboss.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import cc.co.enricosartori.hotelboss.dto.Price;
import cc.co.enricosartori.hotelboss.entities.PriceEB;



@Stateless
public class PriceDAO implements PriceDAOLocal {
	@PersistenceContext
	private EntityManager ent_man;
	
	public PriceDAO() {
	}
	
	
	public List<Price> get_pricelist() {
		Query q = ent_man.createNamedQuery(PriceEB.NQ_PRICELIST_ASC);
		List<PriceEB> l = q.getResultList();
		return convert_list(l);
	}
	
	
	private List<Price> convert_list(List<PriceEB> list) {
		Iterator<PriceEB> i = list.iterator();
		ArrayList<Price> res = new ArrayList<Price>(); 
		while (i.hasNext()) {
			res.add(get_DTO(i.next()));
		}
		return res;
	}
	
	private Price get_DTO(PriceEB ent) {
		Price p = new Price();
		p.setPrice_id(ent.getPrice_id());
		p.setStart_d(ent.getStart_d());
		p.setEnd_d(ent.getEnd_d());
		p.setFb(ent.getFb());
		p.setHb(ent.getHb());
		p.setBb(ent.getBb());
		p.setState("STORED");
		return p;
	}
}
