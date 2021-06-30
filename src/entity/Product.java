package entity;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Product implements Serializable {

	private String id;
	private String name;
	private String type;
	private String specifications;
	private String intro;

	public Product(String id, String name, String type, String specifications, String intro) {

		this.id = id;
		this.name = name;
		this.type = type;
		this.specifications = specifications;
		this.intro = intro;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getType() {
		return type;
	}

	public String getSpecifications() {
		return specifications;
	}

	public String getIntro() {
		return intro;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setSpecifications(String specifications) {
		this.specifications = specifications;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + ", type=" + type + ", specifications=" + specifications
				+ ", intro=" + intro + "]";
	}
}
