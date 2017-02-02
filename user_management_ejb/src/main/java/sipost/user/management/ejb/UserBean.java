package sipost.user.management.ejb;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import sipost.user.management.common.IUser;
import sipost.user.management.jpa.User;

@Stateless
public class UserBean implements IUser{
	@PersistenceContext(unitName = "UserManagement")
	private EntityManager oEntityManager;

	@Override
	public List<User> getAllUsers() {
		@SuppressWarnings("unchecked")
		List<User> users = (List<User>)oEntityManager.createNamedQuery("User.findAll").getResultList();
		return users;
	}

	@Override
	public User getUserById(int id) {
		try{
		User u=oEntityManager.find(User.class, id);
		return u;
		}catch (PersistenceException e) {
			e.printStackTrace();
			return null;
		}		
	}

	@Override
	public void insertUser(User user) {
		int n = ((Number) oEntityManager.createNamedQuery("User.maxId").getSingleResult()).intValue();
		user.setId(n+1);
		oEntityManager.persist(user);	
		oEntityManager.flush();
	}

	@Override
	public void deleteUser(int id) {
		oEntityManager.clear();
		User u = getUserById(id);
		oEntityManager.remove(u);
		oEntityManager.flush();		
	}

	@Override
	public void updateUser(User user) {
		try{
		oEntityManager.merge(user);		
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

}
