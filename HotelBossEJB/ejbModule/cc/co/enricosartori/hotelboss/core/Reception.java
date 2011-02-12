package cc.co.enricosartori.hotelboss.core;

import java.sql.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import cc.co.enricosartori.hotelboss.dao.CustomerDAOLocal;
import cc.co.enricosartori.hotelboss.dao.PurchaseDAOLocal;
import cc.co.enricosartori.hotelboss.dao.ReservDAOLocal;
import cc.co.enricosartori.hotelboss.dto.Customer;
import cc.co.enricosartori.hotelboss.dto.Purchase;
import cc.co.enricosartori.hotelboss.dto.Reservation;

@Stateless
public class Reception implements ReceptionRemote {
	@EJB
	private ReservDAOLocal res_dao;
	@EJB
	private CustomerDAOLocal cus_dao;
	@EJB
	private PurchaseDAOLocal pur_dao;
	
	
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
	
	

	@Override
	public List<Customer> get_customers() {
		return cus_dao.get_customers();
	}

	@Override
	public boolean store_customer(Customer c) {
		boolean res = true;
		if (c.getStatus().equals("NEW")) {
			if (cus_dao.check_cust(c)) {
				cus_dao.insert_cust(c);
			}
			else {
				res = false;
			}
		}
		else if (c.getStatus().equals("UPDATED")) {
			cus_dao.update_cust(c);
		}
		else if (c.getStatus().equals("DELETED")) {
			cus_dao.delete_cust(c);
		}
		return res;
	}

	@Override
	public List<Purchase> get_pur_room(int room) {
		return pur_dao.get_purs(room);
	}

	@Override
	public boolean store_purchase(Purchase p) {
		boolean res = true;
		if (p.getStatus().equals("NEW")) {
			if (cus_dao.check_room(p.getRoom())) {
				pur_dao.insert_pur(p);
			}
			else {
				res = false;
			}
		}
		else if (p.getStatus().equals("DELETED")) {
			pur_dao.delete_pur(p);
		}
		return res;
	}
}
