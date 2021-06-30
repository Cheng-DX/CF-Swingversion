package systems;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;

import entity.Dealer;
import entity.DictionaryItemC;
import entity.DictionaryItemF;
import entity.Equipment;
import entity.Factory;
import entity.FactoryManager;
import entity.Order;
import entity.Product;
import entity.SuperManager;
import entity.User;

public class SuperManagerSystem {

	private static SuperManagerSystem singleSystem = null;

	private ArrayList<User> usersDB = new ArrayList<>();
	private ArrayList<Factory> factories = new ArrayList<>();
	private ArrayList<Equipment> equipments = new ArrayList<>();
	private ArrayList<Product> products = new ArrayList<>();
	private ArrayList<Order> orders = new ArrayList<>();
	private ArrayList<DictionaryItemF> diction = new ArrayList<>();
	
	private SuperManagerSystem() {
		loadData();
	}

	public static SuperManagerSystem getSingleSystem() {
		if (singleSystem == null) {
			singleSystem = new SuperManagerSystem();
		}
		return singleSystem;
	}

	public ArrayList<User> getUsersDB() {
		return usersDB;
	}

	public ArrayList<Equipment> getEquipments() {
		return equipments;
	}

	public ArrayList<Product> getProducts() {
		return products;
	}

	public void deleteUser(String id) {
		for (User eachUser : usersDB) {
			if (eachUser.getId().equals(id)) {
				usersDB.remove(eachUser);
				JOptionPane.showMessageDialog(null, eachUser.getAccount() + "删除成功", "成功",
						JOptionPane.INFORMATION_MESSAGE);
				break;
			}
		}
	}

	public void deleteEquipment(String id) {
		for (Equipment equipment : equipments) {
			if (equipment.getId().equals(id)) {
				equipments.remove(equipment);
				JOptionPane.showMessageDialog(null, equipment.getName() + "删除成功", "成功",
						JOptionPane.INFORMATION_MESSAGE);
				break;
			}
		}
	}

	public void deleteProduct(String id) {
		for (Product product : products) {
			if (product.getId().equals(id)) {
				products.remove(product);
				JOptionPane.showMessageDialog(null, product.getName() + "删除成功", "成功", JOptionPane.INFORMATION_MESSAGE);
				break;
			}
		}
	}

	public void deleteOrder(String id) {
		for (Order order : orders) {
			if (order.getId().equals(id)) {
				orders.remove(order);
				JOptionPane.showMessageDialog(null, "订单" + order.getId() + "删除成功", "成功",
						JOptionPane.INFORMATION_MESSAGE);
				break;
			}
		}
	}
	public void deleteDictionItem(String id) {
		for(DictionaryItemF f : diction) {
			if(f.getId().equals(id)) {
				diction.remove(f);
				JOptionPane.showMessageDialog(null, "类型" + f.getId() + "删除成功", "成功",
						JOptionPane.INFORMATION_MESSAGE);
				break;
			}
		}
		
	}
	public void deleteFactory(String factoryId) {
		for (Factory factory : factories) {
			if (factory.getId().equals(factoryId)) {
				factories.remove(factory);
				break;
			}
		}
	}

	public ArrayList<User> searchUser(User target) {
		ArrayList<User> results = new ArrayList<>();
		results.addAll(usersDB);
		String[] items = {target.getId(),target.getAccount(),
						  target.getAdministrator_name(),target.getAccount_type(),
		            	  target.getReal_name(),target.getContact_method(),
				          target.getIntroduction()};
		for(int i=0;i<7;i++) {
			Pattern pattern = Pattern.compile(items[i]);
			for (int j = 0; j < usersDB.size(); j++) {
				User cUser = usersDB.get(j);
				Matcher matcher = null;
				if(i==0) matcher = pattern.matcher(cUser.getId());
				if(i==1) matcher = pattern.matcher(cUser.getAccount());
				if(i==2) matcher = pattern.matcher(cUser.getAdministrator_name());
				if(i==3) matcher = pattern.matcher(cUser.getAccount_type());
				if(i==4) matcher = pattern.matcher(cUser.getReal_name());
				if(i==5) matcher = pattern.matcher(cUser.getContact_method());
				if(i==6) matcher = pattern.matcher(cUser.getIntroduction());
				if (matcher.find()) {
					//void
				}else {
					results.remove(cUser);
				}
			}
		}
		return results;
	}
	
