package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the publications database table.
 * 
 */
@Entity
@Table(name="publications")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
public abstract class Publication implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int uuid;

	@Column(name="nr_of_copys")
	private int nrOfCopys;

	@Column(name="on_stock")
	private int onStock;

	@Temporal(TemporalType.DATE)
	@Column(name="publication_date")
	private Date publicationDate;

	private String title;

	private String type;

	//bi-directional many-to-one association to Borrow
	@OneToMany(mappedBy="publication")
	private List<Borrow> borrows;

	//bi-directional many-to-one association to Publisher
	@ManyToOne
	@JoinColumn(name="publisher_id")
	private Publisher publisher;

	

	public Publication() {
	}

	public int getUuid() {
		return this.uuid;
	}

	public void setUuid(int uuid) {
		this.uuid = uuid;
	}

	public int getNrOfCopys() {
		return this.nrOfCopys;
	}

	public void setNrOfCopys(int nrOfCopys) {
		this.nrOfCopys = nrOfCopys;
	}

	public int getOnStock() {
		return this.onStock;
	}

	public void setOnStock(int onStock) {
		this.onStock = onStock;
	}

	public Date getPublicationDate() {
		return this.publicationDate;
	}

	public void setPublicationDate(Date publicationDate) {
		this.publicationDate = publicationDate;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<Borrow> getBorrows() {
		return this.borrows;
	}

	public void setBorrows(List<Borrow> borrows) {
		this.borrows = borrows;
	}

	public Borrow addBorrow(Borrow borrow) {
		getBorrows().add(borrow);
		borrow.setPublication(this);

		return borrow;
	}

	public Borrow removeBorrow(Borrow borrow) {
		getBorrows().remove(borrow);
		borrow.setPublication(null);

		return borrow;
	}

	public Publisher getPublisher() {
		return this.publisher;
	}

	public void setPublisher(Publisher publisher) {
		this.publisher = publisher;
	}

}