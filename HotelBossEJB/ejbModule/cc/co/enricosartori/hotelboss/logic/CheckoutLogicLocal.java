package cc.co.enricosartori.hotelboss.logic;

import java.util.Collection;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import cc.co.enricosartori.hotelboss.dto.Period;
import cc.co.enricosartori.hotelboss.dto.Purchase;

@Local
@TransactionManagement(value=TransactionManagementType.CONTAINER)
public interface CheckoutLogicLocal {
	
	/**
	 * Computes the total cost for a collection purchases
	 * TRANSACTION: important, opration used during the billing process
	 * @param c collection of purcase objects
	 * @return the total cost
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public float get_total_pur (Collection<Purchase> c);
	
	/**
	 * Computes the total cost of a list of periods
	 * TRANSACTION: important, opration used during the billing process
	 * @param per list of periods
	 * @return total cost
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public float get_total_pens (List<Period> per);
	
	/**
	 * Retrieves the list of the periods for a customer
	 * TRANSACTION: important, opration used during the billing process
	 * @param room customer
	 * @return list of periods
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<Period> get_periods (int room);
}
