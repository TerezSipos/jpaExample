package sipost.user.management.web;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import sipost.user.management.common.IUser;
import sipost.user.management.jpa.Role;
import sipost.user.management.jpa.User;

@Named("userBean")
@ApplicationScoped
public class UserManagedBean implements IUser {
	private IUser oUserBean = null;
	private User user;
	private int selectedUserid;
	private List<Integer> rolesId = new ArrayList<>();
	private Exception exeptionMessage;

	private IUser getUserBean() {
		if (oUserBean == null) {
			try {
				InitialContext jndi = new InitialContext();
				oUserBean = (IUser) jndi.lookup(IUser.jndiNAME);
			} catch (NamingException e) {
				exeptionMessage = e;
				e.printStackTrace();
			}
		}
		return oUserBean;
	}

	@Override
	public List<User> getAllUsers() {
		return getUserBean().getAllUsers();
	}

	@Override
	public User getUserById(int id) {
		if (id != 0) {
			user = getUserBean().getUserById(id);
		}
		try {
			return user;
		} catch (Exception e) {
			exeptionMessage = e;
			throw e;
		}
	}

	@Override
	public void insertUser(User user) {
		try{
		getUserBean().insertUser(user);
		}catch (Exception e) {
			exeptionMessage=e;
			throw e;
		}
	}

	@Override
	public void deleteUser(int id) {
		getUserBean().deleteUser(id);
		selectedUserid = 0;
		user = null;

	}

	@Override
	public void updateUser(User p_user) {
		getUserBean().updateUser(p_user);
		selectedUserid = 0;
	}

	public int getSelectedUserid() {
		return selectedUserid;
	}

	public void setSelectedUserid(int selectedUserid) {
		this.selectedUserid = selectedUserid;
	}

	public User getUser() {
		if (user == null)
			user = new User();
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Integer> getRolesId() {
		if (rolesId.isEmpty() && user != null && user.getRoles() != null) {
			for (Role r : user.getRoles()) {
				rolesId.add(r.getId());
			}
		}
		return rolesId;
	}

	public void setRolesId(List<Integer> roles) {
		if (!roles.isEmpty()) {
			List<Role> userRoles = new ArrayList<>();
			for (int role : roles) {
				Role userRole = new Role();
				userRole.setId(role);
				userRoles.add(userRole);
			}
			getUser().setRoles(userRoles);
		}
	}

}
