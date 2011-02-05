package cc.co.enricosartori.hotelboss.webclient.server;

import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;

import cc.co.enricosartori.hotelboss.core.ReceptionRemote;
import cc.co.enricosartori.hotelboss.dto.Reservation;
import cc.co.enricosartori.hotelboss.webclient.client.services.HBReception;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class HBReceptionImpl extends RemoteServiceServlet implements HBReception {

	private static final long serialVersionUID = -4707856388465621924L;
	private ReceptionRemote hbRecept;
	
	public HBReceptionImpl (){
		Context ctx;
		try {
			ctx = new InitialContext();
			hbRecept = (ReceptionRemote) ctx.lookup("HotelBossEAR/Reception/remote");
		}
		catch (Exception e) {
			GWT.log("Error " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	@Override
	public List<Reservation> get_reservations() {
		return hbRecept.get_reservations();
	}

	@Override
	public Boolean store_reservation(Reservation res) {
		return hbRecept.store_reservation(res);
	}

}
