package cc.co.enricosartori.hotelboss.webclient.client.extra;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import cc.co.enricosartori.hotelboss.dto.Extra;
import cc.co.enricosartori.hotelboss.webclient.client.services.HBConfiguration;
import cc.co.enricosartori.hotelboss.webclient.client.services.HBConfigurationAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class ExtraModel {
	private final HBConfigurationAsync hbconf = GWT.create(HBConfiguration.class);
	private enum ExtraState {NEW, UPDATED, DELETED, STORED};
	private HashMap<Extra, ExtraState> extras = new HashMap<Extra, ExtraState> ();
	private Extra current;
	private ExtraState curr_state;
	
	public ExtraModel () {
		current = null;
		curr_state = null;
	}
	
	public void new_extra () {
		Extra e = new Extra ();
		current = e;
		curr_state = ExtraState.NEW;
	}
	
	public void edit_extra (Extra e, AsyncCallback<Void> call) {
		update_curr (e);
		if (curr_state != ExtraState.NEW) {
			curr_state = ExtraState.UPDATED;
		}
		save_extra (call);
	}
	
	public void dele_extra (AsyncCallback<Void> call) {
		extras.put(current, ExtraState.DELETED);
		store_extras (call);
	}
	
	public void select_extra (Extra e) {
		current = e;
		curr_state = extras.get(e);
	}
	
	private void save_extra (AsyncCallback<Void> call) {
		if (current != null) {
			extras.put (current, curr_state);
			store_extras (call);
		}
	}
	
	public void cancel () {
		this.current = null;
		this.curr_state = null;
	}
	
	private void store_extras (AsyncCallback<Void> call) {
		Iterator<Extra> i = get_iterator ();
		ArrayList<Extra> l = new ArrayList<Extra> (extras.size());
		while (i.hasNext ()) {
			Extra e = i.next ();
			e.setStatus (extras.get(e).toString());
			l.add (e);
		}
		send_extras (l, call);
	}
	
	private void update_curr (Extra e) {
		current.setName(e.getName());
		current.setPrice(e.getPrice ());
	}
	
	public Iterator<Extra> get_iterator () {
		Set<Extra> s = extras.keySet();
		Iterator<Extra> i = s.iterator();
		return i;
	}
	
	private void read_extras (List<Extra> list) {
		if (list != null) {
			Iterator<Extra> i = list.iterator();
			while (i.hasNext()) {
				Extra tmp = (Extra) i.next();
				extras.put(tmp, ExtraState.STORED);
			}
		}
	}
	
	private void send_extras (List<Extra> list, final AsyncCallback<Void> call) {
		hbconf.store_extras (list, new AsyncCallback<Void> () {
			public void onFailure(Throwable caught) {
				call.onFailure(caught);
			}

			public void onSuccess(Void result) {
				call.onSuccess(result);
			}
		});
	}
	
	public void fetch_extras (final AsyncCallback<Void> call) {
		hbconf.get_extras (new AsyncCallback<List<Extra>> () {
			public void onFailure(Throwable caught) {
				call.onFailure(caught);
			}

			public void onSuccess(List<Extra> result) {
				extras.clear();
				read_extras(result);
				call.onSuccess(null);
			}
		});
	}
	
}
