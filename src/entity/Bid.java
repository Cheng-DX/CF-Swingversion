package entity;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Bid implements Serializable{

	private String factoryId;
	private String price;
	private String mount;
	
	public String getFactoryId() {
		return factoryId;
	}
	public void setFactoryId(String factoryId) {
		this.factoryId = factoryId;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getMount() {
		return mount;
	}
	public void setMount(String mount) {
		this.mount = mount;
	}
	public Bid(String factoryId, String price, String mount) {
		this.factoryId = factoryId;
		this.price = price;
		this.mount = mount;
	}
	public int compareTo(Bid bid) {
		return this.getPrice().compareTo(bid.getPrice());		
	}
	
	
}
