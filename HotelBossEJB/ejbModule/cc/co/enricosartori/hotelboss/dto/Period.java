package cc.co.enricosartori.hotelboss.dto;

import java.io.Serializable;
import java.sql.Date;

public class Period implements Serializable {
	private static final long serialVersionUID = -5512762593838129134L;
	private Date d_start;
	private Date d_end;
	private int days;
	private float price;
	
	
	public Date getD_start() {
		return d_start;
	}
	public void setD_start(Date dStart) {
		d_start = dStart;
	}
	public Date getD_end() {
		return d_end;
	}
	public void setD_end(Date dEnd) {
		d_end = dEnd;
	}
	public int getDays() {
		return days;
	}
	public void setDays(int days) {
		this.days = days;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	
	
}
