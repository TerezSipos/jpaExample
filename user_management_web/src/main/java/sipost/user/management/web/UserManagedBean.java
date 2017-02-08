package sipost.user.management.web;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.jboss.logging.Logger;

import sipost.user.management.common.IUser;
import sipost.user.management.ejb.EjbExeption;
import sipost.user.management.jpa.Role;
import sipost.user.management.jpa.User;

@Named("userBean")
@SessionScoped
public class UserManagedBean implements Serializable, IUser {

	private static final long serialVersionUID = 343694639300783486L;

	private int loginType;
	private String loggedInUser;
	private Logger oLogger = Logger.getLogger(UserManagedBean.class);
	private IUser oUserBean = null;
	private User user = null;
	private int selectedUserid;
	private List<Integer> rolesId = new ArrayList<>();
	private String errorMessage;
	private List<User> search = new ArrayList<>();
	private String searchMessage;

	private IUser getUserBean() {
		errorMessage = null;
		if (oUserBean == null) {
			try {
				InitialContext jndi = new InitialContext();
				oUserBean = (IUser) jndi.lookup(IUser.jndiNAME);
			} catch (NamingException e) {
				errorMessage = e.getMessage();
				oLogger.error(errorMessage);
				throw new WebExeption(errorMessage);
			}
		}
		return oUserBean;
	}

	@Override
	public List<User> getAllUsers() {
		errorMessage = null;
		try {
			return getUserBean().getAllUsers();
		} catch (EjbExeption e) {
			errorMessage = e.getMessage();
			throw new WebExeption(e.getMessage());
		}
	}

	@Override
	public User getUserById(int id) {
		errorMessage = null;
		try {
			if (id != 0) {
				user = getUserBean().getUserById(id);
				return user;
			} else {
				errorMessage = "No selected user.";
				throw new WebExeption(errorMessage);
			}
		} catch (EjbExeption e) {
			errorMessage = e.getMessage();
			throw new WebExeption(e.getMessage());
		}
	}

	@Override
	public void insertUser(User user) throws WebExeption {
		errorMessage = null;
		oLogger.info("--------------new User:" + user);
		try {
			validateString(user.getUsername());
			if (user.getRoles() != null) {
				getUserBean().insertUser(user);
			} else {
				errorMessage = "select at least one role";
				throw new WebExeption(errorMessage);
			}
		} catch (EjbExeption e) {
			errorMessage = e.getMessage();
			throw new WebExeption(e.getMessage());
		}
	}

	@Override
	public void deleteUser(int id) {
		errorMessage = null;
		if (id == 0) {
			errorMessage = "No selected item";
			throw new WebExeption(errorMessage);
		}
		try {
			getUserBean().deleteUser(id);
			selectedUserid = 0;
			user = null;
		} catch (EjbExeption e) {
			errorMessage = e.getMessage();
			throw new WebExeption(e.getMessage());
		}
	}

	@Override
	public void updateUser(User p_user) {
		errorMessage = null;
		try {
			selectedUserid = 0;
			getUserBean().updateUser(p_user);
			getAllUsers();
		} catch (EjbExeption e) {
			errorMessage = e.getMessage();
			throw new WebExeption(e.getMessage());
		}
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
		rolesId = new ArrayList<>();
		if (user != null && user.getRoles() != null) {
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

	public String getErrorMessage() {
		return errorMessage;
	}

	@Override
	public List<User> searchUser(String name) {
		searchMessage = null;
		try {
			if (name == null || name.length() < 3) {
				searchMessage = "Name must be at least 3 caracter.";
				search = new ArrayList<>();
				return null;
			}
			search = getUserBean().searchUser(name);
			if (search.isEmpty()) {
				searchMessage = "Can't find any user with specifield name.";
				return null;
			}
			return search;
		} catch (EjbExeption e) {
			errorMessage = e.getMessage();
			throw new WebExeption(e.getMessage());
		}
	}

	public List<User> getSearch() {
		oLogger.info("++++++++" + search.size());
		return search;
	}

	@Override
	public boolean login(String username, int role) {
		searchMessage=null;
		errorMessage=null;
		search=new ArrayList<>();
		
		validateString(username);
		oLogger.info(username + "------------" + role);
		try {
			if (getUserBean().login(username, role)) {
				loginType = role;
				loggedInUser = username;
				return true;
			}
			errorMessage = "Invalid username";
			throw new WebExeption(errorMessage);
		} catch (EjbExeption e) {
			errorMessage = e.getMessage();
			throw new WebExeption(errorMessage);
		}
	}

	public boolean login(String username) {
		validateString(username);
		try {
			if (login(username, 1)) {// admin
				loginType = 1;
				loggedInUser = username;
				return true;
			}
		} catch (WebExeption e) {
			oLogger.info(e.getMessage());
		}
		try {
			if (login(username, 2)) {// user
				loggedInUser = username;
				loginType = 2;
				return true;
			}
		} catch (WebExeption e) {
			oLogger.info(e.getMessage());
		}
		errorMessage = "Can't login as User or Admin";
		throw new WebExeption(errorMessage);
	}

	public void logout() {
		loginType = 0;
		loggedInUser = null;
	}

	public String isLoggedIn() {
		if (loginType == 0) {
			oLogger.warn("-------not loggedIn----------");
			return "index.xhtml";
		} else {
			oLogger.warn("logged in as " + loginType + "-------------");
			return null;
		}
	}

	public String isAdmin() {
		if (loginType == 1) {// admin
			oLogger.info("---------------" + loggedInUser);
			return null;
		} else {
			oLogger.info("--------------- not admin");
			return "filter.xhtml";
		}
	}

	public String getLoggedInUser() {
		return loggedInUser;
	}

	private boolean validateString(String value) {
		if (value == null || value.length() < 3) {
			errorMessage = "Name must be at least 3 caracter.";
			throw new WebExeption(errorMessage);
		}
		return true;
	}

	public String getSearchMessage() {
		return searchMessage;
	}

}
