package entrance;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import entity.Dealer;
import entity.Factory;
import entity.FactoryManager;
import entity.User;
import framesOfDealerSystem.OrderFrameOfDealer;
import framesOfFactorySystem.MyEquipmentFrame;
import framesOfFactorySystem.OrderFrameOfFactory;
import framesOfSuperManagerSystem.DataDictionaryFrame;
import framesOfSuperManagerSystem.EquipmentFrame;
import framesOfSuperManagerSystem.FactoryFrame;
import framesOfSuperManagerSystem.ProductFrame;
import framesOfSuperManagerSystem.UserFrame;
import systems.SuperManagerSystem;

@SuppressWarnings("serial")
public class MainFrame extends JFrame {
	private JButton button1 = new JButton("登录");
	private JButton button2 = new JButton("没有账号?立即注册");

	private static final Color ZangQing = new Color(0, 127, 174);
	private static final Color QianHui = new Color(219, 222, 221);
	private static final Color ShenHui = new Color(40, 41, 41);
	private Font font = new Font("微软雅黑", ALLBITS, 28);
	private Font fontOfButton = new Font("微软雅黑", ALLBITS, 20);
	private Font fontOfLabel = new Font("微软雅黑", ALLBITS, 15);
	private Font fontOfLabel2 = new Font("微软雅黑", ALLBITS, 18);
	private Font fontOfLabel3 = new Font("微软雅黑", ALLBITS, 23);

