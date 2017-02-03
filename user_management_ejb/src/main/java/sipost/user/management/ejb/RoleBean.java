package sipost.user.management.ejb;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import org.jboss.logging.Logger;

import sipost.user.management.common.IRole;
import sipost.user.management.jpa.Role;

@Stateless
public class RoleBean implements IRole {
	@PersistenceContext(unitName = "UserManagement")
	private EntityManager oEntityManager;
	private Logger oLogger = Logger.getLogger(RoleBean.class);

	@Override
	public List<Role> getAllRoles() {
		try {
			@SuppressWarnings("unchecked")
			List<Role> roles = (List<Role>) oEntityManager.createNamedQuery("Role.findAll").getResultList();
			return roles;
		} catch (PersistenceException e) {
			oLogger.error(e.getMessage());
			return new ArrayList<>();
		}
	}

	@Override
	public Role getRoleById(int id) {
		try {
			return oEntityManager.find(Role.class, id);
		} catch (PersistenceException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void insertRole(Role role) {
		try {
			int n = ((Number) oEntityManager.createNamedQuery("Role.maxId").getSingleResult()).intValue();
			role.setId(n + 1);
			oEntityManager.persist(role);
			oEntityManager.flush();
		} catch (PersistenceException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deleteRole(int id) {
		try {
			Role r = oEntityManager.find(Role.class, id);
			oEntityManager.remove(r);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void updateRole(Role role) {
		try {
			Role r = oEntityManager.find(Role.class, role.getId());
			if (r != null) {
				oEntityManager.merge(role);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
