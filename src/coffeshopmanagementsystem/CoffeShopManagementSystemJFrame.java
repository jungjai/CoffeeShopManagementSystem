package coffeshopmanagementsystem;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.ScrollPaneConstants;
import javax.swing.JTextField;


public class CoffeShopManagementSystemJFrame extends JFrame {

	private String Datestring; // ��¥
	private boolean checksetting = false; // ���� �Ϸ� �Ǹ� true��
	private MemberControl mc; // MemberControl Class
	private OrderMenuControl omc;// OrderMenuControl Class
	public static String[][] contents = new String[300][3];
	public static int total;
	// �ֹ� �ӽ� ���忡 �����
	class Temporder {
		public String orderMenu;
		public String orderPrice;
	}

	private ArrayList<Temporder> tempord; // �ֹ� �ӽ� ����
	
	public static void main(String[] args) {
		getFile file = new getFile();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CoffeShopManagementSystemJFrame frame = new CoffeShopManagementSystemJFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public CoffeShopManagementSystemJFrame() {
		initComponents();// �ݺ��� ����- ������Ʈ�� �ʱ�ȭ

		initvalue();// JComboBox,JTextField �� �ʱ�ȭ
		checksetting = true;// ���� ����
		setResizable(false);// â ũ�� ���ٲٰ�
		mc = new MemberControl();
		omc = new OrderMenuControl();
		tempord = new ArrayList<Temporder>();

		ArrayList<String> tmplist = omc.getMenuList();
		int size = tmplist.size();
		for (int i = 0; i < size; i++) {
			om_jComboBox_memu.addItem(tmplist.get(i));
		}
		printorder(false);// �ֹ����� welcome ����ֱ�

		om_jTextField_membership_number.setText("0000");

	}
	

	// JComboBox,JTextField �� �ʱ�ȭ
	private void initvalue() {
		// ��¥ ǥ��
		SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat(
				"yyyy�� MM�� dd��");
		Date currentTime = new Date();
		Datestring = String.format(("%s"),
				mSimpleDateFormat.format(currentTime));
		om_jTextField_show_date.setText(Datestring);

		// �޺��ڽ��� ���� ����
		for (int i = 1900; i < 2100; i++) {
			String tmp = String.format(("%d"), i);
			sm_jComboBox_from_year.addItem(tmp);
			sm_jComboBox_to_year.addItem(tmp);
			mm_jComboBox_year.addItem(tmp);
		}
		SimpleDateFormat tmp_year = new SimpleDateFormat("yyyy");
		sm_jComboBox_from_year.setSelectedItem(tmp_year.format(currentTime));
		sm_jComboBox_to_year.setSelectedItem(tmp_year.format(currentTime));
		mm_jComboBox_year.setSelectedItem(tmp_year.format(currentTime));

		// �޺��ڽ��� �� ����
		for (int i = 1; i < 13; i++) {
			String tmp = String.format(("%02d"), i);
			sm_jComboBox_from_month.addItem(tmp);
			sm_jComboBox_to_month.addItem(tmp);
			mm_jComboBox_month.addItem(tmp);
		}
		SimpleDateFormat tmp_month = new SimpleDateFormat("MM");
		sm_jComboBox_from_month.setSelectedItem(tmp_month.format(currentTime));
		sm_jComboBox_to_month.setSelectedItem(tmp_month.format(currentTime));
		mm_jComboBox_month.setSelectedItem(tmp_month.format(currentTime));

		// �޺��ڽ��� �� ����
		for (int i = 1; i <= calc_number_of_month(sm_jComboBox_from_year,
				sm_jComboBox_from_month); i++) {
			String tmp = String.format(("%02d"), i);
			sm_jComboBox_from_day.addItem(tmp);
			sm_jComboBox_to_day.addItem(tmp);
			mm_jComboBox_day.addItem(tmp);
		}
		SimpleDateFormat tmp_day = new SimpleDateFormat("dd");
		sm_jComboBox_from_day.setSelectedItem(tmp_day.format(currentTime));
		sm_jComboBox_to_day.setSelectedItem(tmp_day.format(currentTime));
		mm_jComboBox_day.setSelectedItem(tmp_day.format(currentTime));

	}

	// ���� ���� ���ڷ� �޾Ƽ� �ش� ������ ��¥�� ���
	private int calc_number_of_month(javax.swing.JComboBox<String> year,
			javax.swing.JComboBox<String> month) {
		int day = 0;
		String tmp_year = (String) year.getSelectedItem();
		int tmpi_year = Integer.parseInt(tmp_year);
		String tmp_month = (String) month.getSelectedItem();
		int tmpi_month = Integer.parseInt(tmp_month);

		// 2��
		if (tmpi_month == 2) {
			// ����
			if ((tmpi_year % 400) == 0 || (tmpi_year % 4) == 0
					&& (tmpi_year % 100) != 0) {
				day = 29;
			} else {
				day = 28;
			}
		}
		// 7������
		else if (tmpi_month < 8) {
			if (tmpi_month % 2 == 1) {
				day = 31;
			} else {
				day = 30;
			}
		}
		// 8�� ����
		else {
			if (tmpi_month % 2 == 0) {
				day = 31;
			} else {
				day = 30;
			}
		}
		return day;
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
	// <editor-fold defaultstate="collapsed"
	// desc="Generated Code">//GEN-BEGIN:initComponents
	private void initComponents() {

		coffe_shop_management_jTabbedPane = new javax.swing.JTabbedPane();
		order_management_jPanel = new javax.swing.JPanel();
		om_jTextField_show_date = new javax.swing.JTextField();
		om_jLabel1 = new javax.swing.JLabel();
		om_jTextField_membership_number = new javax.swing.JTextField();
		om_jLabel2 = new javax.swing.JLabel();
		om_jComboBox_memu = new javax.swing.JComboBox<String>();
		om_jScrollPane2 = new javax.swing.JScrollPane();
		om_jTextArea_order_list = new javax.swing.JTextArea();
		om_jButton_add_order = new javax.swing.JButton();
		om_jButton_cancel_order = new javax.swing.JButton();
		om_jButton_complete_order = new javax.swing.JButton();
		shop_management_jPanel = new javax.swing.JPanel();
		sm_jLabel1 = new javax.swing.JLabel();
		sm_jLabel2 = new javax.swing.JLabel();
		sm_jTextField_menu_name = new javax.swing.JTextField();
		sm_jTextField_menu_price = new javax.swing.JTextField();
		sm_jButton_new_menu = new javax.swing.JButton();
		sm_jButton_menu_delete = new javax.swing.JButton();
		sm_jButton_menu_search = new javax.swing.JButton();
		sm_jButton_menu_regist = new javax.swing.JButton();
		sm_jComboBox_from_year = new javax.swing.JComboBox<String>();
		sm_jComboBox_from_month = new javax.swing.JComboBox<String>();
		sm_jComboBox_from_day = new javax.swing.JComboBox<String>();
		sm_jLabel3 = new javax.swing.JLabel();
		sm_jLabel4 = new javax.swing.JLabel();
		sm_jLabel5 = new javax.swing.JLabel();
		sm_jComboBox_to_year = new javax.swing.JComboBox<String>();
		sm_jComboBox_to_month = new javax.swing.JComboBox<String>();
		sm_jComboBox_to_day = new javax.swing.JComboBox<String>();
		sm_jLabel6 = new javax.swing.JLabel();
		sm_jLabel7 = new javax.swing.JLabel();
		sm_jLabel8 = new javax.swing.JLabel();
		sm_jButton_sales_information = new javax.swing.JButton();
		sm_jScrollPane1 = new javax.swing.JScrollPane();
		sm_jTextArea_sales_information = new javax.swing.JTextArea();
		member_management_jPanel = new javax.swing.JPanel();
		mm_jButton_new_member = new javax.swing.JButton();
		mm_jButton_search_member = new javax.swing.JButton();
		mm_jButton_delete_member = new javax.swing.JButton();
		mm_jButton_regist_member = new javax.swing.JButton();
		mm_jLabel1 = new javax.swing.JLabel();
		mm_jComboBox_year = new javax.swing.JComboBox<String>();
		mm_jComboBox_month = new javax.swing.JComboBox<String>();
		mm_jComboBox_day = new javax.swing.JComboBox<String>();
		mm_jLabel2 = new javax.swing.JLabel();
		mm_jLabel3 = new javax.swing.JLabel();
		mm_jLabel4 = new javax.swing.JLabel();
		mm_jLabel5 = new javax.swing.JLabel();
		mm_jLabel6 = new javax.swing.JLabel();
		mm_jTextField_membership_number = new javax.swing.JTextField();
		mm_jTextField_member_phone_number = new javax.swing.JTextField();
		mm_jLabel7 = new javax.swing.JLabel();
		mm_jTextField_member_name = new javax.swing.JTextField();
		mm_jLabel8 = new javax.swing.JLabel();
		mm_jTextField_member_birth = new javax.swing.JTextField();
		reserve_management_jPanel = new javax.swing.JPanel();
		
		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

		coffe_shop_management_jTabbedPane
				.addMouseListener(new java.awt.event.MouseAdapter() {
					public void mouseClicked(java.awt.event.MouseEvent evt) {
						coffe_shop_management_jTabbedPaneMouseClicked(evt);
					}
				});

		om_jTextField_show_date.setEditable(false);

		om_jLabel1.setText("������ȣ :");

		om_jLabel2.setText("�� �� ��  :");

		om_jTextArea_order_list.setEditable(false);
		om_jTextArea_order_list.setColumns(20);
		om_jTextArea_order_list.setRows(5);
		om_jScrollPane2.setViewportView(om_jTextArea_order_list);

		om_jButton_add_order.setText("�ֹ��߰�");
		om_jButton_add_order
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						om_jButton_add_orderActionPerformed(evt);
					}
				});

