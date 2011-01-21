package cc.co.enricosartori.hotelboss.dto;

import java.io.Serializable;

public class Extra  implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6592184836907920960L;
	private int id;
	private String name;
	private float price;
	private String status;
	
	
	public int getId() {
		return id;
	}
	
	
	public void setId(int id) {
		this.id = id;
	}
	
	
	public String getName() {
		return name;
	}
	
	
	public void setName(String name) {
		this.name = name;
	}
	
	
	public float getPrice() {
		return price;
	}
	
	
	public void setPrice(float price) {
		this.price = price;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}
}
