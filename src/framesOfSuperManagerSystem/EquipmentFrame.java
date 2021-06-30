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

import entity.Equipment;
import entity.Factory;
import systems.SuperManagerSystem;
import util.MyButtonRender;

@SuppressWarnings("serial")
public class EquipmentFrame extends JFrame {
	private JButton addButton = new JButton("新增");
	private JButton searchButton = new JButton("检索");

	private DefaultTableModel tModel = new DefaultTableModel();
	private JTable table = new JTable(tModel);

	private DefaultTableModel tModel2 = new DefaultTableModel();
	private JTable tableOfResults = new JTable(tModel2);

	private JLabel jLabel_1 = new JLabel("设备信息列表");
	private JPanel mainPanel = new JPanel();;

	// 一些工具
	private static final Color ZangQing = new Color(0, 127, 174);
	private Font font = new Font("微软雅黑", ALLBITS, 28);
	private Font fontOfButton = new Font("微软雅黑", ALLBITS, 20);
	private Font fontOfLabel = new Font("微软雅黑", ALLBITS, 15);
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
		String[] temp = { "设备编号", "*设备名称", "*设备类别", "*设备规格", "设备描述", "设备状态", "租用状态", "所属工厂", "开/关机", "修改", "删除" };
		JTextField[] texts = new JTextField[5];
		
		JComboBox<String> box = new JComboBox<String>();
		ArrayList<String> equipmentType = SuperManagerSystem.getSingleSystem().getequipmentTypes();
		for(int j=0;j<equipmentType.size();j++) {
			box.addItem(equipmentType.get(j));
		}
		for (int i = 0; i < labels.length; i++) {
			labels[i] = new JLabel(temp[i], JLabel.CENTER);
			labels[i].setBounds(30, 40 + 50 * (i + 1), 120, 40);
			labels[i].setOpaque(true);
			labels[i].setFont(fontOfLabel);
			labels[i].setBackground(ZangQing);
			labels[i].setForeground(Color.white);
			panel.add(labels[i]);

			if (i != 0 && i != 2) {
				texts[i] = new JTextField("");
				texts[i].setBounds(200, 40 + 50 * (i + 1), 233, 40);
				texts[i].setFont(fontOfLabel);
				texts[i].setBackground(ZangQing);
				texts[i].setForeground(Color.white);
				panel.add(texts[i]);
			} else if(i==0){
				texts[i] = new JTextField("E" + System.currentTimeMillis());
				texts[i].setEditable(false);
				texts[i].setBounds(200, 40 + 50 * (i + 1), 233, 40);
				texts[i].setFont(fontOfLabel);
				texts[i].setBackground(ZangQing);
				texts[i].setForeground(Color.white);
				panel.add(texts[i]);
			} else if(i==2) {
				box.setFont(fontOfLabel);
				box.setBounds(200, 40 + 50 * (i + 1), 233, 40);
				panel.add(box);
			}

		}

