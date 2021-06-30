package entity;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Factory implements Serializable {

	private String id;
	private String factoryName;
	private String intro;
	private String manager;
	private String contection;
	private String account;
	private String status;

	public Factory(String id, String factoryName, String intro, String manager, String contection, String account) {

		this.id = id;
		this.factoryName = factoryName;
		this.intro = intro;
		this.manager = manager;
		this.contection = contection;
		this.account = account;
		this.status = "正常";
	}

	public String getFactoryName() {
		return factoryName;
	}

	public String getIntro() {
		return intro;
	}

	public String getManager() {
		return manager;
	}

	public String getContection() {
		return contection;
	}

	public String getAccount() {
		return account;
	}

	public String getStatus() {
		return status;
	}

	public String getId() {
		return this.id;
	}

	public void setFactoryName(String factoryName) {
		this.factoryName = factoryName;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public void setManager(String manager) {
		this.manager = manager;
	}

	public void setContection(String contection) {
		this.contection = contection;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Factory [factoryName=" + factoryName + ", intro=" + intro + ", manager=" + manager + ", contection="
				+ contection + ", account=" + account + ", status=" + status + "]";
	}

}
