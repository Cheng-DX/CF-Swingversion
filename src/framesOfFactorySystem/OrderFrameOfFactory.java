package framesOfFactorySystem;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Panel;
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
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import entity.Bid;
import entity.Equipment;
import entity.Order;
import systems.MyFactorySystem;
import systems.SuperManagerSystem;
import util.MyButtonRender;

public class OrderFrameOfFactory {

	private MyFactorySystem myFactorySystem;

	public OrderFrameOfFactory(String factoryId) {
		myFactorySystem = new MyFactorySystem(factoryId);
	}

	private JFrame orderInterface;
	private DefaultTableModel tModel = new DefaultTableModel();
	private JTable table = new JTable(tModel);
	private JLabel jLabel_1 = new JLabel("采购订单列表");
	private JButton addButton = new JButton("添加生产设备");
	private JButton submitButton = new JButton("确定");
	private JPanel mainPanel;

	// 一些工具
	private static final Color ZangQing = new Color(0, 127, 174);
	private Font font = new Font("微软雅黑", JFrame.ALLBITS, 28);

	private Font fontOfLabel2 = new Font("微软雅黑", JFrame.ALLBITS, 18);
	private Font fontOfLabel3 = new Font("微软雅黑", JFrame.ALLBITS, 23);
	private Font fontOfButton = new Font("微软雅黑", JFrame.ALLBITS, 20);
	private Font fontOfTableTitle = new Font("等线", Font.PLAIN, 18);
	private Font fontOfTableData = new Font("等线", Font.PLAIN, 18);

