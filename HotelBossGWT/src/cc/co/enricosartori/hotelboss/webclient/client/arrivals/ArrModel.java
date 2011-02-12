package cc.co.enricosartori.hotelboss.webclient.client.arrivals;

import java.util.List;

import cc.co.enricosartori.hotelboss.dto.Customer;
import cc.co.enricosartori.hotelboss.dto.Reduction;
import cc.co.enricosartori.hotelboss.webclient.client.services.HBConfiguration;
import cc.co.enricosartori.hotelboss.webclient.client.services.HBConfigurationAsync;
import cc.co.enricosartori.hotelboss.webclient.client.services.HBReception;
import cc.co.enricosartori.hotelboss.webclient.client.services.HBReceptionAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class ArrModel {
	private final HBConfigurationAsync hbconf = GWT.create(HBConfiguration.class);
	private final HBReceptionAsync hbrec = GWT.create(HBReception.class);
	
	public void get_reductions (AsyncCallback<List<Reduction>> call) {
		hbconf.get_reductions(call);
	}
	
	public void save_customer (Customer c, AsyncCallback<Boolean> call) {
		hbrec.store_customer(c, call);
	}
	
}
