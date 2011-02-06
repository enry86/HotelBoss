package cc.co.enricosartori.hotelboss.core;

import java.sql.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import cc.co.enricosartori.hotelboss.dao.ReservDAOLocal;
import cc.co.enricosartori.hotelboss.dto.Reservation;

@Stateless
public class Reception implements ReceptionRemote {
	@EJB
	private ReservDAOLocal res_dao;
	
	public List<Reservation> get_reservations () {
		return res_dao.get_reservations();
	}
	
	public List<Reservation> get_arrivals (Date d) {
		return res_dao.get_arrivals(d);
	}
	
	public List<Reservation> get_departures (Date d) {
		return res_dao.get_departures(d);
	}
	
	public boolean store_reservation (Reservation r) {
		boolean res = true;
		if (r.getStatus().equals("NEW")) {
			if (res_dao.check_res(r)) {
				res_dao.insert_res(r);
			}
			else {
				res = false;
			}
		}
		else if (r.getStatus().equals("UPDATED")) {
			res_dao.update_res(r);
		}
		else if (r.getStatus().equals("DELETED")) {
			res_dao.delete_res(r);
		}
		return res;
	}
		
	
}
