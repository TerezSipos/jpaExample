package sipost.user.management.common;

import java.util.List;

import sipost.user.management.jpa.Role;

public interface IRole {
	public static final String jndiNAME = "java:global/user_management_ear-0.0.1-SNAPSHOT/user_management_ejb-0.0.1-SNAPSHOT/RoleBean";

	
	List<Role> getAllRoles();
	Role getRoleById(int id);
	void insertRole(Role role);
	void deleteRole(int id);
	void updateRole(Role role);
	

}
