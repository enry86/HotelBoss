package cc.co.enricosartori.hotelboss.core;

import java.util.List;

import javax.ejb.Remote;

import cc.co.enricosartori.hotelboss.dto.Reservation;

@Remote
public interface ReceptionRemote {
	public List<Reservation> get_reservations ();
	public boolean store_reservation (Reservation r);
}
