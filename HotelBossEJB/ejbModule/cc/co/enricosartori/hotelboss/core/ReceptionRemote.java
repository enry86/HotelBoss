package cc.co.enricosartori.hotelboss.core;

import java.sql.Date;
import java.util.List;

import javax.ejb.Remote;

import cc.co.enricosartori.hotelboss.dto.Customer;
import cc.co.enricosartori.hotelboss.dto.Purchase;
import cc.co.enricosartori.hotelboss.dto.Reservation;;

@Remote
public interface ReceptionRemote {
	public List<Reservation> get_reservations ();
	public List<Reservation> get_arrivals (Date d);
	public List<Reservation> get_departures (Date d);
	public boolean store_reservation (Reservation r);
	
	public List<Customer> get_customers ();
	public boolean store_customer (Customer c);
	
	public List<Purchase> get_pur_room (int room);
	public boolean store_purchase (Purchase p);
}
