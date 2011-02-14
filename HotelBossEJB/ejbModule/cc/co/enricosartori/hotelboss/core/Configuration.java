package cc.co.enricosartori.hotelboss.core;

import java.util.Iterator;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.jboss.aspects.security.SecurityDomain;

import cc.co.enricosartori.hotelboss.dao.ExtraDAOLocal;
import cc.co.enricosartori.hotelboss.dao.PriceDAOLocal;
import cc.co.enricosartori.hotelboss.dao.ReductionDAOLocal;
import cc.co.enricosartori.hotelboss.dao.UserDAOLocal;
import cc.co.enricosartori.hotelboss.dto.Extra;
import cc.co.enricosartori.hotelboss.dto.Price;
import cc.co.enricosartori.hotelboss.dto.Reduction;
import cc.co.enricosartori.hotelboss.dto.User;

@Stateless
@SecurityDomain("HotelBossLogin")
public class Configuration implements ConfigurationRemote {
	@EJB
	PriceDAOLocal price_dao;
	@EJB
	ReductionDAOLocal red_dao;
	@EJB
	ExtraDAOLocal ext_dao;
	@EJB
	UserDAOLocal user_dao;
	
    /**
     * Default constructor. 
     */
    public Configuration() {}

	@Override
	@RolesAllowed("admin")
	public List<Price> get_pricelist() {
		System.out.println("Retrieving pricelist");
		return price_dao.get_pricelist();
	}
	
	@RolesAllowed("admin")
	public void store_pricelist(List<Price> pl) {
		Iterator<Price> i = pl.iterator();
		while (i.hasNext()){
			Price tmp = i.next();
			if (tmp.getState().compareTo("NEW") == 0) {
				price_dao.insert_price(tmp);
			}
			else if (tmp.getState().compareTo("UPDATED") == 0) {
				price_dao.update_price(tmp);
			}
			else if (tmp.getState().compareTo("DELETED") == 0) {
				price_dao.delete_price(tmp);
			}
		}
	}
	
	@RolesAllowed("admin")
	public List<Reduction>  get_reductions () {
		return red_dao.get_reductions();
	}
	
	@RolesAllowed("admin")
	public void store_reductions (List<Reduction> rl) {
		Iterator<Reduction> i = rl.iterator();
		while (i.hasNext()) {
			Reduction tmp = i.next();
			if (tmp.getState().equals("NEW")) {
				red_dao.insert_red (tmp);
			}
			else if (tmp.getState().equals("UPDATED")) {
				red_dao.update_red (tmp);
			}
			else if (tmp.getState().equals("DELETED")) {
				red_dao.delete_red (tmp);
			}
		}
	}

	@RolesAllowed("admin")
	public List<Extra> get_extras() {
		return ext_dao.get_extras ();
	}

	@RolesAllowed("admin")
	public void store_extras(List<Extra> el) {
		Iterator<Extra> i = el.iterator();
		while (i.hasNext()) {
			Extra tmp = i.next();
			if (tmp.getStatus().equals("NEW")) {
				ext_dao.insert_extra(tmp);
			}
			else if (tmp.getStatus().equals("UPDATED")) {
				ext_dao.update_extra(tmp);
			}
			else if (tmp.getStatus().equals("DELETED")) {
				ext_dao.delete_extra(tmp);
			}
		}
	}

	@RolesAllowed("admin")
	public boolean check_username(String user) {
		return user_dao.check_user(user);
	}

	@RolesAllowed("admin")
	public List<User> get_users() {
		return user_dao.get_users();
	}

	@RolesAllowed("admin")
	public boolean store_user(User user) {
		boolean res = true;
		if (user.getStatus().equals("NEW")) {
			System.out.println("Checking Username");
			if (user_dao.check_user(user.getUsername())) {
				System.out.println("Username OK, inserting...");
				user_dao.insert_user(user);
			}
			else {
				System.out.println("Username KO");
				res = false;
			}
		}
		else if (user.getStatus().equals("UPDATED")) {
			user_dao.update_user(user);
		}
		else if (user.getStatus().equals("DELETED")) {
			user_dao.delete_user(user);
		}
		return res;
	}
}
