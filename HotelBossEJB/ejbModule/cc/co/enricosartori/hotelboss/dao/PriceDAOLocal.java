package cc.co.enricosartori.hotelboss.dao;

import java.sql.Date;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import cc.co.enricosartori.hotelboss.dto.Price;

@Local
@TransactionManagement(value=TransactionManagementType.CONTAINER)
public interface PriceDAOLocal {
	/**
	 * Retrives the pricelist
	 * TRANSACTION: Not important, read only operation 
	 * @return pricelist
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<Price> get_pricelist();
	
	/**
	 * Inserts a new price
	 * TRANSACTION: Crud operation, not really needed
	 * @param p price to be inserted
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public void insert_price(Price p);
	
	/**
	 * Updates a price
	 * TRANSACTION: Crud operation, not really needed
	 * @param p price to be updated
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public void update_price(Price p);
	
	/**
	 * Deletes a price
	 * TRANSACTION: Crud operation, not really needed
	 * @param p price to be removed 
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public void delete_price(Price p);
	
	/**
	 * Search a price in the pricelist which contains the whole period
	 * TRASNSACTION: Important to calculate the total price 
	 * @param arr arrival date
	 * @param dep departure date
	 * @return the price object
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Price get_compl_price (Date arr, Date dep);
	
	/**
	 * Search the price which contains the date passed
	 * TRASNSACTION: Important to calculate the total price 
	 * @param date date to evaluate
	 * @return the price object
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Price get_chunk_price (Date date);
	
	/**
	 * Search the prices contained in the date span passed
	 * TRASNSACTION: Important to calculate the total price 
	 * @param arr arrival date
	 * @param dep departure date
	 * @return the price object
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<Price> get_inter_price (Date arr, Date par);
}
