package cc.co.enricosartori.hotelboss.webclient.server;

import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;

import cc.co.enricosartori.hotelboss.core.CheckoutRemote;
import cc.co.enricosartori.hotelboss.dto.Purchase;
import cc.co.enricosartori.hotelboss.webclient.client.services.HBCheckout;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class HBCheckoutImpl extends RemoteServiceServlet implements HBCheckout {

	private static final long serialVersionUID = -654504277699874365L;
	
	private CheckoutRemote hbco;
	
	public HBCheckoutImpl (){
		Context ctx;
		try {
			ctx = new InitialContext();
			hbco = (CheckoutRemote) ctx.lookup("HotelBossEAR/Checkout/remote");
		}
		catch (Exception e) {
			GWT.log("Error " + e.getMessage());
			e.printStackTrace();
		}
	}

	@Override
	public void add_purchase(List<Purchase> p) {
		hbco.add_purchase(p);
		
	}

	@Override
	public List<Purchase> get_purchase () {
		return hbco.get_purchase ();
	}

	@Override
	public float get_total_pur () {
		return hbco.get_total_pur ();
	}

	@Override
	public void remove_purchase (Purchase p) {
		hbco.remove_purchase (p);
	}
	
	public void cancel () {
		hbco.cancel();
	}
}
