package sipost.user.management.common;

import java.util.List;

import sipost.user.management.jpa.User;

public interface IUser {
	
	List<User> getAllUsers();
	User getUserById(int id);
	void insertUser(User user);
	void deleteUser(int id);
	void updateUser(User user);
	

}
