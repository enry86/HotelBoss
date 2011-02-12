package cc.co.enricosartori.hotelboss.dao;

import java.util.List;

import javax.ejb.Local;

import cc.co.enricosartori.hotelboss.dto.Purchase;

@Local
public interface PurchaseDAOLocal {
	public void insert_pur (Purchase p);
	public void delete_pur (Purchase p);
	public void depart_pur (int room);
	public List<Purchase> get_purs (int room);
}
