package cc.co.enricosartori.hotelboss.dto;

import java.io.Serializable;

public class Totals implements Serializable {

	private static final long serialVersionUID = 5557175799783835175L;
	
	private float extra;
	private float pens;
	private float total;
	
	public float getExtra() {
		return extra;
	}
	public void setExtra(float extra) {
		this.extra = extra;
	}
	public float getPens() {
		return pens;
	}
	public void setPens(float pens) {
		this.pens = pens;
	}
	public float getTotal() {
		return total;
	}
	public void setTotal(float total) {
		this.total = total;
	}
	
	
	
}
