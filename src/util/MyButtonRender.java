package util;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class MyButtonRender implements TableCellRenderer {

	private static final Color ZangQing = new Color(0, 127, 174);
	private Font fontOfButton = new Font("微软雅黑", Font.PLAIN, 20);
	private JPanel panel;
	
	private JButton button;

	public MyButtonRender() {
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
	}
	private void initPanel() {
		this.panel = new JPanel();
		this.panel.setLayout(null);
	}
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		this.button.setText(value == null ? "" : String.valueOf(value));
		return this.panel;
	}

}
