package entity;

import java.io.Serializable;

@SuppressWarnings("serial")
public class SuperManager extends User implements Serializable {

	public SuperManager(String id, String account, String password, String administrator_name, String account_type,
			String real_name, String contact_method, String introduction) {
		super(id, account, password, administrator_name, account_type, real_name, contact_method, introduction);

	}

}
