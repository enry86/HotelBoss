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
	
	public void insert_price (Price p) {
		PriceEB ent = new PriceEB();
		ent.setPrice_id(get_priceid());
		update_entity(ent, p);
		ent_man.persist(ent);
	}
	
	public void update_price (Price p) {
		PriceEB e = ent_man.find(PriceEB.class, p.getPrice_id());
		update_entity(e, p);
		ent_man.merge(e);
	}
	
	public void delete_price (Price p) {
		PriceEB e = ent_man.find(PriceEB.class, p.getPrice_id());
		update_entity(e, p);
		ent_man.remove(e);
	}
	
	private void update_entity (PriceEB e, Price p) {	
		e.setStart_d(p.getStart_d());
		e.setEnd_d(p.getEnd_d());
		e.setFb(p.getFb());
		e.setHb(p.getHb());
		e.setBb(p.getBb());
	}
	
	
	private int get_priceid() {
		int res = 0;
		try {
			Query q = ent_man.createNamedQuery(PriceEB.NQ_MAX_PRICEID);
			Integer i = (Integer) q.getSingleResult();
			res = i.intValue();
		} catch (Exception e) {
			res = 0;
		}
		return res + 1;
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
