package systems;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import entity.Bid;
import entity.Capacity;
import entity.Equipment;
import entity.Factory;
import entity.Order;
import entity.Product;

public class MyFactorySystem {

	private ArrayList<Equipment> myEquipments = new ArrayList<>();
	private ArrayList<Order> myOrders = new ArrayList<>();
	private Factory factory;

	public MyFactorySystem(String factoryId) {

		ArrayList<Equipment> allE = SuperManagerSystem.getSingleSystem().getEquipments();
		for (Equipment e : allE) {
			if (e.getBelongingFactoryId().equals(factoryId)) {
				myEquipments.add(e);
			}
		}
		factory = SuperManagerSystem.getSingleSystem().getFactory(factoryId);

		ArrayList<Order> allO = SuperManagerSystem.getSingleSystem().getOrders();

		for (Order order : allO) {
			if (order.getStatus().equals("已发布")) {
				myOrders.add(order);
			} else {
				boolean flag = false;
				ArrayList<Bid> bids = order.getBidInfor();
				for (Bid bid : bids) {
					if (bid.getFactoryId().equals(factoryId)) {
						flag = true;
					}
				}
				if (flag) {
					myOrders.add(order);
				}
			}
		}
	}

	public String[][] getEquipmentTableData() {
		ArrayList<Equipment> allE = SuperManagerSystem.getSingleSystem().getEquipments();
		myEquipments.clear();
		for (Equipment e : allE) {
			if (e.getBelongingFactoryId().equals(factory.getId())) {
				myEquipments.add(e);
			}
		}
		String[][] data = new String[myEquipments.size()][13];

		for (int i = 0; i < myEquipments.size(); i++) {

			Equipment temp1 = myEquipments.get(i);
			data[i][0] = (i + 1) + "";
			data[i][1] = temp1.getId();
			data[i][2] = temp1.getName();
			data[i][3] = temp1.getType();
			data[i][4] = temp1.getSpecification();
			data[i][5] = temp1.getIntro();
			data[i][6] = temp1.getEquipmentStatus();
			if (temp1.getLeaseStatus().equals("已被租用")) {
				data[i][7] = "租用设备";
				data[i][11] = "*-" + temp1.getId();
				data[i][12] = "*-" + temp1.getId();
			} else if (temp1.getLeaseStatus().equals("工厂设备")) {
				data[i][7] = "自有设备";
				data[i][11] = "修改-" + temp1.getId();
				data[i][12] = "删除-" + temp1.getId();
			}

			data[i][8] = temp1.getBelongingFactoryId();

			if (temp1.getEquipmentStatus().equals("生产中")) {
				data[i][9] = "不可进行此操作-" + temp1.getId();
			} else if (temp1.getEquipmentStatus().equals("闲置中")) {
				data[i][9] = "远程关机-" + temp1.getId();
			} else if (temp1.getEquipmentStatus().equals("已关闭")) {
				data[i][9] = "远程开机-" + temp1.getId();
			} else {
				data[i][9] = "异常-" + temp1.getId();
			}
			data[i][10] = "配置产品-" + temp1.getId();

		}
		return data;
	}

	public String[] getEquipmentTableTitle() {
		String[] title = { "序号", "设备编号", "设备名称", "设备类别", "设备规格", "设备描述", "设备状态", "设备来源", "所属工厂", "开/关机", "配置产品", "修改",
				"删除" };
		return title;
	}

