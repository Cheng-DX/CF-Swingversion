package framesOfDealerSystem;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import entity.Bid;
import entity.Factory;
import entity.Order;
import systems.MyDealerSystem;
import systems.SuperManagerSystem;
import util.MyButtonRender;

public class OrderFrameOfDealer {

	private MyDealerSystem myDealerSystem;

	public OrderFrameOfDealer(String dealerId) {
		myDealerSystem = new MyDealerSystem(dealerId);
	}

	private JButton addButton = new JButton("新建");

	private DefaultTableModel tModel = new DefaultTableModel();
	private JTable table = new JTable(tModel);

	private DefaultTableModel tModel2 = new DefaultTableModel();
	private JTable tableOfInfor = new JTable(tModel2);
	private JLabel jLabel_1 = new JLabel("采购订单列表");
	private JPanel mainPanel;

	// 一些工具
	private static final Color ZangQing = new Color(0, 127, 174);
	private static final Color MoLv = new Color(10, 160, 32);
	private Font font = new Font("微软雅黑", JFrame.ALLBITS, 28);
	private Font fontOfButton = new Font("微软雅黑", JFrame.ALLBITS, 20);
	private Font fontOfLabel = new Font("微软雅黑", JFrame.ALLBITS, 15);

	private Font fontOfLabel2 = new Font("微软雅黑", JFrame.ALLBITS, 18);
	private Font fontOfTableTitle = new Font("等线", Font.PLAIN, 18);
	private Font fontOfTableData = new Font("等线", Font.PLAIN, 18);

