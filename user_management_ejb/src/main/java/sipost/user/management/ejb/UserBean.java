package sipost.user.management.ejb;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import org.jboss.logging.Logger;

import sipost.user.management.common.IUser;
import sipost.user.management.jpa.User;

@Stateless
public class UserBean implements IUser {
	@PersistenceContext(unitName = "UserManagement")
	private EntityManager oEntityManager;
	private Logger oLogger = Logger.getLogger(UserBean.class);

	@Override
	public List<User> getAllUsers() {
		try {
			@SuppressWarnings("unchecked")
			List<User> users = (List<User>) oEntityManager.createNamedQuery("User.findAll").getResultList();
			return users;
		} catch (PersistenceException e) {
			oLogger.error(e);
			EjbExeption.getCause(e);
			throw new EjbExeption("Can't find users", e);
		}
	}

	@Override
	public User getUserById(int id) {
		try {
			User u = oEntityManager.find(User.class, id);
			return u;
		} catch (PersistenceException e) {
			oLogger.error(e);
			EjbExeption.getCause(e);
			throw new EjbExeption("Can't find user with specifiel id: <" + id + ">", e);
		}
	}

	@Override
	public void insertUser(User user) {
		try {
			int n = ((Number) oEntityManager.createNamedQuery("User.maxId").getSingleResult()).intValue();
			user.setId(n + 1);
			oEntityManager.persist(user);
			oEntityManager.flush();
		} catch (PersistenceException e) {	
			oLogger.error(e);
			EjbExeption.getCause(e);
			throw new EjbExeption("--------Could not insert user.", e);
		}
	}

	@Override
	public void deleteUser(int id) {
		try {
			oEntityManager.clear();
			User u = getUserById(id);
			oEntityManager.remove(u);
			oEntityManager.flush();
		} catch (PersistenceException e) {
			oLogger.error(e);
			EjbExeption.getCause(e);
			throw new EjbExeption("Can't delete user with specifield id: <" + id + ">", e);
		}
	}

	@Override
	public void updateUser(User user) {
		try {
			oEntityManager.merge(user);
			oEntityManager.flush();
		} catch (PersistenceException e) {
			oLogger.error(e);
			EjbExeption.getCause(e);
			throw new EjbExeption("Can't update user with specifield id: <" + user.getId() + ">", e);
		
		}
	}


}
