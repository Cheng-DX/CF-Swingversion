package framesOfSuperManagerSystem;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import entity.DictionaryItemC;
import entity.DictionaryItemF;
import systems.SuperManagerSystem;
import util.MyButtonRender;

@SuppressWarnings("serial")
public class DataDictionaryFrame extends JFrame {

	private JButton addButton = new JButton("新建");

	private DefaultTableModel tModel = new DefaultTableModel();
	private JTable table = new JTable(tModel);
	private DefaultTableModel tModelC = new DefaultTableModel();
	private JTable childTable = new JTable(tModelC);

	private JLabel jLabel_1 = new JLabel("数据字典");
	private JPanel panel = new JPanel();

	// 一些工具
	private static final Color ZangQing = new Color(0, 127, 174);
	private Font font = new Font("微软雅黑", ALLBITS, 28);
	private Font fontOfButton = new Font("微软雅黑", ALLBITS, 20);
	private Font fontOfLabel = new Font("微软雅黑", ALLBITS, 15);
	private Font fontOfTableTitle = new Font("等线", Font.PLAIN, 18);
	private Font fontOfTableData = new Font("等线", Font.PLAIN, 18);

	public JPanel loadInterface() {
		
		ActionListener buttonListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getActionCommand().equals("新建")) {
					add();
				} 
			}
		};

		addButton.setBackground(ZangQing);
		addButton.setForeground(Color.white);
		addButton.setBounds(10, 80, 100, 66);
		addButton.setFont(fontOfButton);
		addButton.addActionListener(buttonListener);

		jLabel_1.setBounds(0, 0, 20000, 60);
		jLabel_1.setOpaque(true);
		jLabel_1.setFont(font);
		jLabel_1.setBackground(ZangQing);
		jLabel_1.setForeground(Color.white);

		panel.add(jLabel_1);
		panel.add(addButton);
		panel.add(loadTableData());
		panel.setBackground(Color.white);

		panel.setLayout(null);
		panel.setSize(1700, 800);
		panel.setBorder(BorderFactory.createLineBorder(ZangQing));
		return panel;
	}
	
	private JScrollPane loadTableData() {
		String[] title = SuperManagerSystem.getSingleSystem().getDictionTableTitle();
		String[][] rowData = SuperManagerSystem.getSingleSystem().getDictionTableData();

		tModel.setDataVector(rowData, title);
		table.getColumnModel().getColumn(4).setCellRenderer(new MyButtonRender());
		table.getColumnModel().getColumn(4).setCellEditor(new MyButtonEditor1());
		table.getColumnModel().getColumn(5).setCellRenderer(new MyButtonRender());
		table.getColumnModel().getColumn(5).setCellEditor(new MyButtonEditor1());

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
	private void addItemC(DictionaryItemF current) {
		JFrame addFrame = new JFrame();
		addFrame.setLayout(new BorderLayout());
		addFrame.setBounds(500, 255, 488, 600);

		JPanel panel = new JPanel();
		panel.setLayout(null);
		addFrame.setResizable(false);

		JLabel label = new JLabel("为 " + current.getName() + " 新增子项");
		label.setBounds(0, 0, 488, 60);
		label.setOpaque(true);
		label.setFont(font);
		label.setBackground(ZangQing);
		label.setForeground(Color.white);
		panel.add(label);

		JLabel[] labels = new JLabel[3];
		String[] temp = { "ID", "*展示名称", "项值"};
		JTextField[] texts = new JTextField[3];

		for (int i = 0; i < 3; i++) {
			labels[i] = new JLabel(temp[i], JLabel.CENTER);
			labels[i].setBounds(30, 40 + 50 * (i + 1), 120, 40);
			labels[i].setOpaque(true);
			labels[i].setFont(fontOfLabel);
			labels[i].setBackground(ZangQing);
			labels[i].setForeground(Color.white);
			panel.add(labels[i]);
			
			if (i == 0) {
				texts[i] = new JTextField("DC" + System.currentTimeMillis());
				texts[i].setEditable(false);
			
			} else if(i==1){
				texts[i] = new JTextField("");
			} else if(i==2) {
				texts[i] = new JTextField( "" + current.getItems().size());
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
					String value = texts[2].getText();

					if (name.equals("")) {
						JOptionPane.showMessageDialog(addFrame, "信息不足(标*为必填选项)");
					} else {
						DictionaryItemC newItemC = new DictionaryItemC(id, name, value,current.getId());
						current.getItems().add(newItemC);
						addFrame.setVisible(false);
				
						String[] title = SuperManagerSystem.getSingleSystem().getDictionCTableTitle();
						String[][] rowData = SuperManagerSystem.getSingleSystem().getDictionCTableData(current);

						tModelC.setDataVector(rowData, title);
						childTable.getColumnModel().getColumn(6).setCellRenderer(new MyButtonRender());
						childTable.getColumnModel().getColumn(6).setCellEditor(new MyButtonEditor2());
						childTable.getColumnModel().getColumn(5).setCellRenderer(new MyButtonRender());
						childTable.getColumnModel().getColumn(5).setCellEditor(new MyButtonEditor2());

					}
				}
			}
		};

		JButton submitButton = new JButton("提交");
		submitButton.addActionListener(listener);
		submitButton.setBounds(150, 480, 150, 50);
		submitButton.setBackground(ZangQing);
		submitButton.setFont(fontOfButton);

		panel.add(submitButton);

		addFrame.add(panel);
		addFrame.setVisible(true);
		
	}
	public void modify(String id) {
		DictionaryItemF current = SuperManagerSystem.getSingleSystem().getDictionItemF(id);
		JFrame modifyFrame = new JFrame();
		modifyFrame.setLayout(new BorderLayout());
		modifyFrame.setBounds(500, 255, 1000, 600);

		JPanel panel = new JPanel();
		panel.setLayout(null);
		modifyFrame.setResizable(false);

		JLabel label1 = new JLabel("字典名称:");
		label1.setBounds(50, 20, 100, 50);
		label1.setOpaque(true);
		label1.setFont(fontOfButton);
		panel.add(label1);	
		JLabel label11 = new JLabel(current.getName());
		label11.setBounds(200, 20, 100, 50);
		label11.setOpaque(true);
		label11.setFont(fontOfButton);
		label11.setBackground(ZangQing);
		label11.setForeground(Color.white);
		panel.add(label11);
		
		JLabel label2 = new JLabel("字典类型码:");
		label2.setBounds(370, 20, 200, 50);
		label2.setOpaque(true);
		label2.setFont(fontOfButton);
		panel.add(label2);
		JLabel label22 = new JLabel(current.getNameCode());
		label22.setBounds(600, 20, 250, 50);
		label22.setOpaque(true);
		label22.setFont(fontOfButton);
		label22.setBackground(ZangQing);
		label22.setForeground(Color.white);
		panel.add(label22);
		
		JLabel label3 = new JLabel("字典项子项列表:");
		label3.setBounds(0, 80, 10000, 60);
		label3.setOpaque(true);
		label3.setFont(font);
		label3.setBackground(ZangQing);
		label3.setForeground(Color.white);
		panel.add(label3);
		
		String[] title = SuperManagerSystem.getSingleSystem().getDictionCTableTitle();
		String[][] rowData = SuperManagerSystem.getSingleSystem().getDictionCTableData(current);

		tModelC.setDataVector(rowData, title);
		childTable.getColumnModel().getColumn(5).setCellRenderer(new MyButtonRender());
		childTable.getColumnModel().getColumn(5).setCellEditor(new MyButtonEditor2());
		childTable.getColumnModel().getColumn(6).setCellRenderer(new MyButtonRender());
		childTable.getColumnModel().getColumn(6).setCellEditor(new MyButtonEditor2());
		
		childTable.setRowHeight(100);
		childTable.setRowHeight(50);
		childTable.setBackground(ZangQing);
		childTable.setForeground(Color.WHITE);
		childTable.setFont(fontOfTableData);

		childTable.setRowSelectionAllowed(false);
		JTableHeader head = childTable.getTableHeader();
		head.setPreferredSize(new Dimension(head.getWidth(), 35));
		head.setFont(fontOfTableTitle);

		JScrollPane jScrollPane = new JScrollPane(childTable);
		jScrollPane.setBounds(20, 230, 940, 320);
		childTable.setBounds(20, 230, 900, 320);
		panel.add(jScrollPane);
		
		ActionListener listener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getActionCommand().equals("新增子项")) {
						addItemC(current);
						String[] title = SuperManagerSystem.getSingleSystem().getDictionCTableTitle();
						String[][] rowData = SuperManagerSystem.getSingleSystem().getDictionCTableData(current);

						tModelC.setDataVector(rowData, title);
						childTable.getColumnModel().getColumn(5).setCellRenderer(new MyButtonRender());
						childTable.getColumnModel().getColumn(5).setCellEditor(new MyButtonEditor2());
						childTable.getColumnModel().getColumn(6).setCellRenderer(new MyButtonRender());
						childTable.getColumnModel().getColumn(6).setCellEditor(new MyButtonEditor2());

					}
				}
		};
		
		JButton newButton = new JButton("新增子项");
		newButton.setBounds(20,150,150,60);
		newButton.setFont(fontOfButton);
		newButton.setBackground(ZangQing);
		newButton.setForeground(Color.white);
		newButton.addActionListener(listener);
		
		panel.add(newButton);
		
		modifyFrame.add(panel);
		modifyFrame.setVisible(true);
	}

	public void modifyC(String id) {
		DictionaryItemC current = SuperManagerSystem.getSingleSystem().getDictionItemC(id);
		String fatherId = SuperManagerSystem.getSingleSystem().getFatherIdByChildId(id);
		DictionaryItemF father = SuperManagerSystem.getSingleSystem().getDictionItemF(fatherId);
		
		JFrame addFrame = new JFrame();
		addFrame.setLayout(new BorderLayout());
		addFrame.setBounds(500, 255, 488, 600);

		JPanel panel = new JPanel();
		panel.setLayout(null);
		addFrame.setResizable(false);

		JLabel label = new JLabel("修改子项" + current.getName());
		label.setBounds(0, 0, 488, 60);
		label.setOpaque(true);
		label.setFont(font);
		label.setBackground(ZangQing);
		label.setForeground(Color.white);
		panel.add(label);

		JLabel[] labels = new JLabel[3];
		String[] temp = { "ID", "*展示名称", "项值"};
		JTextField[] texts = new JTextField[3];

		for (int i = 0; i < 3; i++) {
			labels[i] = new JLabel(temp[i], JLabel.CENTER);
			labels[i].setBounds(30, 40 + 50 * (i + 1), 120, 40);
			labels[i].setOpaque(true);
			labels[i].setFont(fontOfLabel);
			labels[i].setBackground(ZangQing);
			labels[i].setForeground(Color.white);
			panel.add(labels[i]);
			
			if (i == 0) {
				texts[i] = new JTextField(current.getId());
				texts[i].setEditable(false);
			
			} else if(i==1){
				texts[i] = new JTextField(current.getName());
			} else if(i==2) {
				texts[i] = new JTextField(current.getItemValue());
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
					String name = texts[1].getText();

					if (name.equals("")) {
						JOptionPane.showMessageDialog(addFrame, "信息不足(标*为必填选项)");
					} else {
						current.setName(name);
						addFrame.setVisible(false);
						JOptionPane.showMessageDialog(addFrame, "添加成功", "成功", JOptionPane.INFORMATION_MESSAGE);

						String[] title = SuperManagerSystem.getSingleSystem().getDictionCTableTitle();
						String[][] rowData = SuperManagerSystem.getSingleSystem().getDictionCTableData(father);

						tModelC.setDataVector(rowData, title);
						childTable.getColumnModel().getColumn(6).setCellRenderer(new MyButtonRender());
						childTable.getColumnModel().getColumn(6).setCellEditor(new MyButtonEditor2());
						childTable.getColumnModel().getColumn(5).setCellRenderer(new MyButtonRender());
						childTable.getColumnModel().getColumn(5).setCellEditor(new MyButtonEditor2());

					}
				}
			}
		};

		JButton submitButton = new JButton("提交");
		submitButton.addActionListener(listener);
		submitButton.setBounds(150, 480, 150, 50);
		submitButton.setBackground(ZangQing);
		submitButton.setFont(fontOfButton);

		panel.add(submitButton);

		addFrame.add(panel);
		addFrame.setVisible(true);
	}
	public void add() {
		JFrame addFrame = new JFrame();
		addFrame.setLayout(new BorderLayout());
		addFrame.setBounds(500, 255, 488, 600);

		JPanel panel = new JPanel();
		panel.setLayout(null);
		addFrame.setResizable(false);

		JLabel label = new JLabel("新增类型");
		label.setBounds(0, 0, 488, 60);
		label.setOpaque(true);
		label.setFont(font);
		label.setBackground(ZangQing);
		label.setForeground(Color.white);
		panel.add(label);

		JLabel[] labels = new JLabel[3];
		String[] temp = { "ID", "*字典类型码", "*类型名称"};
		JTextField[] texts = new JTextField[3];

		for (int i = 0; i < 3; i++) {
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
				texts[i] = new JTextField("T" + System.currentTimeMillis());
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
					String nameCode = texts[1].getText();
					String name = texts[2].getText();

					if (nameCode.equals("") || name.equals("")) {
						JOptionPane.showMessageDialog(addFrame, "信息不足(标*为必填选项)");
					} else {
						DictionaryItemF newItemF = new DictionaryItemF(id, name, nameCode);
						SuperManagerSystem.getSingleSystem().getDiction().add(newItemF);
						addFrame.setVisible(false);
						
						String[] title = SuperManagerSystem.getSingleSystem().getDictionTableTitle();
						String[][] rowData = SuperManagerSystem.getSingleSystem().getDictionTableData();

						tModel.setDataVector(rowData, title);
						table.getColumnModel().getColumn(4).setCellRenderer(new MyButtonRender());
						table.getColumnModel().getColumn(4).setCellEditor(new MyButtonEditor1());
						table.getColumnModel().getColumn(5).setCellRenderer(new MyButtonRender());
						table.getColumnModel().getColumn(5).setCellEditor(new MyButtonEditor1());

					}
				}
			}
		};

		JButton submitButton = new JButton("提交");
		submitButton.addActionListener(listener);
		submitButton.setBounds(150, 480, 150, 50);
		submitButton.setBackground(ZangQing);
		submitButton.setFont(fontOfButton);

		panel.add(submitButton);

		addFrame.add(panel);
		addFrame.setVisible(true);
	}

	class MyButtonEditor1 extends DefaultCellEditor {
		private JPanel panel;
		private JButton button;

		public MyButtonEditor1() {
			super(new JTextField());
			this.setClickCountToStart(1);
			this.initButton();
			this.initPanel();
			this.panel.add(this.button);
		}

		private void initButton() {
			this.button = new JButton();
			this.button.setBounds(0, 0, 270, 50);
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
					if (buttonType.equals("修改/添加子项")) {
						modify(id);
					} else if (buttonType.equals("删除该类型")) {
						SuperManagerSystem.getSingleSystem().deleteDictionItem(id);
						String[] title = SuperManagerSystem.getSingleSystem().getDictionTableTitle();
						String[][] rowData = SuperManagerSystem.getSingleSystem().getDictionTableData();

						tModel.setDataVector(rowData, title);
						table.getColumnModel().getColumn(4).setCellRenderer(new MyButtonRender());
						table.getColumnModel().getColumn(4).setCellEditor(new MyButtonEditor1());
						table.getColumnModel().getColumn(5).setCellRenderer(new MyButtonRender());
						table.getColumnModel().getColumn(5).setCellEditor(new MyButtonEditor1());

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
	
	class MyButtonEditor2 extends DefaultCellEditor {
		private JPanel panel;
		private JButton button;

		public MyButtonEditor2() {
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
						modifyC(id);
					} else if (buttonType.equals("删除")) {
						String fatherId = SuperManagerSystem.getSingleSystem().getFatherIdByChildId(id);
						
						DictionaryItemF father = SuperManagerSystem.getSingleSystem().getDictionItemF(fatherId);
						for(DictionaryItemC c : father.getItems()) {
							if(c.getId().equals(id)) {
								father.getItems().remove(c);
								break;
							}
						}
						String[] title = SuperManagerSystem.getSingleSystem().getDictionCTableTitle();
						String[][] rowData = SuperManagerSystem.getSingleSystem().getDictionCTableData(father);

						tModelC.setDataVector(rowData, title);
						childTable.getColumnModel().getColumn(5).setCellRenderer(new MyButtonRender());
						childTable.getColumnModel().getColumn(5).setCellEditor(new MyButtonEditor2());
						childTable.getColumnModel().getColumn(6).setCellRenderer(new MyButtonRender());
						childTable.getColumnModel().getColumn(6).setCellEditor(new MyButtonEditor2());

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
