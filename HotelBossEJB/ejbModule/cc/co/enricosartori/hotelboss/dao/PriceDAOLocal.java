package cc.co.enricosartori.hotelboss.dao;

import java.util.List;

import javax.ejb.Local;

import cc.co.enricosartori.hotelboss.dto.Price;

@Local
public interface PriceDAOLocal {
	public List<Price> get_pricelist();
}
