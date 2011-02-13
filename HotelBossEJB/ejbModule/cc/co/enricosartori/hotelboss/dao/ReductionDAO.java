package cc.co.enricosartori.hotelboss.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import cc.co.enricosartori.hotelboss.dto.Reduction;
import cc.co.enricosartori.hotelboss.entities.ReductionEB;

@Stateless
public class ReductionDAO implements ReductionDAOLocal {

	@PersistenceContext
	private EntityManager ent_man;

	
	@Override
	public List<Reduction> get_reductions() {
		Query q = ent_man.createNamedQuery(ReductionEB.NQ_REDUCTIONS);
		List<ReductionEB> l = q.getResultList();
		return convert_list (l);
	}
	
	
	@Override
	public void insert_red(Reduction red) {
		ReductionEB eb = new ReductionEB ();
		update_entity (red, eb);
		ent_man.persist(eb);
		
	}

	
	@Override
	public void update_red(Reduction red) {
		ReductionEB eb = ent_man.find(ReductionEB.class, red.getId());
		update_entity (red, eb);
		ent_man.merge(eb);
	}

	
	@Override
	public void delete_red(Reduction red) {
		ReductionEB eb = ent_man.find(ReductionEB.class, red.getId());
		update_entity (red, eb);
		ent_man.remove(eb);
	}
	
	
	public Reduction get_reduction (int id) {
		Reduction res = null;
		ReductionEB eb = ent_man.find(ReductionEB.class, id);
		if (eb != null) res = get_DTO (eb);
		return res;
	}
	
	private List<Reduction> convert_list(List<ReductionEB> list) {
		Iterator<ReductionEB> i = list.iterator();
		ArrayList<Reduction> res = new ArrayList<Reduction>(); 
		while (i.hasNext()) {
			res.add(get_DTO(i.next()));
		}
		return res;
	}
	
	
	private Reduction get_DTO(ReductionEB ent) {
		Reduction r = new Reduction();
		r.setId(ent.getId());
		r.setDescr(ent.getDescr());
		r.setRed_type(ent.getRed_type());
		r.setVal(ent.getVal());
		r.setPerc(ent.isPerc());
		r.setState("STORED");
		return r;
	}
	
	
	private void update_entity (Reduction dto, ReductionEB ent) {
		ent.setDescr(dto.getDescr());
		ent.setVal(dto.getVal());
		ent.setRed_type(dto.getRed_type());
		ent.setPerc(dto.isPerc());
	}
}