	public ArrayList<Factory> searchFactory(Factory target,String status){
		
		ArrayList<Factory> results = new ArrayList<>();
		results.addAll(factories);
		String[] items = {  
							target.getId(), target.getFactoryName(),
							target.getIntro(), target.getManager(),
							target.getContection(), target.getAccount(),status
		};
		for(int i=0;i<items.length;i++) {
			Pattern pattern = Pattern.compile(items[i]);
			for (int j = 0; j < factories.size(); j++) {
				Factory cur = factories.get(j);
				Matcher matcher = null;
				if(i==0) matcher = pattern.matcher(cur.getId());
				if(i==1) matcher = pattern.matcher(cur.getFactoryName());
				if(i==2) matcher = pattern.matcher(cur.getIntro());
				if(i==3) matcher = pattern.matcher(cur.getManager());
				if(i==4) matcher = pattern.matcher(cur.getContection());
				if(i==5) matcher = pattern.matcher(cur.getAccount());
				if(i==6) matcher = pattern.matcher(cur.getStatus());
				if (matcher.find()) {
					//void
				}else {
					results.remove(cur);
				}
			}
		}
		return results;
	}
	
	public ArrayList<Factory> searchFactory(String target) {
		ArrayList<Factory> results = new ArrayList<>();
		Pattern pattern = Pattern.compile(target);
		for (int i = 0; i < factories.size(); i++) {
			Matcher matcher = pattern.matcher(((Factory) factories.get(i)).getFactoryName());
			if (matcher.find()) {
				results.add(factories.get(i));
			}
		}
		return results;
	}

	public ArrayList<Equipment> searchEquipment(Equipment target){
		
		ArrayList<Equipment> results = new ArrayList<>();
		results.addAll(equipments);
		String[] items = {  
							target.getId(), target.getName(),
							target.getType(), target.getSpecification(),
							target.getIntro(), target.getEquipmentStatus(),
							target.getLeaseStatus(),target.getBelongingFactoryId()
		};
		for(int i=0;i<items.length;i++) {
			Pattern pattern = Pattern.compile(items[i]);
			for (int j = 0; j < equipments.size(); j++) {
				Equipment cur = equipments.get(j);
				Matcher matcher = null;
				if(i==0) matcher = pattern.matcher(cur.getId());
				if(i==1) matcher = pattern.matcher(cur.getName());
				if(i==2) matcher = pattern.matcher(cur.getType());
				if(i==3) matcher = pattern.matcher(cur.getSpecification());
				if(i==4) matcher = pattern.matcher(cur.getIntro());
				if(i==5) matcher = pattern.matcher(cur.getEquipmentStatus());
				if(i==6) matcher = pattern.matcher(cur.getLeaseStatus());
				if(i==7) matcher = pattern.matcher(cur.getBelongingFactoryId());
				if (matcher.find()) {
					//void
				}else {
					results.remove(cur);
				}
			}
		}
		return results;
	}
	public ArrayList<Equipment> searchEquipment(String target) {
		ArrayList<Equipment> results = new ArrayList<>();
		Pattern pattern = Pattern.compile(target);
		for (int i = 0; i < equipments.size(); i++) {
			Matcher matcher = pattern.matcher(((Equipment) equipments.get(i)).getName());
			if (matcher.find()) {
				results.add(equipments.get(i));
			}
		}
		return results;
	}

