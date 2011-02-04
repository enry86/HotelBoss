package cc.co.enricosartori.hotelboss.dao;

import java.util.List;

import cc.co.enricosartori.hotelboss.dto.User;

public interface UserDAOLocal {
	public List<User> get_users ();
	public void insert_user (User user);
	public void update_user (User user);
	public void delete_user (User user);
	public boolean check_user (String id);
}
