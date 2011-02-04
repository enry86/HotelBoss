package cc.co.enricosartori.hotelboss.core;

import java.util.List;

import javax.ejb.Remote;

import cc.co.enricosartori.hotelboss.dto.Extra;
import cc.co.enricosartori.hotelboss.dto.Price;
import cc.co.enricosartori.hotelboss.dto.Reduction;
import cc.co.enricosartori.hotelboss.dto.User;

@Remote
public interface ConfigurationRemote {
	public List<Price> get_pricelist ();
	public void store_pricelist (List<Price> pl);
	
	public List<Reduction> get_reductions ();
	public void store_reductions (List<Reduction> rl);
	
	public List<Extra> get_extras ();
	public void store_extras (List<Extra> el);
	
	public List<User> get_users ();
	public boolean check_username (String user);
	public boolean store_user (User user);
	
}
