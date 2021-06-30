package systems;

import java.util.ArrayList;

import entity.Dealer;
import entity.Order;

public class MyDealerSystem {

	private ArrayList<Order> myOrders = new ArrayList<>();
	private Dealer dealer;

	public MyDealerSystem(String dealerId) {

		ArrayList<Order> allOrders = SuperManagerSystem.getSingleSystem().getOrders();
		for (Order order : allOrders) {
			if (order.getBelongDealerId().equals(dealerId)) {
				myOrders.add(order);
			}
		}
		dealer = (Dealer) SuperManagerSystem.getSingleSystem().getUser(dealerId);
	}

	public String[][] getOrderTableData() {
		ArrayList<Order> allOrders = SuperManagerSystem.getSingleSystem().getOrders();
		myOrders.clear();
		for (Order order : allOrders) {
			if (order.getBelongDealerId().equals(dealer.getId())) {
				myOrders.add(order);
			}
		}
		String[][] data = new String[myOrders.size()][15];

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

			boolean case2 = temp1.getStatus().equals("投标结束") && temp1.getWinFacName().size() == 0;
			
			if (temp1.getStatus().equals("已保存")) {
				data[i][10] = "发布-" + temp1.getId();
				data[i][13] = "修改-" + temp1.getId();
				data[i][14] = "删除-" + temp1.getId();
			} else {
				data[i][10] = "*-" + temp1.getId();
				data[i][13] = "*-" + temp1.getId();
				data[i][14] = "*-" + temp1.getId();
				if(case2)
					data[i][14] = "删除-" + temp1.getId();
			}
			if ((temp1.getStatus().equals("已发布")) || (temp1.getStatus().equals("投标结束"))) {
				data[i][11] = "投标详情-" + temp1.getId();
			} else {
				data[i][11] = "*-" + temp1.getId();
			}

			if (temp1.getStatus().equals("已发货")) {
				data[i][12] = "收货-" + temp1.getId();
			} else {
				data[i][12] = "*-" + temp1.getId();
			}
		}
		return data;
	}

	public String[] getOrderTableTitle() {
		String[] title = { "序号", "订单编号", "产品名称", "订购数量", "交付日期", "投标截止日期", "收货人", "收货人联系方式", "收货地址", "订单状态", "发布",
				"订单详情", "收货", "修改", "删除" };
		return title;
	}

	public Order getOrder(String id) {
		for (Order order : myOrders) {
			if (order.getId().equals(id)) {
				return order;
			}
		}
		return null;
	}

	public Dealer getDealer() {
		return dealer;
	}

	public ArrayList<Order> getMyOrders() {
		return myOrders;
	}

}
