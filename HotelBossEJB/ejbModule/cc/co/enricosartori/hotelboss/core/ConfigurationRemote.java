package cc.co.enricosartori.hotelboss.core;
import java.util.List;

import javax.ejb.Remote;

import cc.co.enricosartori.hotelboss.dto.Price;

@Remote
public interface ConfigurationRemote {
	public List<Price> get_pricelist();
	public void store_pricelist(List<Price> pl);
}
