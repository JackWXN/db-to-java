package com.wxn.generate;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.Properties;

public class AppMain {
	private JFrame frame;
	private JTextField textDataUrl;
	private JPanel panel;
	private JLabel userName;
	private JTextField TextuserName;
	private JTextField textPassword;
	private JLabel password;
	private JTextField packsName;
	private JTable table;
	private JButton btncontroller;
	private JButton btnservice;
	private JButton btndaoMapper;
	private DefaultTableModel model;
	private JLabel label_1;
	private JLabel label_2;
	private JLabel lblccxccxabc;
	private JPanel panel_3;
	private JButton button;
	private JLabel label_3;
	private JComboBox comboBox;
	private JLabel lblCreateByWxn;
	private JPanel panel_4;
	private JLabel label_4;
	private JPanel panel_5;
	private JLabel label_5;
	private JLabel label_6;
	private JLabel label_6_1;
	private static JTextField outPath;
	private JTextField packageName2;
	private JProgressBar progressBar;
	private JLabel label_7;
	private JLabel lblcom;
	public static int pro = 0;
	public static String url1 = "";
	public static String user1 = "";
	public static String password1 = "";
	static InputStream conf = null;
	static InputStream confUser = null;
	static Properties properties = null;
	private JButton chooseFilePath;
	private static JTextField filePath;
	private JFileChooser jfc;

	public static void main(String[] args) {
		try {
			conf = new FileInputStream(System.getProperty("user.home") + File.separator + "db.properties");
		} catch (FileNotFoundException var8) {
			var8.printStackTrace();
			System.out.println("read jar conf");
			conf = AppMain.class.getResourceAsStream("/config/db.properties");
			boolean var2 = false;

			try {
				FileOutputStream out = new FileOutputStream(System.getProperty("user.home") + File.separator + "db.properties");
				byte[] buff = new byte[128];

				int n;
				while(-1 != (n = conf.read(buff))) {
					out.write(buff, 0, n);
				}

				out.flush();
				out.close();
			} catch (Exception var7) {
				var7.printStackTrace();
			}
		}

		try {
			confUser = new FileInputStream(System.getProperty("user.home") + File.separator + "db.properties");
			properties = new Properties();

			try {
				properties.load(confUser);
				url1 = properties.getProperty("url");
				user1 = properties.getProperty("user");
				password1 = properties.getProperty("pwd");
				confUser.close();
			} catch (IOException var5) {
				var5.printStackTrace();
			}
		} catch (FileNotFoundException var6) {
			var6.printStackTrace();
		}

		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					AppMain window = new AppMain();
					window.frame.setVisible(true);
				} catch (Exception var2) {
					var2.printStackTrace();
				}

			}
		});
	}

	public AppMain() {
		this.initialize();
	}

	private void initialize() {
		this.frame = new JFrame();
		this.frame.getContentPane().setBackground(new Color(Integer.decode("#F0F7FC")));
		this.frame.setBackground(new Color(Integer.decode("#F0F7FB")));
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		this.frame.setSize(476, 522);
		int x = (int)(toolkit.getScreenSize().getWidth() - (double)this.frame.getWidth()) / 2;
		int y = (int)(toolkit.getScreenSize().getHeight() - (double)this.frame.getHeight()) / 2;
		this.frame.setLocation(x, y);
		this.frame.setDefaultCloseOperation(3);
		this.frame.getContentPane().setLayout((LayoutManager)null);
		this.panel = new JPanel();
		this.panel.setBorder(new BevelBorder(0, (Color)null, (Color)null, (Color)null, (Color)null));
		this.panel.setBackground(new Color(Integer.decode("#D1E6F5")));
		this.panel.setBounds(0, 34, 460, 139);
		this.frame.getContentPane().add(this.panel);
		this.panel.setLayout((LayoutManager)null);
		JLabel dataUrl = new JLabel("数据url:");
		dataUrl.setBackground(UIManager.getColor("FormattedTextField.selectionBackground"));
		dataUrl.setBounds(10, 13, 48, 15);
		this.panel.add(dataUrl);
		this.textDataUrl = new JTextField(url1);
		this.textDataUrl.setBounds(66, 10, 374, 18);
		this.panel.add(this.textDataUrl);
		this.textDataUrl.setColumns(10);
		this.userName = new JLabel("用户名:");
		this.userName.setBackground(UIManager.getColor("FormattedTextField.selectionBackground"));
		this.userName.setBounds(10, 41, 48, 15);
		this.panel.add(this.userName);
		this.TextuserName = new JTextField(user1);
		this.TextuserName.setColumns(10);
		this.TextuserName.setBounds(66, 38, 93, 15);
		this.panel.add(this.TextuserName);
		this.textPassword = new JTextField(password1);
		this.textPassword.setColumns(10);
		this.textPassword.setBounds(66, 66, 93, 16);
		this.panel.add(this.textPassword);
		this.password = new JLabel("密    码:");
		this.password.setBounds(10, 69, 48, 15);
		this.panel.add(this.password);
		this.button = new JButton("测试连接");
		this.button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent paramActionEvent) {
				AppMain.this.jdbcConnect();
			}
		});
		this.button.setBounds(10, 99, 149, 30);
		this.panel.add(this.button);
		this.label_3 = new JLabel("数据库类型");
		this.label_3.setBounds(207, 41, 87, 15);
		this.panel.add(this.label_3);
		this.comboBox = new JComboBox();
		this.comboBox.addItem("MySQL");
		this.comboBox.setBounds(284, 38, 70, 21);
		this.panel.add(this.comboBox);
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new BevelBorder(0, (Color)null, (Color)null, (Color)null, (Color)null));
		panel_1.setBackground(new Color(Integer.decode("#D1E6F5")));
		panel_1.setBounds(0, 208, 460, 90);
		this.frame.getContentPane().add(panel_1);
		panel_1.setLayout((LayoutManager)null);
		JLabel label = new JLabel("输入包名:");
		label.setBounds(10, 10, 89, 15);
		panel_1.add(label);
		this.packsName = new JTextField();
		this.packsName.setColumns(10);
		this.packsName.setBounds(80, 10, 163, 16);
		panel_1.add(this.packsName);
