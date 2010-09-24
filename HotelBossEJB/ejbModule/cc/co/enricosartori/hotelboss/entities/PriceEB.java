package cc.co.enricosartori.hotelboss.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="Price")
@NamedQueries({
	@NamedQuery(name="Pricelist_dateAsc",
			query="select price from PriceEB price order by price.start_d asc"),
	@NamedQuery(name="Pricelist_maxId",
			query="select max(price.price_id) from PriceEB price")
})

public class PriceEB implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5664153561686682429L;

	public static final String NQ_PRICELIST_ASC = "Pricelist_dateAsc";
	public static final String NQ_MAX_PRICEID = "Pricelist_maxId";
	
	@Id
	int price_id;
	Date start_d;
	Date end_d;
	double fb;
	double hb;
	double bb;
	
	
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
	
	
	
}
