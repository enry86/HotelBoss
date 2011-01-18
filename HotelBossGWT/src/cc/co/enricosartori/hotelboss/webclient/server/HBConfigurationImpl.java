package cc.co.enricosartori.hotelboss.webclient.server;

import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import cc.co.enricosartori.hotelboss.core.ConfigurationRemote;
import cc.co.enricosartori.hotelboss.dto.Price;
import cc.co.enricosartori.hotelboss.dto.Reduction;
import cc.co.enricosartori.hotelboss.webclient.client.services.HBConfiguration;

public class HBConfigurationImpl extends RemoteServiceServlet implements HBConfiguration {

	private static final long serialVersionUID = 4225855150934657854L;
	private ConfigurationRemote hbConf;
	
	public HBConfigurationImpl (){
		Context ctx;
		try {
			ctx = new InitialContext();
			hbConf = (ConfigurationRemote) ctx.lookup("HotelBossEAR/Configuration/remote");
		} catch (Exception e) {
			GWT.log("Error: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	public List<Price> get_pricelist() {
		return hbConf.get_pricelist();
	}

	public void store_pricelist(List<Price> list) {
		hbConf.store_pricelist(list);
	}
	
	public List<Reduction> get_reductions () {
		return hbConf.get_reductions();
	}
	
	public void store_reductions (List<Reduction> list) {
		hbConf.store_reductions(list);
	}

}
