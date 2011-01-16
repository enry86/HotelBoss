package cc.co.enricosartori.hotelboss.dao;

import java.util.List;

import javax.ejb.Local;

import cc.co.enricosartori.hotelboss.dto.Reduction;

@Local
public interface ReductionDAOLocal {

	public List<Reduction> get_reductions();
	public void insert_red (Reduction red);
	public void update_red (Reduction red);
	public void delete_red (Reduction red);
	
}
