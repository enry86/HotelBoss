package cc.co.enricosartori.hotelboss.core;

import java.sql.Date;
import java.util.List;

import javax.ejb.Remote;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import cc.co.enricosartori.hotelboss.dto.Customer;
import cc.co.enricosartori.hotelboss.dto.Purchase;
import cc.co.enricosartori.hotelboss.dto.Reservation;

@Remote
@TransactionManagement(value=TransactionManagementType.CONTAINER)
public interface ReceptionRemote {
	/**
	 * gives the list of reservation
	 * TRANSACTION: used for visualization, not important
	 * @return list of reservation
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<Reservation> get_reservations ();
	/**
	 * get the list of arrivals fo the date
	 * TRANSACTION: used for visualization, not important
	 * @param d date
	 * @return reservations
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<Reservation> get_arrivals (Date d);
	/**
	 * gives the list of reservation departing on the date given
	 * TRANSACTION: used for visualization, not important
	 * @param d date
	 * @return departures
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<Reservation> get_departures (Date d);
	
	/**
	 * stores a reservation
	 * TRANSACTION: stores a reservation, important to avoid conflicts
	 * @param r reservation
	 * @return true if stored
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public boolean store_reservation (Reservation r);
	
	/**
	 * gives the list of customers
	 * TRANSACTION: used for visualization, not important
	 * @return customers
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<Customer> get_customers ();
	/**
	 * Stores a customer
	 * TRANSACTION: important to avoid conflicts
	 * @param c customer
	 * @return true if stored
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public boolean store_customer (Customer c);
	
	/**
	 * retrieves the list of purcases for a room
	 * TRANSACTION: used for visualization, not important
	 * @param room room
	 * @return purchases
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<Purchase> get_pur_room (int room);
	
	/**
	 * Stores a purchase
	 * TRANSACTION: important to avoid conflicts 
	 * @param p purchase
	 * @return true of stored
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public boolean store_purchase (Purchase p);
}
