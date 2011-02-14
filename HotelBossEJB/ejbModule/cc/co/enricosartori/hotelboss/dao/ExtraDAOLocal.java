package cc.co.enricosartori.hotelboss.dao;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import cc.co.enricosartori.hotelboss.dto.Extra;


@Local
@TransactionManagement(value=TransactionManagementType.CONTAINER)
public interface ExtraDAOLocal {
	
	/**
	 * Returns the list af all the extras
	 * TRANSACTION: Not important, read only operation
	 * @return extras 
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<Extra> get_extras ();
	
	/**
	 * Insert a new extra
	 * TRANSACTION: Not important, common crud operation
	 * @param e extra to be added
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public void insert_extra (Extra e);
	
	/**
	 * Updates an existing extra
	 * TRANSACTION: Not important, common crud operation
	 * @param e extra to be updated
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public void update_extra (Extra e);
	
	/**
	 * deletes an extra
	 * TRANSACTION: Not important, common crud operation
	 * @param e
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public void delete_extra (Extra e);
	
	/**
	 * Gets the price of an extra
	 * TRANSACTION: Important, this operation is called to compute the total price
	 * @param extraId id of the price
	 * @return the price of the extra
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public float get_price (int extraId);
	
}
