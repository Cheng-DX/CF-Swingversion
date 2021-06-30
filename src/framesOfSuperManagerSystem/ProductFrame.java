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
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import entity.Product;
import systems.SuperManagerSystem;
import util.MyButtonRender;

@SuppressWarnings("serial")
public class ProductFrame extends JFrame {

	private JFrame productInterface;

	private JButton addButton = new JButton("新建");
	private JButton searchButton = new JButton("检索");

	private DefaultTableModel tModel = new DefaultTableModel();
	private JTable table = new JTable(tModel);
	private DefaultTableModel tModel2 = new DefaultTableModel();
	private JTable tableOfResult = new JTable(tModel2);

	private JLabel jLabel_1 = new JLabel("产品信息列表");
	private JPanel productPanel = new JPanel();

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

		productPanel.add(jLabel_1);
		productPanel.add(addButton);
		productPanel.add(searchButton);
		productPanel.add(loadTableData());
		productPanel.setBackground(Color.white);

		productPanel.setLayout(null);
		productPanel.setSize(1700, 800);
		productPanel.setBorder(BorderFactory.createLineBorder(ZangQing));
		return productPanel;

	}

	public void search() {
		JFrame addFrame = new JFrame();
		addFrame.setLayout(new BorderLayout());
		addFrame.setBounds(500, 255, 488, 600);

		JPanel panel = new JPanel();
		panel.setLayout(null);
		addFrame.setResizable(false);

		JLabel label = new JLabel("查询产品(支持模糊搜索)");
		label.setBounds(0, 0, 488, 60);
		label.setOpaque(true);
		label.setFont(font);
		label.setBackground(ZangQing);
		label.setForeground(Color.white);
		panel.add(label);

		JLabel[] labels = new JLabel[5];
		String[] temp = { "产品编号", "*产品名称", "*产品类别", "产品规格", "产品描述" };
		JTextField[] texts = new JTextField[4];
		JTextArea jArea = new JTextArea(10, 5);
		
		JComboBox<String> box = new JComboBox<String>();
		ArrayList<String> productType = SuperManagerSystem.getSingleSystem().getProductTypes();
		for(int i=0;i<productType.size();i++) {
			box.addItem(productType.get(i));
		}
		box.setFont(fontOfLabel);
		
		for (int i = 0; i < labels.length; i++) {
			labels[i] = new JLabel(temp[i], JLabel.CENTER);
			labels[i].setBounds(30, 40 + 50 * (i + 1), 120, 40);
			labels[i].setOpaque(true);
			labels[i].setFont(fontOfLabel);
			labels[i].setBackground(ZangQing);
			labels[i].setForeground(Color.white);
			panel.add(labels[i]);
			if (i == 0 ) 
			{
				texts[i] = new JTextField("唯一标识符");
				texts[i].setEditable(false);
				texts[i].setBounds(200, 40 + 50 * (i + 1), 233, 40);
				texts[i].setFont(fontOfLabel);
				texts[i].setBackground(ZangQing);
				texts[i].setForeground(Color.white);
				panel.add(texts[i]);
			} 
			if( i==1 || i==3)
			{
				texts[i] = new JTextField("");
				texts[i].setBounds(200, 40 + 50 * (i + 1), 233, 40);
				texts[i].setFont(fontOfLabel);
				texts[i].setBackground(ZangQing);
				texts[i].setForeground(Color.white);
				panel.add(texts[i]);
			}
			
			if(i==2) {
				box.setBounds(200, 40 + 50 * (i + 1), 233, 40);
				panel.add(box);
			}
			
		}

		jArea.setBounds(200, 290, 233, 150);
		jArea.setFont(fontOfLabel);
		jArea.setBackground(ZangQing);
		jArea.setForeground(Color.white);
		panel.add(jArea);
		ActionListener listener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFrame resultFrame = new JFrame();

				resultFrame.setBounds(300, 300, 1200, 500);
				Product target = new Product(texts[0].getText(), texts[1].getText(), texts[2].getText(), texts[3].getText(), jArea.getText());
				ArrayList<Product> results = SuperManagerSystem.getSingleSystem().searchProduct(target);

				String[] title = SuperManagerSystem.getSingleSystem().getProductTableTitle();
				String[][] data = new String[results.size()][8];

				for (int i = 0; i < results.size(); i++) {
					Product temp = results.get(i);
					data[i][0] = (i + 1) + "";
					data[i][1] = temp.getId();
					data[i][2] = temp.getName();
					data[i][3] = temp.getType();
					data[i][4] = temp.getSpecifications();
					data[i][5] = temp.getIntro();
					data[i][6] = "修改-" + temp.getId();
					data[i][7] = "删除-" + temp.getId();
				}

				tModel2.setDataVector(data, title);
				tableOfResult.getColumnModel().getColumn(7).setCellRenderer(new MyButtonRender());
				tableOfResult.getColumnModel().getColumn(7).setCellEditor(new MyButtonEditor());
				tableOfResult.getColumnModel().getColumn(6).setCellRenderer(new MyButtonRender());
				tableOfResult.getColumnModel().getColumn(6).setCellEditor(new MyButtonEditor());

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
				addFrame.setVisible(false);
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

	private JScrollPane loadTableData() {
		String[] title = SuperManagerSystem.getSingleSystem().getProductTableTitle();
		String[][] rowData = SuperManagerSystem.getSingleSystem().getProductTableData();

		tModel.setDataVector(rowData, title);
		table.getColumnModel().getColumn(7).setCellRenderer(new MyButtonRender());
		table.getColumnModel().getColumn(7).setCellEditor(new MyButtonEditor());
		table.getColumnModel().getColumn(6).setCellRenderer(new MyButtonRender());
		table.getColumnModel().getColumn(6).setCellEditor(new MyButtonEditor());

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

	public void modify(String id) {
		Product currentProduct = SuperManagerSystem.getSingleSystem().getProduct(id);
		JFrame modifyFrame = new JFrame();
		modifyFrame.setLayout(new BorderLayout());
		modifyFrame.setBounds(500, 255, 488, 600);

		JPanel panel = new JPanel();
		panel.setLayout(null);
		modifyFrame.setResizable(false);

		JLabel label = new JLabel("修改产品信息");
		label.setBounds(0, 0, 488, 60);
		label.setOpaque(true);
		label.setFont(font);
		label.setBackground(ZangQing);
		label.setForeground(Color.white);
		panel.add(label);

		JLabel[] labels = new JLabel[5];
		String[] temp = { "产品编号", "*产品名称", "*产品类别", "产品规格", "产品描述" };
		JTextField[] texts = new JTextField[4];
		JTextArea jArea = new JTextArea(currentProduct.getIntro());
		
		JComboBox<String> box = new JComboBox<String>();
		ArrayList<String> productType = SuperManagerSystem.getSingleSystem().getProductTypes();
		for(int i=0;i<productType.size();i++) {
			box.addItem(productType.get(i));
		}
		box.setFont(fontOfLabel);
		
		for (int i = 0; i < labels.length; i++) {
			labels[i] = new JLabel(temp[i], JLabel.CENTER);
			labels[i].setBounds(30, 40 + 50 * (i + 1), 120, 40);
			labels[i].setOpaque(true);
			labels[i].setFont(fontOfLabel);
			labels[i].setBackground(ZangQing);
			labels[i].setForeground(Color.white);
			panel.add(labels[i]);
			if (i == 0 ) 
			{
				texts[i] = new JTextField(currentProduct.getId());
				texts[i].setEditable(false);
				texts[i].setBounds(200, 40 + 50 * (i + 1), 233, 40);
				texts[i].setFont(fontOfLabel);
				texts[i].setBackground(ZangQing);
				texts[i].setForeground(Color.white);
				panel.add(texts[i]);
			} 
			if( i==1)
			{
				texts[i] = new JTextField(currentProduct.getName());
				texts[i].setBounds(200, 40 + 50 * (i + 1), 233, 40);
				texts[i].setFont(fontOfLabel);
				texts[i].setBackground(ZangQing);
				texts[i].setForeground(Color.white);
				panel.add(texts[i]);
			}
			if( i==3)
			{
				texts[i] = new JTextField(currentProduct.getSpecifications());
				texts[i].setBounds(200, 40 + 50 * (i + 1), 233, 40);
				texts[i].setFont(fontOfLabel);
				texts[i].setBackground(ZangQing);
				texts[i].setForeground(Color.white);
				panel.add(texts[i]);
			}
			
			if(i==2) {
				box.setSelectedItem(currentProduct.getType());
				box.setBounds(200, 40 + 50 * (i + 1), 233, 40);
				panel.add(box);
			}
			
		}

		jArea.setBounds(200, 290, 233, 150);
		jArea.setFont(fontOfLabel);
		jArea.setBackground(ZangQing);
		jArea.setForeground(Color.white);
		panel.add(jArea);

		ActionListener listener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getActionCommand().equals("提交")) {
					String name = texts[1].getText();
					String type = (String) box.getSelectedItem();
					String specifications = texts[3].getText();
					String intro = jArea.getText();

					if (name.equals("") || type.equals("")) {
						JOptionPane.showMessageDialog(modifyFrame, "信息不足(标*为必填选项)");
					} else {

						currentProduct.setName(name);
						currentProduct.setIntro(intro);
						currentProduct.setType(type);
						currentProduct.setSpecifications(specifications);

						modifyFrame.setVisible(false);
						JOptionPane.showMessageDialog(productInterface, "修改成功", "成功", JOptionPane.INFORMATION_MESSAGE);

						String[] title = SuperManagerSystem.getSingleSystem().getProductTableTitle();
						String[][] rowData = SuperManagerSystem.getSingleSystem().getProductTableData();

						tModel.setDataVector(rowData, title);
						table.getColumnModel().getColumn(7).setCellRenderer(new MyButtonRender());
						table.getColumnModel().getColumn(7).setCellEditor(new MyButtonEditor());
						table.getColumnModel().getColumn(6).setCellRenderer(new MyButtonRender());
						table.getColumnModel().getColumn(6).setCellEditor(new MyButtonEditor());

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

		modifyFrame.add(panel);
		modifyFrame.setVisible(true);
	}

	public void add() {
		JFrame addFrame = new JFrame();
		addFrame.setLayout(new BorderLayout());
		addFrame.setBounds(500, 255, 488, 600);

		JPanel panel = new JPanel();
		panel.setLayout(null);
		addFrame.setResizable(false);

		JLabel label = new JLabel("新增产品");
		label.setBounds(0, 0, 488, 60);
		label.setOpaque(true);
		label.setFont(font);
		label.setBackground(ZangQing);
		label.setForeground(Color.white);
		panel.add(label);

		JLabel[] labels = new JLabel[5];
		String[] temp = { "产品编号", "*产品名称", "*产品类别", "产品规格", "产品描述" };
		JTextField[] texts = new JTextField[4];
		JTextArea jArea = new JTextArea(10, 5);
		
		JComboBox<String> box = new JComboBox<String>();
		ArrayList<String> productType = SuperManagerSystem.getSingleSystem().getProductTypes();
		for(int i=0;i<productType.size();i++) {
			box.addItem(productType.get(i));
		}
		box.setFont(fontOfLabel);
		
		for (int i = 0; i < labels.length; i++) {
			labels[i] = new JLabel(temp[i], JLabel.CENTER);
			labels[i].setBounds(30, 40 + 50 * (i + 1), 120, 40);
			labels[i].setOpaque(true);
			labels[i].setFont(fontOfLabel);
			labels[i].setBackground(ZangQing);
			labels[i].setForeground(Color.white);
			panel.add(labels[i]);
			if (i == 0 ) 
			{
				texts[i] = new JTextField("P" + System.currentTimeMillis());
				texts[i].setEditable(false);
				texts[i].setBounds(200, 40 + 50 * (i + 1), 233, 40);
				texts[i].setFont(fontOfLabel);
				texts[i].setBackground(ZangQing);
				texts[i].setForeground(Color.white);
				panel.add(texts[i]);
			} 
			if( i==1 || i==3)
			{
				texts[i] = new JTextField("");
				texts[i].setBounds(200, 40 + 50 * (i + 1), 233, 40);
				texts[i].setFont(fontOfLabel);
				texts[i].setBackground(ZangQing);
				texts[i].setForeground(Color.white);
				panel.add(texts[i]);
			}
			
			if(i==2) {
				box.setBounds(200, 40 + 50 * (i + 1), 233, 40);
				panel.add(box);
			}
			
		}

		jArea.setBounds(200, 290, 233, 150);
		jArea.setFont(fontOfLabel);
		jArea.setBackground(ZangQing);
		jArea.setForeground(Color.white);
		panel.add(jArea);

		ActionListener listener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getActionCommand().equals("提交")) {
					String id = texts[0].getText();
					String name = texts[1].getText();
					String type = (String) box.getSelectedItem();
					String specifications = texts[3].getText();
					String intro = jArea.getText();

					if (name.equals("") || type.equals("")) {
						JOptionPane.showMessageDialog(addFrame, "信息不足(标*为必填选项)");
					} else {
						Product newProduct = new Product(id, name, type, specifications, intro);
						SuperManagerSystem.getSingleSystem().getProducts().add(newProduct);
						addFrame.setVisible(false);
						JOptionPane.showMessageDialog(productInterface, "添加成功", "成功", JOptionPane.INFORMATION_MESSAGE);

						String[] title = SuperManagerSystem.getSingleSystem().getProductTableTitle();
						String[][] rowData = SuperManagerSystem.getSingleSystem().getProductTableData();

						tModel.setDataVector(rowData, title);
						table.getColumnModel().getColumn(7).setCellRenderer(new MyButtonRender());
						table.getColumnModel().getColumn(7).setCellEditor(new MyButtonEditor());
						table.getColumnModel().getColumn(6).setCellRenderer(new MyButtonRender());
						table.getColumnModel().getColumn(6).setCellEditor(new MyButtonEditor());

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
					if (buttonType.equals("修改")) {
						modify(id);
					} else if (buttonType.equals("删除")) {
						SuperManagerSystem.getSingleSystem().deleteProduct(id);
						String[] title = SuperManagerSystem.getSingleSystem().getProductTableTitle();
						String[][] rowData = SuperManagerSystem.getSingleSystem().getProductTableData();

						tModel.setDataVector(rowData, title);
						table.getColumnModel().getColumn(6).setCellRenderer(new MyButtonRender());
						table.getColumnModel().getColumn(6).setCellEditor(new MyButtonEditor());
						table.getColumnModel().getColumn(7).setCellRenderer(new MyButtonRender());
						table.getColumnModel().getColumn(7).setCellEditor(new MyButtonEditor());

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
