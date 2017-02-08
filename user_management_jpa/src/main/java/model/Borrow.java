package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the borrows database table.
 * 
 */
@Entity
@Table(name="borrows")
@NamedQuery(name="Borrow.findAll", query="SELECT b FROM Borrow b")
public class Borrow implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int uuid;

	@Temporal(TemporalType.DATE)
	@Column(name="borrow_from")
	private Date borrowFrom;

	@Temporal(TemporalType.DATE)
	@Column(name="borrow_until")
	private Date borrowUntil;

	//bi-directional many-to-one association to Publication
	@ManyToOne
	@JoinColumn(name="publication_id")
	private Publication publication;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;

	public Borrow() {
	}

	public int getUuid() {
		return this.uuid;
	}

	public void setUuid(int uuid) {
		this.uuid = uuid;
	}

	public Date getBorrowFrom() {
		return this.borrowFrom;
	}

	public void setBorrowFrom(Date borrowFrom) {
		this.borrowFrom = borrowFrom;
	}

	public Date getBorrowUntil() {
		return this.borrowUntil;
	}

	public void setBorrowUntil(Date borrowUntil) {
		this.borrowUntil = borrowUntil;
	}

	public Publication getPublication() {
		return this.publication;
	}

	public void setPublication(Publication publication) {
		this.publication = publication;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}