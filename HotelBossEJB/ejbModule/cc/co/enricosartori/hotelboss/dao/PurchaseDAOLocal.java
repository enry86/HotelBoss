package cc.co.enricosartori.hotelboss.dao;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import cc.co.enricosartori.hotelboss.dto.Purchase;

@Local
@TransactionManagement(value=TransactionManagementType.CONTAINER)
public interface PurchaseDAOLocal {
	
	/**
	 * Inserts a new purchase
	 * TRANSACTION: Not important, common Crud operation
	 * @param p
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public void insert_pur (Purchase p);
	
	/**
	 * Deletes a purchase
	 * TRANSACTION: Not important, common Crud operation
	 * @param p
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public void delete_pur (Purchase p);
	
	/**
	 * Deletes all the purchases of a customer, used in the checkout
	 * TRANSACTION: Used in the checkout operation, needs a transaction environment
	 * @param room the customer
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void depart_pur (int room);
	
	/**
	 * Retrieves all the purchase of a room
	 * TRANSACTION: Used in the checkout process, important to avoid failures
	 * @param room the room 
	 * @return the list of the purchases
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<Purchase> get_purs (int room);
}
