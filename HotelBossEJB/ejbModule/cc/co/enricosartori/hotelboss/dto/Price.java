package cc.co.enricosartori.hotelboss.dto;

import java.io.Serializable;
import java.util.Date;

public class Price implements Serializable {

	private static final long serialVersionUID = -5552144730523030193L;
	int price_id;
	Date start_d;
	Date end_d;
	double fb;
	double hb;
	double bb;
	String state;
	
	public int getPrice_id() {
		return price_id;
	}
	public void setPrice_id(int priceId) {
		price_id = priceId;
	}
	public Date getStart_d() {
		return start_d;
	}
	public void setStart_d(Date startD) {
		start_d = startD;
	}
	public Date getEnd_d() {
		return end_d;
	}
	public void setEnd_d(Date endD) {
		end_d = endD;
	}
	public double getFb() {
		return fb;
	}
	public void setFb(double d) {
		this.fb = d;
	}
	public double getHb() {
		return hb;
	}
	public void setHb(double d) {
		this.hb = d;
	}
	public double getBb() {
		return bb;
	}
	public void setBb(double d) {
		this.bb = d;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
}