	public void add() {
		JFrame addFrame = new JFrame("");
		addFrame.setBounds(700, 125, 488, 700);

		JPanel panel = new JPanel();
		panel.setLayout(null);
		addFrame.setResizable(false);

		JLabel label = new JLabel("新建订单");
		label.setBounds(0, 0, 10000, 60);
		label.setOpaque(true);
		label.setFont(font);
		label.setBackground(ZangQing);
		label.setForeground(Color.white);
		panel.add(label);

		JLabel[] labels = new JLabel[8];
		String[] temp = { "订单编号", "订购数量", "交付日期", "投标截止日期", "收货人", "收货人联系方式", "收货地址", "产品名称" };
		JTextField[] texts = new JTextField[6];

		JTextArea rArea = new JTextArea(10, 3);

		rArea.setBounds(200, 390, 233, 100);
		rArea.setFont(fontOfLabel);
		rArea.setBackground(ZangQing);
		rArea.setForeground(Color.white);
		rArea.setLineWrap(true);
		panel.add(rArea);

		String[] productItems = new String[(SuperManagerSystem.getSingleSystem().getProducts().size()) + 1];

		productItems[0] = "---请选择---";
		for (int i = 0; i < productItems.length - 1; i++) {
			productItems[i + 1] = SuperManagerSystem.getSingleSystem().getProducts().get(i).getName();
		}
		JComboBox<String> box = new JComboBox<String>(productItems);

		box.setBounds(200, 500, 233, 40);
		box.setFont(fontOfLabel2);
		panel.add(box);

		for (int i = 0; i < labels.length; i++) {
			labels[i] = new JLabel(temp[i], JLabel.CENTER);
			labels[i].setBounds(30, 40 + 50 * (i + 1), 130, 40);
			labels[i].setOpaque(true);
			labels[i].setFont(fontOfLabel2);
			labels[i].setBackground(ZangQing);
			labels[i].setForeground(Color.white);
			panel.add(labels[i]);

			if (i < 6) {
				if (i != 0) {
					texts[i] = new JTextField("");
				} else {
					texts[i] = new JTextField("O" + System.currentTimeMillis());
					texts[i].setEditable(false);
				}

				texts[i].setBounds(200, 40 + 50 * (i + 1), 233, 40);
				texts[i].setFont(fontOfLabel);
				texts[i].setBackground(ZangQing);
				texts[i].setForeground(Color.white);
				panel.add(texts[i]);
			}
		}
		labels[7].setBounds(30, 500, 130, 40);

		ActionListener listener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getActionCommand().equals("提交")) {
					String id = texts[0].getText();
					String productName = (String) box.getSelectedItem();
					String mount = texts[1].getText();
					String deliverTime = texts[2].getText();
					String bidDeadline = texts[3].getText();
					String receiver = texts[4].getText();
					String receiverConMe = texts[5].getText();
					String shippingAdd = rArea.getText();

					if (productName.equals("---请选择---") || mount.equals("") || deliverTime.equals("")
							|| bidDeadline.equals("") || receiver.equals("") || receiverConMe.equals("")
							|| shippingAdd.equals("")) {
						JOptionPane.showMessageDialog(addFrame, "信息不足");
					} else {
						Order newOrder = new Order(id, productName, Integer.parseInt(mount), deliverTime, bidDeadline,
								receiver, receiverConMe, shippingAdd, "已保存", myDealerSystem.getDealer().getId(), "");
						SuperManagerSystem.getSingleSystem().getOrders().add(newOrder);
						upDateTable1();
						JOptionPane.showMessageDialog(addFrame, "添加成功");
						addFrame.setVisible(false);
					}
				}
			}
		};
		JButton submitButton = new JButton("提交");
		submitButton.addActionListener(listener);
		submitButton.setBounds(150, 580, 150, 50);
		submitButton.setBackground(ZangQing);
		submitButton.setFont(fontOfButton);

		panel.add(submitButton);
		addFrame.add(panel);
		addFrame.setVisible(true);
	}

	// 选标函数
	public void checkBidDetails(String id) {
		Order current = myDealerSystem.getOrder(id);
		JFrame temp = new JFrame("");
		temp.setBounds(250, 200, 1040, 500);

		JPanel panel = new JPanel();
		panel.setLayout(null);
		temp.setResizable(false);

		JLabel label = new JLabel("投标信息列表");
		label.setBounds(0, 0, 10000, 60);
		label.setOpaque(true);
		label.setFont(font);
		label.setBackground(ZangQing);
		label.setForeground(Color.white);
		panel.add(label);

		String[] title = { "序号", "投标工厂", "工厂负责人", "承包数量", "投标价格", "是否中标", "操作" };

		String[][] data;
		ArrayList<Bid> bids = current.getBidInfor();
		if (bids != null) {
			data = new String[bids.size()][7];
			int counter = 0;
			for (Bid bid : bids) {
				String factoryId = bid.getFactoryId();
				Factory currentFac = SuperManagerSystem.getSingleSystem().getFactory(factoryId);
				data[counter][0] = "" + (counter + 1);

				data[counter][1] = currentFac.getFactoryName();
				data[counter][2] = currentFac.getManager();
				data[counter][3] = bid.getMount();
				data[counter][4] = bid.getPrice();
				if (current.getWinFacName().contains(factoryId)) {
					data[counter][5] = "是";
					data[counter][6] = "*-" + id;
				} else {
					data[counter][5] = "否";
					data[counter][6] = "选标-" + id + "-" + factoryId;
				}
				counter++;
			}
		} else {
			data = null;
		}

		ActionListener listener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getActionCommand().equals("投标结束")) {
					current.setStatus("投标结束");
					upDateTable1();
				}
				if (e.getActionCommand().equals("自动选标")) {
					autoBid(current);
				}
			}
		};

		JButton FButton = new JButton("投标结束");
		FButton.addActionListener(listener);
		FButton.setBounds(850, 400, 150, 60);
		FButton.setBackground(MoLv);
		FButton.setFont(fontOfButton);

		JButton AButton = new JButton("自动选标");
		AButton.addActionListener(listener);
		AButton.setBounds(700, 400, 150, 60);
		AButton.setBackground(ZangQing);
		AButton.setFont(fontOfButton);

		panel.add(FButton);
		panel.add(AButton);

		tModel2.setDataVector(data, title);

		tableOfInfor.getColumnModel().getColumn(6).setCellRenderer(new MyButtonRender());
		tableOfInfor.getColumnModel().getColumn(6).setCellEditor(new MyButtonEditor());

		tableOfInfor.setRowHeight(100);
		tableOfInfor.setRowHeight(50);
		tableOfInfor.setBackground(ZangQing);
		tableOfInfor.setForeground(Color.WHITE);
		tableOfInfor.setFont(fontOfTableData);

		tableOfInfor.setRowSelectionAllowed(false);
		JTableHeader head = tableOfInfor.getTableHeader();
		head.setPreferredSize(new Dimension(head.getWidth(), 35));
		head.setFont(fontOfTableTitle);

		JScrollPane jScrollPane = new JScrollPane(tableOfInfor);
		jScrollPane.setBounds(20, 100, 1000, 300);
		tableOfInfor.setBounds(20, 100, 1000, 300);

		panel.add(jScrollPane);
		panel.add(jLabel_1);
		temp.add(panel);
		temp.setVisible(true);
	}

	private void autoBid(Order curOrder) {
		// 界面
		JFrame frame = new JFrame();
		frame.setLayout(new BorderLayout());
		frame.setBounds(500, 255, 400, 580);

		JPanel panel = new JPanel();
		panel.setLayout(null);
		frame.setResizable(false);

		JLabel label = new JLabel("自动选标");
		label.setBounds(0, 0, 10000, 60);
		label.setOpaque(true);
		label.setFont(font);
		label.setBackground(ZangQing);
		label.setForeground(Color.white);
		panel.add(label);

		String text = "竞标规则说明：\n" + 
				"  a.多家工厂竞标时，承接数量均达到订单需\n"
				+ "求时，单个报价低者中标。\n" + 
				"  \n" + 
				"  b.多家工厂竞标时，单个报价低者\n"
				+ "中标但其承接数量未达到订单需求\n"
				+ "时将在其余竞标者中选择报价最低\n"
				+ "者，直至满足订单需求。";
		JTextArea rArea = new JTextArea(text);
		rArea.setBounds(20,70,350,350);
		rArea.setFont(fontOfButton);
		
		panel.add(rArea);
		
		// 逻辑
		ActionListener listener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getActionCommand().equals("选标")) {
					ArrayList<Bid> bids = curOrder.getBidInfor();
					if(bids.size() == 0)
						JOptionPane.showMessageDialog(null, "暂无工厂投标");
					else {
					int target = curOrder.getMount();
					ArrayList<String> winFacId = curOrder.getWinFacName();
					ArrayList<Integer> winMount = curOrder.getResultMount();

					ArrayList<Bid> result = quickSort(bids);

					int currentMount = 0;
					int index = 0;
					while (currentMount < target && index < result.size()) {
						Bid curBid = result.get(index);
						index++;
 
						int thisMount = Integer.parseInt(curBid.getMount());
						if (thisMount + currentMount <= target) {
							winFacId.add(curBid.getFactoryId());
							winMount.add(thisMount);
							currentMount += thisMount;
						} else if (thisMount + currentMount > target) {
							int needMount = target - currentMount;
							winFacId.add(curBid.getFactoryId());
							winMount.add(needMount);
							currentMount += needMount;
						}
					}
					curOrder.setStatus("投标结束");
					upDateTable1();
					String message = "";
					ArrayList<String> winFacId2 = curOrder.getWinFacName();
					ArrayList<Integer> winMount2 = curOrder.getResultMount();
					for (int i = 0; i < winFacId2.size(); i++) {
						String name = SuperManagerSystem.getSingleSystem().getFactory(winFacId2.get(i)).getFactoryName();
						message = message + name + "中标 " + winMount2.get(i) + "件\n";
						upDateTable1();
					}
					JOptionPane.showMessageDialog(null, message);
				}
				}
			}
		};

		JButton submitButton = new JButton("选标");
		submitButton.addActionListener(listener);
		submitButton.setBounds(130, 460, 150, 50);
		submitButton.setBackground(ZangQing);
		submitButton.setFont(fontOfButton);

		panel.add(submitButton);
		frame.add(panel);
		frame.setVisible(true);

	}

	public ArrayList<Bid> quickSort(ArrayList<Bid> arr) {
		Bid pivot = arr.get(0);
		ArrayList<Bid> smaller = new ArrayList<>(); 
		ArrayList<Bid> bigger = new ArrayList<>(); 
		for (int i = 1; i < arr.size(); i++) { 
			if (pivot.compareTo(arr.get(i)) == 1 || pivot.compareTo(arr.get(i)) == 0)
				smaller.add(arr.get(i));
			else
				bigger.add(arr.get(i));
		}
		if (smaller.size() > 1)
			quickSort(smaller);
		if (bigger.size() > 1)
			quickSort(bigger);
		arr.removeAll(arr);
		arr.addAll(smaller);
		arr.add(pivot);
		arr.addAll(bigger);
		return arr;
	}

	public void modify(String id) {
		Order currentOrder = myDealerSystem.getOrder(id);
		JFrame modifyFrame = new JFrame("");
		modifyFrame.setBounds(700, 125, 488, 700);

		JPanel panel = new JPanel();
		panel.setLayout(null);
		modifyFrame.setResizable(false);

		JLabel label = new JLabel("新建订单");
		label.setBounds(0, 0, 10000, 60);
		label.setOpaque(true);
		label.setFont(font);
		label.setBackground(ZangQing);
		label.setForeground(Color.white);
		panel.add(label);

		JLabel[] labels = new JLabel[8];
		String[] temp = { "订单编号", "订购数量", "交付日期", "投标截止日期", "收货人", "收货人联系方式", "收货地址", "产品名称" };
		JTextField[] texts = new JTextField[6];

		JTextArea rArea = new JTextArea(10, 3);

		rArea.setBounds(200, 390, 233, 100);
		rArea.setFont(fontOfLabel);
		rArea.setBackground(ZangQing);
		rArea.setForeground(Color.white);
		rArea.setLineWrap(true);
		panel.add(rArea);

		String[] productItems = new String[(SuperManagerSystem.getSingleSystem().getProducts().size()) + 1];

		productItems[0] = "---请选择---";
		for (int i = 0; i < productItems.length - 1; i++) {
			productItems[i + 1] = SuperManagerSystem.getSingleSystem().getProducts().get(i).getName();
		}
		JComboBox<String> box = new JComboBox<String>(productItems);

		box.setBounds(200, 500, 233, 40);
		box.setBackground(ZangQing);
		box.setFont(fontOfLabel2);
		panel.add(box);

		for (int i = 0; i < labels.length; i++) {
			labels[i] = new JLabel(temp[i], JLabel.CENTER);
			labels[i].setBounds(30, 40 + 50 * (i + 1), 130, 40);
			labels[i].setOpaque(true);
			labels[i].setFont(fontOfLabel2);
			labels[i].setBackground(ZangQing);
			labels[i].setForeground(Color.white);
			panel.add(labels[i]);

			if (i < 6) {
				texts[i] = new JTextField("");
				texts[i].setBounds(200, 40 + 50 * (i + 1), 233, 40);
				texts[i].setFont(fontOfLabel);
				texts[i].setBackground(ZangQing);
				texts[i].setForeground(Color.white);
				panel.add(texts[i]);
			}

		}
		texts[0].setText(currentOrder.getId());
		texts[0].setEditable(false);
		texts[1].setText(currentOrder.getMount() + "");
		texts[2].setText(currentOrder.getDeliverTime());
		texts[3].setText(currentOrder.getBidDeadline());
		texts[4].setText(currentOrder.getReceiver());
		texts[5].setText(currentOrder.getReceiverConMe());
		rArea.setText(currentOrder.getShippingAdd());
		box.setSelectedItem(currentOrder.getProductName());

		labels[7].setBounds(30, 500, 130, 40);

		ActionListener listener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getActionCommand().equals("提交")) {
					String productName = (String) box.getSelectedItem();
					String mount = texts[1].getText();
					String deliverTime = texts[2].getText();
					String bidDeadline = texts[3].getText();
					String receiver = texts[4].getText();
					String receiverConMe = texts[5].getText();
					String shippingAdd = rArea.getText();

					if (productName.equals("---请选择---") || mount.equals("") || deliverTime.equals("")
							|| bidDeadline.equals("") || receiver.equals("") || receiverConMe.equals("")
							|| shippingAdd.equals("")) {
						JOptionPane.showMessageDialog(modifyFrame, "信息不足");
					} else {
						currentOrder.setMount(Integer.parseInt(mount));
						currentOrder.setDeliverTime(deliverTime);
						currentOrder.setBidDeadline(bidDeadline);
						currentOrder.setReceiver(receiver);
						currentOrder.setReceiverConMe(receiverConMe);
						currentOrder.setShippingAdd(shippingAdd);
						currentOrder.setProductName(productName);
						upDateTable1();
						JOptionPane.showMessageDialog(modifyFrame, "修改成功");
						modifyFrame.setVisible(false);
					}
				}
			}
		};
		JButton submitButton = new JButton("提交");
		submitButton.addActionListener(listener);
		submitButton.setBounds(150, 580, 150, 50);
		submitButton.setBackground(ZangQing);
		submitButton.setFont(fontOfButton);

		panel.add(submitButton);
		modifyFrame.add(panel);
		modifyFrame.setVisible(true);
	}

	private JScrollPane loadTableData() {
		String[] title = myDealerSystem.getOrderTableTitle();
		String[][] data = myDealerSystem.getOrderTableData();
		tModel.setDataVector(data, title);

		for (int i = 10; i <= 14; i++) {

			table.getColumnModel().getColumn(i).setCellRenderer(new MyButtonRender());
			table.getColumnModel().getColumn(i).setCellEditor(new MyButtonEditor());
		}

		table.setRowHeight(100);
		table.setRowHeight(50);
		table.setBackground(ZangQing);
		table.setForeground(Color.WHITE);
		table.setFont(fontOfTableData);

		table.setRowSelectionAllowed(false);
		JTableHeader head = table.getTableHeader();
		head.setPreferredSize(new Dimension(head.getWidth(), 35));
		head.setFont(fontOfTableTitle);

		JScrollPane jScrollPane = new JScrollPane(table);
		jScrollPane.setBounds(0, 200, 1600, 600);
		table.setBounds(0, 200, 1600, 600);

		return jScrollPane;

	}

	public JPanel loadInterface() {
		ActionListener buttonListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getActionCommand().equals("新建")) {
					add();
				}
			}
		};
		mainPanel = new JPanel();
		mainPanel.setLayout(null);

		addButton.setBackground(MoLv);
		addButton.setForeground(Color.white);
		addButton.setBounds(10, 80, 100, 66);
		addButton.setFont(fontOfButton);
		addButton.addActionListener(buttonListener);

		jLabel_1.setBounds(0, 0, 20000, 60);
		jLabel_1.setOpaque(true);
		jLabel_1.setFont(font);
		jLabel_1.setBackground(ZangQing);
		jLabel_1.setForeground(Color.white);

		mainPanel.add(jLabel_1);
		mainPanel.add(addButton);
		mainPanel.add(loadTableData());
		mainPanel.setBackground(Color.white);
		mainPanel.setBorder(BorderFactory.createLineBorder(ZangQing));
		return mainPanel;

	}

	@SuppressWarnings("serial")
	class MyButtonEditor extends DefaultCellEditor {
		private JPanel panel;
		private JButton button;

		public MyButtonEditor() {
			super(new JTextField());
			this.setClickCountToStart(1);
			this.initButton();
			this.initPanel();
			this.panel.add(this.button);
		}

		private void initButton() {
			this.button = new JButton();
			this.button.setBounds(0, 0, 170, 50);
			this.button.setFont(fontOfButton);

			this.button.setBackground(ZangQing);
			this.button.setForeground(Color.white);
			ActionListener listener = new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					String temp = e.getActionCommand();
					String[] mess = temp.split("-");
					String buttonType = mess[0];
					String id = mess[1];
					Order current = SuperManagerSystem.getSingleSystem().getOrder(id);

					if (buttonType.equals("投标详情")) {
						checkBidDetails(id);
					} else if (buttonType.equals("发布")) {
						current.setStatus("已发布");
						upDateTable1();
						JOptionPane.showMessageDialog(null, "订单" + id + "发布成功");
					} else if (buttonType.equals("收货")) {
						current.setStatus("已完成");
						upDateTable1();
						JOptionPane.showMessageDialog(null, "订单" + id + "收货成功");
					} else if (buttonType.equals("修改")) {
						modify(id);
						upDateTable1();
					} else if (buttonType.equals("删除")) {
						SuperManagerSystem.getSingleSystem().deleteOrder(id);
						upDateTable1();
					} else if (buttonType.equals("*")) {
						JOptionPane.showMessageDialog(null, "订单" + id + "禁用此操作");
					}
					if (buttonType.equals("选标")) {
						String factoryid = mess[2];
						Order order = SuperManagerSystem.getSingleSystem().getOrder(id);
						int mount = order.getMount();
						int currentMount = 0;

						for (String factoryId : order.getWinFacName()) {
							for (Bid bid : order.getBidInfor()) {
								if (factoryId.equals(bid.getFactoryId())) {
									int m = Integer.parseInt(bid.getMount());
									currentMount += m;
								}
							}
						}
						if (currentMount < mount) {
							order.getWinFacName().add(factoryid);
							JOptionPane.showMessageDialog(null, "订单" + id + "选标成功");
							String[] title = { "序号", "投标工厂", "工厂负责人", "承包数量", "投标价格", "是否中标", "操作" };

							ArrayList<Bid> bids = current.getBidInfor();
							String[][] data = new String[bids.size()][7];
							int counter = 0;
							for (Bid bid : bids) {
								String factoryId = bid.getFactoryId();
								Factory currentFac = SuperManagerSystem.getSingleSystem().getFactory(factoryId);
								data[counter][0] = "" + (counter + 1);

								data[counter][1] = currentFac.getFactoryName();
								data[counter][2] = currentFac.getManager();
								data[counter][3] = bid.getMount();
								data[counter][4] = bid.getPrice();
								if (current.getWinFacName().contains(factoryId)) {
									data[counter][5] = "是";
									data[counter][6] = "*-" + id;
								} else {
									data[counter][5] = "否";
									data[counter][6] = "选标-" + id + "-" + factoryId;
								}
								counter++;
							}

							upDateTable1();
							tModel2.setDataVector(data, title);

							tableOfInfor.getColumnModel().getColumn(6).setCellRenderer(new MyButtonRender());
							tableOfInfor.getColumnModel().getColumn(6).setCellEditor(new MyButtonEditor());
						} else {
							JOptionPane.showMessageDialog(null, "中标数量已达订单数量,无需投标");
						}
					}
				}
			};
			button.addActionListener(listener);
		}

		private void initPanel() {
			this.panel = new JPanel();
			this.panel.setLayout(null);
		}

		@Override
		public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row,
				int column) {

			this.button.setText(value == null ? "" : String.valueOf(value));

			return this.panel;
		}

		@Override
		public Object getCellEditorValue() {
			return this.button.getText();
		}
	}

	public void upDateTable1() {
		String[] title = myDealerSystem.getOrderTableTitle();
		String[][] data = myDealerSystem.getOrderTableData();
		tModel.setDataVector(data, title);
		for (int i = 10; i <= 14; i++) {

			table.getColumnModel().getColumn(i).setCellRenderer(new MyButtonRender());
			table.getColumnModel().getColumn(i).setCellEditor(new MyButtonEditor());
		}
	}
}