		ActionListener listener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getActionCommand().equals("提交")) {
					String id = texts[0].getText();
					String name = texts[1].getText();
					String type = (String) box.getSelectedItem();
					String specification = texts[3].getText();
					String intro = texts[4].getText();
					if (name.equals("") || type.equals("") || specification.equals("")) {
						JOptionPane.showMessageDialog(addFrame, "信息不足(标*为必填选项)");
					} else {
						Equipment newEquipment = new Equipment(id, name, type, specification, intro, "已关闭", "自由设备", "");

						SuperManagerSystem.getSingleSystem().getEquipments().add(newEquipment);
						JOptionPane.showMessageDialog(addFrame, "添加成功");
						addFrame.setVisible(false);
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

		addFrame.add(panel);

		addFrame.setVisible(true);
	}
	private void search() {
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
		JLabel[] labels = new JLabel[8];
		String[] temp = { "id", "设备名称", "设备类别", "设备规格", "设备描述", "设备状态", "租用状态","所属工厂名"};
		
		JComboBox<String> box = new JComboBox<String>();
		ArrayList<String> equipmentType = SuperManagerSystem.getSingleSystem().getequipmentTypes();
		for(int j=0;j<equipmentType.size();j++) {
			box.addItem(equipmentType.get(j));
		}
		
		JTextField[] jTextFields = new JTextField[8];
		for (int i = 1; i <= jTextFields.length; i++) {
				labels[i - 1] = new JLabel(temp[i - 1], JLabel.CENTER);
				labels[i - 1].setBounds(30, 40 + 50 * i, 120, 40);
				labels[i - 1].setOpaque(true);
				labels[i - 1].setFont(fontOfLabel);
				labels[i - 1].setBackground(ZangQing);
				labels[i - 1].setForeground(Color.white);
				panel.add(labels[i - 1]);
				if(i!=3) {
				jTextFields[i - 1] = new JTextField("");
				jTextFields[i - 1].setBounds(200, 40 + 50 * i, 233, 40);
				jTextFields[i - 1].setFont(fontOfLabel);
				jTextFields[i - 1].setBackground(ZangQing);
				jTextFields[i - 1].setForeground(Color.white);
				panel.add(jTextFields[i-1]);
				}
				if(i==3) {
					box.setFont(fontOfLabel);
					box.setBounds(200, 40 + 50 * i , 233, 40);
					panel.add(box);
				}
		}
		ActionListener listener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFrame resultFrame = new JFrame();

				resultFrame.setBounds(400, 400, 1600, 600);
				String factoryName = jTextFields[7].getText();
				Factory temp = SuperManagerSystem.getSingleSystem().getFactoryByName(factoryName);
				String factoryID = "";
				if(temp != null) {
					factoryID += temp.getId();
				}
				Equipment target = new Equipment(
						jTextFields[0].getText(), jTextFields[1].getText(),
						(String)box.getSelectedItem(), jTextFields[3].getText(),
						jTextFields[4].getText(), jTextFields[5].getText(),
						jTextFields[6].getText(),factoryID);
				
				ArrayList<Equipment> results = SuperManagerSystem.getSingleSystem().searchEquipment(target);
				String[][] data = new String[results.size()][12];

				for (int i = 0; i < results.size(); i++) {

					Equipment temp1 = results.get(i);
					data[i][0] = (i + 1) + "";
					data[i][1] = temp1.getId();
					data[i][2] = temp1.getName();
					data[i][3] = temp1.getType();
					data[i][4] = temp1.getSpecification();
					data[i][5] = temp1.getIntro();
					data[i][6] = temp1.getEquipmentStatus();
					data[i][7] = temp1.getLeaseStatus();
					String factoryId = temp1.getBelongingFactoryId();
					Factory temp23 = SuperManagerSystem.getSingleSystem().getFactory(factoryId);
					if(temp23 != null) {
						data[i][8] = temp23.getFactoryName();
					}else {
						data[i][8] = "总系统";
					}
					data[i][9] = "更改设备状态-" + temp1.getId();
					
					data[i][10] = "修改-" + temp1.getId();
					data[i][11] = "删除-" + temp1.getId();
				}
				String[] title = SuperManagerSystem.getSingleSystem().getEquipmentTableTitle();
				tModel2.setDataVector(data, title);

				tableOfResults.getColumnModel().getColumn(9).setCellRenderer(new MyButtonRender());
				tableOfResults.getColumnModel().getColumn(9).setCellEditor(new MyButtonEditor());

				tableOfResults.getColumnModel().getColumn(10).setCellRenderer(new MyButtonRender());
				tableOfResults.getColumnModel().getColumn(10).setCellEditor(new MyButtonEditor());

				tableOfResults.getColumnModel().getColumn(11).setCellRenderer(new MyButtonRender());
				tableOfResults.getColumnModel().getColumn(11).setCellEditor(new MyButtonEditor());

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

				resultFrame.add(jScrollPane);
				resultFrame.setVisible(true);
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
	public void upDateTable1() {
		String[][] data = SuperManagerSystem.getSingleSystem().getEquipmentTableData();
		String[] title = SuperManagerSystem.getSingleSystem().getEquipmentTableTitle();
		tModel.setDataVector(data, title);
		table.getColumnModel().getColumn(9).setCellRenderer(new MyButtonRender());
		table.getColumnModel().getColumn(9).setCellEditor(new MyButtonEditor());

		table.getColumnModel().getColumn(10).setCellRenderer(new MyButtonRender());
		table.getColumnModel().getColumn(10).setCellEditor(new MyButtonEditor());

		table.getColumnModel().getColumn(11).setCellRenderer(new MyButtonRender());
		table.getColumnModel().getColumn(11).setCellEditor(new MyButtonEditor());
	}
	private void changeStatus(Equipment current) {
		JFrame frame = new JFrame("修改状态");
		frame.setLayout(new BorderLayout());
		frame.setBounds(500, 255, 488, 388);

		JPanel panel = new JPanel();
		panel.setLayout(null);
		frame.setResizable(false);

		JLabel label = new JLabel("为设备" + current.getId() + "修改状态");
		label.setBounds(0, 0, 10000, 60);
		label.setOpaque(true);
		label.setFont(font);
		label.setBackground(ZangQing);
		label.setForeground(Color.white);
		panel.add(label);
		
		JButton button = new JButton("修改");
		button.setBounds(200, 200, 135, 55);
		button.setBackground(ZangQing);
		button.setForeground(Color.white);
		button.setFont(fontOfButton);
		panel.add(button);
		
		JLabel label3 = new JLabel("更改状态为:");
		label3.setBounds(40, 100, 80, 40);
		label3.setFont(fontOfLabel);
		panel.add(label3);
		
		JComboBox<String> box = new JComboBox<String>();
		ArrayList<String> equipmentStatus = SuperManagerSystem.getSingleSystem().getEquipmentStatus();
		for(int i=0;i<equipmentStatus.size();i++) {
			box.addItem(equipmentStatus.get(i));
		}
		box.setBounds(170, 100, 120, 40);
		box.setFont(fontOfLabel);
		box.setSelectedItem(current.getEquipmentStatus());
		panel.add(box);
		
		frame.add(panel);
		frame.setVisible(true);
		
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				if(e.getActionCommand().equals("修改")) {
					String status = (String) box.getSelectedItem();
					if(status.equals("")) {
						JOptionPane.showMessageDialog(null, "未选中状态！", "失败",
								JOptionPane.INFORMATION_MESSAGE);
					}
					else {
						current.setEquipmentStatus(status);
						upDateTable1();
						frame.setVisible(false);
					}
				}
			}
		});
		
	}
	
	public void modify(String id) {
		Equipment currentEquip = SuperManagerSystem.getSingleSystem().getEquipment(id);
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
		String[] temp = { "设备编号", "*设备名称", "*设备类别", "*设备规格", "设备描述", "设备状态", "租用状态", "所属工厂", "开/关机", "修改", "删除" };
		JTextField[] texts = new JTextField[5];
		
		JComboBox<String> box = new JComboBox<String>();
		ArrayList<String> equipmentType = SuperManagerSystem.getSingleSystem().getequipmentTypes();
		for(int j=0;j<equipmentType.size();j++) {
			box.addItem(equipmentType.get(j));
		}
		for (int i = 0; i < labels.length; i++) {
			labels[i] = new JLabel(temp[i], JLabel.CENTER);
			labels[i].setBounds(30, 40 + 50 * (i + 1), 120, 40);
			labels[i].setOpaque(true);
			labels[i].setFont(fontOfLabel);
			labels[i].setBackground(ZangQing);
			labels[i].setForeground(Color.white);
			panel.add(labels[i]);

			if (i != 0 && i != 2) {
				texts[i] = new JTextField("");
				texts[i].setBounds(200, 40 + 50 * (i + 1), 233, 40);
				texts[i].setFont(fontOfLabel);
				texts[i].setBackground(ZangQing);
				texts[i].setForeground(Color.white);
				panel.add(texts[i]);
			} else if(i==0){
				texts[i] = new JTextField("");
				texts[i].setEditable(false);
				texts[i].setBounds(200, 40 + 50 * (i + 1), 233, 40);
				texts[i].setFont(fontOfLabel);
				texts[i].setBackground(ZangQing);
				texts[i].setForeground(Color.white);
				panel.add(texts[i]);
			} else if(i==2) {
				box.setFont(fontOfLabel);
				box.setBounds(200, 40 + 50 * (i + 1), 233, 40);
				panel.add(box);
			}

		}
		texts[0].setText(currentEquip.getId());
		texts[1].setText(currentEquip.getName());
		box.setSelectedItem(currentEquip.getType());
		texts[3].setText(currentEquip.getSpecification());
		texts[4].setText(currentEquip.getIntro());

		ActionListener listener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getActionCommand().equals("提交")) {
					String name = texts[1].getText();
					String type = (String) box.getSelectedItem();
					String simp = texts[3].getText();
					String intro = texts[4].getText();

					if (name.equals("") || type.equals("")
							|| simp.equals("")) {
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


	private JScrollPane loadTableData() {
		String[] title = SuperManagerSystem.getSingleSystem().getEquipmentTableTitle();

		String[][] data = SuperManagerSystem.getSingleSystem().getEquipmentTableData();

		tModel.setDataVector(data, title);
		table.getColumnModel().getColumn(9).setCellRenderer(new MyButtonRender());
		table.getColumnModel().getColumn(9).setCellEditor(new MyButtonEditor());

		table.getColumnModel().getColumn(10).setCellRenderer(new MyButtonRender());
		table.getColumnModel().getColumn(10).setCellEditor(new MyButtonEditor());

		table.getColumnModel().getColumn(11).setCellRenderer(new MyButtonRender());
		table.getColumnModel().getColumn(11).setCellEditor(new MyButtonEditor());

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
				}
			}
		};
		addButton.setBackground(ZangQing);
		addButton.setForeground(Color.WHITE);
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

		mainPanel.add(jLabel_1);
		mainPanel.add(addButton);
		mainPanel.add(searchButton);
		mainPanel.add(loadTableData());
		mainPanel.setBackground(Color.white);
		mainPanel.setLayout(null);
		mainPanel.setSize(1700, 800);

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
					} else if (buttonType.equals("更改设备状态")) {
						Equipment current = SuperManagerSystem.getSingleSystem().getEquipment(id);
						changeStatus(current);
						upDateTable1();
					} else if (buttonType.equals("不可进行此操作")) {
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



}
