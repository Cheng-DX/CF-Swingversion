package framesOfSuperManagerSystem;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import entity.Bid;
import entity.Dealer;
import entity.Equipment;
import entity.Factory;
import entity.FactoryManager;
import entity.Order;
import entity.User;
import systems.MyDealerSystem;
import systems.MyFactorySystem;
import systems.SuperManagerSystem;
import util.MyButtonRender;

@SuppressWarnings("serial")
public class UserFrame extends JFrame {
	private JButton addButton = new JButton("新增");
	private JButton searchButton = new JButton("检索");

	private DefaultTableModel tModel1 = new DefaultTableModel();
	private JTable tableOfLoading = new JTable(tModel1);

	private DefaultTableModel tModel2 = new DefaultTableModel();
	private JTable tableOfSearchResult = new JTable(tModel2);

	private JLabel jLabel_1 = new JLabel("用户信息列表");
	private JPanel userPanel = new JPanel();

	// 一些工具
	private static final Color ZangQing = new Color(0, 127, 174);
	private Font font = new Font("微软雅黑", ALLBITS, 28);
	private Font fontOfButton = new Font("微软雅黑", ALLBITS, 20);
	private Font fontOfLabel = new Font("微软雅黑", ALLBITS, 15);
	private Font fontOfTableTitle = new Font("等线", Font.PLAIN, 18);
	private Font fontOfTableData = new Font("等线", Font.PLAIN, 18);

