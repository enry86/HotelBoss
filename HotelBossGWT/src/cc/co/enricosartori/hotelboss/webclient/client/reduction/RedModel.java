package cc.co.enricosartori.hotelboss.webclient.client.reduction;

import java.util.ArrayList;
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
	
	
	private void send_reductions (List<Reduction> lr, final AsyncCallback<Void> callback) {
		hbconf.store_reductions(lr, 
				new AsyncCallback<Void> () {
					public void onFailure(Throwable caught) {
						callback.onFailure(caught);
					}

					public void onSuccess(Void result) {
						callback.onSuccess(result);
					}
				});
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
	
	
	public void store_reductions (final AsyncCallback<Void> callback) {
		Iterator<Reduction> i = get_iterator ();
		ArrayList<Reduction> l = new ArrayList<Reduction> ();
		while (i.hasNext()) {
			Reduction r = i.next();
			r.setState(reductions.get(r).toString());
			l.add(r);
		}
		send_reductions (l, callback);
	}
	
	
	public void select_red (Reduction r) {
		current = r;
		curr_state = reductions.get(r);
	}
	
	
	public void new_red () {
		Reduction r = new Reduction ();
		current = r;
		curr_state = RedState.NEW;
	}
	
	
	public void save_red (AsyncCallback<Void> call) {
		if (current != null) {
			reductions.put(current, curr_state);
			store_reductions (call);
		}
	}
	
	
	public void edit_price (Reduction r, AsyncCallback<Void> call) {
		update_curr (r);
		if (curr_state != RedState.NEW) {
			curr_state = RedState.UPDATED;
		}
		save_red (call);
	}
	
	
	public void dele_red (AsyncCallback<Void> call) {
		reductions.put(current, RedState.DELETED);
		store_reductions (call);
	}
	
	
	public void cancel () {
		current = null;
		curr_state = null;
	}
	
	
	private void update_curr (Reduction r) {
		current.setDescr(r.getDescr());
		current.setVal(r.getVal());
		current.setRed_type(r.getRed_type());
		current.setPerc(r.isPerc());
	}
	
}
