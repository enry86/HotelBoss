package cc.co.enricosartori.hotelboss.webclient.client.services;

import java.util.List;

import cc.co.enricosartori.hotelboss.dto.Price;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("hbConfiguration")
public interface HBConfiguration extends RemoteService{
	public List<Price> get_pricelist();
	public void store_pricelist(List<Price> list);
}