	public ArrayList<Product> searchProduct(String target) {
		ArrayList<Product> results = new ArrayList<>();
		Pattern pattern = Pattern.compile(target);
		for (int i = 0; i < products.size(); i++) {
			Matcher matcher = pattern.matcher(((Product) products.get(i)).getName());
			if (matcher.find()) {
				results.add(products.get(i));
			}
		}
		return results;
	}
	public ArrayList<Product> searchProduct(Product target){
		ArrayList<Product> results = new ArrayList<>();
		results.addAll(products);
		String[] items = {target.getId(),target.getName(),target.getType(),target.getSpecifications(),target.getIntro()};
		for(int i=0;i<items.length;i++) {
			Pattern pattern = Pattern.compile(items[i]);
			for (int j = 0; j < products.size(); j++) {
				Product cur = products.get(j);
				Matcher matcher = null;
				if(i==0) matcher = pattern.matcher(cur.getId());
				if(i==1) matcher = pattern.matcher(cur.getName());
				if(i==2) matcher = pattern.matcher(cur.getType());
				if(i==3) matcher = pattern.matcher(cur.getSpecifications());
				if(i==4) matcher = pattern.matcher(cur.getIntro());
				if (matcher.find()) {
					//void
				}else {
					results.remove(cur);
				}
			}
		}
		return results;
	}
	public String[][] getUserTableData() {
		String[][] data = new String[usersDB.size()][9];

		for (int i = 0; i < usersDB.size(); i++) {

			User temp1 = usersDB.get(i);
			data[i][0] = temp1.getId();
			data[i][1] = temp1.getAccount_type();
			data[i][2] = temp1.getAccount();
			data[i][3] = temp1.getAdministrator_name();
			data[i][4] = temp1.getReal_name();
			data[i][5] = temp1.getContact_method();
			data[i][6] = temp1.getIntroduction();
			data[i][7] = "修改-" + temp1.getId();
			if (temp1.getId().equals("administer")) {
				data[i][8] = "不可删除-" + temp1.getId();
			} else {
				data[i][8] = "删除-" + temp1.getId();
			}

		}
		return data;
	}

	public String[][] getProductTableData() {
		String[][] data = new String[products.size()][8];

		for (int i = 0; i < products.size(); i++) {
			Product temp = products.get(i);
			data[i][0] = (i + 1) + "";
			data[i][1] = temp.getId();
			data[i][2] = temp.getName();
			data[i][3] = temp.getType();
			data[i][4] = temp.getSpecifications();
			data[i][5] = temp.getIntro();
			data[i][6] = "修改-" + temp.getId();
			data[i][7] = "删除-" + temp.getId();
		}
		return data;
	}

	public String[][] getEquipmentTableData() {
		String[][] data = new String[equipments.size()][12];

		for (int i = 0; i < equipments.size(); i++) {

			Equipment temp1 = equipments.get(i);
			data[i][0] = (i + 1) + "";
			data[i][1] = temp1.getId();
			data[i][2] = temp1.getName();
			data[i][3] = temp1.getType();
			data[i][4] = temp1.getSpecification();
			data[i][5] = temp1.getIntro();
			data[i][6] = temp1.getEquipmentStatus();
			data[i][7] = temp1.getLeaseStatus();

			String factoryId = temp1.getBelongingFactoryId();
			if (factoryId.equals("")) {
				data[i][8] = "总系统";
			} else {
				data[i][8] = SuperManagerSystem.getSingleSystem().getFactory(factoryId).getFactoryName();
			}

			data[i][9] = "更改设备状态-" + temp1.getId();
			
			data[i][10] = "修改-" + temp1.getId();
			data[i][11] = "删除-" + temp1.getId();
		}
		return data;
	}

	public String[][] getDictionTableData() {
		String[][] rowData = new String[diction.size()][6];
		for(int i=0;i<diction.size();i++) {
			DictionaryItemF temp = diction.get(i);
			rowData[i][0] = temp.getId();
			rowData[i][1] = temp.getNameCode();
			rowData[i][2] = temp.getName();
			rowData[i][3] = temp.getItems().size() + "";
			rowData[i][4] = "修改/添加子项-" + temp.getId();
			rowData[i][5] = "删除该类型-" + temp.getId();
		}
		return rowData;
	}
	public String[][] getDictionCTableData(DictionaryItemF father) {
		ArrayList<DictionaryItemC> items = father.getItems();
		String[][] rowData = new String[items.size()][7];
		int count = 0;
		for(DictionaryItemC c : items) {
			rowData[count][0] = c.getId();
			rowData[count][1] = father.getNameCode();
			rowData[count][2] = father.getName();
			rowData[count][3] = c.getName();
			rowData[count][4] = c.getItemValue();
			rowData[count][5] = "修改-" + c.getId();
			rowData[count][6] = "删除-" + c.getId();
			count++;
		}
		return rowData;
	}
	public String[][] getFactoryTableData() {
		String[][] rowData = new String[factories.size()][8];

		for (int i = 0; i < factories.size(); i++) {

			Factory temp = factories.get(i);
			rowData[i][0] = temp.getId();
			rowData[i][1] = temp.getFactoryName();
			rowData[i][2] = temp.getIntro();
			rowData[i][3] = temp.getManager();
			rowData[i][4] = temp.getContection();
			rowData[i][5] = temp.getAccount();
			rowData[i][6] = temp.getStatus();
				rowData[i][7] = "修改-" + temp.getId();

		}
		return rowData;
	}


