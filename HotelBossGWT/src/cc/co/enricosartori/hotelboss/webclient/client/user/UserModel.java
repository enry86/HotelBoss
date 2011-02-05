package cc.co.enricosartori.hotelboss.webclient.client.user;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import cc.co.enricosartori.hotelboss.dto.User;
import cc.co.enricosartori.hotelboss.webclient.client.services.HBConfiguration;
import cc.co.enricosartori.hotelboss.webclient.client.services.HBConfigurationAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class UserModel {
	private final HBConfigurationAsync hbconf = GWT.create(HBConfiguration.class);
	private enum UserState {NEW, UPDATED, DELETED, STORED};
	private HashMap<User, UserState> users = new HashMap<User, UserState> ();
	private User current;
	private UserState curr_state;
	
	public UserModel () {
		this.current = null;
		this.curr_state = null;
	}
	
	public void new_user () {
		User u = new User ();
		current = u;
		curr_state = UserState.NEW;
	}
	
	public void edit_user (User u, AsyncCallback<Boolean> call) {
		update_curr(u);
		if (curr_state != UserState.NEW) {
			curr_state = UserState.UPDATED;
		}
		save_user(call);
	}
	
	public void dele_user (AsyncCallback<Boolean> call) {
		if (curr_state != UserState.NEW) {
			curr_state = UserState.DELETED;
			users.put(current, UserState.DELETED);
			send_user (call);
		}
	}
	
	public void save_user (AsyncCallback<Boolean> call) {
		if (current != null) {
			users.put(current, curr_state);
			send_user (call);
		}
	}
	
	public void select_user (User u) {
		current = u;
		curr_state = users.get(u);
	}
	
	public void cancel () {
		this.current = null;
		this.curr_state = null;
	}
	
	private void update_curr (User u) {
		current.setUsername(u.getUsername());
		current.setPasswd(u.getPasswd());
		current.setRole(u.getRole());
		current.setName(u.getName());
		current.setSurname(u.getSurname());
		current.setEmail(u.getEmail());
	}
	
	public Iterator<User> get_iterator () {
		Set<User> s = users.keySet();
		Iterator<User> i = s.iterator();
		return i;
	}
	
	private void read_users (List<User> l) {
		if (l != null) {
			Iterator<User> i = l.iterator();
			while (i.hasNext()) {
				User tmp = (User) i.next();
				users.put(tmp, UserState.STORED);
			}
		}
	}
	
	private void send_user (final AsyncCallback<Boolean> call) {
		current.setStatus(curr_state.toString());
		hbconf.store_user(current, new AsyncCallback<Boolean> () {

			@Override
			public void onFailure(Throwable caught) {
				call.onFailure(caught);
			}

			@Override
			public void onSuccess(Boolean result) {
				call.onSuccess(result);
			}
		});
	}
	
	public void fetch_users (final AsyncCallback<Void> call) {
		hbconf.get_users (new AsyncCallback<List<User>> () {

			@Override
			public void onFailure(Throwable caught) {
				call.onFailure(caught);
			}

			@Override
			public void onSuccess(List<User> result) {
				users.clear();
				read_users (result);
				call.onSuccess(null);
			}
		});
	}
}
