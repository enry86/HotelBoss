package cc.co.enricosartori.hotelboss.core;

import java.util.List;
import javax.ejb.Stateless;
import cc.co.enricosartori.hotelboss.dto.Price;

/**
 * Session Bean implementation class Configuration
 */
@Stateless
public class Configuration implements ConfigurationRemote {

    /**
     * Default constructor. 
     */
    public Configuration() {
        // TODO Auto-generated constructor stub
    }

	@Override
	public List<Price> get_pricelist() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void store_pricelist(List<Price> pl) {
		
	}
}
