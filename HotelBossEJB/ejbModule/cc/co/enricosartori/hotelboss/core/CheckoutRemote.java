package cc.co.enricosartori.hotelboss.core;

import java.util.List;

import javax.ejb.Remote;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import cc.co.enricosartori.hotelboss.dto.Period;
import cc.co.enricosartori.hotelboss.dto.Purchase;
import cc.co.enricosartori.hotelboss.dto.Totals;

@Remote
@TransactionManagement(value=TransactionManagementType.CONTAINER)
public interface CheckoutRemote {
	/**
	 * Adds the purchase to the customer bill
	 * TRANSACTION: Important: used in the billing process
	 * @param p purchase to add
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void add_purchase (List<Purchase> p);
	
	/**
	 * removes a purchase from the customer bill
	 * TRANSACTION: Important: used in the billing process
	 * @param p purchase to remove
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void remove_purchase (Purchase p);
	
	/**
	 * retrieves the list of purchase currently on the bill
	 * TRANSACTION: Not so important: used in a visualization context
	 * @return list of purchases
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<Purchase> get_purchase ();
	
	/**
	 * Retrieves and stores in the stateful bean the list of periods of the bill
	 * TRANSACTION: Important: used in the billing process
	 * @param room customer
	 * @return list of periods
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<Period> get_periods (int room);
	
	/**
	 * Returns the total cost for the purchases in the bill
	 * TRANSACTION: Important: used in the billing process
	 * @return cost
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public float get_total_pur ();
	
	/**
	 * Returns the totals for the bill
	 * TRANSACTION: Important: used in the billing process
	 * @return total costs
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Totals get_totals ();
	
	/**
	 * Checkouts the costumer, removing all the purchases and the customer
	 * TRANSACTION: Important: used in the billing process
	 * @param room
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void checkout (int room);
	
	/**
	 * Cancels the billing process, resetting the bean
	 * TRANSACTION: Important: used in the billing process
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void cancel ();
}
