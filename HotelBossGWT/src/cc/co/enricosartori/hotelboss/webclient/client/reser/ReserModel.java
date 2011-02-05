package cc.co.enricosartori.hotelboss.webclient.client.reser;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import cc.co.enricosartori.hotelboss.dto.Reservation;
import cc.co.enricosartori.hotelboss.webclient.client.services.HBReception;
import cc.co.enricosartori.hotelboss.webclient.client.services.HBReceptionAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class ReserModel {
	private final HBReceptionAsync hbrec = GWT.create(HBReception.class);
	private enum ReservState {NEW, UPDATED, DELETED, STORED}
	private HashMap<Reservation, ReservState> reservations = new HashMap<Reservation, ReservState> ();
	private Reservation current;
	private ReservState curr_state;
	
	public ReserModel () {
		this.current = null;
		this.curr_state = null;
	}
	
	public void new_reserv () {
		Reservation r = new Reservation ();
		current = r;
		curr_state = ReservState.NEW;
	}
	
	public void edit_reserv (Reservation r, AsyncCallback<Boolean> call) {
		update_curr (r);
		if (curr_state != ReservState.NEW) {
			curr_state = ReservState.UPDATED;
		}
		save_reserv (call);
	}
	
	public void dele_reserv (AsyncCallback<Boolean> call) {
		if (curr_state != ReservState.NEW) {
			curr_state = ReservState.DELETED;
		}
		save_reserv (call);
	}
	
	public void select_reserv (Reservation r) {
		current = r;
		curr_state = reservations.get(r);
	}
	
	public void cancel () {
		this.current = null;
		this.curr_state = null;
	}
	
	private void update_curr (Reservation r) {
		current.setDate_arr(r.getDate_arr());
		current.setDate_dep(r.getDate_dep());
		current.setRoom(r.getRoom());
		current.setCustomer(r.getCustomer());
		current.setNote(r.getNote());
	}
	
	public Iterator<Reservation> get_iterator () {
		Set<Reservation> s = reservations.keySet();
		Iterator<Reservation> i = s.iterator();
		return i;
	}
	
	private void read_reservations (List<Reservation> l) {
		if (l != null) {
			Iterator<Reservation> i = l.iterator();
			while (i.hasNext()) {
				Reservation tmp = i.next();
				reservations.put(tmp, ReservState.STORED);
			}
		}
	}
	
	private void save_reserv (AsyncCallback<Boolean> call) {
		reservations.put(current, curr_state);
		current.setStatus(curr_state.toString());
		hbrec.store_reservation(current, call);
	}
	
	public void fetch_reservations (final AsyncCallback<Void> call) {
		hbrec.get_reservations(new AsyncCallback<List<Reservation>> () {

			@Override
			public void onFailure(Throwable caught) {
				call.onFailure(caught);				
			}

			@Override
			public void onSuccess(List<Reservation> result) {
				reservations.clear();
				read_reservations (result);
				call.onSuccess(null);
			}
			
		});
	}
}
