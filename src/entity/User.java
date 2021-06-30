package entity;

import java.io.Serializable;

@SuppressWarnings("serial")
public class User implements Serializable {

	private String id;
	private String account;
	private String password;
	private String administrator_name;
	private String account_type;

	private String real_name;
	private String contact_method;
	private String introduction;

	public User(String id, String account, String password, String administrator_name, String account_type,
			String real_name, String contact_method, String introduction) {
		this.id = id;
		this.account = account;
		this.password = password;
		this.administrator_name = administrator_name;
		this.account_type = account_type;
		this.real_name = real_name;
		this.contact_method = contact_method;
		this.introduction = introduction;
	}

	public String getId() {
		return id;
	}

	public String getAccount() {
		return account;
	}

	public String getPassword() {
		return password;
	}

	public String getAdministrator_name() {
		return administrator_name;
	}

	public String getAccount_type() {
		return account_type;
	}

	public String getReal_name() {
		return real_name;
	}

	public String getContact_method() {
		return contact_method;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setAdministrator_name(String administrator_name) {
		this.administrator_name = administrator_name;
	}

	public void setReal_name(String real_name) {
		this.real_name = real_name;
	}

	public void setContact_method(String contact_method) {
		this.contact_method = contact_method;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", account=" + account + ", password=" + password + ", administrator_name="
				+ administrator_name + ", account_type=" + account_type + ", real_name=" + real_name
				+ ", contact_method=" + contact_method + ", introduction=" + introduction + "]";
	}

}
