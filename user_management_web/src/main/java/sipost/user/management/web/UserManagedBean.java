package sipost.user.management.web;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import sipost.user.management.common.IUser;
import sipost.user.management.jpa.User;

@Named("userBean")
@ApplicationScoped
public class UserManagedBean implements IUser {
	private String message="almafa";
	private IUser oUserBean=null;
	private List<User> users=null;

	private IUser getUserBean() {
		if (oUserBean == null) {
			try {
				InitialContext jndi = new InitialContext();
				oUserBean = (IUser) jndi.lookup(IUser.jndiNAME);
			} catch (NamingException e) {
				System.out.println("++++++++++++++++++++++++JNDI LOOKUP PROBLEMS+++++++++++++++");
				e.printStackTrace();
			}
			catch (Exception e) {
				System.out.println("++++++++++++++++++++++++PROBLEMS+++++++++++++++");
				e.printStackTrace();
				System.out.println("++++++++++++++++++++++++PROBLEMS ending+++++++++++++++");
			 throw e;
			}
		}
		System.out.println("getEchoBean");
		return oUserBean;
	}
	@Override
	public List<User> getAllUsers() {
		System.out.println("getAllUsers");
		//users=getEchoBean().getAllUsers();
		return getUserBean().getAllUsers();
	}

	@Override
	public User getUserById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void insertUser(User user) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteUser(int id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateUser(User user) {
		// TODO Auto-generated method stub
		
	}
	public String getMessage() {
		System.out.println("++++++++++++++++++getMessage+++++++++++++++++");
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

}
