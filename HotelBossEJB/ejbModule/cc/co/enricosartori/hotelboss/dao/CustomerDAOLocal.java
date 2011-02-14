package cc.co.enricosartori.hotelboss.dao;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import cc.co.enricosartori.hotelboss.dto.Customer;

@Local
@TransactionManagement(value=TransactionManagementType.CONTAINER)
public interface CustomerDAOLocal {
	
	/**
	 * Retrieves the list of the customers
	 * TRANSACTION: Not important, Read only operation
	 * @return list of customers
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<Customer> get_customers ();
	
	/**
	 * Inserts a new customer
	 * TRANSACTION: Not important, common crud operation
	 * @param c the customer
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public void insert_cust (Customer c);
	
	/**
	 * Updates an existing customer
	 * TRANSACTION: Not important, common crud operation
	 * @param c the customer
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public void update_cust (Customer c);
	
	/**
	 * Deletes a customer
	 * TRANSACTION: Not important, common crud operation
	 * @param c the customer
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public void delete_cust (Customer c);
	
	/**
	 * Deletes a customer by room number (id)
	 * TRANSACTION: Important, needed in the checkout process
	 * @param room
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void delete_room (int room);
	
	/**
	 * Checks if it's possible to insert the customer
	 * TRANSACTION: Not important, used in common crud operation
	 * @param c the customer
	 * @return true if the room is free
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public boolean check_cust (Customer c);
	
	/**
	 * Checks if the room is used
	 * TRANSACTION: This operation is used in the billing process, failure can determine inconsistencies
	 * @param room
	 * @return
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public boolean check_room (int room);
	
	/**
	 * Retrives the data of the customer by the room
	 * TRANSACTION: This operation is included in the billing process, it requires a transaction
	 * @param room
	 * @return thre customer object
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Customer get_customer (int room);
}