	public void run() {
		setSize(500, 400);
		setLocation(700, 300);
		JLabel label = new JLabel(new ImageIcon("background.jpg"));
		label.setBounds(0, 0, this.getWidth(), this.getHeight());

		JTextField accText = new JTextField("");
		JPasswordField passText = new JPasswordField("");
		
		JLabel label1 = new JLabel("账号:");
		JLabel label2 = new JLabel("密码:");
		label1.setBounds(50, 80, 100, 50);
		label1.setForeground(Color.white);
		label1.setFont(fontOfLabel2);
		label2.setBounds(50, 180, 100, 50);
		label2.setForeground(Color.white);
		label2.setFont(fontOfLabel2);
		accText.setBounds(150, 80, 250, 50);
		accText.setFont(fontOfLabel2);
		accText.setBackground(ZangQing);
		accText.setForeground(Color.WHITE);

		passText.setBounds(150, 180, 250, 50);
		passText.setFont(fontOfLabel2);
		passText.setBackground(ZangQing);
		passText.setForeground(Color.WHITE);

		ActionListener listener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String userId;
				if (e.getActionCommand().equals("没有账号?立即注册")) {
					regist();
				} else if (e.getActionCommand().equals("登录")) {
					String userAcc = accText.getText();
					String password = passText.getText();
					User user = SuperManagerSystem.getSingleSystem().getUserByAcc(userAcc);
					if (user == null) {
						JOptionPane.showMessageDialog(null, "账号错误");
					} else {
						if (password.equals(user.getPassword())) {
							setVisible(false);
							userId = user.getId();
							if (userId.startsWith("D")) {
								loadDealerInterface(userId);
							} else if (userId.startsWith("M")) {
								String factoryId = ((FactoryManager) user).getFactoryId();
								loadFactoryManagerInterface(factoryId);
							} else if (userAcc.equals("a")) {
								loadSuperManagerInterface();
							}
						} else {
							JOptionPane.showMessageDialog(null, "密码错误");
						}
					}

				}
			}
		};

		button1.setBounds(90, 280, 140, 60);
		button1.setFont(fontOfLabel3);
		button1.setBackground(ZangQing);
		button1.setForeground(Color.WHITE);
		button1.addActionListener(listener);
		button2.setBounds(240, 300, 200, 40);
		button2.setFont(fontOfLabel2);
		button2.setContentAreaFilled(false);
		button2.setBorder(null);
		button2.setForeground(Color.WHITE);
		button2.addActionListener(listener);
		JPanel imagePanel = (JPanel) this.getContentPane();
		imagePanel.setOpaque(false);
		this.getLayeredPane().add(label, new Integer(Integer.MIN_VALUE));
		this.getLayeredPane().add(button1);
		this.getLayeredPane().add(button2);
		this.getLayeredPane().add(accText);
		this.getLayeredPane().add(passText);
		this.getLayeredPane().add(label1);
		this.getLayeredPane().add(label2);

		// this.setUndecorated(true);
		setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private void loadFactoryManagerInterface(String factoryId) {
		JFrame currentFrame = new JFrame();
		currentFrame.setLayout(null);
		currentFrame.setBounds(10, 10, 1900, 1000);
		currentFrame.setResizable(false);
		String name = SuperManagerSystem.getSingleSystem().getFactory(factoryId).getManager();
		JLabel label = new JLabel("云工厂管理员: " + name + "    云制造平台欢迎您!");
		label.setBounds(0, 0, 2000, 60);
		label.setOpaque(true);
		label.setFont(font);
		label.setBackground(ShenHui);
		label.setForeground(Color.white);
		currentFrame.add(label);
		JPanel panelOfSelection = new JPanel();
		panelOfSelection.setLayout(null);
		panelOfSelection.setBounds(0, 60, 200, 1000);
		panelOfSelection.setBackground(QianHui);

		panelOfSelection.setLayout(null);
		JLabel[] labels = new JLabel[2];
		String[] temp = { "我的工厂", "订单管理" };
		for (int i = 0; i < temp.length; i++) {
			labels[i] = new JLabel(temp[i]);
			labels[i].setBounds(20, 50 + 200 * i, 200, 60);
			labels[i].setFont(fontOfLabel3);
			labels[i].setForeground(Color.black);
			panelOfSelection.add(labels[i]);
		}
		JPanel panelOfFeatures = new JPanel();
		panelOfFeatures.setBackground(Color.white);
		panelOfFeatures.setBounds(250, 80, 1600, 800);
		CardLayout cardLayout = new CardLayout();
		panelOfFeatures.setLayout(cardLayout);
		ActionListener listener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(panelOfFeatures, e.getActionCommand());
			}
		};

		String[] temp2 = { "我的设备", "订单接单/排产" };
		JButton[] buttons = new JButton[2];
		for (int i = 0; i < temp2.length; i++) {
			buttons[i] = new JButton(temp2[i]);
			buttons[i].setContentAreaFilled(false);
			buttons[i].setBorder(null);
			buttons[i].setBounds(20, 120 + 200 * i, 150, 60);
			buttons[i].setFont(fontOfLabel2);
			buttons[i].setForeground(ZangQing);
			buttons[i].addActionListener(listener);
			panelOfSelection.add(buttons[i]);
		}

		JPanel equipmentPanel = (new MyEquipmentFrame(factoryId)).loadInterface();
		JPanel orderPanel = (new OrderFrameOfFactory(factoryId)).loadInterface();

		panelOfFeatures.add("我的设备", equipmentPanel);
		panelOfFeatures.add("订单接单/排产", orderPanel);

		currentFrame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				SuperManagerSystem.getSingleSystem().writeEquipmentData();
				SuperManagerSystem.getSingleSystem().writeFactoryData();
				SuperManagerSystem.getSingleSystem().writeOrderData();
				SuperManagerSystem.getSingleSystem().writeProductData();
				SuperManagerSystem.getSingleSystem().writeUserData();
				System.exit(0);
			}
		});

		currentFrame.add(panelOfSelection);
		currentFrame.add(panelOfFeatures);
		currentFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		currentFrame.setVisible(true);
	}

	private void loadDealerInterface(String dealerId) {
		JFrame currentFrame = new JFrame();
		currentFrame.setLayout(null);
		currentFrame.setBounds(10, 10, 1900, 1000);
		currentFrame.setResizable(false);
		// currentFrame.setUndecorated(true);
		String string = SuperManagerSystem.getSingleSystem().getUser(dealerId).getReal_name();
		JLabel label = new JLabel("经销商: " + string + "       云制造平台欢迎您！");
		label.setBounds(0, 0, 2000, 60);
		label.setOpaque(true);
		label.setFont(font);
		label.setBackground(ShenHui);
		label.setForeground(Color.white);
		currentFrame.add(label);
		JPanel panelOfSelection = new JPanel();
		panelOfSelection.setLayout(null);
		panelOfSelection.setBounds(0, 60, 200, 1000);
		panelOfSelection.setBackground(QianHui);

		panelOfSelection.setLayout(null);
		JLabel label1 = new JLabel("订单管理");
		label1.setBounds(20, 50, 200, 60);
		label1.setFont(fontOfLabel3);
		label1.setForeground(Color.black);
		panelOfSelection.add(label1);

		JPanel panelOfFeatures = new JPanel();
		panelOfFeatures.setBackground(Color.white);
		panelOfFeatures.setBounds(250, 80, 1600, 800);
		CardLayout cardLayout = new CardLayout();
		panelOfFeatures.setLayout(cardLayout);
		ActionListener listener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				cardLayout.show(panelOfFeatures, e.getActionCommand());

			}
		};
		JButton button = new JButton("我的订单");
		button.setContentAreaFilled(false);
		button.setBorder(null);
		button.setBounds(20, 120, 100, 60);
		button.setFont(fontOfLabel2);
		button.setForeground(ZangQing);
		button.addActionListener(listener);
		panelOfSelection.add(button);
		currentFrame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				SuperManagerSystem.getSingleSystem().writeEquipmentData();
				SuperManagerSystem.getSingleSystem().writeFactoryData();
				SuperManagerSystem.getSingleSystem().writeOrderData();
				SuperManagerSystem.getSingleSystem().writeProductData();
				SuperManagerSystem.getSingleSystem().writeUserData();
				System.exit(0);
			}
		});

		JPanel orderPanel = (new OrderFrameOfDealer(dealerId)).loadInterface();
		panelOfFeatures.add("我的订单", orderPanel);

		currentFrame.add(panelOfSelection);
		currentFrame.add(panelOfFeatures);
		currentFrame.setVisible(true);
	}

	private void loadSuperManagerInterface() {
		JFrame currentFrame = new JFrame();
		currentFrame.setLayout(null);
		currentFrame.setBounds(10, 10, 1900, 1000);
		currentFrame.setResizable(false);
		// currentFrame.setUndecorated(true);
		JLabel label = new JLabel("超级管理员：DUAN    云制造平台欢迎您！");
		label.setBounds(0, 0, 2000, 60);
		label.setOpaque(true);
		label.setFont(font);
		label.setBackground(ShenHui);
		label.setForeground(Color.white);
		currentFrame.add(label);
		JPanel panelOfSelection = new JPanel();
		panelOfSelection.setLayout(null);
		panelOfSelection.setBounds(0, 60, 200, 1000);
		panelOfSelection.setBackground(QianHui);

		panelOfSelection.setLayout(null);
		JLabel[] labels = new JLabel[5];
		String[] temp = { "系统设置", "云工厂", "产品管理", "产能中心", "数据字典" };
		for (int i = 0; i < temp.length; i++) {
			labels[i] = new JLabel(temp[i]);
			labels[i].setBounds(20, 50 + 150 * i, 200, 60);
			labels[i].setFont(fontOfLabel3);
			labels[i].setForeground(Color.black);
			panelOfSelection.add(labels[i]);
		}
		JPanel panelOfFeatures = new JPanel();
		panelOfFeatures.setBackground(Color.white);
		panelOfFeatures.setBounds(250, 80, 1600, 800);
		CardLayout cardLayout = new CardLayout();
		panelOfFeatures.setLayout(cardLayout);

		JPanel userPanel = (new UserFrame()).loadInterface();

		JPanel productPanel = (new ProductFrame()).loadInterface();

		JPanel dictionPanel = (new DataDictionaryFrame()).loadInterface();

		ActionListener listener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (e.getActionCommand().equals("云工厂信息")) {
					FactoryFrame temp1 = new FactoryFrame();
					temp1.upDataTable();
					JPanel factoryPanel = temp1.loadInterface();
					panelOfFeatures.add("云工厂信息", factoryPanel);
				} else if (e.getActionCommand().equals("设备管理")) {
					EquipmentFrame temp1 = new EquipmentFrame();
					temp1.upDateTable1();
					JPanel equipmentPanel = temp1.loadInterface();
					panelOfFeatures.add("设备管理", equipmentPanel);
				}

				cardLayout.show(panelOfFeatures, e.getActionCommand());
			}
		};

		String[] temp2 = { "用户管理", "云工厂信息", "产品信息管理", "设备管理", "数据字典管理" };
		JButton[] buttons = new JButton[5];
		for (int i = 0; i < temp2.length; i++) {
			buttons[i] = new JButton(temp2[i]);
			buttons[i].setContentAreaFilled(false);
			buttons[i].setBorder(null);
			buttons[i].setBounds(20, 120 + 150 * i, 130, 60);
			buttons[i].setFont(fontOfLabel2);
			buttons[i].setForeground(ZangQing);
			buttons[i].addActionListener(listener);
			panelOfSelection.add(buttons[i]);
		}
		currentFrame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				SuperManagerSystem.getSingleSystem().writeEquipmentData();
				SuperManagerSystem.getSingleSystem().writeFactoryData();
				SuperManagerSystem.getSingleSystem().writeOrderData();
				SuperManagerSystem.getSingleSystem().writeProductData();
				SuperManagerSystem.getSingleSystem().writeUserData();
				SuperManagerSystem.getSingleSystem().writeDictionData();

				System.exit(0);
			}
		});

		panelOfFeatures.add("用户管理", userPanel);
		panelOfFeatures.add("产品信息管理", productPanel);
		panelOfFeatures.add("数据字典管理", dictionPanel);

		currentFrame.add(panelOfSelection);
		currentFrame.add(panelOfFeatures);
		currentFrame.setVisible(true);
	}

	private void regist() {
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

	public static void main(String[] args) {
		MainFrame system = new MainFrame();
		system.run();
	}
}
