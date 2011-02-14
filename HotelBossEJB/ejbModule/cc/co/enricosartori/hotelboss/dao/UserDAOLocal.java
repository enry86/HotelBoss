package cc.co.enricosartori.hotelboss.dao;

import java.util.List;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import cc.co.enricosartori.hotelboss.dto.User;
@TransactionManagement(value=TransactionManagementType.CONTAINER)
public interface UserDAOLocal {
	
	/**
	 * retrives the list of the users
	 * TRANSACTION: used only for visualization, not in a critical context
	 * @return the list of users
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<User> get_users ();
	
	/**
	 * Inserts a new user
	 * TRANSACTION: Not important, used in a crud operation
	 * @param user the user
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public void insert_user (User user);
	
	/**
	 * Update an existing user
	 * TRANSACTION: Not important, used in a crud operation
	 * @param user the user to update
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public void update_user (User user);
	
	/**
	 * Deletes an user
	 * TRANSACTION: Not important, used in a crud operation
	 * @param user the user to be deleted
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public void delete_user (User user);
	
	/**
	 * Checks if the user already exists
	 * TRANSACTION: Not important, used in a crud operation
	 * @param id the id of the user
	 * @return true if the username is free
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public boolean check_user (String id);
}
