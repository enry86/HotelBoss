package cc.co.enricosartori.hotelboss.webclient.client.dashboard;

import java.sql.Date;
import java.util.List;

import cc.co.enricosartori.hotelboss.dto.Reservation;
import cc.co.enricosartori.hotelboss.webclient.client.services.HBReception;
import cc.co.enricosartori.hotelboss.webclient.client.services.HBReceptionAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class DashModel {
	private HBReceptionAsync hbrec = GWT.create(HBReception.class);
	
	public void update_arr (Date d, AsyncCallback<List<Reservation>> call) {
		hbrec.get_arrivals (d, call);
	}
	
	public void update_dep (Date d, AsyncCallback<List<Reservation>> call) {
		hbrec.get_departures (d, call);
	}
}
