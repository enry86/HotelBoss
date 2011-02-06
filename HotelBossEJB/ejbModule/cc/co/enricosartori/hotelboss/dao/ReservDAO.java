package cc.co.enricosartori.hotelboss.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import cc.co.enricosartori.hotelboss.dto.Reservation;
import cc.co.enricosartori.hotelboss.entities.ReservEB;

@Stateless
public class ReservDAO implements ReservDAOLocal {
	@PersistenceContext
	private EntityManager ent_man;
	
	@Override
	public boolean check_res(Reservation res) {
		Query q = ent_man.createNamedQuery(ReservEB.RESERV_CHECK);
		q.setParameter("room", res.getRoom());
		q.setParameter("date_arr", res.getDate_arr());
		List<ReservEB> l = q.getResultList();
		return l.size() == 0;
	}

	@Override
	public void delete_res(Reservation res) {
		ReservEB eb = ent_man.find(ReservEB.class, res.getId());
		ent_man.remove(eb);
		
	}

	@Override
	public List<Reservation> get_reservations() {
		Query q = ent_man.createNamedQuery(ReservEB.RESERV_ORD_DATE_ROOM);
		List<ReservEB> l = q.getResultList();
		return convert_list(l);
	}

	@Override
	public void insert_res(Reservation res) {
		ReservEB eb = get_EB (res);
		ent_man.persist(eb);
		
	}

	@Override
	public void update_res(Reservation res) {
		ReservEB eb = get_EB(res);
		ent_man.merge(eb);
	}
	
	public List<Reservation> get_arrivals (Date d) {
		Query q = ent_man.createNamedQuery(ReservEB.RESERV_DATEARR);
		q.setParameter("date_arr", d);
		List<ReservEB> res = q.getResultList();
		System.out.println("Arrivi: " + res.size());
		System.out.println("Data arrivo: " + d);
		return convert_list (res);
	}
	
	public List<Reservation> get_departures (Date d) {
		Query q = ent_man.createNamedQuery(ReservEB.RESERV_DATEDEP);
		q.setParameter("date_dep", d);
		List<ReservEB> res = q.getResultList();
		System.out.println("Partenze: " + res.size());
		return convert_list (res);
	}
	
	
	private ReservEB get_EB (Reservation dto) {
		ReservEB eb = new ReservEB ();
		eb.setId(dto.getId());
		eb.setDate_arr(dto.getDate_arr ());
		eb.setDate_dep(dto.getDate_dep ());
		eb.setRoom(dto.getRoom());
		eb.setCustomer(dto.getCustomer());
		eb.setNote(dto.getNote());
		return eb;
	}

	private Reservation get_DTO (ReservEB eb) {
		Reservation dto = new Reservation ();
		dto.setId(eb.getId());
		dto.setDate_arr(eb.getDate_arr());
		dto.setDate_dep(eb.getDate_dep());
		dto.setRoom(eb.getRoom());
		dto.setCustomer(eb.getCustomer());
		dto.setNote(eb.getNote());
		dto.setStatus("STORED");
		return dto;
	}
	
	
	private List<Reservation> convert_list (List<ReservEB> l) {
		Iterator<ReservEB> i = l.iterator ();
		ArrayList<Reservation> res = new ArrayList<Reservation> (l.size());
		while (i.hasNext()) {
			ReservEB eb = i.next();
			res.add(get_DTO(eb));
			System.out.println("Data entity: " + eb.getDate_arr());
		}
		return res;
	}
}
