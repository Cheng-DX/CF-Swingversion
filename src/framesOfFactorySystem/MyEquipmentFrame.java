package framesOfFactorySystem;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.HeadlessException;
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

import entity.Capacity;
import entity.Equipment;
import entity.Product;
import systems.MyFactorySystem;
import systems.SuperManagerSystem;
import util.MyButtonRender;

@SuppressWarnings("serial")
public class MyEquipmentFrame extends JFrame {

	private MyFactorySystem myFactorySystem;

	public MyEquipmentFrame(String factoryId) throws HeadlessException {
		this.myFactorySystem = new MyFactorySystem(factoryId);
	}

	private JButton addButton = new JButton("新增");
	private JButton searchButton = new JButton("检索");
	private JButton leaseButton = new JButton("租用设备");
	private JButton submitButton = new JButton("确定");

	private DefaultTableModel tModel = new DefaultTableModel();
	private JTable table = new JTable(tModel);

	private DefaultTableModel tModel2 = new DefaultTableModel();
	private JTable tableOfResults = new JTable(tModel2);

	private DefaultTableModel tModel3 = new DefaultTableModel();
	private JTable tableOfLease = new JTable(tModel3);

	private JLabel label = new JLabel("我的工厂——设备信息列表");
	private JPanel mainPanel;

	// 一些工具
	private static final Color ZangQing = new Color(0, 127, 174);
	private static final Color MoLv = new Color(10, 160, 32);
	private Font font = new Font("微软雅黑", ALLBITS, 28);
	private Font fontOfButton = new Font("微软雅黑", ALLBITS, 20);
	private Font fontOfLabel = new Font("微软雅黑", ALLBITS, 15);
	private Font fontOfLabel2 = new Font("微软雅黑", ALLBITS, 18);
	private Font fontOfLabel3 = new Font("微软雅黑", ALLBITS, 23);
	private Font fontOfTableTitle = new Font("等线", Font.PLAIN, 18);
	private Font fontOfTableData = new Font("等线", Font.PLAIN, 18);

