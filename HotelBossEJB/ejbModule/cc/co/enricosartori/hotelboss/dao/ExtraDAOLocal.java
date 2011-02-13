package cc.co.enricosartori.hotelboss.dao;

import java.util.List;

import javax.ejb.Local;

import cc.co.enricosartori.hotelboss.dto.Extra;


@Local
public interface ExtraDAOLocal {

	public List<Extra> get_extras ();
	public void insert_extra (Extra e);
	public void update_extra (Extra e);
	public void delete_extra (Extra e);
	public float get_price (int extraId);
	
}