//		this.lblccxccxabc = new JLabel("可不填写(一级a二级b则生成com.a.b.xxx)");
//		this.lblccxccxabc.setBounds(206, 35, 244, 15);
//		panel_1.add(this.lblccxccxabc);
		this.label_6 = new JLabel("输出位置:");
		this.label_6.setBounds(10, 35, 89, 15);
		panel_1.add(this.label_6);
		this.label_6_1 = new JLabel("输出目录:");
		this.label_6_1.setBounds(10, 60, 89, 15);
		panel_1.add(this.label_6_1);
		this.outPath = new JTextField();
		this.outPath.setEditable(false);
		this.outPath.setBounds(80, 60, 163, 16);
		panel_1.add(this.outPath);
//		this.packageName2 = new JTextField();
//		this.packageName2.setColumns(10);
//		this.packageName2.setBounds(80, 35, 113, 16);
//		panel_1.add(this.packageName2);
		this.filePath = new JTextField();
		this.filePath.setColumns(10);
		this.filePath.setEditable(false);
		this.filePath.setBounds(80, 35, 163, 16);
		panel_1.add(this.filePath);
		this.chooseFilePath = new JButton("请选择");
		jfc = new JFileChooser();
		jfc.setCurrentDirectory(new File("D://"));
		this.chooseFilePath.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent paramActionEvent) {
				AppMain.filePath.setText("");
				jfc.setFileSelectionMode(1);// 设定只能选择到文件夹
				int state = jfc.showOpenDialog(null);// 此句是打开文件选择器界面的触发语句
				if (state != 1) {
					File f = jfc.getSelectedFile();// f为选择到的目录
					AppMain.filePath.setText(f.getAbsolutePath());
					AppMain.outPath.setText(f.getAbsolutePath() + "wxn_mybatisGenerate" + File.separator);
				}
			}
		});
		this.chooseFilePath.setBounds(256, 35, 80, 16);
		panel_1.add(this.chooseFilePath);
		this.lblcom = new JLabel("必须填写(例:xx.xx)");
		this.lblcom.setBounds(256, 10, 200, 15);
		panel_1.add(this.lblcom);
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new BevelBorder(0, (Color)null, (Color)null, (Color)null, (Color)null));
		panel_2.setBackground(new Color(Integer.decode("#D1E6F5")));
		panel_2.setBounds(1, 340, 460, 127);
		this.frame.getContentPane().add(panel_2);
		panel_2.setLayout((LayoutManager)null);
		String[] columnNames = new String[]{"表名", "主键类型", "操作"};
		this.model = new DefaultTableModel((Object[][])null, columnNames);
		this.table = new JTable(this.model){
			@Override
			public boolean isCellEditable(int row, int column){
				return false;
			}
		};
		this.table.setBounds(1, 27, 329, 43);
		this.table.setModel(this.model);
		//0是代表的第一列
		TableColumn column = table.getColumnModel().getColumn(0);
		//这个是设置列的宽度
		column.setPreferredWidth(199);
		column = table.getColumnModel().getColumn(1);
		column.setPreferredWidth(80);
		column = table.getColumnModel().getColumn(2);
		column.setPreferredWidth(50);
		this.table.setBorder(BorderFactory.createEtchedBorder());
		JScrollPane scrollPane = new JScrollPane(this.table);
		scrollPane.setBounds(0, 0, 281, 127);
		panel_2.add(scrollPane);
		this.btncontroller = new JButton("生成controller");
		this.btncontroller.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent paramActionEvent) {
				if ("".equals(AppMain.this.packsName.getText())) {
					JOptionPane.showMessageDialog((Component)null, "输入包名必须填写", "提示", 0);
				} else if (AppMain.this.table.getSelectedRows().length == 0) {
					JOptionPane.showMessageDialog((Component)null, "至少选择一张表", "提示", 0);
				} else {
					(new Thread() {
						boolean flag = true;

						@Override
						public void run() {
							while(this.flag) {
								try {
									Thread.sleep(100L);
									AppMain.this.progressBar.setValue(AppMain.pro);
									if (100 == AppMain.pro) {
										AppMain.pro = 0;
										this.flag = false;
									}
								} catch (InterruptedException var2) {
									var2.printStackTrace();
								}
							}

							AppMain.this.progressBar.setString("Controller完成");
						}
					}).start();
					(new Thread() {
						public void run() {
							Generate.genController(AppMain.this.progressBar, AppMain.this.table, AppMain.this.packsName.getText(),
									"", AppMain.this.filePath.getText());
						}
					}).start();
				}
			}
		});
		this.btncontroller.setBounds(308, 10, 132, 23);
		panel_2.add(this.btncontroller);
		this.btnservice = new JButton("生成service");
		this.btnservice.setBounds(308, 54, 132, 23);
		panel_2.add(this.btnservice);
		this.btndaoMapper = new JButton("生成逆向工程");
		this.btndaoMapper.setToolTipText("生成Model Mapper Xml Dao");
		this.btndaoMapper.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent paramActionEvent) {
				if ("".equals(AppMain.this.packsName.getText())) {
					JOptionPane.showMessageDialog((Component)null, "输入包名必须填写", "提示", 0);
				} else if (AppMain.this.table.getSelectedRows().length == 0) {
					JOptionPane.showMessageDialog((Component)null, "至少选择一张表", "提示", 0);
				} else {
					(new Thread() {
						boolean flag = true;

						@Override
						public void run() {
							while(this.flag) {
								try {
									Thread.sleep(100L);
									AppMain.this.progressBar.setValue(AppMain.pro);
									if (100 == AppMain.pro) {
										AppMain.pro = 0;
										this.flag = false;
									}
								} catch (InterruptedException var2) {
									var2.printStackTrace();
								}
							}

							AppMain.this.progressBar.setString("逆向工程完成");
						}
					}).start();
					(new Thread() {
						public void run() {
							Generate.genMapper(AppMain.this.progressBar, AppMain.this.packsName.getText(), "",
									AppMain.this.table, AppMain.this.textDataUrl.getText(), AppMain.this.TextuserName.getText(),
									AppMain.this.textPassword.getText(), AppMain.this.filePath.getText());
						}
					}).start();
				}
			}
		});
		this.btndaoMapper.setBounds(308, 94, 132, 23);
		panel_2.add(this.btndaoMapper);
		this.label_2 = new JLabel("生成规则配置");
		this.label_2.setBounds(20, 134, 102, 15);
		this.frame.getContentPane().add(this.label_2);
		this.panel_3 = new JPanel();
		this.panel_3.setBorder(new BevelBorder(0, (Color)null, (Color)null, (Color)null, (Color)null));
		this.panel_3.setBounds(0, 0, 460, 37);
		this.panel_3.setBackground(new Color(Integer.decode("#D1E6F5")));
		this.frame.getContentPane().add(this.panel_3);
		this.panel_3.setLayout((LayoutManager)null);
		this.label_1 = new JLabel("数据库配置");
		this.label_1.setBounds(10, 10, 121, 15);
		this.panel_3.add(this.label_1);
		this.lblCreateByWxn = new JLabel("Create by wxn");
		this.lblCreateByWxn.setBounds(302, 440, 188, 33);
		this.frame.getContentPane().add(this.lblCreateByWxn);
		this.panel_4 = new JPanel();
		this.panel_4.setLayout((LayoutManager)null);
		this.panel_4.setBorder(new BevelBorder(0, (Color)null, (Color)null, (Color)null, (Color)null));
		this.panel_4.setBackground(new Color(209, 230, 245));
		this.panel_4.setBounds(0, 176, 460, 37);
		this.frame.getContentPane().add(this.panel_4);
		this.label_4 = new JLabel("规则配置");
		this.label_4.setBounds(10, 10, 121, 15);
		this.panel_4.add(this.label_4);
		this.panel_5 = new JPanel();
		this.panel_5.setLayout((LayoutManager)null);
		this.panel_5.setBorder(new BevelBorder(0, (Color)null, (Color)null, (Color)null, (Color)null));
		this.panel_5.setBackground(new Color(209, 230, 245));
		this.panel_5.setBounds(0, 300, 460, 37);
		this.frame.getContentPane().add(this.panel_5);
		this.label_5 = new JLabel("生成操作");
		this.label_5.setBounds(10, 10, 121, 15);
		this.panel_5.add(this.label_5);
		this.progressBar = new JProgressBar();
		this.progressBar.setBounds(141, 10, 282, 14);
		this.progressBar.setMinimum(0);
		this.progressBar.setMaximum(100);
		this.progressBar.setStringPainted(true);
		this.panel_5.add(this.progressBar);
		this.label_7 = new JLabel("进度：");
		this.label_7.setBounds(103, 10, 45, 15);
		this.panel_5.add(this.label_7);
		this.btnservice.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent paramActionEvent) {
				if ("".equals(AppMain.this.packsName.getText())) {
					JOptionPane.showMessageDialog((Component)null, "输入包名必须填写", "提示", 0);
				} else if (AppMain.this.table.getSelectedRows().length == 0) {
					JOptionPane.showMessageDialog((Component)null, "至少选择一张表", "提示", 0);
				} else {
					(new Thread() {
						boolean flag = true;

						@Override
						public void run() {
							while(this.flag) {
								try {
									Thread.sleep(100L);
									AppMain.this.progressBar.setValue(AppMain.pro);
									if (100 == AppMain.pro) {
										AppMain.pro = 0;
										this.flag = false;
									}
								} catch (InterruptedException var2) {
									var2.printStackTrace();
								}
							}

							AppMain.this.progressBar.setString("Service完成");
						}
					}).start();
					(new Thread() {
						public void run() {
							Generate.genService(AppMain.this.progressBar, AppMain.this.table, AppMain.this.packsName.getText(),
									"", AppMain.this.filePath.getText());
						}
					}).start();
				}
			}
		});
	}

	private Object jdbcConnect() {
		if ("".equals(this.textDataUrl.getText())) {
			JOptionPane.showMessageDialog((Component)null, "请输入数据库url", "提示", 0);
			return null;
		} else if ("".equals(this.TextuserName.getText())) {
			JOptionPane.showMessageDialog((Component)null, "请输入数据库登录名", "提示", 0);
			return null;
		} else if ("".equals(this.textPassword.getText())) {
			JOptionPane.showMessageDialog((Component)null, "请输入数据库密码", "提示", 0);
			return null;
		} else {
			try {
				FileOutputStream out = new FileOutputStream(System.getProperty("user.home") + "\\db.properties");
				properties.setProperty("url", this.textDataUrl.getText());
				properties.setProperty("user", this.TextuserName.getText());
				properties.setProperty("pwd", this.textPassword.getText());
				properties.store(out, "update pro");
				out.close();
			} catch (Exception var2) {
				var2.printStackTrace();
			}

			(new Thread() {
				boolean flag = true;

				@Override
				public void run() {
					while(this.flag) {
						try {
							Thread.sleep(100L);
							AppMain.this.progressBar.setValue(AppMain.pro);
							if (100 == AppMain.pro) {
								AppMain.pro = 0;
								this.flag = false;
							}
						} catch (InterruptedException var2) {
							var2.printStackTrace();
						}
					}

					AppMain.this.progressBar.setString("数据库连接完成");
				}
			}).start();
			(new Thread() {
				public void run() {
					try {
						String driver = "com.mysql.jdbc.Driver";
						Class.forName(driver);
						Connection conn = DriverManager.getConnection(AppMain.this.textDataUrl.getText(), AppMain.this.TextuserName.getText(), AppMain.this.textPassword.getText());
						if (!conn.isClosed()) {
							System.out.println("Succeeded connecting to the Database!");
						} else {
							System.err.println("connect filed");
							JOptionPane.showMessageDialog((Component)null, "连接失败", "提示", 0);
						}

						AppMain.this.getTables(conn);
					} catch (Exception var3) {
						var3.printStackTrace();
						JOptionPane.showMessageDialog((Component)null, "连接失败", "提示", 0);
					}

				}
			}).start();
			return null;
		}
	}


	private void getTables(Connection conn) throws Exception {
		this.model.getDataVector().clear();
		this.model.fireTableDataChanged();
		table.updateUI();

		DatabaseMetaData dbMetData = conn.getMetaData();
		ResultSet rs = dbMetData.getTables(null, null, null, new String[]{"TABLE"});

		// 移动到最后
		rs.last();
		// 获得结果集长度
		int rowSize = rs.getRow();
		if(rowSize < 1){
			JOptionPane.showMessageDialog(null, "连接成功，但是没有表信息", "提示", JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		// 返回第一个（记住不是rs.frist()）,不写的话下面的循环里面没值
		rs.beforeFirst();
		int pro = rowSize<90?90/rowSize:1;
		int n = 0;

		while (rs.next()) {
			++n;
			if (rs.getString(4) != null && (rs.getString(4).equalsIgnoreCase("TABLE") || rs.getString(4).equalsIgnoreCase("VIEW"))) {
				String tableName = rs.getString("TABLE_NAME");
				System.out.print(tableName + "\t");
				String pkName = "";

				for(ResultSet pkRSet = dbMetData.getPrimaryKeys((String)null, (String)null, tableName); pkRSet.next(); pkName = (String)pkRSet.getObject(4)) {
				}

				ResultSet colRet = dbMetData.getColumns((String)null, "%", tableName, "%");

				while(colRet.next()) {
					String columnName = colRet.getString("COLUMN_NAME");
					String columnType = colRet.getString("TYPE_NAME");
					int datasize = colRet.getInt("COLUMN_SIZE");
					int digits = colRet.getInt("DECIMAL_DIGITS");
					int nullable = colRet.getInt("NULLABLE");
					if (columnName.equals(pkName)) {
						System.out.println("主键类型" + columnType);
						this.model.addRow(new String[]{tableName, columnType+"", "选择"});
					}
				}
			}
			AppMain.pro = Math.min(pro * n, 99);
		}
		AppMain.pro = 100;
	}
}
