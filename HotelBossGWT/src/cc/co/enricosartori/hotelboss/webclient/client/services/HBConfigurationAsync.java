package cc.co.enricosartori.hotelboss.webclient.client.services;

import java.util.List;

import cc.co.enricosartori.hotelboss.dto.Extra;
import cc.co.enricosartori.hotelboss.dto.Price;
import cc.co.enricosartori.hotelboss.dto.Reduction;
import cc.co.enricosartori.hotelboss.dto.User;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface HBConfigurationAsync {
	void get_pricelist (AsyncCallback<List<Price>> callback);
	void store_pricelist (List<Price> list, AsyncCallback<Void> callback);
	
	void get_reductions (AsyncCallback<List<Reduction>> callback);
	void store_reductions (List<Reduction> list, AsyncCallback<Void> callback);
	
	void get_extras (AsyncCallback<List<Extra>> callback);
	void store_extras (List<Extra> list, AsyncCallback<Void> callback);
	
	void get_users (AsyncCallback<List<User>> callback);
	void store_user (User user, AsyncCallback<Boolean> callback);
}
