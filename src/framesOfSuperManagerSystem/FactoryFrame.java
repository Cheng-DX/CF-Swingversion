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
import entity.Factory;
import systems.SuperManagerSystem;
import util.MyButtonRender;

@SuppressWarnings("serial")
public class FactoryFrame extends JFrame {

	private JButton searchButton = new JButton("检索");
	private JLabel jLabel_1 = new JLabel("云工厂信息列表");
	private JPanel panel = new JPanel();

	private DefaultTableModel tModel = new DefaultTableModel();
	private JTable table = new JTable(tModel);
	private DefaultTableModel tModel2 = new DefaultTableModel();
	private JTable tableOfResult = new JTable(tModel2);

	private static final Color ZangQing = new Color(0, 127, 174);
	private Font font = new Font("微软雅黑", ALLBITS, 28);
	private Font fontOfLabel = new Font("微软雅黑", ALLBITS, 15);
	private Font fontOfButton = new Font("微软雅黑", ALLBITS, 20);
	private Font fontOfTableTitle = new Font("等线", Font.PLAIN, 18);
	private Font fontOfTableData = new Font("等线", Font.PLAIN, 18);

	public JPanel loadInterface() {

		ActionListener buttonListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getActionCommand().equals("检索")) {
					search();
				}
			}
		};

		jLabel_1.setBounds(0, 0, 12000, 60);
		jLabel_1.setOpaque(true);
		jLabel_1.setFont(font);
		jLabel_1.setBackground(ZangQing);
		jLabel_1.setForeground(Color.white);

		searchButton.setBounds(20, 80, 100, 66);
		searchButton.setBackground(Color.WHITE);
		searchButton.setFont(fontOfButton);
		searchButton.addActionListener(buttonListener);

		panel.add(jLabel_1);
		panel.add(searchButton);
		panel.add(loadTableData());
		panel.setBackground(Color.white);
		panel.setLayout(null);
		panel.setSize(1700, 800);
		panel.setBorder(BorderFactory.createLineBorder(ZangQing));
		panel.setBorder(BorderFactory.createLineBorder(ZangQing));
		return panel;
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
		JLabel[] labels = new JLabel[7];
		String[] temp = { "id", "云工厂名称", "简介", "云工厂负责人", "联系方式", "登陆账号", "云工厂状态" };

		JTextField[] jTextFields = new JTextField[7];
		for (int i = 1; i <= jTextFields.length; i++) {
				labels[i - 1] = new JLabel(temp[i - 1], JLabel.CENTER);
				labels[i - 1].setBounds(30, 40 + 50 * i, 120, 40);
				labels[i - 1].setOpaque(true);
				labels[i - 1].setFont(fontOfLabel);
				labels[i - 1].setBackground(ZangQing);
				labels[i - 1].setForeground(Color.white);
				panel.add(labels[i - 1]);
				
				jTextFields[i - 1] = new JTextField("");
				jTextFields[i - 1].setBounds(200, 40 + 50 * i, 233, 40);
				jTextFields[i - 1].setFont(fontOfLabel);
				jTextFields[i - 1].setBackground(ZangQing);
				jTextFields[i - 1].setForeground(Color.white);
				panel.add(jTextFields[i-1]);
		}
		ActionListener listener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFrame resultFrame = new JFrame();
				Factory target = new Factory(jTextFields[0].getText(), 
						jTextFields[1].getText(), jTextFields[2].getText(), 
						jTextFields[3].getText(), jTextFields[4].getText(), 
						jTextFields[5].getText());
				
				ArrayList<Factory> results = SuperManagerSystem.getSingleSystem().searchFactory(target,jTextFields[6].getText());
				String[][] rowData = new String[results.size()][8];

				for (int i = 0; i < results.size(); i++) {

					Factory temp = results.get(i);
					rowData[i][0] = temp.getId();
					rowData[i][1] = temp.getFactoryName();
					rowData[i][2] = temp.getIntro();
					rowData[i][3] = temp.getManager();
					rowData[i][4] = temp.getContection();
					rowData[i][5] = temp.getAccount();
					rowData[i][6] = temp.getStatus();
					if (temp.getStatus().equals("正常")) {
						rowData[i][7] = "关停-" + temp.getId();
					} else if (temp.getStatus().equals("关停")) {
						rowData[i][7] = "开启-" + temp.getId();
					} else {
						rowData[i][7] = "异常";
					}
				}

				tModel2.setDataVector(rowData, SuperManagerSystem.getSingleSystem().getFactoryTableTitle());
				tableOfResult.getColumnModel().getColumn(7).setCellRenderer(new MyButtonRender());
				tableOfResult.getColumnModel().getColumn(7).setCellEditor(new MyButtonEditor());

				resultFrame.setBounds(200, 200, 1200, 500);
				resultFrame.setResizable(false);
				JPanel panel = new JPanel();
				panel.setLayout(null);

				tableOfResult.setRowHeight(100);
				tableOfResult.setRowHeight(50);
				tableOfResult.setBackground(ZangQing);
				tableOfResult.setForeground(Color.WHITE);
				tableOfResult.setFont(fontOfTableData);

				tableOfResult.setRowSelectionAllowed(false);
				JTableHeader head = tableOfResult.getTableHeader();
				head.setPreferredSize(new Dimension(head.getWidth(), 35));
				head.setFont(fontOfTableTitle);

				JScrollPane jScrollPane = new JScrollPane(tableOfResult);
				jScrollPane.setBounds(0, 200, 1200, 275);
				tableOfResult.setBounds(0, 200, 1200, 275);

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
	public void upDataTable() {
		ArrayList<Factory> factories = SuperManagerSystem.getSingleSystem().getFactories();
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
		tModel.setDataVector(rowData, SuperManagerSystem.getSingleSystem().getFactoryTableTitle());
		table.getColumnModel().getColumn(7).setCellRenderer(new MyButtonRender());
		table.getColumnModel().getColumn(7).setCellEditor(new MyButtonEditor());

	}
	private JScrollPane loadTableData() {
		String[] title = SuperManagerSystem.getSingleSystem().getFactoryTableTitle();
		String[][] rowData = SuperManagerSystem.getSingleSystem().getFactoryTableData();

		tModel.setDataVector(rowData, title);
		table.getColumnModel().getColumn(7).setCellRenderer(new MyButtonRender());
		table.getColumnModel().getColumn(7).setCellEditor(new MyButtonEditor());

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
	private void changeStatus(Factory currentFactory) {
		JFrame frame = new JFrame("修改状态");
		frame.setLayout(new BorderLayout());
		frame.setBounds(500, 255, 488, 388);

		JPanel panel = new JPanel();
		panel.setLayout(null);
		frame.setResizable(false);

		JLabel label = new JLabel("为云工厂" + currentFactory.getId() + "修改状态");
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
		ArrayList<String> factoryStatus = SuperManagerSystem.getSingleSystem().getFactoryStatus();
		for(int i=0;i<factoryStatus.size();i++) {
			box.addItem(factoryStatus.get(i));
		}
		box.setBounds(170, 100, 120, 40);
		box.setFont(fontOfLabel);
		box.setSelectedItem(currentFactory.getStatus());
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
						currentFactory.setStatus(status);
						upDataTable();
						frame.setVisible(false);
					}
				}
			}
		});
		
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
					Factory currentFactory = SuperManagerSystem.getSingleSystem().getFactory(id);
					if (buttonType.equals("修改")) 
						changeStatus(currentFactory);

					String[] title = SuperManagerSystem.getSingleSystem().getFactoryTableTitle();
					String[][] rowData = SuperManagerSystem.getSingleSystem().getFactoryTableData();

					tModel.setDataVector(rowData, title);
					table.getColumnModel().getColumn(7).setCellRenderer(new MyButtonRender());
					table.getColumnModel().getColumn(7).setCellEditor(new MyButtonEditor());
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