		om_jButton_cancel_order.setText("�ֹ����");
		om_jButton_cancel_order
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						om_jButton_cancel_orderActionPerformed(evt);
					}
				});

		om_jButton_complete_order.setText("�ֹ��Ϸ�");
		om_jButton_complete_order
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						om_jButton_complete_orderActionPerformed(evt);
					}
				});

		javax.swing.GroupLayout order_management_jPanelLayout = new javax.swing.GroupLayout(
				order_management_jPanel);
		order_management_jPanel.setLayout(order_management_jPanelLayout);
		order_management_jPanelLayout
				.setHorizontalGroup(order_management_jPanelLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								order_management_jPanelLayout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												order_management_jPanelLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addComponent(
																om_jTextField_show_date,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																250,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addGroup(
																order_management_jPanelLayout
																		.createSequentialGroup()
																		.addComponent(
																				om_jButton_add_order)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				om_jButton_cancel_order)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				om_jButton_complete_order))
														.addGroup(
																order_management_jPanelLayout
																		.createParallelGroup(
																				javax.swing.GroupLayout.Alignment.TRAILING,
																				false)
																		.addGroup(
																				order_management_jPanelLayout
																						.createSequentialGroup()
																						.addComponent(
																								om_jLabel2,
																								javax.swing.GroupLayout.PREFERRED_SIZE,
																								56,
																								javax.swing.GroupLayout.PREFERRED_SIZE)
																						.addPreferredGap(
																								javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																						.addComponent(
																								om_jComboBox_memu,
																								javax.swing.GroupLayout.PREFERRED_SIZE,
																								189,
																								javax.swing.GroupLayout.PREFERRED_SIZE))
																		.addGroup(
																				order_management_jPanelLayout
																						.createSequentialGroup()
																						.addComponent(
																								om_jLabel1)
																						.addPreferredGap(
																								javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																						.addComponent(
																								om_jTextField_membership_number,
																								javax.swing.GroupLayout.PREFERRED_SIZE,
																								189,
																								javax.swing.GroupLayout.PREFERRED_SIZE))))
										.addGap(12, 12, 12)
										.addComponent(
												om_jScrollPane2,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												293, Short.MAX_VALUE)
										.addContainerGap()));
		order_management_jPanelLayout
				.setVerticalGroup(order_management_jPanelLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								order_management_jPanelLayout
										.createSequentialGroup()
										.addGap(28, 28, 28)
										.addGroup(
												order_management_jPanelLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addComponent(
																om_jScrollPane2)
														.addGroup(
																order_management_jPanelLayout
																		.createSequentialGroup()
																		.addComponent(
																				om_jTextField_show_date,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				30,
																				javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addGap(18,
																				18,
																				18)
																		.addGroup(
																				order_management_jPanelLayout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.BASELINE)
																						.addComponent(
																								om_jLabel1,
																								javax.swing.GroupLayout.PREFERRED_SIZE,
																								25,
																								javax.swing.GroupLayout.PREFERRED_SIZE)
																						.addComponent(
																								om_jTextField_membership_number,
																								javax.swing.GroupLayout.PREFERRED_SIZE,
																								javax.swing.GroupLayout.DEFAULT_SIZE,
																								javax.swing.GroupLayout.PREFERRED_SIZE))
																		.addGap(18,
																				18,
																				18)
																		.addGroup(
																				order_management_jPanelLayout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.BASELINE)
																						.addComponent(
																								om_jLabel2)
																						.addComponent(
																								om_jComboBox_memu,
																								javax.swing.GroupLayout.PREFERRED_SIZE,
																								javax.swing.GroupLayout.DEFAULT_SIZE,
																								javax.swing.GroupLayout.PREFERRED_SIZE))
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED,
																				182,
																				Short.MAX_VALUE)
																		.addGroup(
																				order_management_jPanelLayout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.BASELINE)
																						.addComponent(
																								om_jButton_add_order)
																						.addComponent(
																								om_jButton_cancel_order)
																						.addComponent(
																								om_jButton_complete_order))))
										.addContainerGap()));

		coffe_shop_management_jTabbedPane.addTab("�ֹ�����",
				order_management_jPanel);

		sm_jLabel1.setText("�޴��� :");

		sm_jLabel2.setText("��   �� :");

		sm_jButton_new_menu.setText("���Ŵ�");
		sm_jButton_new_menu
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						sm_jButton_new_menuActionPerformed(evt);
					}
				});

		sm_jButton_menu_delete.setText("�޴�����");
		sm_jButton_menu_delete
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						sm_jButton_menu_deleteActionPerformed(evt);
					}
				});

		sm_jButton_menu_search.setText("�޴��˻�");
		sm_jButton_menu_search
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						sm_jButton_menu_searchActionPerformed(evt);
					}
				});

		sm_jButton_menu_regist.setText("�޴����");
		sm_jButton_menu_regist
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						sm_jButton_menu_registActionPerformed(evt);
					}
				});

		sm_jComboBox_from_year
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						sm_jComboBox_from_yearActionPerformed(evt);
					}
				});

		sm_jComboBox_from_month
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						sm_jComboBox_from_monthActionPerformed(evt);
					}
				});

		sm_jLabel3.setText("��");

		sm_jLabel4.setText("��");

		sm_jLabel5.setText("�� ����");

		sm_jComboBox_to_year
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						sm_jComboBox_to_yearActionPerformed(evt);
					}
				});

		sm_jComboBox_to_month
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						sm_jComboBox_to_monthActionPerformed(evt);
					}
				});

		sm_jLabel6.setText("��");

		sm_jLabel7.setText("��");

		sm_jLabel8.setText("�� ����");

		sm_jButton_sales_information.setText("��������");
		sm_jButton_sales_information
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						sm_jButton_sales_informationActionPerformed(evt);
					}
				});

		sm_jTextArea_sales_information.setEditable(false);
		sm_jTextArea_sales_information.setColumns(20);
		sm_jTextArea_sales_information.setRows(5);
		sm_jScrollPane1.setViewportView(sm_jTextArea_sales_information);

		javax.swing.GroupLayout shop_management_jPanelLayout = new javax.swing.GroupLayout(
				shop_management_jPanel);
		shop_management_jPanel.setLayout(shop_management_jPanelLayout);
		shop_management_jPanelLayout
				.setHorizontalGroup(shop_management_jPanelLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								shop_management_jPanelLayout
										.createSequentialGroup()
										.addGap(27, 27, 27)
										.addGroup(
												shop_management_jPanelLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addGroup(
																shop_management_jPanelLayout
																		.createSequentialGroup()
																		.addComponent(
																				sm_jLabel2)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
																		.addComponent(
																				sm_jTextField_menu_price,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				120,
																				javax.swing.GroupLayout.PREFERRED_SIZE))
														.addGroup(
																shop_management_jPanelLayout
																		.createSequentialGroup()
																		.addComponent(
																				sm_jLabel1)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
																		.addComponent(
																				sm_jTextField_menu_name,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				120,
																				javax.swing.GroupLayout.PREFERRED_SIZE))
														.addGroup(
																shop_management_jPanelLayout
																		.createSequentialGroup()
																		.addGroup(
																				shop_management_jPanelLayout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.TRAILING,
																								false)
																						.addComponent(
																								sm_jButton_new_menu,
																								javax.swing.GroupLayout.DEFAULT_SIZE,
																								javax.swing.GroupLayout.DEFAULT_SIZE,
																								Short.MAX_VALUE)
																						.addComponent(
																								sm_jButton_menu_search))
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
																		.addGroup(
																				shop_management_jPanelLayout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.LEADING)
																						.addComponent(
																								sm_jButton_menu_delete)
																						.addComponent(
																								sm_jButton_menu_regist))))
										.addGap(82, 82, 82)
										.addGroup(
												shop_management_jPanelLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addGroup(
																shop_management_jPanelLayout
																		.createSequentialGroup()
																		.addGroup(
																				shop_management_jPanelLayout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.LEADING)
																						.addGroup(
																								shop_management_jPanelLayout
																										.createSequentialGroup()
																										.addComponent(
																												sm_jComboBox_from_year,
																												javax.swing.GroupLayout.PREFERRED_SIZE,
																												javax.swing.GroupLayout.DEFAULT_SIZE,
																												javax.swing.GroupLayout.PREFERRED_SIZE)
																										.addPreferredGap(
																												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																										.addComponent(
																												sm_jLabel3)
																										.addGap(0,
																												0,
																												Short.MAX_VALUE))
																						.addGroup(
																								shop_management_jPanelLayout
																										.createSequentialGroup()
																										.addComponent(
																												sm_jComboBox_to_year,
																												javax.swing.GroupLayout.PREFERRED_SIZE,
																												javax.swing.GroupLayout.DEFAULT_SIZE,
																												javax.swing.GroupLayout.PREFERRED_SIZE)
																										.addPreferredGap(
																												javax.swing.LayoutStyle.ComponentPlacement.RELATED,
																												javax.swing.GroupLayout.DEFAULT_SIZE,
																												Short.MAX_VALUE)
																										.addComponent(
																												sm_jLabel6)))
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				Short.MAX_VALUE)
																		.addGroup(
																				shop_management_jPanelLayout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.LEADING)
																						.addGroup(
																								shop_management_jPanelLayout
																										.createSequentialGroup()
																										.addComponent(
																												sm_jComboBox_from_month,
																												javax.swing.GroupLayout.PREFERRED_SIZE,
																												javax.swing.GroupLayout.DEFAULT_SIZE,
																												javax.swing.GroupLayout.PREFERRED_SIZE)
																										.addPreferredGap(
																												javax.swing.LayoutStyle.ComponentPlacement.RELATED,
																												javax.swing.GroupLayout.DEFAULT_SIZE,
																												Short.MAX_VALUE)
																										.addComponent(
																												sm_jLabel4))
																						.addGroup(
																								shop_management_jPanelLayout
																										.createSequentialGroup()
																										.addComponent(
																												sm_jComboBox_to_month,
																												javax.swing.GroupLayout.PREFERRED_SIZE,
																												javax.swing.GroupLayout.DEFAULT_SIZE,
																												javax.swing.GroupLayout.PREFERRED_SIZE)
																										.addPreferredGap(
																												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																										.addComponent(
																												sm_jLabel7)))
																		.addGroup(
																				shop_management_jPanelLayout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.LEADING)
																						.addGroup(
																								shop_management_jPanelLayout
																										.createSequentialGroup()
																										.addGap(4,
																												4,
																												4)
																										.addComponent(
																												sm_jComboBox_from_day,
																												javax.swing.GroupLayout.PREFERRED_SIZE,
																												javax.swing.GroupLayout.DEFAULT_SIZE,
																												javax.swing.GroupLayout.PREFERRED_SIZE))
																						.addGroup(
																								shop_management_jPanelLayout
																										.createSequentialGroup()
																										.addPreferredGap(
																												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																										.addComponent(
																												sm_jComboBox_to_day,
																												javax.swing.GroupLayout.PREFERRED_SIZE,
																												javax.swing.GroupLayout.DEFAULT_SIZE,
																												javax.swing.GroupLayout.PREFERRED_SIZE)))
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addGroup(
																				shop_management_jPanelLayout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.LEADING)
																						.addComponent(
																								sm_jLabel8)
																						.addComponent(
																								sm_jLabel5))
																		.addGap(183,
																				183,
																				183))
														.addGroup(
																shop_management_jPanelLayout
																		.createSequentialGroup()
																		.addGroup(
																				shop_management_jPanelLayout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.LEADING)
																						.addComponent(
																								sm_jButton_sales_information)
																						.addComponent(
																								sm_jScrollPane1,
																								javax.swing.GroupLayout.PREFERRED_SIZE,
																								288,
																								javax.swing.GroupLayout.PREFERRED_SIZE))
																		.addContainerGap(
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				Short.MAX_VALUE)))));
		shop_management_jPanelLayout
				.setVerticalGroup(shop_management_jPanelLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								shop_management_jPanelLayout
										.createSequentialGroup()
										.addGap(15, 15, 15)
										.addComponent(
												sm_jButton_sales_information)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(
												shop_management_jPanelLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(
																sm_jLabel1)
														.addComponent(
																sm_jTextField_menu_name,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(
																sm_jComboBox_from_year,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(
																sm_jComboBox_from_month,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(
																sm_jComboBox_from_day,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(
																sm_jLabel3)
														.addComponent(
																sm_jLabel4)
														.addComponent(
																sm_jLabel5))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
										.addGroup(
												shop_management_jPanelLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(
																sm_jLabel2)
														.addComponent(
																sm_jTextField_menu_price,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(
																sm_jComboBox_to_year,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(
																sm_jComboBox_to_month,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(
																sm_jComboBox_to_day,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(
																sm_jLabel6)
														.addComponent(
																sm_jLabel7)
														.addComponent(
																sm_jLabel8))
										.addGap(45, 45, 45)
										.addGroup(
												shop_management_jPanelLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addGroup(
																shop_management_jPanelLayout
																		.createSequentialGroup()
																		.addGroup(
																				shop_management_jPanelLayout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.BASELINE)
																						.addComponent(
																								sm_jButton_new_menu)
																						.addComponent(
																								sm_jButton_menu_delete))
																		.addGap(18,
																				18,
																				18)
																		.addGroup(
																				shop_management_jPanelLayout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.BASELINE)
																						.addComponent(
																								sm_jButton_menu_search)
																						.addComponent(
																								sm_jButton_menu_regist))
																		.addGap(0,
																				140,
																				Short.MAX_VALUE))
														.addComponent(
																sm_jScrollPane1))
										.addContainerGap()));

		coffe_shop_management_jTabbedPane
				.addTab("�������", shop_management_jPanel);

		mm_jButton_new_member.setText("������");
		mm_jButton_new_member
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						mm_jButton_new_memberActionPerformed(evt);
					}
				});

		mm_jButton_search_member.setText("���������˻�");
		mm_jButton_search_member
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						mm_jButton_search_memberActionPerformed(evt);
					}
				});

		mm_jButton_delete_member.setText("������������");
		mm_jButton_delete_member
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						mm_jButton_delete_memberActionPerformed(evt);
					}
				});

		mm_jButton_regist_member.setText("�������");
		mm_jButton_regist_member
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						mm_jButton_regist_memberActionPerformed(evt);
					}
				});

		mm_jLabel1.setText("�� �� �� :");

		mm_jComboBox_year
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						mm_jComboBox_yearActionPerformed(evt);
					}
				});

		mm_jComboBox_month
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						mm_jComboBox_monthActionPerformed(evt);
					}
				});

		mm_jLabel2.setText("��");

		mm_jLabel3.setText("��");

		mm_jLabel4.setText("��");

		mm_jLabel5.setText("������ȣ :");

		mm_jLabel6.setText("��ȭ��ȣ :");

		mm_jLabel7.setText("�� �� �� :");

		mm_jLabel8.setText("��    �� :");

		javax.swing.GroupLayout member_management_jPanelLayout = new javax.swing.GroupLayout(
				member_management_jPanel);
		member_management_jPanel.setLayout(member_management_jPanelLayout);
		member_management_jPanelLayout
				.setHorizontalGroup(member_management_jPanelLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								member_management_jPanelLayout
										.createSequentialGroup()
										.addGap(39, 39, 39)
										.addGroup(
												member_management_jPanelLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addGroup(
																member_management_jPanelLayout
																		.createSequentialGroup()
																		.addComponent(
																				mm_jLabel6)
																		.addGap(18,
																				18,
																				18)
																		.addComponent(
																				mm_jTextField_member_phone_number,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				100,
																				javax.swing.GroupLayout.PREFERRED_SIZE))
														.addGroup(
																member_management_jPanelLayout
																		.createSequentialGroup()
																		.addComponent(
																				mm_jLabel1)
																		.addGap(18,
																				18,
																				18)
																		.addComponent(
																				mm_jComboBox_year,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				mm_jLabel2)
																		.addGap(6,
																				6,
																				6)
																		.addComponent(
																				mm_jComboBox_month,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addGap(2,
																				2,
																				2)
																		.addComponent(
																				mm_jLabel3)
																		.addGap(4,
																				4,
																				4)
																		.addComponent(
																				mm_jComboBox_day,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				mm_jLabel4))
														.addGroup(
																member_management_jPanelLayout
																		.createSequentialGroup()
																		.addComponent(
																				mm_jLabel5)
																		.addGap(18,
																				18,
																				18)
																		.addComponent(
																				mm_jTextField_membership_number,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				100,
																				javax.swing.GroupLayout.PREFERRED_SIZE))
														.addGroup(
																member_management_jPanelLayout
																		.createSequentialGroup()
																		.addComponent(
																				mm_jButton_new_member,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				120,
																				javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
																		.addComponent(
																				mm_jButton_search_member,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				120,
																				javax.swing.GroupLayout.PREFERRED_SIZE)))
										.addGroup(
												member_management_jPanelLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addGroup(
																member_management_jPanelLayout
																		.createSequentialGroup()
																		.addGap(51,
																				51,
																				51)
																		.addGroup(
																				member_management_jPanelLayout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.LEADING)
																						.addComponent(
																								mm_jLabel7)
																						.addComponent(
																								mm_jLabel8))
																		.addGap(18,
																				18,
																				18)
																		.addGroup(
																				member_management_jPanelLayout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.LEADING)
																						.addComponent(
																								mm_jTextField_member_birth,
																								javax.swing.GroupLayout.PREFERRED_SIZE,
																								100,
																								javax.swing.GroupLayout.PREFERRED_SIZE)
																						.addComponent(
																								mm_jTextField_member_name,
																								javax.swing.GroupLayout.PREFERRED_SIZE,
																								100,
																								javax.swing.GroupLayout.PREFERRED_SIZE)))
														.addGroup(
																member_management_jPanelLayout
																		.createSequentialGroup()
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
																		.addComponent(
																				mm_jButton_delete_member,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				120,
																				javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
																		.addComponent(
																				mm_jButton_regist_member,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				120,
																				javax.swing.GroupLayout.PREFERRED_SIZE)))
										.addContainerGap(31, Short.MAX_VALUE)));
		member_management_jPanelLayout
				.setVerticalGroup(member_management_jPanelLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								member_management_jPanelLayout
										.createSequentialGroup()
										.addGap(96, 96, 96)
										.addGroup(
												member_management_jPanelLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(
																mm_jButton_new_member)
														.addComponent(
																mm_jButton_search_member)
														.addComponent(
																mm_jButton_delete_member)
														.addComponent(
																mm_jButton_regist_member,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																Short.MAX_VALUE))
										.addGap(18, 18, 18)
										.addGroup(
												member_management_jPanelLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(
																mm_jLabel1)
														.addComponent(
																mm_jComboBox_year,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(
																mm_jComboBox_month,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(
																mm_jComboBox_day,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(
																mm_jLabel2)
														.addComponent(
																mm_jLabel3)
														.addComponent(
																mm_jLabel4))
										.addGap(18, 18, 18)
										.addGroup(
												member_management_jPanelLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(
																mm_jLabel5)
														.addComponent(
																mm_jTextField_membership_number,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(
																mm_jLabel7)
														.addComponent(
																mm_jTextField_member_name,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE))
										.addGap(18, 18, 18)
										.addGroup(
												member_management_jPanelLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(
																mm_jLabel6)
														.addComponent(
																mm_jTextField_member_phone_number,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(
																mm_jLabel8)
														.addComponent(
																mm_jTextField_member_birth,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE))
										.addContainerGap(119, Short.MAX_VALUE)));

		coffe_shop_management_jTabbedPane.addTab("��������",
				member_management_jPanel);	
							
		coffe_shop_management_jTabbedPane.addTab("�������",
				reserve_management_jPanel);
		reserve_management_jPanel.setLayout(null);
		
		btnNewButton = new JButton("������ȸ");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Thread thread3 = new Thread() {
					public void run() {
						manager_search();
					}
				};
				thread3.start();	
			}
			private synchronized void manager_search() {
				try {
					String day = textField.getText();
					if (day.equals(""))
						throw new MyException5("��¥�� ����ֽ��ϴ�.");
					else if (!day.matches("^[0-9]{4}-[0-9]{2}-[0-9]{2}$"))
						throw new MyException6("��¥�� �ٽ� �Է����ּ���");
					else if (!day.matches(".*[0-9].*"))
						throw new MyException7("��¥�� ���ڰ� ���ԵǾ����ϴ�");
					else {				        
						JOptionPane.showMessageDialog(reserve_management_jPanel, "��ȸ�Ǿ����ϴ�.");
					}
					
				} catch (MyException5 e5) {
					JOptionPane.showMessageDialog(reserve_management_jPanel, e5.getMessage());
				} catch (MyException6 e6) {
					JOptionPane.showMessageDialog(reserve_management_jPanel, e6.getMessage());
				} catch (MyException7 e7) {
					JOptionPane.showMessageDialog(reserve_management_jPanel, e7.getMessage());
				}
			}
		});
		btnNewButton.setBounds(195, 291, 106, 27);
		reserve_management_jPanel.add(btnNewButton);
		
		button = new JButton("�������");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(reserve_management_jPanel, "��ҵǾ����ϴ�.");
			}
		});
		button.setBounds(313, 291, 106, 27);
		reserve_management_jPanel.add(button);
		
		button_1 = new JButton("����ȭ��");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AddFrame frame1 = new AddFrame();
				frame1.setVisible(true);
			}
		});
		button_1.setBounds(470, 291, 106, 27);
		reserve_management_jPanel.add(button_1);
		
		JTable table_1 = new JTable();
		DefaultTableModel defaultTableModel = (DefaultTableModel) table_1.getModel();
		String []content = {"�ڸ� NO.", "�����ڸ�", "����ó"};
		defaultTableModel.setColumnIdentifiers(content);
		for (int i = 1; i < total; i++)
			defaultTableModel.addRow(contents[i]);
		
		JScrollPane panel_1 = new JScrollPane(table_1);
		panel_1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		panel_1.setBounds(5, 21, 571, 260);
		reserve_management_jPanel.add(panel_1);
		
		textField = new JTextField();
		textField.setBounds(12, 294, 158, 24);
		reserve_management_jPanel.add(textField);
		textField.setColumns(10);
		
		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(
				getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addComponent(
				coffe_shop_management_jTabbedPane,
				javax.swing.GroupLayout.PREFERRED_SIZE, 591, Short.MAX_VALUE));
		layout.setVerticalGroup(layout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addComponent(
				coffe_shop_management_jTabbedPane));

		pack();
	}// </editor-fold>//GEN-END:initComponents

	// ȭ�鿡 ������ �޴��� ���� ǥ������
	// iscomplete�� true���� ���ݰ� �հ� ����
	private void printorder(boolean iscomplte) {
		String print;
		int sizelist = tempord.size();
		int sum = 0;
		print = String.format("++++++++++Welcome++++++++++\r\n\r\n");
		for (int i = 0; i < sizelist; i++) {
			String tmpprice = tempord.get(i).orderPrice;
			// �޸���������
			String tmpprice_comma = NumberFormat.getNumberInstance(Locale.US)
					.format(Integer.parseInt(tmpprice));
			// ����
			sum += Integer.parseInt(tmpprice);
			print = String.format("%s%-10s%-5s\r\n", print,
					tempord.get(i).orderMenu, tmpprice_comma);
		}

		// �Ϸ� ��
		if (iscomplte) {
			int sub_total = (int) (sum / 1.1);
			int tax = sum - sub_total;
			String Str_sum = NumberFormat.getNumberInstance(Locale.US).format(
					sum);
			String Str_sub_total = NumberFormat.getNumberInstance(Locale.US)
					.format(sub_total);
			String Str_tax = NumberFormat.getNumberInstance(Locale.US).format(
					tax);

			print = String.format("%s\r\n", print);
			print = String.format("%s%-10s%-5s\r\n", print, "SUB TOTAL",
					Str_sub_total);
			print = String.format("%s%-10s%-5s\r\n", print, "TAX", Str_tax);
			print = String.format("%s%-10s%-5s\r\n", print, "TOTAL", Str_sum);
			print = String.format("%s\r\nThank you - Have a nice day", print,
					"TOTAL", Str_sum);
		}

		// ���
		om_jTextArea_order_list.setText(print);
	}

	// �ֹ� �߰�
	private void om_jButton_add_orderActionPerformed(
			java.awt.event.ActionEvent evt) {// GEN-FIRST:event_om_jButton_add_orderActionPerformed
		// TODO add your handling code here:

		// �ֹ� �� �ֹ� �߰� ����
		if (om_jTextField_membership_number.isEditable()) {
			String tmpmenuname = (String) om_jComboBox_memu.getSelectedItem();
			String tmpprice = null;
			try {
				tmpprice = omc.getPrice(tmpmenuname);
				Temporder t = new Temporder();
				t.orderMenu = tmpmenuname;
				t.orderPrice = tmpprice;
				tempord.add(t);
				printorder(false);
			} catch (MenuRegisterException e) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(this, e.getMessage(), "",
						JOptionPane.PLAIN_MESSAGE);
			}
		} else {
			JOptionPane.showMessageDialog(this, "�ֹ� �� �߰����� ���մϴ�.", "",
					JOptionPane.PLAIN_MESSAGE);
		}

	}// GEN-LAST:event_om_jButton_add_orderActionPerformed

	// �ֹ� ���
	private void om_jButton_cancel_orderActionPerformed(
			java.awt.event.ActionEvent evt) {// GEN-FIRST:event_om_jButton_cancel_orderActionPerformed
		// TODO add your handling code here:
		// �ֹ� �� �ֹ� ��� ����
		if (om_jTextField_membership_number.isEditable()) {
			if (tempord.size() == 0) {
				JOptionPane.showMessageDialog(this, "����� �ֹ��� �����ϴ�.", "",
						JOptionPane.PLAIN_MESSAGE);
			} else {
				tempord.remove(tempord.size() - 1);
				printorder(false);
			}
		} else {
			JOptionPane.showMessageDialog(this, "�ֹ� �� ������� ���մϴ�.", "",
					JOptionPane.PLAIN_MESSAGE);
		}

	}// GEN-LAST:event_om_jButton_cancel_orderActionPerformed

	// �ֹ� �Ϸ�
	private void om_jButton_complete_orderActionPerformed(
			java.awt.event.ActionEvent evt) {// GEN-FIRST:event_om_jButton_complete_orderActionPerformed
		// TODO add your handling code here:

		if (tempord.size() == 0) {
			JOptionPane.showMessageDialog(this, "�ֹ��� �����ϴ�.", "",
					JOptionPane.PLAIN_MESSAGE);
		} else {
			final String mnumber = om_jTextField_membership_number.getText()
					.trim();

			// ���� ��ȣ ���� �˻�
			Pattern pa;
			pa = Pattern.compile("^[0-9]{1,4}$");
			Matcher ma = pa.matcher(mnumber);
			if (!ma.matches()) {
				JOptionPane.showMessageDialog(this, "�߸��� ���� ��ȣ�Դϴ�.", "",
						JOptionPane.PLAIN_MESSAGE);
				return;
			}

			// ��¥
			SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat(
					"yyyy/MM/dd");
			Date currentTime = new Date();
			final String curdate = String.format(("%s"),
					mSimpleDateFormat.format(currentTime));
			final CoffeShopManagementSystemJFrame comp = this;

			// �ֹ��� ������ȣ �� �ٲٰ� ��
			om_jTextField_membership_number.setEditable(false);

			Thread thread = new Thread(new Runnable() {
				public void run() {
					try {
						Thread.sleep(3000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						// e.printStackTrace();
					}

					int mid = Integer.parseInt(mnumber);
					// ��ȸ�� �ֹ�
					if (mid == 0) {
						int num = tempord.size();
						for (int i = 0; i < num; i++) {
							omc.registerOrder(curdate,
									tempord.get(i).orderMenu,
									tempord.get(i).orderPrice);
						}

						JOptionPane.showMessageDialog(comp, "��ȸ���� �ֹ��Ǿ����ϴ�.",
								"", JOptionPane.PLAIN_MESSAGE);
						// �ֹ����� ���
						printorder(true);
						// �ʱ�ȭ
						tempord = new ArrayList<Temporder>();
						// ������ȣ�� �ɸ� �� Ǯ��
						om_jTextField_membership_number.setEditable(true);
					}
					// ȸ�� �ֹ�
					else {

						try {
							// ȸ�� ��ȣ Ȯ��
							mc.getMemberName(mnumber);

							int statuscupoon = mc.member_order(mnumber);
							// ȸ����ȣ �ѹ��� Ȯ��
							if (statuscupoon == 0) {
								JOptionPane.showMessageDialog(comp,
										"���� ���� ��ȣ �Դϴ�.", "",
										JOptionPane.PLAIN_MESSAGE);
								om_jTextField_membership_number
										.setEditable(true);
								return;
							}

							int num = tempord.size();
							for (int i = 0; i < num; i++)
								omc.registerOrder(curdate,
										tempord.get(i).orderMenu,
										tempord.get(i).orderPrice);

							if (statuscupoon == 2) {
								JOptionPane.showMessageDialog(comp, "�ֹ��Ϸ�\r\n"
										+ mnumber + "������\r\n���������� �߱޵Ǿ����ϴ�.",
										"", JOptionPane.PLAIN_MESSAGE);
								omc.registerOrder(curdate, "����", "0");
							} else {
								JOptionPane.showMessageDialog(comp, mnumber
										+ "������\r\n�ֹ��Ǿ����ϴ�", "",
										JOptionPane.PLAIN_MESSAGE);
							}
							// �ֹ����� ���
							printorder(true);
							// �ʱ�ȭ
							tempord = new ArrayList<Temporder>();
							// ������ȣ�� �ɸ� �� Ǯ��
							om_jTextField_membership_number.setEditable(true);

						} catch (MemberRegisterException e) {
							// TODO Auto-generated catch block
							JOptionPane.showMessageDialog(comp, e.getMessage(),
									"", JOptionPane.PLAIN_MESSAGE);
							om_jTextField_membership_number.setEditable(true);
							return;
						}

					}

				}
			});
			thread.start();

		}

		// ID ��ȣ �޾� �ֹ�ȸ�� ����, ���� ID 0, �ֹ� Ƚ�� ���� 1, ���� ������ 2
		// public int member_order(String ID) {

	}// GEN-LAST:event_om_jButton_complete_orderActionPerformed

	// ���� ��Ȳ
	private void sm_jButton_sales_informationActionPerformed(
			java.awt.event.ActionEvent evt) {// GEN-FIRST:event_sm_jButton_sales_informationActionPerformed
		// TODO add your handling code here:

		String temp1 = (String) sm_jComboBox_from_year.getSelectedItem();
		String temp2 = (String) sm_jComboBox_from_month.getSelectedItem();
		String temp3 = (String) sm_jComboBox_from_day.getSelectedItem();
		final String datefromtemp = String.format("%s/%s/%s", temp1, temp2,
				temp3);
		temp1 = (String) sm_jComboBox_to_year.getSelectedItem();
		temp2 = (String) sm_jComboBox_to_month.getSelectedItem();
		temp3 = (String) sm_jComboBox_to_day.getSelectedItem();
		final String datetotemp = String
				.format("%s/%s/%s", temp1, temp2, temp3);

		Thread thread = new Thread(new Runnable() {
			public void run() {
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					// e.printStackTrace();
				}
				// ���� ���� ������ ���ڷ� �Ⱓ�� ������.
				String print = omc
						.getSalesInformation(datefromtemp, datetotemp);
				sm_jTextArea_sales_information.setText(print);
			}
		});
		thread.start();

	}// GEN-LAST:event_sm_jButton_sales_informationActionPerformed

	// ���Ŵ�
	private void sm_jButton_new_menuActionPerformed(
			java.awt.event.ActionEvent evt) {// GEN-FIRST:event_sm_jButton_new_menuActionPerformed
		// TODO add your handling code here:

		sm_jTextField_menu_name.setText("");
		sm_jTextField_menu_price.setText("");

		sm_jTextField_menu_name.setEditable(true);

	}// GEN-LAST:event_sm_jButton_new_menuActionPerformed

	// �޴� ����
	private void sm_jButton_menu_deleteActionPerformed(
			java.awt.event.ActionEvent evt) {// GEN-FIRST:event_sm_jButton_menu_deleteActionPerformed
		// TODO add your handling code here:

		final String menuname = sm_jTextField_menu_name.getText();
		final CoffeShopManagementSystemJFrame comp = this;
		if (!sm_jTextField_menu_name.isEditable()) {

			Thread thread = new Thread(new Runnable() {
				public void run() {
					try {
						Thread.sleep(3000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block

					}

					if (!omc.deleteMenu(menuname)) {
						JOptionPane.showMessageDialog(comp, "�޴��̸� : "
								+ menuname + "\r\n���� �޴��Դϴ�.", "��������!!",
								JOptionPane.PLAIN_MESSAGE);
						return;
					}

					JOptionPane.showMessageDialog(comp, "�޴��̸� : " + menuname
							+ "\r\n���� �Ǿ����ϴ�.", "��������!!",
							JOptionPane.PLAIN_MESSAGE);

					sm_jTextField_menu_name.setText("");
					sm_jTextField_menu_price.setText("");

					sm_jTextField_menu_name.setEditable(true);

					// �ֹ��������� �޴� ����
					om_jComboBox_memu.removeItem(menuname);

				}
			});
			thread.start();

		} else {
			JOptionPane.showMessageDialog(this, "�˻��� ���¿��� ���� �����մϴ�.", "��������!!",
					JOptionPane.PLAIN_MESSAGE);
		}

	}// GEN-LAST:event_sm_jButton_menu_deleteActionPerformed

	// �޴� �˻�
	private void sm_jButton_menu_searchActionPerformed(
			java.awt.event.ActionEvent evt) {// GEN-FIRST:event_sm_jButton_menu_searchActionPerformed
		// TODO add your handling code here:

		if (!sm_jTextField_menu_name.isEditable()) {
			JOptionPane.showMessageDialog(this, "���� �˻��� �Ͱ� ���� �޴��Դϴ�.",
					"�˻�����!!", JOptionPane.PLAIN_MESSAGE);
			return;
		}

		final String menuname = sm_jTextField_menu_name.getText();
		final CoffeShopManagementSystemJFrame comp = this;
		if (menuname.isEmpty()) {
			JOptionPane.showMessageDialog(this, "�޴� �̸��� ������ϴ�.", "�˻�����!!",
					JOptionPane.PLAIN_MESSAGE);
			return;
		}

		Thread thread = new Thread(new Runnable() {
			public void run() {
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					// e.printStackTrace();
				}

				String price;
				try {
					price = omc.getPrice(menuname);
					sm_jTextField_menu_name.setText(menuname);
					sm_jTextField_menu_price.setText(price);
					sm_jTextField_menu_name.setEditable(false);
					JOptionPane.showMessageDialog(comp, "�޴��̸� :" + menuname
							+ "\r\n�˻��Ϸ� �Ǿ����ϴ�.", "�˻�����!!",
							JOptionPane.PLAIN_MESSAGE);
				} catch (MenuRegisterException e) {
					// TODO Auto-generated catch block
					// �޴� ���� ��
					JOptionPane.showMessageDialog(comp, "�޴��̸� :" + menuname
							+ "\r\n" + e.getMessage(), "�˻�����!!",
							JOptionPane.PLAIN_MESSAGE);
				}

			}
		});
		thread.start();

	}// GEN-LAST:event_sm_jButton_menu_searchActionPerformed

	// �޴� �߰�
	private void sm_jButton_menu_registActionPerformed(
			java.awt.event.ActionEvent evt) {// GEN-FIRST:event_sm_jButton_menu_registActionPerformed
		// TODO add your handling code here:

		final String menuname = sm_jTextField_menu_name.getText();
		final String menuprice = sm_jTextField_menu_price.getText();
		final CoffeShopManagementSystemJFrame comp = this;

		Thread thread = new Thread(new Runnable() {
			public void run() {
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					// e.printStackTrace();
				}

				// ���޴� ���
				if (sm_jTextField_menu_name.isEditable()) {
					try {
						omc.registerMenu(menuname, menuprice);
						JOptionPane.showMessageDialog(comp, "�޴��̸�: " + menuname
								+ "\r\n��ϼ���!", "", JOptionPane.PLAIN_MESSAGE);
						sm_jTextField_menu_name.setEditable(false);

						// �ֹ��������� �޴� �߰�
						om_jComboBox_memu.addItem(menuname);

					} catch (MenuRegisterException e) {
						JOptionPane.showMessageDialog(comp, "�޴��̸�: " + menuname
								+ " ��Ͻ���!!\r\n" + e.getMessage(), "��Ͻ���!",
								JOptionPane.PLAIN_MESSAGE);
					}
				}
				// ����
				else {
					try {
						omc.editMenu(menuname, menuprice);
						JOptionPane.showMessageDialog(comp, "�޴��̸�: " + menuname
								+ "\r\n��������!", "", JOptionPane.PLAIN_MESSAGE);
						sm_jTextField_menu_name.setEditable(false);

					} catch (MenuRegisterException e) {
						JOptionPane.showMessageDialog(comp, "�޴��̸�: " + menuname
								+ " ��������!!\r\n" + e.getMessage(), "��������!",
								JOptionPane.PLAIN_MESSAGE);
					}
				}

			}
		});
		thread.start();

	}// GEN-LAST:event_sm_jButton_menu_registActionPerformed

	// ���� ���� ������ ��ư
	private void mm_jButton_new_memberActionPerformed(
			java.awt.event.ActionEvent evt) {// GEN-FIRST:event_mm_jButton_new_memberActionPerformed
		// TODO add your handling code here:
		// ���� ��¥�� ����
		Date currentTime = new Date();
		SimpleDateFormat tmp_year = new SimpleDateFormat("yyyy");
		mm_jComboBox_year.setSelectedItem(tmp_year.format(currentTime));
		SimpleDateFormat tmp_month = new SimpleDateFormat("MM");
		mm_jComboBox_month.setSelectedItem(tmp_month.format(currentTime));

		mm_jComboBox_day.removeAllItems();
		// �޺��ڽ��� �� ����
		for (int i = 1; i <= calc_number_of_month(mm_jComboBox_year,
				mm_jComboBox_month); i++) {
			String tmp = String.format(("%02d"), i);
			mm_jComboBox_day.addItem(tmp);
		}
		SimpleDateFormat tmp_day = new SimpleDateFormat("dd");
		mm_jComboBox_day.setSelectedItem(tmp_day.format(currentTime));

		// editbox�ʱ�ȭ
		mm_jTextField_member_birth.setText("");
		mm_jTextField_member_name.setText("");
		mm_jTextField_member_phone_number.setText("");
		mm_jTextField_membership_number.setText("");

		mm_jTextField_membership_number.setEditable(true);
	}// GEN-LAST:event_mm_jButton_new_memberActionPerformed

	// �˻� ��ư
	private void mm_jButton_search_memberActionPerformed(
			java.awt.event.ActionEvent evt) {// GEN-FIRST:event_mm_jButton_search_memberActionPerformed
		// TODO add your handling code here:

		if (!mm_jTextField_membership_number.isEditable()) {
			JOptionPane.showMessageDialog(this, "���� �˻��� �Ͱ� ���� ������ȣ �Դϴ�.",
					"�˻�����!!", JOptionPane.PLAIN_MESSAGE);
			return;
		}

		final String num = mm_jTextField_membership_number.getText();
		final CoffeShopManagementSystemJFrame comp = this;
		if (num.isEmpty()) {
			JOptionPane.showMessageDialog(this, "���� ��ȣ�� ������ϴ�.", "�˻�����!!",
					JOptionPane.PLAIN_MESSAGE);
			return;
		}

		Thread thread = new Thread(new Runnable() {
			public void run() {
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					// e.printStackTrace();
				}

				String name;
				try {
					name = mc.getMemberName(num);

					mm_jTextField_membership_number.setText(num);
					mm_jTextField_membership_number.setEditable(false);
					mm_jTextField_member_name.setText(name);
					mm_jTextField_member_birth.setText(mc.getMemberBirth(num));
					mm_jTextField_member_phone_number.setText(mc
							.getMemberPhoneNumber(num));
					String datetmp = mc.getDate_of_register(num);
					StringTokenizer st1 = new StringTokenizer(datetmp, "/");
					mm_jComboBox_year.setSelectedItem(st1.nextToken());
					mm_jComboBox_month.setSelectedItem(st1.nextToken());
					// �޺��ڽ��� �� ����
					for (int i = 1; i <= calc_number_of_month(
							mm_jComboBox_year, mm_jComboBox_month); i++) {
						String tmp = String.format(("%02d"), i);
						mm_jComboBox_day.addItem(tmp);
					}
					mm_jComboBox_day.setSelectedItem(st1.nextToken());
					JOptionPane.showMessageDialog(comp, "������ȣ:" + num
							+ "\r\n�˻� �Ϸ� �Ǿ����ϴ�.", "�˻�����!!",
							JOptionPane.PLAIN_MESSAGE);

				} catch (MemberRegisterException e) {
					// TODO Auto-generated catch block
					// ID ���� ��
					JOptionPane.showMessageDialog(comp, "������ȣ:" + num + "\r\n"
							+ e.getMessage(), "�˻�����!!",
							JOptionPane.PLAIN_MESSAGE);

				}

			}
		});
		thread.start();

	}// GEN-LAST:event_mm_jButton_search_memberActionPerformed

	// ȸ�� ���� ��ư
	private void mm_jButton_delete_memberActionPerformed(
			java.awt.event.ActionEvent evt) {// GEN-FIRST:event_mm_jButton_delete_memberActionPerformed
		// TODO add your handling code here:
		// �˻��� ���¿����� ����

		final String num = mm_jTextField_membership_number.getText();
		final CoffeShopManagementSystemJFrame comp = this;
		if (!mm_jTextField_membership_number.isEditable()) {

			Thread thread = new Thread(new Runnable() {
				public void run() {
					try {
						Thread.sleep(3000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block

					}

					if (!mc.DeleteMember(num)) {
						JOptionPane.showMessageDialog(comp, "������ȣ : " + num
								+ "\r\n���� ������ȣ�Դϴ�.", "��������!!",
								JOptionPane.PLAIN_MESSAGE);
						return;
					}

					JOptionPane.showMessageDialog(comp, "������ȣ : " + num
							+ "\r\n���� �Ǿ����ϴ�.", "��������!!",
							JOptionPane.PLAIN_MESSAGE);
					Date currentTime = new Date();
					SimpleDateFormat tmp_year = new SimpleDateFormat("yyyy");
					mm_jComboBox_year.setSelectedItem(tmp_year
							.format(currentTime));
					SimpleDateFormat tmp_month = new SimpleDateFormat("MM");
					mm_jComboBox_month.setSelectedItem(tmp_month
							.format(currentTime));

					mm_jComboBox_day.removeAllItems();
					// �޺��ڽ��� �� ����
					for (int i = 1; i <= calc_number_of_month(
							mm_jComboBox_year, mm_jComboBox_month); i++) {
						String tmp = String.format(("%02d"), i);
						mm_jComboBox_day.addItem(tmp);
					}
					SimpleDateFormat tmp_day = new SimpleDateFormat("dd");
					mm_jComboBox_day.setSelectedItem(tmp_day
							.format(currentTime));

					// editbox�ʱ�ȭ
					mm_jTextField_member_birth.setText("");
					mm_jTextField_member_name.setText("");
					mm_jTextField_member_phone_number.setText("");
					mm_jTextField_membership_number.setText("");

					mm_jTextField_membership_number.setEditable(true);
				}
			});
			thread.start();

		} else {
			JOptionPane.showMessageDialog(this, "�˻��� ���¿��� ���� �����մϴ�.", "��������!!",
					JOptionPane.PLAIN_MESSAGE);
		}

	}// GEN-LAST:event_mm_jButton_delete_memberActionPerformed

	// ���� ���
	private void mm_jButton_regist_memberActionPerformed(
			java.awt.event.ActionEvent evt) {// GEN-FIRST:event_mm_jButton_regist_memberActionPerformed
		String temp1 = (String) mm_jComboBox_year.getSelectedItem();
		String temp2 = (String) mm_jComboBox_month.getSelectedItem();
		String temp3 = (String) mm_jComboBox_day.getSelectedItem();
		final String datetemp = String.format("%s/%s/%s", temp1, temp2, temp3);
		final String tmpname = mm_jTextField_member_name.getText();
		final String tmpnumber = mm_jTextField_membership_number.getText();
		final String tmpbirth = mm_jTextField_member_birth.getText();
		final String tmpphone = mm_jTextField_member_phone_number.getText();
		final CoffeShopManagementSystemJFrame comp = this;

		Thread thread = new Thread(new Runnable() {
			public void run() {
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					// e.printStackTrace();
				}

				// ������ ���
				if (mm_jTextField_membership_number.isEditable()) {
					try {
						mc.RegisterMember(tmpname, tmpnumber, tmpbirth,
								tmpphone, datetemp);
						JOptionPane.showMessageDialog(comp, "������ȣ: "
								+ tmpnumber + "\r\n��ϼ���!", "",
								JOptionPane.PLAIN_MESSAGE);
						mm_jTextField_membership_number.setEditable(false);
					} catch (MemberRegisterException e) {
						JOptionPane.showMessageDialog(comp, "������ȣ: "
								+ tmpnumber + "��Ͻ���!!\r\n" + e.getMessage(),
								" ��Ͻ���", JOptionPane.PLAIN_MESSAGE);
					}
				}
				// ����
				else {
					try {
						mc.EditMember(tmpname, tmpnumber, tmpbirth, tmpphone,
								datetemp);
						JOptionPane.showMessageDialog(comp, "������ȣ: "
								+ tmpnumber + "\r\n��������!", "",
								JOptionPane.PLAIN_MESSAGE);
						mm_jTextField_membership_number.setEditable(false);
					} catch (MemberRegisterException e) {
						JOptionPane.showMessageDialog(comp, "������ȣ: "
								+ tmpnumber + "��������!!\r\n" + e.getMessage(),
								" ��������", JOptionPane.PLAIN_MESSAGE);
					}
				}

			}
		});
		thread.start();

		// TODO add your handling code here:
	}// GEN-LAST:event_mm_jButton_regist_memberActionPerformed

	// ���� �������� from ���� ���� ��
	private void sm_jComboBox_from_yearActionPerformed(
			java.awt.event.ActionEvent evt) {// GEN-FIRST:event_sm_jComboBox_from_yearActionPerformed
		// TODO add your handling code here:
		set_day(sm_jComboBox_from_year, sm_jComboBox_from_month,
				sm_jComboBox_from_day);
		// ���� ���� from �ش� ������ �°� �� ����
	}// GEN-LAST:event_sm_jComboBox_from_yearActionPerformed
		// ���� �������� from �� �����

	private void sm_jComboBox_from_monthActionPerformed(
			java.awt.event.ActionEvent evt) {// GEN-FIRST:event_sm_jComboBox_from_monthActionPerformed
		// TODO add your handling code here:
		set_day(sm_jComboBox_from_year, sm_jComboBox_from_month,
				sm_jComboBox_from_day);
		// ���� ���� from �ش� ������ �°� �� ����
	}// GEN-LAST:event_sm_jComboBox_from_monthActionPerformed

	// ���� �������� to ���� ���� ��
	private void sm_jComboBox_to_yearActionPerformed(
			java.awt.event.ActionEvent evt) {// GEN-FIRST:event_sm_jComboBox_to_yearActionPerformed
		// TODO add your handling code here:
		set_day(sm_jComboBox_to_year, sm_jComboBox_to_month,
				sm_jComboBox_to_day);
		// ���� ���� to �ش� ������ �°� �� ����
	}// GEN-LAST:event_sm_jComboBox_to_yearActionPerformed
		// ���� �������� to �� ���� ��

	private void sm_jComboBox_to_monthActionPerformed(
			java.awt.event.ActionEvent evt) {// GEN-FIRST:event_sm_jComboBox_to_monthActionPerformed
		// TODO add your handling code here:
		set_day(sm_jComboBox_to_year, sm_jComboBox_to_month,
				sm_jComboBox_to_day);
		// ���� ���� to �ش� ������ �°� �� ����
	}// GEN-LAST:event_sm_jComboBox_to_monthActionPerformed

	// ���� �������� ���� ���� ��
	private void mm_jComboBox_yearActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_mm_jComboBox_yearActionPerformed
		// TODO add your handling code here:
		set_day(mm_jComboBox_year, mm_jComboBox_month, mm_jComboBox_day);
		// ���� �������� �ش� ������ �°� �� ����
	}// GEN-LAST:event_mm_jComboBox_yearActionPerformed

	// ���� �������� �� �����
	private void mm_jComboBox_monthActionPerformed(
			java.awt.event.ActionEvent evt) {// GEN-FIRST:event_mm_jComboBox_monthActionPerformed
		// TODO add your handling code here:
		set_day(mm_jComboBox_year, mm_jComboBox_month, mm_jComboBox_day);
		// ���� �������� �ش� ������ �°� �� ����
	}// GEN-LAST:event_mm_jComboBox_monthActionPerformed

	// ������ �޺��ڽ��� ���ڷ� �޾� �ش� ������ �°� �� ����
	private void set_day(javax.swing.JComboBox<String> year,
			javax.swing.JComboBox<String> month,
			javax.swing.JComboBox<String> day) {
		// �ʱ�ȭ �Ϸ�� ���¿��� �����ϰ�
		if (checksetting) {
			// ���� ���õ� �ϼ� �ӽ� ����
			String tmp_day = (String) day.getSelectedItem();
			// ���� ������ ����
			day.removeAllItems();
			// calc_number_of_month�Լ��� ������ �´� �ϼ� ���ϰ� �װſ� �°� ����ֱ�
			for (int i = 1; i <= calc_number_of_month(year, month); i++) {
				String tmp = String.format(("%02d"), i);
				day.addItem(tmp);
			}
			// �ӽ÷� ���� �� ���������� ����, ���� ���̸� 31,29... 1�Ϸ� �ڵ� ����
			day.setSelectedItem(tmp_day);
		}
	}

	// �� ���� �� ���� ���� �ؾ� �Ұ͵�
	private void coffe_shop_management_jTabbedPaneMouseClicked(
			java.awt.event.MouseEvent evt) {// GEN-FIRST:event_coffe_shop_management_jTabbedPaneMouseClicked
		// TODO add your handling code here:
		// �ֹ����� �� ������ ��¥ Ȯ���ؼ� �ٲ���
		if (coffe_shop_management_jTabbedPane.getSelectedIndex() == 0) {
			SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat(
					"yyyy�� MM�� dd��");
			Date currentTime = new Date();
			Datestring = String.format(("%s"),
					mSimpleDateFormat.format(currentTime));
			om_jTextField_show_date.setText(Datestring);
		}
	}// GEN-LAST:event_coffe_shop_management_jTabbedPaneMouseClicked

	// �ݺ��� ����..
	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JTabbedPane coffe_shop_management_jTabbedPane;
	private javax.swing.JPanel member_management_jPanel;
	private javax.swing.JButton mm_jButton_delete_member;
	private javax.swing.JButton mm_jButton_new_member;
	private javax.swing.JButton mm_jButton_regist_member;
	private javax.swing.JButton mm_jButton_search_member;
	private javax.swing.JComboBox<String> mm_jComboBox_day;
	private javax.swing.JComboBox<String> mm_jComboBox_month;
	private javax.swing.JComboBox<String> mm_jComboBox_year;
	private javax.swing.JLabel mm_jLabel1;
	private javax.swing.JLabel mm_jLabel2;
	private javax.swing.JLabel mm_jLabel3;
	private javax.swing.JLabel mm_jLabel4;
	private javax.swing.JLabel mm_jLabel5;
	private javax.swing.JLabel mm_jLabel6;
	private javax.swing.JLabel mm_jLabel7;
	private javax.swing.JLabel mm_jLabel8;
	private javax.swing.JTextField mm_jTextField_member_birth;
	private javax.swing.JTextField mm_jTextField_member_name;
	private javax.swing.JTextField mm_jTextField_member_phone_number;
	private javax.swing.JTextField mm_jTextField_membership_number;
	private javax.swing.JButton om_jButton_add_order;
	private javax.swing.JButton om_jButton_cancel_order;
	private javax.swing.JButton om_jButton_complete_order;
	private javax.swing.JComboBox<String> om_jComboBox_memu;
	private javax.swing.JLabel om_jLabel1;
	private javax.swing.JLabel om_jLabel2;
	private javax.swing.JScrollPane om_jScrollPane2;
	private javax.swing.JTextArea om_jTextArea_order_list;
	private javax.swing.JTextField om_jTextField_membership_number;
	private javax.swing.JTextField om_jTextField_show_date;
	private javax.swing.JPanel order_management_jPanel;
	private javax.swing.JPanel shop_management_jPanel;
	private javax.swing.JButton sm_jButton_menu_delete;
	private javax.swing.JButton sm_jButton_menu_regist;
	private javax.swing.JButton sm_jButton_menu_search;
	private javax.swing.JButton sm_jButton_new_menu;
	private javax.swing.JButton sm_jButton_sales_information;
	private javax.swing.JComboBox<String> sm_jComboBox_from_day;
	private javax.swing.JComboBox<String> sm_jComboBox_from_month;
	private javax.swing.JComboBox<String> sm_jComboBox_from_year;
	private javax.swing.JComboBox<String> sm_jComboBox_to_day;
	private javax.swing.JComboBox<String> sm_jComboBox_to_month;
	private javax.swing.JComboBox<String> sm_jComboBox_to_year;
	private javax.swing.JLabel sm_jLabel1;
	private javax.swing.JLabel sm_jLabel2;
	private javax.swing.JLabel sm_jLabel3;
	private javax.swing.JLabel sm_jLabel4;
	private javax.swing.JLabel sm_jLabel5;
	private javax.swing.JLabel sm_jLabel6;
	private javax.swing.JLabel sm_jLabel7;
	private javax.swing.JLabel sm_jLabel8;
	private javax.swing.JScrollPane sm_jScrollPane1;
	private javax.swing.JTextArea sm_jTextArea_sales_information;
	private javax.swing.JTextField sm_jTextField_menu_name;
	private javax.swing.JTextField sm_jTextField_menu_price;
	private javax.swing.JPanel reserve_management_jPanel;
	private JButton btnNewButton;
	private JButton button;
	private JButton button_1;
	private JScrollPane scrollPane;
	private JTextField textField;
}


class getFile extends CoffeShopManagementSystemJFrame {
	public getFile() {
		String str = ("reserve.txt");

		String s;
		try {
			BufferedReader br = new BufferedReader(new FileReader(str));

			int p = 0;
			while ((s = br.readLine()) != null) {
				if (s.equals("") != true) {
					StringTokenizer c = new StringTokenizer(s);
					int i = 0;
					while (c.hasMoreTokens()) {
						contents[p][i] = c.nextToken();
						
						i++;
					}
					if (contents[p].equals(""))
						continue;
					p++;
				}
			}

			total = p;
			br.close();
		} catch (FileNotFoundException fn) {
			System.out.println("������ ã�� �� �����ϴ�");
		} catch (IOException e) {
			System.out.println("������ �о� �� �� �����ϴ�");
		} finally {
			try {
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}