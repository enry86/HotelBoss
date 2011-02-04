package cc.co.enricosartori.hotelboss.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import cc.co.enricosartori.hotelboss.dto.User;
import cc.co.enricosartori.hotelboss.entities.UsersEB;

@Stateless
public class UserDAO implements UserDAOLocal {
	@PersistenceContext
	private EntityManager ent_man;
	

	@Override
	public List<User> get_users() {
		Query q = ent_man.createNamedQuery(UsersEB.USERS_LIST);
		List<UsersEB> l = q.getResultList();
		return convert_list (l);
	}	
	
	@Override
	public void delete_user(User user) {
		UsersEB u = ent_man.find(UsersEB.class, user.getUsername());
		ent_man.remove (u);
	}

	@Override
	public void insert_user(User user) {
		UsersEB u = get_EB (user);
		ent_man.persist (u);
		
	}

	@Override
	public void update_user(User user) {
		UsersEB u = get_EB (user);
		ent_man.merge (u);
	}
	
	public boolean check_user (String id) {
		UsersEB u = ent_man.find(UsersEB.class, id);
		return u.equals(null);
	}
	
	
	private List<User> convert_list (List<UsersEB> l) {
		Iterator<UsersEB> i = l.iterator();
		ArrayList<User> res = new ArrayList<User> (l.size());
		while (i.hasNext()) {
			res.add(get_DTO (i.next()));
		}
		return res;
	}
	
	
	private User get_DTO (UsersEB eb) {
		User u = new User ();
		u.setUsername(eb.getUsername());
		u.setPasswd(eb.getPasswd());
		u.setRole(eb.getRole());
		u.setName(eb.getName());
		u.setSurname(eb.getSurname());
		u.setEmail(eb.getEmail());
		return u;
	}
	
	
	private UsersEB get_EB (User dto) {
		UsersEB eb = new UsersEB ();
		eb.setUsername(dto.getUsername());
		eb.setPasswd(dto.getPasswd());
		eb.setRole(dto.getRole());
		eb.setName(dto.getName());
		eb.setSurname(dto.getSurname());
		eb.setEmail(dto.getEmail());
		return eb;
	}
	
	
	
	
}
