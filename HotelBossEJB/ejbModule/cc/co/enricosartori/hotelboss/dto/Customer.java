package cc.co.enricosartori.hotelboss.dto;

import java.io.Serializable;
import java.sql.Date;

public class Customer implements Serializable {

	private static final long serialVersionUID = -5438994452463814877L;
	private int room;
	private Date date_arr;
	private String name;
	private int people;
	private int treatment;
	private int discount;
	private String status;
	
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
	
}