	public void add() {
		JFrame addFrame = new JFrame("添加设备");
		addFrame.setBounds(500, 255, 488, 720);

		JPanel panel = new JPanel();
		panel.setLayout(null);
		addFrame.setResizable(false);

		JLabel label = new JLabel("新增设备");
		label.setBounds(0, 0, 10000, 60);
		label.setOpaque(true);
		label.setFont(font);
		label.setBackground(ZangQing);
		label.setForeground(Color.white);
		panel.add(label);

		JLabel[] labels = new JLabel[5];
		String[] temp = { "设备编号", "*设备名称", "*设备类别", "*设备规格", "设备描述" };
		JTextField[] texts = new JTextField[5];

		for (int i = 0; i < labels.length; i++) {
			labels[i] = new JLabel(temp[i], JLabel.CENTER);
			labels[i].setBounds(30, 40 + 50 * (i + 1), 120, 40);
			labels[i].setOpaque(true);
			labels[i].setFont(fontOfLabel);
			labels[i].setBackground(ZangQing);
			labels[i].setForeground(Color.white);
			panel.add(labels[i]);

			if (i != 0) {
				texts[i] = new JTextField("");
			} else {
				texts[i] = new JTextField("E" + System.currentTimeMillis());
				texts[i].setEditable(false);
			}

			texts[i].setBounds(200, 40 + 50 * (i + 1), 233, 40);
			texts[i].setFont(fontOfLabel);
			texts[i].setBackground(ZangQing);
			texts[i].setForeground(Color.white);
			panel.add(texts[i]);
		}

		ActionListener listener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getActionCommand().equals("提交")) {
					String id = texts[0].getText();
					String name = texts[1].getText();
					String type = texts[2].getText();
					String specification = texts[3].getText();
					String intro = texts[4].getText();
					if (name.equals("") || type.equals("") || specification.equals("")) {
						JOptionPane.showMessageDialog(addFrame, "信息不足(标*为必填选项)");
					} else {
						Equipment newEquipment = new Equipment(id, name, type, specification, intro, "已关闭", "工厂设备",
								myFactorySystem.getFactory().getId());

						SuperManagerSystem.getSingleSystem().getEquipments().add(newEquipment);

						upDateTable1();
						JOptionPane.showMessageDialog(addFrame, "添加成功");
						addFrame.setVisible(false);

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
		JFrame addFrame = new JFrame();
		addFrame.setLayout(new BorderLayout());
		addFrame.setBounds(500, 255, 488, 350);

		JPanel panel = new JPanel();
		panel.setLayout(null);
		addFrame.setResizable(false);

		JLabel label = new JLabel("请输入名称(支持模糊搜索)", JLabel.CENTER);
		label.setBounds(0, 0, 488, 60);
		label.setOpaque(true);
		label.setFont(font);
		label.setBackground(ZangQing);
		label.setForeground(Color.white);
		panel.add(label);

		JTextField jTextField = new JTextField();
		jTextField.setBounds(100, 100, 290, 33);
		jTextField.setBackground(ZangQing);
		jTextField.setForeground(Color.WHITE);
		panel.add(jTextField);

		ActionListener listener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// 查找
				JFrame temp = new JFrame();

				temp.setBounds(50, 100, 1800, 800);
				String target = jTextField.getText();
				ArrayList<Equipment> equipmentsOfResult = myFactorySystem.searchEquipment(target);
				String[][] data = new String[equipmentsOfResult.size()][13];

				for (int i = 0; i < equipmentsOfResult.size(); i++) {

					Equipment temp1 = myFactorySystem.getMyEquipments().get(i);
					data[i][0] = (i + 1) + "";
					data[i][1] = temp1.getId();
					data[i][2] = temp1.getName();
					data[i][3] = temp1.getType();
					data[i][4] = temp1.getSpecification();
					data[i][5] = temp1.getIntro();
					data[i][6] = temp1.getEquipmentStatus();
					if (temp1.getLeaseStatus().equals("已被租用")) {
						data[i][7] = "租用设备";

						data[i][11] = "禁用-" + temp1.getId();
						data[i][12] = "禁用-" + temp1.getId();
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
				String[] title = myFactorySystem.getEquipmentTableTitle();
				tModel2.setDataVector(data, title);
				for (int i = 9; i <= 12; i++) {
					tableOfResults.getColumnModel().getColumn(i).setCellRenderer(new MyButtonRender());
					tableOfResults.getColumnModel().getColumn(i).setCellEditor(new MyButtonEditor());

				}
				tableOfResults.setRowHeight(100);
				tableOfResults.setRowHeight(50);
				tableOfResults.setBackground(ZangQing);
				tableOfResults.setForeground(Color.WHITE);
				tableOfResults.setFont(fontOfTableData);

				tableOfResults.setRowSelectionAllowed(false);
				JTableHeader head = tableOfResults.getTableHeader();
				head.setPreferredSize(new Dimension(head.getWidth(), 35));
				head.setFont(fontOfTableTitle);

				JScrollPane jScrollPane = new JScrollPane(tableOfResults);
				jScrollPane.setBounds(0, 200, 1200, 275);
				tableOfResults.setBounds(0, 200, 1200, 275);

				temp.add(jScrollPane);
				temp.setVisible(true);
			}
		};

		JButton searchButton = new JButton("查找");
		searchButton.addActionListener(listener);
		searchButton.setBounds(175, 150, 140, 50);
		searchButton.setBackground(ZangQing);
		searchButton.setFont(fontOfButton);

		panel.add(searchButton);

		addFrame.add(panel);
		addFrame.setVisible(true);
	}

	public void modify(String id) {
		Equipment currentEquip = myFactorySystem.getEquipment(id);
		JFrame modifyInterface = new JFrame();
		modifyInterface.setLayout(new BorderLayout());
		modifyInterface.setBounds(500, 255, 488, 720);

		JPanel panel = new JPanel();
		panel.setLayout(null);
		modifyInterface.setResizable(false);

		JLabel label = new JLabel("修改设备信息");
		label.setBounds(0, 0, 10000, 60);
		label.setOpaque(true);
		label.setFont(font);
		label.setBackground(ZangQing);
		label.setForeground(Color.white);
		panel.add(label);

		JLabel[] labels = new JLabel[5];
		String[] temp = { "设备编号", "*设备名称", "*设备类别", "*设备规格", "设备描述" };
		JTextField[] jTextFields = new JTextField[5];

		for (int i = 0; i < labels.length; i++) {
			labels[i] = new JLabel(temp[i], JLabel.CENTER);
			labels[i].setBounds(30, 40 + 50 * (i + 1), 120, 40);
			labels[i].setOpaque(true);
			labels[i].setFont(fontOfLabel);
			labels[i].setBackground(ZangQing);
			labels[i].setForeground(Color.white);
			panel.add(labels[i]);

			jTextFields[i] = new JTextField("");

			jTextFields[i].setBounds(200, 40 + 50 * (i + 1), 233, 40);
			jTextFields[i].setFont(fontOfLabel);
			jTextFields[i].setBackground(ZangQing);
			jTextFields[i].setForeground(Color.white);
			panel.add(jTextFields[i]);
		}
		jTextFields[0].setText(currentEquip.getId());
		jTextFields[0].setEditable(false);
		jTextFields[1].setText(currentEquip.getName());
		jTextFields[2].setText(currentEquip.getType());
		jTextFields[3].setText(currentEquip.getSpecification());
		jTextFields[4].setText(currentEquip.getIntro());

		ActionListener listener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getActionCommand().equals("提交")) {
					String name = jTextFields[1].getText();
					String type = jTextFields[2].getText();
					String simp = jTextFields[3].getText();
					String intro = jTextFields[4].getText();

					if (jTextFields[1].getText().equals("") || jTextFields[2].getText().equals("")
							|| jTextFields[3].getText().equals("")) {
						JOptionPane.showMessageDialog(modifyInterface, "信息不足(标*为必填选项)");

					} else {
						currentEquip.setName(name);
						currentEquip.setType(type);
						currentEquip.setSpecification(simp);
						currentEquip.setIntro(intro);
						modifyInterface.setVisible(false);
						upDateTable1();
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

	public void ConfigureProduct(String id) {
		Equipment currentEquipment = SuperManagerSystem.getSingleSystem().getEquipment(id);
		JFrame configureFrame = new JFrame();
		Panel panel = new Panel(null);
		configureFrame.setBounds(250, 130, 1200, 500);
		configureFrame.setResizable(false);

		JLabel jLabel_0 = new JLabel("添加产品产能信息:");
		JLabel jLabel_1 = new JLabel("设备编号:", JLabel.CENTER);
		JLabel jLabel_2 = new JLabel("设备名称:", JLabel.CENTER);
		JLabel[] jLabels_1 = new JLabel[5];
		JLabel[] jLabels_2 = new JLabel[5];
		String[] productItems = new String[(SuperManagerSystem.getSingleSystem().getProducts().size()) + 1];

		JTextField[] fields = new JTextField[3];
		for (int i = 0; i < fields.length; i++) {
			fields[i] = new JTextField("");
			fields[i].setBounds(740, 150 + 55 * (i + 1), 340, 45);
			fields[i].setBackground(ZangQing);
			fields[i].setForeground(Color.WHITE);
			fields[i].setFont(fontOfLabel3);
		}
		productItems[0] = "---请选择---";
		for (int i = 0; i < productItems.length - 1; i++) {
			productItems[i + 1] = SuperManagerSystem.getSingleSystem().getProducts().get(i).getName();
		}
		JComboBox<String> box1 = new JComboBox<String>(productItems);
		JComboBox<String> box2 = new JComboBox<String>(productItems);
		JComboBox<String> box3 = new JComboBox<String>(productItems);

		ArrayList<Capacity> loadData = currentEquipment.getCapacities();
		if (!loadData.isEmpty()) {
			box1.setSelectedItem(loadData.get(0).getProduct().getName());
			fields[0].setText(loadData.get(0).getProductCap());
			if (loadData.size() == 2) {
				box2.setSelectedItem(loadData.get(1).getProduct().getName());
				fields[1].setText(loadData.get(1).getProductCap());
				if (loadData.size() == 3) {
					box3.setSelectedItem(loadData.get(2).getProduct().getName());
					fields[2].setText(loadData.get(2).getProductCap());
				}
			}

		}

		for (int i = 0; i < 3; i++) {
			jLabels_1[i] = new JLabel("产品名称:", JLabel.CENTER);
			jLabels_1[i].setBounds(20, 150 + (i + 1) * 55, 100, 45);
			jLabels_1[i].setFont(fontOfLabel2);
			jLabels_1[i].setOpaque(true);
			jLabels_1[i].setBackground(ZangQing);
			jLabels_1[i].setForeground(Color.white);
			panel.add(jLabels_1[i]);

			jLabels_2[i] = new JLabel("产品产能(件/小时):", JLabel.CENTER);
			jLabels_2[i].setBounds(540, 150 + (i + 1) * 55, 180, 45);
			jLabels_2[i].setFont(fontOfLabel2);
			jLabels_2[i].setOpaque(true);
			jLabels_2[i].setBackground(ZangQing);
			jLabels_2[i].setForeground(Color.white);
			panel.add(jLabels_2[i]);
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

		JTextField textField_1 = new JTextField(currentEquipment.getId());
		JTextField textField_2 = new JTextField(currentEquipment.getName());

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

		jLabel_1.setBounds(20, 70, 100, 45);
		jLabel_1.setOpaque(true);
		jLabel_1.setFont(fontOfLabel2);
		jLabel_1.setBackground(ZangQing);
		jLabel_1.setForeground(Color.white);
		jLabel_2.setBounds(620, 70, 100, 45);
		jLabel_2.setOpaque(true);
		jLabel_2.setFont(fontOfLabel2);
		jLabel_2.setBackground(ZangQing);
		jLabel_2.setForeground(Color.white);

		jLabel_0.setBounds(0, 0, 20000, 60);
		jLabel_0.setOpaque(true);
		jLabel_0.setFont(font);
		jLabel_0.setBackground(ZangQing);
		jLabel_0.setForeground(Color.white);

		ActionListener buttonListener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getActionCommand().equals("确定")) {
					ArrayList<Capacity> capacities = currentEquipment.getCapacities();
					capacities.clear();
					String[] temp = new String[3];
					temp[0] = (String) box1.getSelectedItem();
					temp[1] = (String) box2.getSelectedItem();
					temp[2] = (String) box3.getSelectedItem();
					int count = 0;
					for (int i = 0; i < 3; i++) {

						String name = temp[i];
						String productCap = fields[i].getText();
						if ((!name.equals("---请选择---")) && (!productCap.equals(""))) {
							Product newProduct = SuperManagerSystem.getSingleSystem().getProductByName(name);

							Capacity capacity = new Capacity(newProduct, productCap);
							capacities.add(capacity);
							configureFrame.setVisible(false);
							JOptionPane.showMessageDialog(null, "配置产品" + (i + 1) + "成功");
							count++;
						}
					}
					if (count == 0) {
						JOptionPane.showMessageDialog(configureFrame, "未配置任何产品");
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
		panel.add(jLabel_2);
		panel.add(textField_1);
		panel.add(textField_2);
		panel.add(box1);
		panel.add(box2);
		panel.add(box3);

		panel.add(fields[0]);
		panel.add(fields[1]);
		panel.add(fields[2]);
		panel.add(submitButton);
		panel.setBackground(Color.white);

		configureFrame.add(panel);
		configureFrame.setVisible(true);
	}

	public void lease() {
		JFrame tempFrame = new JFrame();
		tempFrame.setBounds(200, 200, 1200, 500);
		tempFrame.setResizable(false);

		JPanel panel = new JPanel(null);
		JLabel jLabel_1 = new JLabel("可租用的设备列表");
		jLabel_1.setBounds(0, 0, 20000, 60);
		jLabel_1.setOpaque(true);
		jLabel_1.setFont(font);
		jLabel_1.setBackground(ZangQing);
		jLabel_1.setForeground(Color.white);

		String[] title = { "序号", "设备编号", "设备名称", "设备类别", "设备规格", "设备描述", "设备状态", "操作" };

		ArrayList<Equipment> equipments = SuperManagerSystem.getSingleSystem().getEquipments();
		ArrayList<Equipment> result = new ArrayList<>();
		for (Equipment equipment : equipments) {
			if (equipment.getLeaseStatus().equals("自由设备")) {
				result.add(equipment);
			}
		}

		String[][] data = new String[result.size()][8];
		for (int i = 0; i < result.size(); i++) {
			data[i][0] = "" + (i + 1);
			data[i][1] = result.get(i).getId();
			data[i][2] = result.get(i).getName();
			data[i][3] = result.get(i).getType();
			data[i][4] = result.get(i).getSpecification();
			data[i][5] = result.get(i).getIntro();
			data[i][6] = result.get(i).getEquipmentStatus();
			data[i][7] = "租用-" + result.get(i).getId();
		}
		tModel3.setDataVector(data, title);
		tableOfLease.getColumnModel().getColumn(7).setCellRenderer(new MyButtonRender());
		tableOfLease.getColumnModel().getColumn(7).setCellEditor(new MyButtonEditor());

		tableOfLease.setRowHeight(50);
		tableOfLease.setBackground(ZangQing);
		tableOfLease.setForeground(Color.WHITE);
		tableOfLease.setFont(fontOfTableData);

		JScrollPane jScrollPane = new JScrollPane(tableOfLease);
		tableOfLease.setRowSelectionAllowed(false);
		JTableHeader head = tableOfLease.getTableHeader();
		head.setPreferredSize(new Dimension(head.getWidth(), 35));
		head.setFont(fontOfTableTitle);

		tableOfLease.setBounds(0, 70, 1200, 400);
		jScrollPane.setBounds(0, 70, 1200, 400);

		panel.add(jLabel_1);
		panel.add(jScrollPane);
		tempFrame.add(panel);
		tempFrame.setVisible(true);
	}

	private JScrollPane loadTableData() {
		String[] title = myFactorySystem.getEquipmentTableTitle();
		String[][] data = myFactorySystem.getEquipmentTableData();

		tModel.setDataVector(data, title);
		for (int i = 9; i <= 12; i++) {
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
				if (e.getActionCommand().equals("新增")) {
					add();
				} else if (e.getActionCommand().equals("检索")) {
					search();
				} else if (e.getActionCommand().equals("租用设备")) {
					lease();
				}
			}
		};
		mainPanel = new JPanel();
		mainPanel.setLayout(null);

		addButton.setBackground(ZangQing);
		addButton.setForeground(Color.white);
		addButton.setBounds(10, 80, 100, 66);
		addButton.setFont(fontOfButton);
		addButton.addActionListener(buttonListener);

		leaseButton.setBackground(MoLv);
		leaseButton.setForeground(Color.white);
		leaseButton.setBounds(120, 80, 150, 66);
		leaseButton.setFont(fontOfButton);
		leaseButton.addActionListener(buttonListener);

		searchButton.setBackground(Color.white);
		searchButton.setForeground(Color.BLACK);
		searchButton.setBounds(280, 80, 100, 66);
		searchButton.setFont(fontOfButton);
		searchButton.addActionListener(buttonListener);

		label.setBounds(0, 0, 20000, 60);
		label.setOpaque(true);
		label.setFont(font);
		label.setBackground(ZangQing);
		label.setForeground(Color.white);

		mainPanel.add(label);
		mainPanel.add(addButton);
		mainPanel.add(searchButton);
		mainPanel.add(leaseButton);
		mainPanel.add(loadTableData());
		mainPanel.setBackground(Color.white);

		mainPanel.setBorder(BorderFactory.createLineBorder(ZangQing));
		return mainPanel;

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
					if (buttonType.equals("修改")) {
						modify(id);
					} else if (buttonType.equals("删除")) {
						SuperManagerSystem.getSingleSystem().deleteEquipment(id);
						upDateTable1();
					} else if (buttonType.equals("远程开机")) {
						JOptionPane.showMessageDialog(null, id + "已开机");
						SuperManagerSystem.getSingleSystem().getEquipment(id).setEquipmentStatus("闲置中");
						upDateTable1();
					} else if (buttonType.equals("远程关机")) {
						JOptionPane.showMessageDialog(null, id + "已关机");
						SuperManagerSystem.getSingleSystem().getEquipment(id).setEquipmentStatus("已关闭");
						upDateTable1();
					} else if (buttonType.equals("配置产品")) {
						ConfigureProduct(id);
					} else if (buttonType.equals("租用")) {
						SuperManagerSystem.getSingleSystem().getEquipment(id)
								.setBelongingFactory(myFactorySystem.getFactory().getId());
						SuperManagerSystem.getSingleSystem().getEquipment(id).setLeaseStatus("已被租用");
						upDateTable1();
					} else if (buttonType.equals("*")) {
						JOptionPane.showMessageDialog(null, id + "禁用此操作");
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
		String[][] data = myFactorySystem.getEquipmentTableData();
		String[] title = myFactorySystem.getEquipmentTableTitle();
		tModel.setDataVector(data, title);
		for (int i = 9; i <= 12; i++) {
			table.getColumnModel().getColumn(i).setCellRenderer(new MyButtonRender());
			table.getColumnModel().getColumn(i).setCellEditor(new MyButtonEditor());
		}
	}
}
