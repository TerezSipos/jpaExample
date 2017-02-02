package sipost.user.management.jpa;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the roles database table.
 * 
 */
@Entity
@Table(name = "roles")

@NamedQueries({@NamedQuery(name = "Role.findAll", query = "SELECT r FROM Role r"),
	@NamedQuery(name = "Role.maxId", query = "SELECT max(r.id) FROM Role r")})
public class Role implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	private String role;

	public Role() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRole() {
		return this.role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "Role:" + role;
	}

}