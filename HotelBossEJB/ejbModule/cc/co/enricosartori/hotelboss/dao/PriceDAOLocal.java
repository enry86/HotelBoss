package cc.co.enricosartori.hotelboss.dao;

import java.sql.Date;
import java.util.List;

import javax.ejb.Local;

import cc.co.enricosartori.hotelboss.dto.Price;

@Local
public interface PriceDAOLocal {
	public List<Price> get_pricelist();
	public void insert_price(Price p);
	public void update_price(Price p);
	public void delete_price(Price p);
	public Price get_compl_price (Date arr, Date dep);
	public Price get_chunk_price (Date date);
	public List<Price> get_inter_price (Date arr, Date par);
}
