package cc.co.enricosartori.hotelboss.webclient.client.services;

import java.util.List;
import cc.co.enricosartori.hotelboss.dto.Price;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface HBConfigurationAsync {
	void get_pricelist (AsyncCallback<List<Price>> callback);
	void store_pricelist (List<Price> list, AsyncCallback<Void> callback);
}
