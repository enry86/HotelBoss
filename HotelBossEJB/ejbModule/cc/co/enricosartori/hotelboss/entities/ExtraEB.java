package cc.co.enricosartori.hotelboss.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;


@Entity
@NamedQueries ({
		@NamedQuery (name="Extras",
				query="select extra from ExtraEB extra order by extra.id")
	})
public class ExtraEB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7753336838504271625L;
	public static final String EXTRAS = "Extras";
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	private String name;
	private float price;

	
	public int getId() {
		return id;
	}
	
	
	public void setId(int id) {
		this.id = id;
	}
	
	
	public String getName() {
		return name;
	}
	
	
	public void setName(String name) {
		this.name = name;
	}
	
	
	public float getPrice() {
		return price;
	}
	
	
	public void setPrice(float price) {
		this.price = price;
	}
}

