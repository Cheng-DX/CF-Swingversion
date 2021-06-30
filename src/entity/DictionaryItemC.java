package entity;

import java.io.Serializable;

@SuppressWarnings("serial")
public class DictionaryItemC  implements Serializable {

	private String id;
	private String name;
	private String itemValue;
	
	private String fatherId;

	public DictionaryItemC(String id, String name, String itemValue,String fatherId) {
		this.id = id;
		this.name = name;
		this.itemValue = itemValue;
		this.fatherId = fatherId;
	}

	public String getId() {
		return id;
	}
	public String getName() {
		return name;
	}

	public String getItemValue() {
		return itemValue;
	}

	public void setItemValue(String itemValue) {
		this.itemValue = itemValue;
	}

	public void setName(String name) {
		this.name = name;
	}
	public String getFatherId() {
		return fatherId;
	}
	public void setFatherId(String fatherId) {
		this.fatherId = fatherId;
	}		
}
