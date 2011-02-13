package cc.co.enricosartori.hotelboss.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import cc.co.enricosartori.hotelboss.dto.Customer;
import cc.co.enricosartori.hotelboss.entities.CustomerEB;

@Stateless
public class CustomerDAO implements CustomerDAOLocal {
	@PersistenceContext
	private EntityManager ent_man;
	
	public boolean check_room (int room) {
		CustomerEB eb = ent_man.find(CustomerEB.class, room);
		return !(eb == null);
	}
	
	public Customer get_customer (int room) {
		Customer res = null;
		CustomerEB eb = ent_man.find(CustomerEB.class, room);
		if (eb != null) res = get_DTO (eb);
		return res;
	}
	
	@Override
	public boolean check_cust(Customer c) {
		CustomerEB eb = ent_man.find(CustomerEB.class, c.getRoom());
		return eb == null;
	}

	@Override
	public void delete_cust(Customer c) {
		CustomerEB eb = ent_man.find(CustomerEB.class, c.getRoom());
		ent_man.remove(eb);
	}

	@Override
	public List<Customer> get_customers() {
		Query q = ent_man.createNamedQuery(CustomerEB.CUST_LIST);
		List<CustomerEB> l = q.getResultList();
		return convert_list(l);
	}

	@Override
	public void insert_cust(Customer c) {
		CustomerEB eb = get_EB(c);
		ent_man.persist(eb);
	}

	@Override
	public void update_cust(Customer c) {
		CustomerEB eb = get_EB(c);
		ent_man.merge(eb);
	}
	
	private Customer get_DTO (CustomerEB eb) {
		Customer dto = new Customer ();
		dto.setRoom(eb.getRoom());
		dto.setDate_arr(eb.getDate_arr());
		dto.setName(eb.getName());
		dto.setDiscount(eb.getDiscount());
		dto.setPeople(eb.getPeople());
		dto.setTreatment(eb.getTreatment());
		return dto;
	}
	
	private CustomerEB get_EB (Customer dto) {
		CustomerEB eb = new CustomerEB ();
		eb.setRoom(dto.getRoom());
		eb.setDate_arr(dto.getDate_arr());
		eb.setName(dto.getName());
		eb.setDiscount(dto.getDiscount());
		eb.setPeople(dto.getPeople());
		eb.setTreatment(dto.getTreatment());
		return eb;
	}
	
	private List<Customer> convert_list (List<CustomerEB> l) {
		Iterator<CustomerEB> i = l.iterator();
		ArrayList<Customer> res = new ArrayList<Customer> (l.size());
		while (i.hasNext()) {
			res.add(get_DTO(i.next()));
		}
		return res;
	}

}
