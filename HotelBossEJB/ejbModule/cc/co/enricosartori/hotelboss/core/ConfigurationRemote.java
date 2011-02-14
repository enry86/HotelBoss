package cc.co.enricosartori.hotelboss.core;

import java.util.List;

import javax.ejb.Remote;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import cc.co.enricosartori.hotelboss.dto.Extra;
import cc.co.enricosartori.hotelboss.dto.Price;
import cc.co.enricosartori.hotelboss.dto.Reduction;
import cc.co.enricosartori.hotelboss.dto.User;

@Remote
@TransactionManagement(value=TransactionManagementType.CONTAINER)
public interface ConfigurationRemote {
	
	/**
	 * retrieves the list of the prices
	 * TRANSACTION: operation used in visualization context, not important
	 * @return the pricelist
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<Price> get_pricelist ();
	
	/**
	 * stores the pricelist
	 * TRANSACTION: used for crud operation, not important 
	 * @param pl pricelist
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public void store_pricelist (List<Price> pl);
	
	/**
	 * returns the list of reductions
	 * TRANSACTION: operation used in visualization context, not important
	 * @return reductions
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<Reduction> get_reductions ();
	/**
	 * stores a list of reductions
	 * TRANSACTION: used for crud operation, not important
	 * @param rl reductions
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public void store_reductions (List<Reduction> rl);
	
	/**
	 * retrieves the list of extras
	 * TRANSACTION: operation used in visualization context, not important
	 * @return extras
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<Extra> get_extras ();
	/**
	 * stores a list of extras
	 * TRANSACTION: used for crud operation, not important
	 * @param el extras
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public void store_extras (List<Extra> el);
	
	
	/**
	 * Retrieves the list of users
	 * TRANSACTION: operation used in visualization context, not important
	 * @return users
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<User> get_users ();
	
	/**
	 * checks if a username is free
	 * TRANSACTION: used for crud operation, not important
	 * @param user user
	 * @returns true if free
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public boolean check_username (String user);
	/**
	 * stores a user
	 * TRANSACTION: used for crud operation, not important
	 * @param user the user
	 * @return true if stored
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public boolean store_user (User user);
	
}
