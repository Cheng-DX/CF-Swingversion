package entity;

import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class Equipment implements Serializable{

	private String id;
	private String name;
	private String type;
	private String specification;
	private String intro;
	private String equipmentStatus;
	private String leaseStatus;
	private String belongingFactoryId;
	
	private ArrayList<Capacity> capacities;
	
	public ArrayList<Capacity> getCapacities() {
		return capacities;
	}
	public Equipment(String id, String name, String type, String specification,String intro,
			String equipmentStatus, String leaseStatus, String belongingFactoryId
			) 
	{
		this.id = id;
		this.name = name;
		this.type = type;
		this.specification = specification;
		this.intro = intro;
		this.equipmentStatus = equipmentStatus;
		this.leaseStatus = leaseStatus;
		this.belongingFactoryId = belongingFactoryId;
		capacities = new ArrayList<>();
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

	public String getSpecification() {
		return specification;
	}

	public String getIntro() {
		return intro;
	}
	public String getEquipmentStatus() {
		return equipmentStatus;
	}

	public String getLeaseStatus() {
		return leaseStatus;
	}

	public String getBelongingFactoryId() {
		return belongingFactoryId;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setSpecification(String specification) {
		this.specification = specification;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public void setEquipmentStatus(String equipmentStatus) {
		this.equipmentStatus = equipmentStatus;
	}

	public void setLeaseStatus(String leaseStatus) {
		this.leaseStatus = leaseStatus;
	}

	public void setBelongingFactory(String belongingFactoryId) {
		this.belongingFactoryId = belongingFactoryId;
	}
	public void setCapacities(ArrayList<Capacity> capacities) {
		this.capacities = capacities;
	}
	
	
	
}
