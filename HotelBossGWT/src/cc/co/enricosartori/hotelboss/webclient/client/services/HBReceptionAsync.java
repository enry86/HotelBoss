package cc.co.enricosartori.hotelboss.webclient.client.services;

import java.util.List;

import cc.co.enricosartori.hotelboss.dto.Reservation;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface HBReceptionAsync {
	public void get_reservations (AsyncCallback<List<Reservation>> asyncCallback);
	public void store_reservation (Reservation res, AsyncCallback<Boolean> callback);
}
