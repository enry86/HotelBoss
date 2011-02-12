package cc.co.enricosartori.hotelboss.webclient.client.services;

import java.sql.Date;
import java.util.List;

import cc.co.enricosartori.hotelboss.dto.Customer;
import cc.co.enricosartori.hotelboss.dto.Purchase;
import cc.co.enricosartori.hotelboss.dto.Reservation;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface HBReceptionAsync {
	
	public void get_reservations (AsyncCallback<List<Reservation>> asyncCallback);
	public void store_reservation (Reservation res, AsyncCallback<Boolean> callback);
	public void get_arrivals (Date d, AsyncCallback<List<Reservation>> call);
	public void get_departures (Date d, AsyncCallback<List<Reservation>> call);
	public void get_customers (AsyncCallback<List<Customer>> asyncCallback);
	public void store_customer (Customer c, AsyncCallback<Boolean> callback);
	public void get_pur_room (int room, AsyncCallback<List<Purchase>> callback);
	public void store_purchase (Purchase p, AsyncCallback<Boolean> callback);
}
