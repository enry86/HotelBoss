package cc.co.enricosartori.hotelboss.webclient.client.services;

import java.util.List;

import cc.co.enricosartori.hotelboss.dto.Reservation;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("hbReception")
public interface HBReception extends RemoteService {
	public List<Reservation> get_reservations ();
	public Boolean store_reservation (Reservation res);
}
