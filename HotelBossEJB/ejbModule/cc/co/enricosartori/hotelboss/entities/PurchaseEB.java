package cc.co.enricosartori.hotelboss.entities;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table (name="purchase")
@NamedQueries ({
	@NamedQuery (name="Purchases_room",
			query="select p from PurchaseEB p where p.room = :room")
})
public class PurchaseEB {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	private int room;
	private Date date;
	private int qty;
	private int extra_id;

	
	public static final String PURCHASES_ROOM = "Purchases_room";

	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getRoom() {
		return room;
	}
	public void setRoom(int room) {
		this.room = room;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public int getQty() {
		return qty;
	}
	public void setQty(int qty) {
		this.qty = qty;
	}
	public int getExtra_id() {
		return extra_id;
	}
	public void setExtra_id(int extraId) {
		extra_id = extraId;
	}
	
	
}
