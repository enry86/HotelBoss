package cc.co.enricosartori.hotelboss.dao;

import java.sql.Date;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import cc.co.enricosartori.hotelboss.dto.Reservation;

@Local
@TransactionManagement(value=TransactionManagementType.CONTAINER)
public interface ReservDAOLocal {
	
	/**
	 * Returns all the reservations
	 * TRANSACTION: Not important, it's a read only operation
	 * @return reservation list
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<Reservation> get_reservations();
	
	/**
	 * Returns the arrivals for the date given
	 * TRANSACTION: Operation used in a visualization context, not important
	 * @param d date
	 * @return list of arrivals
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<Reservation> get_arrivals (Date d);
	
	/**
	 * Returns the departures for the date given
	 * TRANSACTION: Operation used in a visualization context, not important
	 * @param d date
	 * @return list of departures
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<Reservation> get_departures (Date d);
	
	/**
	 * Inserts a new reservation
	 * TRANSACTION: not important, used in crud operation
	 * @param res the reservation 
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public void insert_res (Reservation res);
	
	/**
	 * Updates an existing reservation
	 * TRANSACTION: not important, used in crud operation
	 * @param res reservation to update
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public void update_res (Reservation res);
	
	/**
	 * Deletes a reservation
	 * TRANSACTION: not important, used in crud operation
	 * @param res reservation to delete
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public void delete_res (Reservation res);
	
	/**
	 * Check if it's possible to insert a reservation
	 * TRANSACTION: not important, used in crud operation
	 * @param res reservation
	 * @return true if it's valid
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public boolean check_res (Reservation res);
}
