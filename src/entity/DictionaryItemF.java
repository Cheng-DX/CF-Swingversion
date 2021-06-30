package entity;

import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class DictionaryItemF  implements Serializable {

	private String id;
	private String name;
	private String nameCode;
	private ArrayList<DictionaryItemC> items;
	
	public DictionaryItemF(String id, String name, String nameCode) {
		this.id = id;
		this.name = name;
		this.nameCode = nameCode;
		this.items = new ArrayList<>();
	}
	public String getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNameCode() {
		return nameCode;
	}
	public void setNameCode(String nameCode) {
		this.nameCode = nameCode;
	}
	public ArrayList<DictionaryItemC> getItems() {
		return items;
	}
	@Override
	public String toString() {
		return "DictionaryItemF [id=" + id + ", name=" + name + ", nameCode=" + nameCode + ", items=" + items + "]";
	}
	
}
