package cc.co.enricosartori.hotelboss.dto;

import java.io.Serializable;

public class Reduction implements Serializable {

	private static final long serialVersionUID = 647290509584246118L;
	
	private int id;
	private String descr;
	private float val;
	private int red_type;
	private boolean perc;
	private String state;
	
	public String getState() {
		return state;
	}


	public void setState(String state) {
		this.state = state;
	}


	public int getId() {
		return id;
	}
	
	
	public void setId(int id) {
		this.id = id;
	}
	
	
	public String getDescr() {
		return descr;
	}
	
	
	public void setDescr(String descr) {
		this.descr = descr;
	}
	
	
	public float getVal() {
		return val;
	}
	
	
	public void setVal(float val) {
		this.val = val;
	}
	
	
	public int getRed_type() {
		return red_type;
	}
	
	
	public void setRed_type(int red_type) {
		this.red_type = red_type;
	}
	
	
	public boolean isPerc() {
		return perc;
	}
	
	
	public void setPerc(boolean perc) {
		this.perc = perc;
	}
	
}
