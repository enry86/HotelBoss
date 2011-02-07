package cc.co.enricosartori.hotelboss.entities;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table (name="customer")
@NamedQueries ({
@NamedQuery (name="Customers_list",
		query="select c from CustomerEB c")
})
public class CustomerEB {
	public static final String CUST_LIST = "Customer_list";
	
	@Id
	private int room;
	private Date date_arr;
	private String name;
	private int people;
	private int treatment;
	private int discount;
	
	
	public int getRoom() {
		return room;
	}
	public void setRoom(int room) {
		this.room = room;
	}
	public Date getDate_arr() {
		return date_arr;
	}
	public void setDate_arr(Date dateArr) {
		date_arr = dateArr;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getPeople() {
		return people;
	}
	public void setPeople(int people) {
		this.people = people;
	}
	public int getTreatment() {
		return treatment;
	}
	public void setTreatment(int treatment) {
		this.treatment = treatment;
	}
	public int getDiscount() {
		return discount;
	}
	public void setDiscount(int discount) {
		this.discount = discount;
	}
}
