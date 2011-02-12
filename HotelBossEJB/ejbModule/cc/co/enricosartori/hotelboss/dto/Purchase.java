package cc.co.enricosartori.hotelboss.dto;

import java.io.Serializable;
import java.sql.Date;

public class Purchase implements Serializable {
	private static final long serialVersionUID = -8645910098118850342L;
	private int id;
	private int room;
	private Date date;
	private int qty;
	private int extra_id;
	private String status;
	
	
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
