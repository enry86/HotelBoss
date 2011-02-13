package cc.co.enricosartori.hotelboss.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import cc.co.enricosartori.hotelboss.dto.Extra;
import cc.co.enricosartori.hotelboss.entities.ExtraEB;

@Stateless
public class ExtraDAO implements ExtraDAOLocal {
	
	@PersistenceContext
	private EntityManager ent_man;
	
	public List<Extra> get_extras() {
		Query q = ent_man.createNamedQuery(ExtraEB.EXTRAS);
		List<ExtraEB> res = q.getResultList();
		return convert_list (res);
	}

	public void insert_extra(Extra e) {
		ExtraEB ent = new ExtraEB ();
		update_entity (e, ent);
		ent_man.persist (ent);
	}

	public void update_extra(Extra e) {
		ExtraEB ent = ent_man.find(ExtraEB.class, e.getId ());
		update_entity (e, ent);
		ent_man.merge (ent);
	}
	
	public void delete_extra(Extra e) {
		ExtraEB ent = ent_man.find(ExtraEB.class, e.getId ());
		update_entity (e, ent);
		ent_man.remove (ent);
	}
	
	private List<Extra> convert_list (List<ExtraEB> list) {
		ArrayList<Extra> res = new ArrayList<Extra> (list.size());
		Iterator<ExtraEB> i = list.iterator();
		while (i.hasNext()) {
			ExtraEB tmp = i.next();
			res.add(get_DTO (tmp));
		}
		return res;
	}
	
	
	private Extra get_DTO (ExtraEB ent) {
		Extra dto = new Extra ();
		dto.setId (ent.getId());
		dto.setName (ent.getName());
		dto.setPrice (ent.getPrice());
		return dto;
	}
	
	
	private void update_entity (Extra dto, ExtraEB ent) {
		ent.setName (dto.getName());
		ent.setPrice (dto.getPrice());
	}

	@Override
	public float get_price (int extraId) {
		float res = 0.0f;
		ExtraEB eb = ent_man.find(ExtraEB.class, extraId);
		if (eb != null) res = eb.getPrice();
		return res;
	}
}