	public User getUser(String id) {
		for (User user : usersDB) {
			if (user.getId().equals(id)) {
				return user;
			}
		}
		return null;
	}

	public DictionaryItemF getDictionItemF(String id) {
		for(DictionaryItemF f : diction) {
			if(f.getId().equals(id)) {
				return f;
			}
		}
		return null;
	}
	public DictionaryItemC getDictionItemC(String id) {
		for(DictionaryItemF f : diction) {
			for(DictionaryItemC c : f.getItems()) {
				if(c.getId().equals(id)) {
					return c;
				}
			}
		}
		return null;
	}
	public User getUserByAcc(String acc) {
		for (User user : usersDB) {
			if (user.getAccount().equals(acc)) {
				if (user.getId().startsWith("D")) {
					return (Dealer) user;
				} else if (user.getId().startsWith("M")) {
					return (FactoryManager) user;
				} else if (acc.equals("a")) {
					return (SuperManager) user;
				}
			}
		}
		return null;
	}

	public Equipment getEquipment(String id) {
		for (Equipment equipment : equipments) {
			if (equipment.getId().equals(id)) {
				return equipment;

			}
		}
		return null;
	}

	public Product getProduct(String id) {
		for (Product product : products) {
			if (product.getId().equals(id)) {
				return product;

			}
		}
		return null;
	}

	public Product getProductByName(String productName) {
		for (Product product : products) {
			if (product.getName().equals(productName)) {
				return product;
			}
		}
		return null;
	}

	public Order getOrder(String id) {
		for (Order order : orders) {
			if (order.getId().equals(id)) {
				return order;
			}
		}
		return null;
	}

	public ArrayList<Order> getOrders() {
		return orders;
	}

	public ArrayList<DictionaryItemF> getDiction(){
		return diction;
	}
	public ArrayList<Factory> getFactories() {
		return factories;
	}

	public Factory getFactory(String id) {
		for (Factory factory : factories) {
			if (factory.getId().equals(id)) {
				return factory;
			}
		}
		return null;
	}
	public Factory getFactoryByName(String name) {
		for (Factory factory : factories) {
			if (factory.getFactoryName().equals(name)) {
				return factory;
			}
		}
		return null;
	}
	public String[] getUserTableTitle() {
		String[] title = { "用户Id", "账号类型", "登陆账号", "工厂名/经销商名", "负责人姓名", "联系方式", "简介", "修改", "删除" };
		return title;
	}

	public String[] getDictionTableTitle() {
		String[] title = {"ID","类型码","类型名称","子项数量","修改/添加子项","删除该类型"};
		return title;
	}

	public String[] getDictionCTableTitle() {
		String[] title = {"ID","主项字典类型码","主项字典名称","展示名称","项值","修改","删除"};
		return title;
	}
	public String[] getEquipmentTableTitle() {
		String[] title = { "序号", "设备编号", "设备名称", "设备类别", "设备规格", "设备描述", "设备状态", "租用状态", "所属工厂", "更改设备状态", "修改", "删除" };
		return title;
	}

	public String[] getFactoryTableTitle() {
		String[] title = { "编号", "工厂名称", "工厂简介", "负责人", "联系方式", "登陆账号", "工厂状态", "更改工厂状态" };
		return title;
	}

	public String[] getProductTableTitle() {
		String[] title = { "序号", "产品编号", "产品名称", "产品类别", "产品规格", "产品描述", "修改", "删除" };
		return title;
	}

	public boolean hasThisAccount(String account) {
		for (User u : usersDB) {
			if (u.getAccount().equals(account)) {
				return true;
			}
		}
		return false;
	}

