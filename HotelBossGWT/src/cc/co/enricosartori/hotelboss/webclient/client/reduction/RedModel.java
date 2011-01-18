package cc.co.enricosartori.hotelboss.webclient.client.reduction;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import cc.co.enricosartori.hotelboss.dto.Reduction;
import cc.co.enricosartori.hotelboss.webclient.client.services.HBConfiguration;
import cc.co.enricosartori.hotelboss.webclient.client.services.HBConfigurationAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class RedModel {
	private final HBConfigurationAsync hbconf = GWT.create(HBConfiguration.class);
	private enum RedState {NEW, UPDATED, DELETED, STORED};
	private HashMap<Reduction, RedState> reductions = new HashMap<Reduction, RedState>();
	private Reduction current;
	private RedState curr_state;
	
	public RedModel () {
		current = null;
		curr_state = null;
	}
	
	private void read_reductions (List<Reduction> red) {
		Iterator<Reduction> i = red.iterator();
		while (i.hasNext()) {
			Reduction tmp = i.next();
			reductions.put(tmp, RedState.STORED);
		}
	}
	
	
	public Iterator<Reduction> get_iterator () {
		Set<Reduction> s = reductions.keySet();
		Iterator<Reduction> i = s.iterator();
		return i;
	}
	
	
	public void fetch_reductions (final AsyncCallback<Void> callback) {
		hbconf.get_reductions ( 
				new AsyncCallback<List<Reduction>>() {
					public void onFailure(Throwable caught) {
						callback.onFailure(caught);
					}

					public void onSuccess(List<Reduction> result) {
						reductions.clear();
						read_reductions (result);
						callback.onSuccess(null);
					}
				});
	}
	
	
	public void select_red (Reduction r) {
		current = r;
		curr_state = reductions.get(r);
	}
	
	
	public void new_red () {
		
	}
	
	
	public void save_red () {
		
	}
	
	
	public void dele_red () {
		
	}
	
	
	public void cancel () {
		
	}
	
}
