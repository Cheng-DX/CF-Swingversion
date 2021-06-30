package entity;

import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class FactoryManager extends User implements Serializable {

	private ArrayList<Equipment> Equipments;
	private String factoryId;

	public FactoryManager(String id, String account, String password, String administrator_name, String account_type,
			String real_name, String contact_method, String introduction, String factoryId) {
		super(id, account, password, administrator_name, account_type, real_name, contact_method, introduction);
		Equipments = new ArrayList<Equipment>();
		this.factoryId = factoryId;
	}

	public String getFactoryId() {
		return factoryId;
	}

	public ArrayList<Equipment> getEquipments() {
		return Equipments;
	}
}