	public void add() {
		JFrame addFrame = new JFrame("添加用户");
		addFrame.setLayout(new BorderLayout());
		addFrame.setBounds(500, 255, 488, 720);

		JPanel panel = new JPanel();
		panel.setLayout(null);
		addFrame.setResizable(false);

		JLabel label = new JLabel("新增用户");
		label.setBounds(0, 0, 10000, 60);
		label.setOpaque(true);
		label.setFont(font);
		label.setBackground(ZangQing);
		label.setForeground(Color.white);
		panel.add(label);
		JLabel[] labels = new JLabel[9];
		String[] temp = { "*用户类型", "", "*账号", "*密码", "*再次输入密码", "*工厂/经销商名称", "真实姓名", "联系方式", "简介" };

		JTextField[] jTextFields = new JTextField[6];
		JTextArea rArea = new JTextArea(10, 5);
		JRadioButton typeButton1 = new JRadioButton("云工厂管理员");
		JRadioButton typeButton2 = new JRadioButton("经销商");
		ButtonGroup bGroup = new ButtonGroup();
		bGroup.add(typeButton1);
		bGroup.add(typeButton2);

		for (int i = 1; i <= 9; i++) {
			if (i != 2) {
				labels[i - 1] = new JLabel(temp[i - 1], JLabel.CENTER);
				labels[i - 1].setBounds(30, 40 + 50 * i, 120, 40);
				labels[i - 1].setOpaque(true);
				labels[i - 1].setFont(fontOfLabel);
				labels[i - 1].setBackground(ZangQing);
				labels[i - 1].setForeground(Color.white);

				panel.add(labels[i - 1]);
			}
			if (i == 1) {
				typeButton1.setBounds(200, 40 + 50 * i, 233, 40);
				typeButton1.setFont(fontOfLabel);
				panel.add(typeButton1);
			} else if (i == 2) {
				typeButton2.setBounds(200, 40 + 50 * i, 233, 40);
				typeButton2.setFont(fontOfLabel);
				panel.add(typeButton2);
			} else if (i >= 3 && i <= 8) {
				jTextFields[i - 3] = new JTextField("");
				jTextFields[i - 3].setBounds(200, 40 + 50 * i, 233, 40);
				jTextFields[i - 3].setFont(fontOfLabel);
				jTextFields[i - 3].setBackground(ZangQing);
				jTextFields[i - 3].setForeground(Color.white);

				panel.add(jTextFields[i - 3]);
			} else if (i == 9) {
				rArea.setBounds(200, 40 + 50 * i, 233, 100);
				rArea.setFont(fontOfLabel);
				rArea.setBackground(ZangQing);
				rArea.setForeground(Color.white);
				rArea.setLineWrap(true);
				panel.add(rArea);
			}
		}

		ActionListener listener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getActionCommand().equals("提交")) {
					String id;
					String account = jTextFields[0].getText();
					String password1 = jTextFields[1].getText();
					String password2 = jTextFields[2].getText();
					String adminiName = jTextFields[3].getText();
					String accType;
					if (typeButton1.isSelected()) {
						accType = "云工厂管理员";
					} else {
						accType = "经销商";
					}
					String realName = jTextFields[4].getText();
					String conMeth = jTextFields[5].getText();
					String intro = rArea.getText();

					if ((!typeButton1.isSelected() && !typeButton2.isSelected()) || jTextFields[0].getText().equals("")
							|| jTextFields[1].getText().equals("") || jTextFields[2].getText().equals("")
							|| jTextFields[3].getText().equals("")) {
						JOptionPane.showMessageDialog(addFrame, "信息不足(标*为必填选项)");

					} else if (SuperManagerSystem.getSingleSystem().hasThisAccount(account)) {
						JOptionPane.showMessageDialog(addFrame, "账户已存在");

					} else if (!password1.equals(password2)) {
						JOptionPane.showMessageDialog(addFrame, "两次输入的密码不一致");

					} else {
						if (accType.equals("云工厂管理员")) {
							id = "M" + System.currentTimeMillis();
							String factoryId = "F" + System.currentTimeMillis();
							FactoryManager newManager = new FactoryManager(id, account, password1, adminiName, accType,
									realName, conMeth, intro, factoryId);
							SuperManagerSystem.getSingleSystem().getUsersDB().add(newManager);
							JOptionPane.showMessageDialog(null, "新云工厂管理员添加完成！\n 请牢记您的账号和密码", "成功",
									JOptionPane.INFORMATION_MESSAGE);

							Factory newFactory = new Factory(factoryId, adminiName, intro, realName, conMeth, account);
							SuperManagerSystem.getSingleSystem().getFactories().add(newFactory);

							JOptionPane.showMessageDialog(null, "已自动生成您的云工厂！", "成功", JOptionPane.INFORMATION_MESSAGE);
							addFrame.setVisible(false);
						} else {
							id = "D" + System.currentTimeMillis();
							Dealer newDealer = new Dealer(id, account, password1, adminiName, accType, realName,
									conMeth, intro);
							SuperManagerSystem.getSingleSystem().getUsersDB().add(newDealer);
							JOptionPane.showMessageDialog(null, "新经销商添加完成！\n 请牢记您的账号和密码", "成功",
									JOptionPane.INFORMATION_MESSAGE);
							addFrame.setVisible(false);
						}
						String[][] data = SuperManagerSystem.getSingleSystem().getUserTableData();
						String[] title = SuperManagerSystem.getSingleSystem().getUserTableTitle();
						tModel1.setDataVector(data, title);
						tableOfLoading.getColumnModel().getColumn(7).setCellRenderer(new MyButtonRender());
						tableOfLoading.getColumnModel().getColumn(7).setCellEditor(new MyButtonEditor());
						tableOfLoading.getColumnModel().getColumn(8).setCellRenderer(new MyButtonRender());
						tableOfLoading.getColumnModel().getColumn(8).setCellEditor(new MyButtonEditor());
					}
				}
			}
		};
		JButton submitButton = new JButton("提交");
		submitButton.addActionListener(listener);
		submitButton.setBounds(150, 620, 150, 50);
		submitButton.setBackground(ZangQing);
		submitButton.setFont(fontOfButton);

		panel.add(submitButton);
		addFrame.add(panel);
		addFrame.setVisible(true);
	}

	public void search() {
		JFrame addFrame = new JFrame("查询");
		addFrame.setLayout(new BorderLayout());
		addFrame.setBounds(500, 255, 488, 720);

		JPanel panel = new JPanel();
		panel.setLayout(null);
		addFrame.setResizable(false);

		JLabel label = new JLabel("查询(支持模糊查找)");
		label.setBounds(0, 0, 10000, 60);
		label.setOpaque(true);
		label.setFont(font);
		label.setBackground(ZangQing);
		label.setForeground(Color.white);
		panel.add(label);
		JLabel[] labels = new JLabel[9];
		String[] temp = { "用户类型", "", "账号", "id", "", "工厂/经销商名称", "真实姓名", "联系方式", "简介" };

		JTextField[] jTextFields = new JTextField[6];
		JTextArea rArea = new JTextArea(10, 5);
		JRadioButton typeButton1 = new JRadioButton("云工厂管理员");
		JRadioButton typeButton2 = new JRadioButton("经销商");
		ButtonGroup bGroup = new ButtonGroup();
		bGroup.add(typeButton1);
		bGroup.add(typeButton2);

		for (int i = 1; i <= 9; i++) {
			if (i != 2) {
				labels[i - 1] = new JLabel(temp[i - 1], JLabel.CENTER);
				labels[i - 1].setBounds(30, 40 + 50 * i, 120, 40);
				labels[i - 1].setOpaque(true);
				labels[i - 1].setFont(fontOfLabel);
				labels[i - 1].setBackground(ZangQing);
				labels[i - 1].setForeground(Color.white);

				panel.add(labels[i - 1]);
			}
			if (i == 1) {
				typeButton1.setBounds(200, 40 + 50 * i, 233, 40);
				typeButton1.setFont(fontOfLabel);
				panel.add(typeButton1);
			} else if (i == 2) {
				typeButton2.setBounds(200, 40 + 50 * i, 233, 40);
				typeButton2.setFont(fontOfLabel);
				panel.add(typeButton2);
			} else if (i >= 3 && i <= 8) {
				jTextFields[i - 3] = new JTextField("");
				jTextFields[i - 3].setBounds(200, 40 + 50 * i, 233, 40);
				jTextFields[i - 3].setFont(fontOfLabel);
				jTextFields[i - 3].setBackground(ZangQing);
				jTextFields[i - 3].setForeground(Color.white);

				panel.add(jTextFields[i - 3]);
			} else if (i == 9) {
				rArea.setBounds(200, 40 + 50 * i, 233, 100);
				rArea.setFont(fontOfLabel);
				rArea.setBackground(ZangQing);
				rArea.setForeground(Color.white);
				rArea.setLineWrap(true);
				panel.add(rArea);
			}
		}
		ActionListener listener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// 查找
				JFrame temp = new JFrame();

				temp.setBounds(400, 400, 1200, 500);
				String id = jTextFields[1].getText();
				String account = jTextFields[0].getText();
				String adminiName = jTextFields[3].getText();
				String accType;
				if (typeButton1.isSelected()) {
					accType = "云工厂管理员";
				} else {
					accType = "经销商";
				}
				String realName = jTextFields[4].getText();
				String conMeth = jTextFields[5].getText();
				String intro = rArea.getText();
				User target = new User(id, account, "", adminiName, accType, realName, conMeth, intro);
				
				ArrayList<User> usersOfResult = SuperManagerSystem.getSingleSystem().searchUser(target);
				
				String[][] data = new String[usersOfResult.size()][9];

				for (int i = 0; i < usersOfResult.size(); i++) {

					User temp1 = usersOfResult.get(i);
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
				String[] title = SuperManagerSystem.getSingleSystem().getUserTableTitle();
				tModel2.setDataVector(data, title);

				tableOfSearchResult.getColumnModel().getColumn(7).setCellRenderer(new MyButtonRender());
				tableOfSearchResult.getColumnModel().getColumn(7).setCellEditor(new MyButtonEditor());
				tableOfSearchResult.getColumnModel().getColumn(8).setCellRenderer(new MyButtonRender());
				tableOfSearchResult.getColumnModel().getColumn(8).setCellEditor(new MyButtonEditor());

				tableOfSearchResult.setRowHeight(100);
				tableOfSearchResult.setRowHeight(50);
				tableOfSearchResult.setBackground(ZangQing);
				tableOfSearchResult.setForeground(Color.WHITE);
				tableOfSearchResult.setFont(fontOfTableData);

				tableOfSearchResult.setRowSelectionAllowed(false);
				JTableHeader head = tableOfSearchResult.getTableHeader();
				head.setPreferredSize(new Dimension(head.getWidth(), 35));
				head.setFont(fontOfTableTitle);

				JScrollPane jScrollPane = new JScrollPane(tableOfSearchResult);
				jScrollPane.setBounds(0, 200, 1200, 275);
				tableOfSearchResult.setBounds(0, 200, 1200, 275);

				temp.add(jScrollPane);
				temp.setVisible(true);
			}
		};
		JButton submitButton = new JButton("查询");
		submitButton.addActionListener(listener);
		submitButton.setBounds(150, 620, 150, 50);
		submitButton.setBackground(ZangQing);
		submitButton.setFont(fontOfButton);
		
		panel.add(submitButton);

		addFrame.add(panel);
		addFrame.setVisible(true);
	}

	public boolean delete(String userId) {
		if (userId.startsWith("M")) {
			FactoryManager currentUser = (FactoryManager) SuperManagerSystem.getSingleSystem().getUser(userId);
			boolean flag = true;
			String factoryId = currentUser.getFactoryId();
			MyFactorySystem faSystem = new MyFactorySystem(factoryId);
			for (Order order : faSystem.getMyOrders()) {
				if (order.getWinFacName().contains(factoryId) && (!order.getStatus().equals("已完成"))) {
					flag = false;
				}
			}
			if (!flag) {
				return false;
			} else {
				SuperManagerSystem.getSingleSystem().deleteUser(userId);
				SuperManagerSystem.getSingleSystem().deleteFactory(factoryId);
				for (Order order : faSystem.getMyOrders()) {
					for(Bid bid : order.getBidInfor()) {
						if(bid.getFactoryId().equals(factoryId))
							order.getBidInfor().remove(bid);
					}
				}
				for (Equipment equipment : faSystem.getMyEquipments()) {
					if (equipment.getLeaseStatus().equals("工厂设备")) {
						SuperManagerSystem.getSingleSystem().deleteEquipment(equipment.getId());

					} else if (equipment.getLeaseStatus().equals("已被租用")) {
						SuperManagerSystem.getSingleSystem().getEquipment(equipment.getId()).setLeaseStatus("自由设备");
						SuperManagerSystem.getSingleSystem().getEquipment(equipment.getId()).setBelongingFactory("");

					}
				}
				return true;
			}

		} else {
			MyDealerSystem deSys = new MyDealerSystem(userId);
			boolean flag = true;
			for (Order order : deSys.getMyOrders()) {
				if ((!order.getStatus().equals("已保存")) && (!order.getStatus().equals("已完成"))) {
					flag = false;
				}
			}

			if (!flag) {
				return false;
			} else {
				SuperManagerSystem.getSingleSystem().deleteUser(userId);
				for (Order order : deSys.getMyOrders()) {
					SuperManagerSystem.getSingleSystem().deleteOrder(order.getId());
				}
				return true;
			}
		}
	}

	public void modify(String id) {
		User currentUser = SuperManagerSystem.getSingleSystem().getUser(id);
		JFrame modifyInterface = new JFrame();
		modifyInterface.setLayout(new BorderLayout());
		modifyInterface.setBounds(500, 255, 488, 720);

		JPanel panel = new JPanel();
		panel.setLayout(null);
		modifyInterface.setResizable(false);

		JLabel label = new JLabel("修改用户信息");
		label.setBounds(0, 0, 10000, 60);
		label.setOpaque(true);
		label.setFont(font);
		label.setBackground(ZangQing);
		label.setForeground(Color.white);
		panel.add(label);
		JLabel[] labels = new JLabel[9];
		String[] temp = { "用户类型", "", "账号", "*密码", "*再次输入密码", "*工厂/经销商名称", "真实姓名", "联系方式", "简介" };

		JTextField[] jTextFields = new JTextField[6];
		JTextArea rArea = new JTextArea(10, 5);
		JRadioButton typeButton1 = new JRadioButton("云工厂管理员");
		JRadioButton typeButton2 = new JRadioButton("经销商");
		ButtonGroup bGroup = new ButtonGroup();
		bGroup.add(typeButton1);
		bGroup.add(typeButton2);

		for (int i = 1; i <= 9; i++) {
			if (i != 2) {
				labels[i - 1] = new JLabel(temp[i - 1], JLabel.CENTER);
				labels[i - 1].setBounds(30, 40 + 50 * i, 120, 40);
				labels[i - 1].setOpaque(true);
				labels[i - 1].setFont(fontOfLabel);
				labels[i - 1].setBackground(ZangQing);
				labels[i - 1].setForeground(Color.white);

				panel.add(labels[i - 1]);
			}
			if (i == 1) {
				typeButton1.setBounds(200, 40 + 50 * i, 233, 40);
				typeButton1.setFont(fontOfLabel);
				panel.add(typeButton1);
			} else if (i == 2) {
				typeButton2.setBounds(200, 40 + 50 * i, 233, 40);
				typeButton2.setFont(fontOfLabel);
				panel.add(typeButton2);
			} else if (i >= 3 && i <= 8) {
				jTextFields[i - 3] = new JTextField("");
				jTextFields[i - 3].setBounds(200, 40 + 50 * i, 233, 40);
				jTextFields[i - 3].setFont(fontOfLabel);
				jTextFields[i - 3].setBackground(ZangQing);
				jTextFields[i - 3].setForeground(Color.white);

				panel.add(jTextFields[i - 3]);
			} else if (i == 9) {
				rArea.setBounds(200, 40 + 50 * i, 233, 100);
				rArea.setFont(fontOfLabel);
				rArea.setBackground(ZangQing);
				rArea.setForeground(Color.white);
				rArea.setLineWrap(true);
				panel.add(rArea);
			}
		}
		if (currentUser.getAccount_type().equals("云工厂管理员")) {
			typeButton1.setSelected(true);
			typeButton2.setSelected(false);
			typeButton2.setEnabled(false);
		} else if(currentUser.getAccount_type().equals("经销商")){
			typeButton2.setSelected(true);
			typeButton1.setSelected(false);
			typeButton1.setEnabled(false);
		} else {
			typeButton2.setSelected(false);
			typeButton1.setSelected(false);
			typeButton1.setEnabled(false);
			typeButton2.setEnabled(false);
		}
		jTextFields[0].setText(currentUser.getAccount());
		jTextFields[0].setEditable(false);
		jTextFields[1].setText(currentUser.getPassword());
		jTextFields[2].setText(currentUser.getPassword());
		jTextFields[3].setText(currentUser.getAdministrator_name());
		jTextFields[4].setText(currentUser.getReal_name());
		jTextFields[5].setText(currentUser.getContact_method());
		rArea.setText(currentUser.getIntroduction());

		ActionListener listener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getActionCommand().equals("提交")) {
					String password1 = jTextFields[1].getText();
					String password2 = jTextFields[2].getText();
					String adminiName = jTextFields[3].getText();
					String realName = jTextFields[4].getText();
					String conMeth = jTextFields[5].getText();
					String intro = rArea.getText();

					if (jTextFields[1].getText().equals("") || jTextFields[2].getText().equals("")
							|| jTextFields[3].getText().equals("")) {
						JOptionPane.showMessageDialog(modifyInterface, "信息不足(标*为必填选项)");

					} else if (!password1.equals(password2)) {
						JOptionPane.showMessageDialog(modifyInterface, "两次输入的密码不一致");

					} else {
						currentUser.setAdministrator_name(adminiName);
						currentUser.setContact_method(conMeth);
						currentUser.setIntroduction(intro);
						currentUser.setReal_name(realName);
						currentUser.setPassword(password1);
						if (currentUser instanceof FactoryManager) {
							FactoryManager facMa = (FactoryManager) currentUser;
							Factory temp = SuperManagerSystem.getSingleSystem().getFactory(facMa.getFactoryId());
							temp.setFactoryName(adminiName);
							temp.setContection(conMeth);
							temp.setIntro(intro);
							temp.setManager(realName);
						}

						modifyInterface.setVisible(false);
						String[][] data = SuperManagerSystem.getSingleSystem().getUserTableData();
						String[] title = SuperManagerSystem.getSingleSystem().getUserTableTitle();
						tModel1.setDataVector(data, title);
						tableOfLoading.getColumnModel().getColumn(7).setCellRenderer(new MyButtonRender());
						tableOfLoading.getColumnModel().getColumn(7).setCellEditor(new MyButtonEditor());
						tableOfLoading.getColumnModel().getColumn(8).setCellRenderer(new MyButtonRender());
						tableOfLoading.getColumnModel().getColumn(8).setCellEditor(new MyButtonEditor());
						JOptionPane.showMessageDialog(modifyInterface, "修改成功");
					}
				}
			}
		};

		JButton submitButton = new JButton("提交");
		submitButton.addActionListener(listener);
		submitButton.setBounds(150, 620, 150, 50);
		submitButton.setBackground(ZangQing);
		submitButton.setFont(fontOfButton);

		panel.add(submitButton);
		modifyInterface.add(panel);
		modifyInterface.setVisible(true);

	}

	private JScrollPane loadTableData() {
		String[][] data = SuperManagerSystem.getSingleSystem().getUserTableData();
		String[] title = SuperManagerSystem.getSingleSystem().getUserTableTitle();
		tModel1.setDataVector(data, title);

		tableOfLoading.getColumnModel().getColumn(7).setCellRenderer(new MyButtonRender());
		tableOfLoading.getColumnModel().getColumn(7).setCellEditor(new MyButtonEditor());
		tableOfLoading.getColumnModel().getColumn(8).setCellRenderer(new MyButtonRender());
		tableOfLoading.getColumnModel().getColumn(8).setCellEditor(new MyButtonEditor());

		tableOfLoading.setRowHeight(100);
		tableOfLoading.setRowHeight(50);
		tableOfLoading.setBackground(ZangQing);
		tableOfLoading.setForeground(Color.WHITE);
		tableOfLoading.setFont(fontOfTableData);

		tableOfLoading.setRowSelectionAllowed(false);
		JTableHeader head = tableOfLoading.getTableHeader();
		head.setPreferredSize(new Dimension(head.getWidth(), 35));
		head.setFont(fontOfTableTitle);

		JScrollPane jScrollPane = new JScrollPane(tableOfLoading);
		jScrollPane.setBounds(0, 200, 1600, 600);
		tableOfLoading.setBounds(0, 200, 1600, 600);

		return jScrollPane;
	}

	public JPanel loadInterface() {
		ActionListener buttonListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getActionCommand().equals("新增")) {
					add();
				} else if (e.getActionCommand().equals("检索")) {
					search();
				}
			}
		};

		addButton.setBackground(ZangQing);
		addButton.setForeground(Color.white);
		addButton.setBounds(10, 80, 100, 66);
		addButton.setFont(fontOfButton);
		addButton.addActionListener(buttonListener);

		searchButton.setBackground(Color.white);
		searchButton.setForeground(Color.BLACK);
		searchButton.setBounds(120, 80, 100, 66);
		searchButton.setFont(fontOfButton);
		searchButton.addActionListener(buttonListener);

		jLabel_1.setBounds(0, 0, 20000, 60);
		jLabel_1.setOpaque(true);
		jLabel_1.setFont(font);
		jLabel_1.setBackground(ZangQing);
		jLabel_1.setForeground(Color.white);

		userPanel.add(jLabel_1);
		userPanel.add(addButton);
		userPanel.add(searchButton);
		userPanel.add(loadTableData());
		userPanel.setBackground(Color.white);
		
		userPanel.setLayout(null);
		userPanel.setSize(1700, 800);
		userPanel.setBorder(BorderFactory.createLineBorder(ZangQing));
		return userPanel;
	}

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
			this.button.setBounds(0, 0, 200, 50);
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
					if (buttonType.equals("修改")) {
						modify(id);
					} else if (buttonType.equals("删除")) {
						boolean message = delete(id);
						if (message) {
							JOptionPane.showMessageDialog(null,
									"已删除该用户\n已删除该用户的工厂信息\n已删除该用户的招标信息\n已删除该用户工厂的自有设备\n已归还该用户租用的设备\n");
							String[][] data = SuperManagerSystem.getSingleSystem().getUserTableData();
							String[] title = SuperManagerSystem.getSingleSystem().getUserTableTitle();
							tModel1.setDataVector(data, title);
							tableOfLoading.getColumnModel().getColumn(7).setCellRenderer(new MyButtonRender());
							tableOfLoading.getColumnModel().getColumn(7).setCellEditor(new MyButtonEditor());
							tableOfLoading.getColumnModel().getColumn(8).setCellRenderer(new MyButtonRender());
							tableOfLoading.getColumnModel().getColumn(8).setCellEditor(new MyButtonEditor());

						} else {
							JOptionPane.showMessageDialog(null, "删除失败");
						}

					} else if (buttonType.equals("不可删除")) {
						JOptionPane.showMessageDialog(null, "超级管理员是无法被删除的");
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
}
