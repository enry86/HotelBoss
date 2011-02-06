package cc.co.enricosartori.hotelboss.dao;

import java.sql.Date;
import java.util.List;

import cc.co.enricosartori.hotelboss.dto.Reservation;


public interface ReservDAOLocal {
	public List<Reservation> get_reservations();
	public List<Reservation> get_arrivals (Date d);
	public List<Reservation> get_departures (Date d);
	public void insert_res (Reservation res);
	public void update_res (Reservation res);
	public void delete_res (Reservation res);
	public boolean check_res (Reservation res);
}
