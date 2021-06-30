package entity;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Capacity implements Serializable {

	private Product product;
	private String productCap;

	public Capacity(Product product, String productCap) {
		this.product = product;
		this.productCap = productCap;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public String getProductCap() {
		return productCap;
	}

	public void setProductCap(String productCap) {
		this.productCap = productCap;
	}

	@Override
	public String toString() {
		return "Capacity [product=" + product + ", productCap=" + productCap + "]";
	}

}
