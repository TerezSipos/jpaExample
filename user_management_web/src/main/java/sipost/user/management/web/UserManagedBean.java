package sipost.user.management.web;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.jboss.logging.Logger;

import sipost.user.management.common.IUser;
import sipost.user.management.ejb.EjbExeption;
import sipost.user.management.jpa.Role;
import sipost.user.management.jpa.User;

@Named("userBean")
@ApplicationScoped
public class UserManagedBean implements IUser {
	private String loginType;
	private Logger oLogger = Logger.getLogger(UserManagedBean.class);
	private IUser oUserBean = null;
	private User user = null;
	private int selectedUserid;
	private List<Integer> rolesId = new ArrayList<>();
	private List<User> search=new ArrayList<>();
	private String errorMessage;

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
			}
			else {
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
		try {
			getUserBean().insertUser(user);
		} catch (EjbExeption e) {
			errorMessage = e.getMessage();
			throw new WebExeption(e.getMessage());
		}
	}

	@Override
	public void deleteUser(int id) {
		errorMessage = null;
		if(id==0){
			errorMessage ="No selected item";
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
		rolesId=new ArrayList<>();
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
		try{
			if(name.length()<3){
				errorMessage="Name must be at least 3 caracter.";
				return search;
			}
			search=getUserBean().searchUser(name);
			if(search.isEmpty()){
				errorMessage="Can't find any user with specifield name.";
			}
			return search;
		}catch (EjbExeption e) {
			errorMessage = e.getMessage();
			throw new WebExeption(e.getMessage());
		}
	}

	public List<User> getSearch() {
		if(search.isEmpty()){
			search=getAllUsers();
		}
		return search;
	}

	@Override
	public boolean login(String username, String role) {
		oLogger.info(username+ "------------"+role);
		try{
			if(getUserBean().login(username, role)){
				loginType=role;
				return true;
			}
			errorMessage ="Invalid username";
		}catch (EjbExeption e) {
			errorMessage = e.getMessage();
		}
		return false;
	}
	
	public String isLoggedIn(){
		if(loginType==null){
			oLogger.warn("-------not loggedIn----------");
			return "filter.xhtml";
		}else {
			oLogger.warn("logged in as " + loginType + "-------------");
			return null;
		}		
	}
	
	

}
