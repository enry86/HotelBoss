package cc.co.enricosartori.hotelboss.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;


import cc.co.enricosartori.hotelboss.dto.Purchase;
import cc.co.enricosartori.hotelboss.entities.PurchaseEB;

public class PurchaseDAO implements PurchaseDAOLocal {
	@PersistenceContext
	EntityManager ent_man;
	
	@Override
	public void delete_pur(Purchase p) {
		PurchaseEB eb = ent_man.find(PurchaseEB.class, p.getRoom());
		ent_man.remove(eb);
	}

	@Override
	public void depart_pur(int room) {
		Query q = ent_man.createNamedQuery(PurchaseEB.PURCHASES_ROOM);
		q.setParameter("room", room);
		List<PurchaseEB> r =  q.getResultList();
		Iterator<PurchaseEB> i = r.iterator();
		while (i.hasNext()) {
			ent_man.remove(i.next());
		}
	}

	@Override
	public List<Purchase> get_purs(int room) {
		Query q = ent_man.createNamedQuery(PurchaseEB.PURCHASES_ROOM);
		q.setParameter("room", room);
		List<PurchaseEB> r =  q.getResultList();
		return convert_list (r);
	}

	@Override
	public void insert_pur(Purchase p) {
		PurchaseEB eb = get_EB (p);
		ent_man.persist(eb);
	}
	
	private Purchase get_DTO (PurchaseEB e) {
		Purchase d = new Purchase ();
		d.setId(e.getId());
		d.setRoom(e.getRoom());
		d.setDate(e.getDate());
		return d;
	}
	
	private PurchaseEB get_EB (Purchase d) {
		PurchaseEB e = new PurchaseEB();
		e.setId(d.getId());
		e.setDate(d.getDate());
		e.setRoom(d.getRoom());
		return e;
	}
	
	private List<Purchase> convert_list (List<PurchaseEB> l) {
		Iterator<PurchaseEB> i = l.iterator();
		ArrayList<Purchase> res = new ArrayList<Purchase> (l.size());
		while (i.hasNext()) {
			res.add(get_DTO(i.next()));
		}
		return res;
	}

}
