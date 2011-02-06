package cc.co.enricosartori.hotelboss.core;

import java.util.Date;
import java.util.List;

import javax.ejb.Remote;

import cc.co.enricosartori.hotelboss.dto.Reservation;

@Remote
public interface ReceptionRemote {
	public List<Reservation> get_reservations ();
	public List<Reservation> get_arrivals (Date d);
	public List<Reservation> get_departures (Date d);
	public boolean store_reservation (Reservation r);
}
