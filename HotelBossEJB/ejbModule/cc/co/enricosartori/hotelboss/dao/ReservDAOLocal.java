package cc.co.enricosartori.hotelboss.dao;

import java.util.List;

import cc.co.enricosartori.hotelboss.dto.Reservation;


public interface ReservDAOLocal {
	public List<Reservation> get_reservations();
	public void insert_res (Reservation res);
	public void update_res (Reservation res);
	public void delete_res (Reservation res);
	public boolean check_res (Reservation res);
}