	public void writeUserData() {
		if (usersDB != null) {
			ObjectOutputStream oosOfInfor = null;
			try {
				oosOfInfor = new ObjectOutputStream(new FileOutputStream(new File("userData.dat")));
				oosOfInfor.writeObject(usersDB);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					oosOfInfor.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void writeFactoryData() {
		if (factories != null) {
			ObjectOutputStream oosOfInfor = null;
			try {
				oosOfInfor = new ObjectOutputStream(new FileOutputStream(new File("factoryData.dat")));
				oosOfInfor.writeObject(factories);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					oosOfInfor.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void writeEquipmentData() {
		if (equipments != null) {
			ObjectOutputStream oosOfInfor = null;
			try {
				oosOfInfor = new ObjectOutputStream(new FileOutputStream(new File("equipmentData.dat")));
				oosOfInfor.writeObject(equipments);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					oosOfInfor.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void writeOrderData() {
		if (orders != null) {
			ObjectOutputStream oosOfInfor = null;
			try {
				oosOfInfor = new ObjectOutputStream(new FileOutputStream(new File("orderData.dat")));
				oosOfInfor.writeObject(orders);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					oosOfInfor.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void writeProductData() {
		if (products != null) {
			ObjectOutputStream oosOfInfor = null;
			try {
				oosOfInfor = new ObjectOutputStream(new FileOutputStream(new File("productData.dat")));
				oosOfInfor.writeObject(products);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					oosOfInfor.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void writeDictionData() {
		if (diction != null) {
			ObjectOutputStream oosOfInfor = null;
			try {
				oosOfInfor = new ObjectOutputStream(new FileOutputStream(new File("dictionData.dat")));
				oosOfInfor.writeObject(diction);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					oosOfInfor.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public void loadData() {
		File[] files = new File[6];
		files[0] = new File("userData.dat");
		files[1] = new File("factoryData.dat");
		files[2] = new File("equipmentData.dat");
		files[3] = new File("productData.dat");
		files[4] = new File("orderData.dat");
		files[5] = new File("dictionData.dat");
		
		for (int i = 0; i < 6; i++) {
			if (files[i].exists() && files[i].length() != 0) {
				ObjectInputStream ois = null;
				try {
					ois = new ObjectInputStream(new FileInputStream(files[i]));
					Object obj = ois.readObject();
					if (obj != null) {
						if (i == 0) {
							usersDB = (ArrayList<User>) obj;
						} else if (i == 1) {
							factories = (ArrayList<Factory>) obj;
						} else if (i == 2) {
							equipments = (ArrayList<Equipment>) obj;
						} else if (i == 3) {
							products = (ArrayList<Product>) obj;
						} else if (i == 4) {
							orders = (ArrayList<Order>) obj;
						} else if (i == 5) {
							diction = (ArrayList<DictionaryItemF>) obj;
						}
					}
				} catch (FileNotFoundException e) {

					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (ClassNotFoundException e) {

					e.printStackTrace();
				} finally {
					try {
						ois.close();
					} catch (IOException e) {

						e.printStackTrace();
					}
				}
			}
		}
	}

	public ArrayList<String> getFactoryStatus() {
		ArrayList<String> strings = new ArrayList<>();
		for(DictionaryItemF f : diction) {
			if(f.getNameCode().equals("factoryStatus")) {
				for(DictionaryItemC c : f.getItems()) {
					strings.add(c.getName()); 
				}	
				return strings;
			}
		}
		return null;
	}

	public ArrayList<String> getEquipmentStatus() {
		ArrayList<String> strings = new ArrayList<>();
		for(DictionaryItemF f : diction) {
			if(f.getNameCode().equals("equipmentStatus")) {
				for(DictionaryItemC c : f.getItems()) {
					strings.add(c.getName()); 
				}	
				return strings;
			}
		}
		return null;
	}
	
	public ArrayList<String> getProductTypes() {
		ArrayList<String> strings = new ArrayList<>();
		for(DictionaryItemF f : diction) {
			if(f.getNameCode().equals("productType")) {
				for(DictionaryItemC c : f.getItems()) {
					strings.add(c.getName()); 
				}	
				return strings;
			}
		}
		return null;
	}
	public ArrayList<String> getequipmentTypes() {
		ArrayList<String> strings = new ArrayList<>();
		for(DictionaryItemF f : diction) {
			if(f.getNameCode().equals("equipmentType")) {
				for(DictionaryItemC c : f.getItems()) {
					strings.add(c.getName()); 
				}	
				return strings;
			}
		}
		return null;
	}

	public String getFatherIdByChildId(String id) {
		for(DictionaryItemF f : diction) {
			ArrayList<DictionaryItemC> items = f.getItems();
			for(DictionaryItemC c : items) {
				if(c.getId().equals(id)) {
					return f.getId();
				}
			}
		}
		return null;
	}



}