	private JScrollPane loadTableData() {
		String[] title = myFactorySystem.getOrderTableTitle();
		String[][] data = myFactorySystem.getOrderTableData();
		tModel.setDataVector(data, title);

		for (int i = 10; i <= 13; i++) {
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

	public void schedule(String orderId) {

		Order currentOrder = myFactorySystem.getOrder(orderId);
		JFrame scheduleFrame = new JFrame();
		Panel panel = new Panel(null);
		scheduleFrame.setBounds(250, 130, 1200, 500);
		scheduleFrame.setResizable(false);

		int myMount = 0;
		ArrayList<String> wins = currentOrder.getWinFacName();
		for(int i=0;i<wins.size();i++) {
			if(wins.get(i).equals(myFactorySystem.getFactory().getId())) {
				myMount = currentOrder.getResultMount().get(i);
			}
			
		}
		
		JLabel jLabel_0 = new JLabel("排产   (注意:设备名称列表中仅会显示您的可用设备中,具备该产品生产能力的设备) ");
		JLabel[] jLabels1 = new JLabel[5];
		JLabel[] jLabels2 = new JLabel[5];

		ArrayList<Equipment> matchedEqs = myFactorySystem.getMatchedEqs(orderId);
		String[] equipmentItems = new String[matchedEqs.size() + 1];
		JTextField[] fields = new JTextField[5];
		equipmentItems[0] = "---请选择---";
		for (int i = 0; i < equipmentItems.length - 1; i++) {
			equipmentItems[i + 1] = matchedEqs.get(i).getName();
		}
		JComboBox<String> box1 = new JComboBox<String>(equipmentItems);
		JComboBox<String> box2 = new JComboBox<String>(equipmentItems);
		JComboBox<String> box3 = new JComboBox<String>(equipmentItems);
		JComboBox<String> box4 = new JComboBox<String>(equipmentItems);
		JComboBox<String> box5 = new JComboBox<String>(equipmentItems);

		for (int i = 0; i < 3; i++) {
			jLabels1[i] = new JLabel("设备名称:", JLabel.CENTER);
			jLabels1[i].setBounds(20, 150 + (i + 1) * 55, 100, 45);
			jLabels1[i].setFont(fontOfLabel2);
			jLabels1[i].setOpaque(true);
			jLabels1[i].setBackground(ZangQing);
			jLabels1[i].setForeground(Color.white);
			panel.add(jLabels1[i]);

			jLabels2[i] = new JLabel("生产时长(小时):", JLabel.CENTER);
			jLabels2[i].setBounds(540, 150 + (i + 1) * 55, 180, 45);
			jLabels2[i].setFont(fontOfLabel2);
			jLabels2[i].setOpaque(true);
			jLabels2[i].setBackground(ZangQing);
			jLabels2[i].setForeground(Color.white);
			panel.add(jLabels2[i]);
		}
		box1.setBounds(140, 205, 340, 45);
		box1.setBackground(ZangQing);
		box1.setFont(fontOfLabel3);
		box2.setBounds(140, 260, 340, 45);
		box2.setBackground(ZangQing);
		box2.setFont(fontOfLabel3);

		box3.setBounds(140, 315, 340, 45);
		box3.setBackground(ZangQing);
		box3.setFont(fontOfLabel3);

		box4.setBounds(140, 370, 340, 45);
		box4.setBackground(ZangQing);
		box4.setFont(fontOfLabel3);

		box5.setBounds(140, 425, 340, 45);
		box5.setBackground(ZangQing);
		box5.setFont(fontOfLabel3);

		JTextField textField_1 = new JTextField(currentOrder.getId());
		JTextField textField_2 = new JTextField(
				"商家要求您生产 " + currentOrder.getProductName() + " " + myMount + " 个");
		for (int i = 0; i < fields.length; i++) {
			fields[i] = new JTextField("");
			fields[i].setBounds(740, 150 + 55 * (i + 1), 340, 45);
			fields[i].setBackground(ZangQing);
			fields[i].setForeground(Color.WHITE);
			fields[i].setFont(fontOfLabel3);
		}

		textField_1.setBounds(140, 70, 340, 45);
		textField_1.setFont(fontOfLabel2);
		textField_1.setBackground(ZangQing);
		textField_1.setForeground(Color.white);
		textField_1.setEditable(false);
		textField_2.setBounds(740, 70, 340, 45);
		textField_2.setFont(fontOfLabel2);
		textField_2.setBackground(ZangQing);
		textField_2.setForeground(Color.white);
		textField_2.setEditable(false);

		jLabel_0.setBounds(0, 0, 20000, 60);
		jLabel_0.setOpaque(true);
		jLabel_0.setFont(font);
		jLabel_0.setBackground(ZangQing);
		jLabel_0.setForeground(Color.white);

		ActionListener buttonListener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getActionCommand().equals("确定")) {
					String[] temp = new String[3];
					temp[0] = (String) box1.getSelectedItem();
					temp[1] = (String) box2.getSelectedItem();
					temp[2] = (String) box3.getSelectedItem();
					int counter = 0;
					for (int i = 0; i < 3; i++) {
						String name = temp[i];
						String time = fields[i].getText();
						if ((!name.equals("---请选择---")) && (!time.equals(""))) {
							Equipment currentEq = myFactorySystem.getEquipmentByName(name);
							currentOrder.getEquipments().add(currentEq.getId());
							currentEq.setEquipmentStatus("生产中");
							scheduleFrame.setVisible(false);
							counter++;
							JOptionPane.showMessageDialog(orderInterface, "信息已保存");
						}
					}
					if (counter == 0) {
						JOptionPane.showMessageDialog(null, "未排产");

					} else {
						currentOrder.setStatus("已排产");
					}
				}
			}
		};

		submitButton.setBackground(ZangQing);
		submitButton.setForeground(Color.white);
		submitButton.setBounds(1000, 420, 100, 40);
		submitButton.setFont(fontOfButton);
		submitButton.addActionListener(buttonListener);
		panel.add(jLabel_0);
		panel.add(jLabel_1);
		panel.add(textField_1);
		panel.add(textField_2);
		panel.add(box1);
		panel.add(box2);
		panel.add(box3);

		panel.add(fields[0]);
		panel.add(fields[1]);
		panel.add(fields[2]);

		panel.add(addButton);
		panel.add(submitButton);
		panel.setBackground(Color.white);

