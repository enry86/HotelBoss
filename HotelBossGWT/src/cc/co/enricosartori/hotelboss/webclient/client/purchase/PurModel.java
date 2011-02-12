package cc.co.enricosartori.hotelboss.webclient.client.purchase;

import java.util.List;

import cc.co.enricosartori.hotelboss.dto.Extra;
import cc.co.enricosartori.hotelboss.dto.Purchase;
import cc.co.enricosartori.hotelboss.webclient.client.services.HBConfiguration;
import cc.co.enricosartori.hotelboss.webclient.client.services.HBConfigurationAsync;
import cc.co.enricosartori.hotelboss.webclient.client.services.HBReception;
import cc.co.enricosartori.hotelboss.webclient.client.services.HBReceptionAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class PurModel {
	private HBReceptionAsync hbr = GWT.create(HBReception.class);
	private HBConfigurationAsync hbc = GWT.create(HBConfiguration.class);
	
	public void save_pur (Purchase p, AsyncCallback<Boolean> cb) {
		hbr.store_purchase(p, cb);
	}
	
	public void search_room (int room, AsyncCallback<List<Purchase>> cb) {
		hbr.get_pur_room(room, cb);
	}
	
	public void fetch_extras (AsyncCallback<List<Extra>> cb) {
		hbc.get_extras(cb);
	}
}
