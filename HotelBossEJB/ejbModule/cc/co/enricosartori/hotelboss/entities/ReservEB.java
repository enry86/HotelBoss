package cc.co.enricosartori.hotelboss.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


@Entity
@Table(name="reservation")
@NamedQueries ({
@NamedQuery(name="Reserv_datearr_room",
		query="select r from ReservEB r order by r.date_arr, r.room"),
@NamedQuery(name="Reserv_check",
		query="select r from ReservEB r where r.room = :room and r.date_arr <= :date_arr and r.date_dep > :date_arr")
})
public class ReservEB {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	private Date date_arr;
	private Date date_dep;
	private String room;
	private String customer;
	private String note;
	
	public static final String RESERV_ORD_DATE_ROOM = "Reserv_datearr_room";
	public static final String RESERV_CHECK = "Reserv_check";
	
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
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getCustomer() {
		return customer;
	}
	public void setCustomer(String customer) {
		this.customer = customer;
	}
	
}
