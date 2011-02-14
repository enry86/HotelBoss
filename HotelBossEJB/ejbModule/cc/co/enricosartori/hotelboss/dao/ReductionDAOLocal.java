package cc.co.enricosartori.hotelboss.dao;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import cc.co.enricosartori.hotelboss.dto.Reduction;

@Local
@TransactionManagement(value=TransactionManagementType.CONTAINER)
public interface ReductionDAOLocal {
	
	/**
	 * Returns the list of the reductions
	 * TRANSACTION: Used in a visualization context, not important
	 * @return the list of reductions
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<Reduction> get_reductions();
	
	/**
	 * Inserts a new reduction
	 * TRANSACTION: used for crud operation, not important
	 * @param red the reduction to be inserted
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public void insert_red (Reduction red);
	
	/**
	 * update an existing reduction
	 * TRANSACTION: used for crud operation, not important
	 * @param red the reduction to update
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public void update_red (Reduction red);
	
	/**
	 * deletes a reduction
	 * TRANSACTION: used for crud operation, not important
	 * @param red the reduction to delete
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public void delete_red (Reduction red);
	
	/**
	 * retrieves the reduction identified by the id
	 * TRANSACTION: used for billing process, important to be into a transaction environment
	 * @param id id to search for
	 * @return the reduction
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Reduction get_reduction (int id);
	
}
