package sipost.user.management.common;

import java.util.List;

import sipost.user.management.jpa.Role;

public interface IRole {
	
	List<Role> getAllRoles();
	Role getRoleById(int id);
	void insertRole(Role role);
	void deleteRole(int id);
	void updateRole(Role role);
	

}
