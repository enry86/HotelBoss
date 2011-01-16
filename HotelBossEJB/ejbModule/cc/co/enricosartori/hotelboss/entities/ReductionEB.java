package cc.co.enricosartori.hotelboss.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table (name="reductions")
@NamedQueries ({
		@NamedQuery (name="Reductions",
				query="select * from reductions")
})


public class ReductionEB implements Serializable {

	private static final long serialVersionUID = -4103939403197084541L;
	
	public static final String NQ_REDUCTIONS = "Reductions";
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	int id;
	String descr;
	float val;
	int red_type;
	boolean perc;
	
	
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
	public void setRed_type(int redType) {
		red_type = redType;
	}
	public boolean isPerc() {
		return perc;
	}
	public void setPerc(boolean perc) {
		this.perc = perc;
	}
}
