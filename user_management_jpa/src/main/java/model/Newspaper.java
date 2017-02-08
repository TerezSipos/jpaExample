package model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("Newspaper")
public class Newspaper extends Publication {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7358930538078727479L;

}
