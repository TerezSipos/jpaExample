package sipost.user.management.common;

import java.util.List;

import sipost.user.management.jpa.User;

public interface IUser {
	public static final String jndiNAME = "java:global/user_management_ear-0.0.1-SNAPSHOT/user_management_ejb-0.0.1-SNAPSHOT/UserBean";

	List<User> getAllUsers();

	User getUserById(int id);

	void insertUser(User user);

	void deleteUser(int id);

	void updateUser(User user);

}
