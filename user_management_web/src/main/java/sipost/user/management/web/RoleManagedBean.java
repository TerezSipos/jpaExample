package sipost.user.management.web;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import sipost.user.management.common.IRole;
import sipost.user.management.jpa.Role;
import org.jboss.logging.Logger;

@Named("roleBean")
@ApplicationScoped
public class RoleManagedBean implements IRole {
	private IRole oRoleBean;
	private List<Role> roles=new ArrayList<>();
	private Role oRole;
	private int selectedRoleid=0;
	private Logger oLogger = Logger.getLogger(RoleManagedBean.class);

	
	private IRole getRoleBean() {
		if (oRoleBean == null) {
			try {
				InitialContext jndi = new InitialContext();
				oRoleBean = (IRole) jndi.lookup(IRole.jndiNAME);
			} catch (NamingException e) {				
				oLogger.error(e.getMessage());
			} 
		}
		return oRoleBean;
	}
	@Override
	public List<Role> getAllRoles() {
		roles= getRoleBean().getAllRoles();
		return roles;
	}

	@Override
	public Role getRoleById(int id) {
		oRole=getRoleBean().getRoleById(id);
		System.out.println("-------------"+id);
		System.out.println("-------------"+oRole);
		return oRole;
	}

	@Override
	public void insertRole(Role role) {
		getRoleBean().insertRole(role);
		
	}

	@Override
	public void deleteRole(int id) {
		getRoleBean().deleteRole(id);
		oRole=null;
		
	}

	@Override
	public void updateRole(Role role) {
		getRoleBean().updateRole(role);		
		oRole=null;
	}
	
	public List<Role> getRoles() {
		return roles;
	}
	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	public Role getoRole() {
		return oRole;
	}
	public Role newRole() {
		oRole=new Role();
		return oRole;
	}
	public void setoRole(Role oRole) {
		this.oRole = oRole;
	}
	public int getSelectedRoleid() {
		return selectedRoleid;
	}
	public void setSelectedRoleid(int selectedRoleid) {
		this.selectedRoleid = selectedRoleid;
	}

}
