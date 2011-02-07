package cc.co.enricosartori.hotelboss.dao;

import java.util.List;

import cc.co.enricosartori.hotelboss.dto.Customer;

public interface CustomerDAOLocal {
	public List<Customer> get_customers ();
	public void insert_cust (Customer c);
	public void update_cust (Customer c);
	public void delete_cust (Customer c);
	public boolean check_cust (Customer c);
}
