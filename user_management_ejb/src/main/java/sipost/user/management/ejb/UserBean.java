package sipost.user.management.ejb;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import sipost.user.management.common.IUser;
import sipost.user.management.jpa.User;

@Stateless
public class UserBean implements IUser{
	@PersistenceContext(unitName = "UserManagement")
	private EntityManager oEntityManager;

	@Override
	public List<User> getAllUsers() {
		System.out.println("userBean getAllUser");
		@SuppressWarnings("unchecked")
		List<User> users = (List<User>)oEntityManager.createNamedQuery("User.findAll").getResultList();
		return users;
	}

	@Override
	public User getUserById(int id) {
		User u=oEntityManager.find(User.class, id);
		return u;
	}

	@Override
	public void insertUser(User user) {
		int n = ((Number) oEntityManager.createNamedQuery("User.countAll").getSingleResult()).intValue();
		user.setId(n+1);
		oEntityManager.persist(user);		
	}

	@Override
	public void deleteUser(int id) {
		User u = getUserById(id);
		oEntityManager.remove(u);	
		
	}

	@Override
	public void updateUser(User user) {
		oEntityManager.merge(user);		
	}

}
