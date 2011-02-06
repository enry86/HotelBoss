package cc.co.enricosartori.hotelboss.dto;

import java.io.Serializable;
import java.sql.Date;

public class Reservation implements Serializable {

	private static final long serialVersionUID = -6964491813969053247L;
	private int id;
	private Date date_arr;
	private Date date_dep;
	private String room;
	private String customer;
	private String note;
	private String status;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getDate_arr() {
		return date_arr;
	}
	public void setDate_arr(Date dateArr) {
		date_arr = dateArr;
	}
	public Date getDate_dep() {
		return date_dep;
	}
	public void setDate_dep(Date dateDep) {
		date_dep = dateDep;
	}
	public String getRoom() {
		return room;
	}
	public void setRoom(String room) {
		this.room = room;
	}
	public String getCustomer() {
		return customer;
	}
	public void setCustomer(String customer) {
		this.customer = customer;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
	
}
