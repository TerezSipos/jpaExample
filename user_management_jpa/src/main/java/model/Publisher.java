package model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the publishers database table.
 * 
 */
@Entity
@Table(name = "publishers")
@NamedQuery(name = "Publisher.findAll", query = "SELECT p FROM Publisher p")
public class Publisher implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int uuid;

	private String name;

	public Publisher() {
	}

	public int getUuid() {
		return this.uuid;
	}

	public void setUuid(int uuid) {
		this.uuid = uuid;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

}