	public String[][] getOrderTableData() {
		ArrayList<Order> allO = SuperManagerSystem.getSingleSystem().getOrders();
		myOrders.clear();
		for (Order order : allO) {
			if (order.getStatus().equals("已发布")) {
				myOrders.add(order);
			} else {
				boolean flag = false;
				ArrayList<Bid> bids = order.getBidInfor();
				for (Bid bid : bids) {
					if (bid.getFactoryId().equals(factory.getId())) {
						flag = true;
					}
				}
				if (flag) {
					myOrders.add(order);
				}
			}
		}
		String[][] data = new String[myOrders.size()][14];

		for (int i = 0; i < myOrders.size(); i++) {

			Order temp1 = myOrders.get(i);
			data[i][0] = (i + 1) + "";
			data[i][1] = temp1.getId();
			data[i][2] = temp1.getProductName();
			data[i][3] = temp1.getMount() + "";
			data[i][4] = temp1.getDeliverTime();
			data[i][5] = temp1.getBidDeadline();
			data[i][6] = temp1.getReceiver();
			data[i][7] = temp1.getReceiverConMe();
			data[i][8] = temp1.getShippingAdd();
			data[i][9] = temp1.getStatus();

			if (temp1.getStatus().equals("已发布")) {
				data[i][10] = "投标-" + temp1.getId();
			} else {
				data[i][10] = "*-" + temp1.getId();
			}
			if (temp1.getStatus().equals("投标结束") && temp1.getWinFacName().contains(factory.getId())) {
				data[i][11] = "排产-" + temp1.getId();
			}
			if (temp1.getStatus().equals("投标结束") && !temp1.getWinFacName().contains(factory.getId())) {
				data[i][11] = "您未中标-" + temp1.getId();
			}
			if (temp1.getStatus().equals("已排产")) {
				data[i][12] = "完工-" + temp1.getId();
			} else {
				data[i][12] = "*-" + temp1.getId();
			}
			if (temp1.getStatus().equals("已完工")) {
				data[i][13] = "发货-" + temp1.getId();
			} else {
				data[i][13] = "*-" + temp1.getId();
			}
		}
		return data;
	}

	public String[] getOrderTableTitle() {
		String[] title = { "序号", "订单编号", "产品名称", "订购数量", "交付日期", "投标截止日期", "收货人", "收货人联系方式", "收货地址", "订单状态", "投标", "排产",
				"完工", "发货" };
		return title;
	}

	public ArrayList<Equipment> searchEquipment(String target) {
		ArrayList<Equipment> allE = SuperManagerSystem.getSingleSystem().getEquipments();
		myEquipments.clear();
		for (Equipment e : allE) {
			if (e.getBelongingFactoryId().equals(factory.getId())) {
				myEquipments.add(e);
			}
		}
		ArrayList<Equipment> results = new ArrayList<>();
		Pattern pattern = Pattern.compile(target);
		for (int i = 0; i < myEquipments.size(); i++) {
			Matcher matcher = pattern.matcher(((Equipment) myEquipments.get(i)).getName());
			if (matcher.find()) {
				results.add(myEquipments.get(i));
			}
		}
		return results;
	}

	public Order getOrder(String id) {
		for (Order order : myOrders) {
			if (order.getId().equals(id)) {
				return order;
			}
		}
		return null;
	}

	public Equipment getEquipment(String id) {
		for (Equipment equipment : myEquipments) {
			if (equipment.getId().equals(id)) {
				return equipment;
			}
		}
		return null;
	}

	public Equipment getEquipmentByName(String name) {
		for (Equipment equipment : myEquipments) {
			if (equipment.getName().equals(name)) {
				return equipment;
			}
		}
		return null;
	}

	public ArrayList<Equipment> getMyEquipments() {

		return myEquipments;
	}

	public ArrayList<Order> getMyOrders() {
		return myOrders;
	}

	public Factory getFactory() {
		return factory;
	}

	public ArrayList<Equipment> getMatchedEqs(String orderId) {
		ArrayList<Equipment> result = new ArrayList<>();
		String productName = getOrder(orderId).getProductName();
		Product product = SuperManagerSystem.getSingleSystem().getProductByName(productName);

		String productId = product.getId();
		for (Equipment e : myEquipments) {
			if (e.getEquipmentStatus().equals("闲置中")) {
				ArrayList<Capacity> capacities = e.getCapacities();
				for (Capacity capacity : capacities) {
					if (capacity.getProduct().getId().equals(productId)) {
						result.add(e);

					}
				}
			}
		}

		return result;
	}
}