		scheduleFrame.add(panel);
		scheduleFrame.setVisible(true);
	}

	public JPanel loadInterface() {
		mainPanel = new JPanel();
		mainPanel.setLayout(null);

		jLabel_1.setBounds(0, 0, 20000, 60);
		jLabel_1.setOpaque(true);
		jLabel_1.setFont(font);
		jLabel_1.setBackground(ZangQing);
		jLabel_1.setForeground(Color.white);

		mainPanel.add(jLabel_1);
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

					if (buttonType.equals("投标")) {
						boolean flag = true;
						ArrayList<Bid> temp2 = current.getBidInfor();
						if (temp2 == null) {
							flag = true;
						} else {
							for (Bid bid : temp2) {
								if (bid.getFactoryId().equals(myFactorySystem.getFactory().getId())) {
									flag = false;
								}
							}
						}

						if (!flag) {
							JOptionPane.showMessageDialog(null, "订单" + id + "请勿重复投标");
						} else {
							JFrame tempFrame = new JFrame();
							tempFrame.setBounds(800, 300, 400, 400);
							tempFrame.setResizable(false);
							JPanel panel = new JPanel();
							panel.setLayout(null);

							JLabel label = new JLabel("为订单"+ current.getId() +"投标", JLabel.CENTER);
							label.setBounds(0, 0, 400, 60);
							label.setOpaque(true);
							label.setFont(font);
							label.setBackground(ZangQing);
							label.setForeground(Color.white);

							JLabel label1 = new JLabel("投标单价:");
							label1.setOpaque(true);
							label1.setBounds(10, 70, 130, 60);
							label1.setFont(font);
							label1.setBackground(ZangQing);
							label1.setForeground(Color.white);
							
							JTextField field = new JTextField("");
							field.setBounds(160, 70, 200, 60);
							field.setBackground(ZangQing);
							field.setForeground(Color.WHITE);
							field.setFont(font);
							
							JLabel label2 = new JLabel("承担数量:");
							label2.setBounds(10, 170, 130, 60);
							label2.setFont(font);
							label2.setBackground(ZangQing);
							label2.setForeground(Color.white);
							label2.setOpaque(true);
							JTextField field2 = new JTextField("");
							field2.setBounds(160, 170, 200, 60);
							field2.setBackground(ZangQing);
							field2.setForeground(Color.WHITE);
							field2.setFont(font);

							ActionListener listener = new ActionListener() {

								@Override
								public void actionPerformed(ActionEvent e) {
									if (e.getActionCommand().equals("投标")) {
										if (field.getText().equals("") || field2.getText().equals("")) {
											JOptionPane.showMessageDialog(tempFrame, "未填写完整");
										} else {
											String price = field.getText();
											String mount = field2.getText();
											Bid newbid = new Bid(myFactorySystem.getFactory().getId(), price, mount);
											current.getBidInfor().add(newbid);
											JOptionPane.showMessageDialog(null, "投标成功");
											tempFrame.setVisible(false);
										}
									}
								}
							};
							JButton submitButton = new JButton("投标");
							submitButton.setBounds(120, 280, 150, 50);
							submitButton.setFont(fontOfButton);
							submitButton.setBackground(ZangQing);
							submitButton.addActionListener(listener);
							panel.add(label);
							panel.add(field);
							panel.add(field2);
							panel.add(label1);
							panel.add(label2);
							panel.add(submitButton);
							panel.setBackground(Color.white);
							tempFrame.add(panel);
							tempFrame.setVisible(true);

						}
					} else if (buttonType.equals("排产")) {
						schedule(id);
						upDateTable1();
					} else if(buttonType.equals("您未中标")) {
						JOptionPane.showMessageDialog(null, "很遗憾，您未中标");
					} else if (buttonType.equals("完工")) {
						for (String each : current.getEquipments()) {
							Equipment equipment = SuperManagerSystem.getSingleSystem().getEquipment(each);

							equipment.setEquipmentStatus("闲置中");
						}
						current.setStatus("已完工");
						upDateTable1();
						JOptionPane.showMessageDialog(null, "订单" + id + "已完工");
					} else if (buttonType.equals("发货")) {
						current.setStatus("已发货");
						upDateTable1();
						JOptionPane.showMessageDialog(null, "订单" + id + "发货成功");
					} else if (buttonType.equals("*")) {
						JOptionPane.showMessageDialog(null, "订单" + id + "禁用此操作");
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
		String[] title = myFactorySystem.getOrderTableTitle();
		String[][] data = myFactorySystem.getOrderTableData();
		tModel.setDataVector(data, title);
		for (int i = 10; i <= 13; i++) {

			table.getColumnModel().getColumn(i).setCellRenderer(new MyButtonRender());
			table.getColumnModel().getColumn(i).setCellEditor(new MyButtonEditor());
		}
	}

}
