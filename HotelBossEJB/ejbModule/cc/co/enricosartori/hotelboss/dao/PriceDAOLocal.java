package cc.co.enricosartori.hotelboss.dao;

import java.util.List;

import javax.ejb.Local;

import cc.co.enricosartori.hotelboss.dto.Price;

@Local
public interface PriceDAOLocal {
	public List<Price> get_pricelist();
	public void insert_price(Price p);
	public void update_price(Price p);
	public void delete_price(Price p);
}
