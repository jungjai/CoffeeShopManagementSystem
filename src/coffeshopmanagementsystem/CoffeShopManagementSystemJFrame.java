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

	private String Datestring; // 날짜
	private boolean checksetting = false; // 세팅 완료 되면 true로
	private MemberControl mc; // MemberControl Class
	private OrderMenuControl omc;// OrderMenuControl Class
	public static String[][] contents = new String[300][3];
	public static int total;
	// 주문 임시 저장에 사용함
	class Temporder {
		public String orderMenu;
		public String orderPrice;
	}

	private ArrayList<Temporder> tempord; // 주문 임시 저장
	
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
		initComponents();// 넷빈즈 제공- 컴포너트들 초기화

		initvalue();// JComboBox,JTextField 값 초기화
		checksetting = true;// 세팅 원래
		setResizable(false);// 창 크기 못바꾸게
		mc = new MemberControl();
		omc = new OrderMenuControl();
		tempord = new ArrayList<Temporder>();

		ArrayList<String> tmplist = omc.getMenuList();
		int size = tmplist.size();
		for (int i = 0; i < size; i++) {
			om_jComboBox_memu.addItem(tmplist.get(i));
		}
		printorder(false);// 주문관리 welcome 찍어주기

		om_jTextField_membership_number.setText("0000");

	}
	

	// JComboBox,JTextField 값 초기화
	private void initvalue() {
		// 날짜 표시
		SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat(
				"yyyy년 MM월 dd일");
		Date currentTime = new Date();
		Datestring = String.format(("%s"),
				mSimpleDateFormat.format(currentTime));
		om_jTextField_show_date.setText(Datestring);

		// 콤보박스에 연도 세팅
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

		// 콤보박스에 월 세팅
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

		// 콤보박스에 일 세팅
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

	// 연과 월을 인자로 받아서 해당 연월에 날짜수 계산
	private int calc_number_of_month(javax.swing.JComboBox<String> year,
			javax.swing.JComboBox<String> month) {
		int day = 0;
		String tmp_year = (String) year.getSelectedItem();
		int tmpi_year = Integer.parseInt(tmp_year);
		String tmp_month = (String) month.getSelectedItem();
		int tmpi_month = Integer.parseInt(tmp_month);

		// 2월
		if (tmpi_month == 2) {
			// 윤달
			if ((tmpi_year % 400) == 0 || (tmpi_year % 4) == 0
					&& (tmpi_year % 100) != 0) {
				day = 29;
			} else {
				day = 28;
			}
		}
		// 7월이하
		else if (tmpi_month < 8) {
			if (tmpi_month % 2 == 1) {
				day = 31;
			} else {
				day = 30;
			}
		}
		// 8월 부터
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

		om_jLabel1.setText("고객번호 :");

		om_jLabel2.setText("메 뉴 명  :");

		om_jTextArea_order_list.setEditable(false);
		om_jTextArea_order_list.setColumns(20);
		om_jTextArea_order_list.setRows(5);
		om_jScrollPane2.setViewportView(om_jTextArea_order_list);

		om_jButton_add_order.setText("주문추가");
		om_jButton_add_order
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						om_jButton_add_orderActionPerformed(evt);
					}
				});

		om_jButton_cancel_order.setText("주문취소");
		om_jButton_cancel_order
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						om_jButton_cancel_orderActionPerformed(evt);
					}
				});

		om_jButton_complete_order.setText("주문완료");
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

		coffe_shop_management_jTabbedPane.addTab("주문관리",
				order_management_jPanel);

		sm_jLabel1.setText("메뉴명 :");

		sm_jLabel2.setText("가   격 :");

		sm_jButton_new_menu.setText("새매뉴");
		sm_jButton_new_menu
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						sm_jButton_new_menuActionPerformed(evt);
					}
				});

		sm_jButton_menu_delete.setText("메뉴삭제");
		sm_jButton_menu_delete
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						sm_jButton_menu_deleteActionPerformed(evt);
					}
				});

		sm_jButton_menu_search.setText("메뉴검색");
		sm_jButton_menu_search
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						sm_jButton_menu_searchActionPerformed(evt);
					}
				});

		sm_jButton_menu_regist.setText("메뉴등록");
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

		sm_jLabel3.setText("년");

		sm_jLabel4.setText("월");

		sm_jLabel5.setText("일 부터");

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

		sm_jLabel6.setText("년");

		sm_jLabel7.setText("월");

		sm_jLabel8.setText("일 까지");

		sm_jButton_sales_information.setText("매출정보");
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
				.addTab("매장관리", shop_management_jPanel);

		mm_jButton_new_member.setText("새고객");
		mm_jButton_new_member
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						mm_jButton_new_memberActionPerformed(evt);
					}
				});

		mm_jButton_search_member.setText("고객정보검색");
		mm_jButton_search_member
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						mm_jButton_search_memberActionPerformed(evt);
					}
				});

		mm_jButton_delete_member.setText("고객정보삭제");
		mm_jButton_delete_member
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						mm_jButton_delete_memberActionPerformed(evt);
					}
				});

		mm_jButton_regist_member.setText("고객등록");
		mm_jButton_regist_member
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						mm_jButton_regist_memberActionPerformed(evt);
					}
				});

		mm_jLabel1.setText("등 록 일 :");

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

		mm_jLabel2.setText("년");

		mm_jLabel3.setText("월");

		mm_jLabel4.setText("일");

		mm_jLabel5.setText("고객번호 :");

		mm_jLabel6.setText("전화번호 :");

		mm_jLabel7.setText("고 객 명 :");

		mm_jLabel8.setText("생    일 :");

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

		coffe_shop_management_jTabbedPane.addTab("고객관리",
				member_management_jPanel);	
							
		coffe_shop_management_jTabbedPane.addTab("예약관리",
				reserve_management_jPanel);
		reserve_management_jPanel.setLayout(null);
		
		btnNewButton = new JButton("예약조회");
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
						throw new MyException5("날짜가 비어있습니다.");
					else if (!day.matches("^[0-9]{4}-[0-9]{2}-[0-9]{2}$"))
						throw new MyException6("날짜를 다시 입력해주세요");
					else if (!day.matches(".*[0-9].*"))
						throw new MyException7("날짜에 글자가 포함되었습니다");
					else {				        
						JOptionPane.showMessageDialog(reserve_management_jPanel, "조회되었습니다.");
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
		
		button = new JButton("예약취소");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(reserve_management_jPanel, "취소되었습니다.");
			}
		});
		button.setBounds(313, 291, 106, 27);
		reserve_management_jPanel.add(button);
		
		button_1 = new JButton("예약화면");
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
		String []content = {"자리 NO.", "예약자명", "연락처"};
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

	// 화면에 선택한 메뉴와 가격 표시해줌
	// iscomplete에 true오면 세금과 합계 내줌
	private void printorder(boolean iscomplte) {
		String print;
		int sizelist = tempord.size();
		int sum = 0;
		print = String.format("++++++++++Welcome++++++++++\r\n\r\n");
		for (int i = 0; i < sizelist; i++) {
			String tmpprice = tempord.get(i).orderPrice;
			// 콤마형식으로
			String tmpprice_comma = NumberFormat.getNumberInstance(Locale.US)
					.format(Integer.parseInt(tmpprice));
			// 총합
			sum += Integer.parseInt(tmpprice);
			print = String.format("%s%-10s%-5s\r\n", print,
					tempord.get(i).orderMenu, tmpprice_comma);
		}

		// 완료 시
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

		// 출력
		om_jTextArea_order_list.setText(print);
	}

	// 주문 추가
	private void om_jButton_add_orderActionPerformed(
			java.awt.event.ActionEvent evt) {// GEN-FIRST:event_om_jButton_add_orderActionPerformed
		// TODO add your handling code here:

		// 주문 중 주문 추가 못함
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
			JOptionPane.showMessageDialog(this, "주문 중 추가하지 못합니다.", "",
					JOptionPane.PLAIN_MESSAGE);
		}

	}// GEN-LAST:event_om_jButton_add_orderActionPerformed

	// 주문 취소
	private void om_jButton_cancel_orderActionPerformed(
			java.awt.event.ActionEvent evt) {// GEN-FIRST:event_om_jButton_cancel_orderActionPerformed
		// TODO add your handling code here:
		// 주문 중 주문 취소 못함
		if (om_jTextField_membership_number.isEditable()) {
			if (tempord.size() == 0) {
				JOptionPane.showMessageDialog(this, "취소할 주문이 없습니다.", "",
						JOptionPane.PLAIN_MESSAGE);
			} else {
				tempord.remove(tempord.size() - 1);
				printorder(false);
			}
		} else {
			JOptionPane.showMessageDialog(this, "주문 중 취소하지 못합니다.", "",
					JOptionPane.PLAIN_MESSAGE);
		}

	}// GEN-LAST:event_om_jButton_cancel_orderActionPerformed

	// 주문 완료
	private void om_jButton_complete_orderActionPerformed(
			java.awt.event.ActionEvent evt) {// GEN-FIRST:event_om_jButton_complete_orderActionPerformed
		// TODO add your handling code here:

		if (tempord.size() == 0) {
			JOptionPane.showMessageDialog(this, "주문이 없습니다.", "",
					JOptionPane.PLAIN_MESSAGE);
		} else {
			final String mnumber = om_jTextField_membership_number.getText()
					.trim();

			// 고객 번호 형식 검사
			Pattern pa;
			pa = Pattern.compile("^[0-9]{1,4}$");
			Matcher ma = pa.matcher(mnumber);
			if (!ma.matches()) {
				JOptionPane.showMessageDialog(this, "잘못된 고객 번호입니다.", "",
						JOptionPane.PLAIN_MESSAGE);
				return;
			}

			// 날짜
			SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat(
					"yyyy/MM/dd");
			Date currentTime = new Date();
			final String curdate = String.format(("%s"),
					mSimpleDateFormat.format(currentTime));
			final CoffeShopManagementSystemJFrame comp = this;

			// 주문중 고객번호 못 바꾸게 락
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
					// 비회원 주문
					if (mid == 0) {
						int num = tempord.size();
						for (int i = 0; i < num; i++) {
							omc.registerOrder(curdate,
									tempord.get(i).orderMenu,
									tempord.get(i).orderPrice);
						}

						JOptionPane.showMessageDialog(comp, "비회원님 주문되었습니다.",
								"", JOptionPane.PLAIN_MESSAGE);
						// 주문내역 출력
						printorder(true);
						// 초기화
						tempord = new ArrayList<Temporder>();
						// 고객번호에 걸린 락 풀기
						om_jTextField_membership_number.setEditable(true);
					}
					// 회원 주문
					else {

						try {
							// 회원 번호 확인
							mc.getMemberName(mnumber);

							int statuscupoon = mc.member_order(mnumber);
							// 회원번호 한번더 확인
							if (statuscupoon == 0) {
								JOptionPane.showMessageDialog(comp,
										"없는 고객 번호 입니다.", "",
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
								JOptionPane.showMessageDialog(comp, "주문완료\r\n"
										+ mnumber + "고객님\r\n무료쿠폰이 발급되었습니다.",
										"", JOptionPane.PLAIN_MESSAGE);
								omc.registerOrder(curdate, "쿠폰", "0");
							} else {
								JOptionPane.showMessageDialog(comp, mnumber
										+ "고객님\r\n주문되었습니다", "",
										JOptionPane.PLAIN_MESSAGE);
							}
							// 주문내역 출력
							printorder(true);
							// 초기화
							tempord = new ArrayList<Temporder>();
							// 고객번호에 걸린 락 풀기
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

		// ID 번호 받아 주문회수 증가, 없는 ID 0, 주문 횟수 증가 1, 쿠폰 줄차례 2
		// public int member_order(String ID) {

	}// GEN-LAST:event_om_jButton_complete_orderActionPerformed

	// 매출 현황
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
				// 매출 정보 얻어오기 인자로 기간을 보낸다.
				String print = omc
						.getSalesInformation(datefromtemp, datetotemp);
				sm_jTextArea_sales_information.setText(print);
			}
		});
		thread.start();

	}// GEN-LAST:event_sm_jButton_sales_informationActionPerformed

	// 새매뉴
	private void sm_jButton_new_menuActionPerformed(
			java.awt.event.ActionEvent evt) {// GEN-FIRST:event_sm_jButton_new_menuActionPerformed
		// TODO add your handling code here:

		sm_jTextField_menu_name.setText("");
		sm_jTextField_menu_price.setText("");

		sm_jTextField_menu_name.setEditable(true);

	}// GEN-LAST:event_sm_jButton_new_menuActionPerformed

	// 메뉴 삭제
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
						JOptionPane.showMessageDialog(comp, "메뉴이름 : "
								+ menuname + "\r\n없는 메뉴입니다.", "삭제실패!!",
								JOptionPane.PLAIN_MESSAGE);
						return;
					}

					JOptionPane.showMessageDialog(comp, "메뉴이름 : " + menuname
							+ "\r\n삭제 되었습니다.", "삭제성공!!",
							JOptionPane.PLAIN_MESSAGE);

					sm_jTextField_menu_name.setText("");
					sm_jTextField_menu_price.setText("");

					sm_jTextField_menu_name.setEditable(true);

					// 주문관리에서 메뉴 삭제
					om_jComboBox_memu.removeItem(menuname);

				}
			});
			thread.start();

		} else {
			JOptionPane.showMessageDialog(this, "검색된 상태에만 삭제 가능합니다.", "삭제실패!!",
					JOptionPane.PLAIN_MESSAGE);
		}

	}// GEN-LAST:event_sm_jButton_menu_deleteActionPerformed

	// 메뉴 검색
	private void sm_jButton_menu_searchActionPerformed(
			java.awt.event.ActionEvent evt) {// GEN-FIRST:event_sm_jButton_menu_searchActionPerformed
		// TODO add your handling code here:

		if (!sm_jTextField_menu_name.isEditable()) {
			JOptionPane.showMessageDialog(this, "현재 검색된 것과 같은 메뉴입니다.",
					"검색실패!!", JOptionPane.PLAIN_MESSAGE);
			return;
		}

		final String menuname = sm_jTextField_menu_name.getText();
		final CoffeShopManagementSystemJFrame comp = this;
		if (menuname.isEmpty()) {
			JOptionPane.showMessageDialog(this, "메뉴 이름이 비었습니다.", "검색실패!!",
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
					JOptionPane.showMessageDialog(comp, "메뉴이름 :" + menuname
							+ "\r\n검색완료 되었습니다.", "검색성공!!",
							JOptionPane.PLAIN_MESSAGE);
				} catch (MenuRegisterException e) {
					// TODO Auto-generated catch block
					// 메뉴 없을 때
					JOptionPane.showMessageDialog(comp, "메뉴이름 :" + menuname
							+ "\r\n" + e.getMessage(), "검색실패!!",
							JOptionPane.PLAIN_MESSAGE);
				}

			}
		});
		thread.start();

	}// GEN-LAST:event_sm_jButton_menu_searchActionPerformed

	// 메뉴 추가
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

				// 새메뉴 등록
				if (sm_jTextField_menu_name.isEditable()) {
					try {
						omc.registerMenu(menuname, menuprice);
						JOptionPane.showMessageDialog(comp, "메뉴이름: " + menuname
								+ "\r\n등록성공!", "", JOptionPane.PLAIN_MESSAGE);
						sm_jTextField_menu_name.setEditable(false);

						// 주문관리에서 메뉴 추가
						om_jComboBox_memu.addItem(menuname);

					} catch (MenuRegisterException e) {
						JOptionPane.showMessageDialog(comp, "메뉴이름: " + menuname
								+ " 등록실패!!\r\n" + e.getMessage(), "등록실패!",
								JOptionPane.PLAIN_MESSAGE);
					}
				}
				// 수정
				else {
					try {
						omc.editMenu(menuname, menuprice);
						JOptionPane.showMessageDialog(comp, "메뉴이름: " + menuname
								+ "\r\n수정성공!", "", JOptionPane.PLAIN_MESSAGE);
						sm_jTextField_menu_name.setEditable(false);

					} catch (MenuRegisterException e) {
						JOptionPane.showMessageDialog(comp, "메뉴이름: " + menuname
								+ " 수정실패!!\r\n" + e.getMessage(), "수정실패!",
								JOptionPane.PLAIN_MESSAGE);
					}
				}

			}
		});
		thread.start();

	}// GEN-LAST:event_sm_jButton_menu_registActionPerformed

	// 고객 관리 새고객 버튼
	private void mm_jButton_new_memberActionPerformed(
			java.awt.event.ActionEvent evt) {// GEN-FIRST:event_mm_jButton_new_memberActionPerformed
		// TODO add your handling code here:
		// 오늘 날짜로 변경
		Date currentTime = new Date();
		SimpleDateFormat tmp_year = new SimpleDateFormat("yyyy");
		mm_jComboBox_year.setSelectedItem(tmp_year.format(currentTime));
		SimpleDateFormat tmp_month = new SimpleDateFormat("MM");
		mm_jComboBox_month.setSelectedItem(tmp_month.format(currentTime));

		mm_jComboBox_day.removeAllItems();
		// 콤보박스에 일 세팅
		for (int i = 1; i <= calc_number_of_month(mm_jComboBox_year,
				mm_jComboBox_month); i++) {
			String tmp = String.format(("%02d"), i);
			mm_jComboBox_day.addItem(tmp);
		}
		SimpleDateFormat tmp_day = new SimpleDateFormat("dd");
		mm_jComboBox_day.setSelectedItem(tmp_day.format(currentTime));

		// editbox초기화
		mm_jTextField_member_birth.setText("");
		mm_jTextField_member_name.setText("");
		mm_jTextField_member_phone_number.setText("");
		mm_jTextField_membership_number.setText("");

		mm_jTextField_membership_number.setEditable(true);
	}// GEN-LAST:event_mm_jButton_new_memberActionPerformed

	// 검색 버튼
	private void mm_jButton_search_memberActionPerformed(
			java.awt.event.ActionEvent evt) {// GEN-FIRST:event_mm_jButton_search_memberActionPerformed
		// TODO add your handling code here:

		if (!mm_jTextField_membership_number.isEditable()) {
			JOptionPane.showMessageDialog(this, "현재 검색된 것과 같은 고객번호 입니다.",
					"검색실패!!", JOptionPane.PLAIN_MESSAGE);
			return;
		}

		final String num = mm_jTextField_membership_number.getText();
		final CoffeShopManagementSystemJFrame comp = this;
		if (num.isEmpty()) {
			JOptionPane.showMessageDialog(this, "고객 번호가 비었습니다.", "검색실패!!",
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
					// 콤보박스에 일 세팅
					for (int i = 1; i <= calc_number_of_month(
							mm_jComboBox_year, mm_jComboBox_month); i++) {
						String tmp = String.format(("%02d"), i);
						mm_jComboBox_day.addItem(tmp);
					}
					mm_jComboBox_day.setSelectedItem(st1.nextToken());
					JOptionPane.showMessageDialog(comp, "고객번호:" + num
							+ "\r\n검색 완료 되었습니다.", "검색성공!!",
							JOptionPane.PLAIN_MESSAGE);

				} catch (MemberRegisterException e) {
					// TODO Auto-generated catch block
					// ID 없을 때
					JOptionPane.showMessageDialog(comp, "고객번호:" + num + "\r\n"
							+ e.getMessage(), "검색실패!!",
							JOptionPane.PLAIN_MESSAGE);

				}

			}
		});
		thread.start();

	}// GEN-LAST:event_mm_jButton_search_memberActionPerformed

	// 회원 삭제 버튼
	private void mm_jButton_delete_memberActionPerformed(
			java.awt.event.ActionEvent evt) {// GEN-FIRST:event_mm_jButton_delete_memberActionPerformed
		// TODO add your handling code here:
		// 검색된 상태에서만 가능

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
						JOptionPane.showMessageDialog(comp, "고객번호 : " + num
								+ "\r\n없는 고객번호입니다.", "삭제실패!!",
								JOptionPane.PLAIN_MESSAGE);
						return;
					}

					JOptionPane.showMessageDialog(comp, "고객번호 : " + num
							+ "\r\n삭제 되었습니다.", "삭제성공!!",
							JOptionPane.PLAIN_MESSAGE);
					Date currentTime = new Date();
					SimpleDateFormat tmp_year = new SimpleDateFormat("yyyy");
					mm_jComboBox_year.setSelectedItem(tmp_year
							.format(currentTime));
					SimpleDateFormat tmp_month = new SimpleDateFormat("MM");
					mm_jComboBox_month.setSelectedItem(tmp_month
							.format(currentTime));

					mm_jComboBox_day.removeAllItems();
					// 콤보박스에 일 세팅
					for (int i = 1; i <= calc_number_of_month(
							mm_jComboBox_year, mm_jComboBox_month); i++) {
						String tmp = String.format(("%02d"), i);
						mm_jComboBox_day.addItem(tmp);
					}
					SimpleDateFormat tmp_day = new SimpleDateFormat("dd");
					mm_jComboBox_day.setSelectedItem(tmp_day
							.format(currentTime));

					// editbox초기화
					mm_jTextField_member_birth.setText("");
					mm_jTextField_member_name.setText("");
					mm_jTextField_member_phone_number.setText("");
					mm_jTextField_membership_number.setText("");

					mm_jTextField_membership_number.setEditable(true);
				}
			});
			thread.start();

		} else {
			JOptionPane.showMessageDialog(this, "검색된 상태에만 삭제 가능합니다.", "삭제실패!!",
					JOptionPane.PLAIN_MESSAGE);
		}

	}// GEN-LAST:event_mm_jButton_delete_memberActionPerformed

	// 고객 등록
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

				// 새고객 등록
				if (mm_jTextField_membership_number.isEditable()) {
					try {
						mc.RegisterMember(tmpname, tmpnumber, tmpbirth,
								tmpphone, datetemp);
						JOptionPane.showMessageDialog(comp, "고객번호: "
								+ tmpnumber + "\r\n등록성공!", "",
								JOptionPane.PLAIN_MESSAGE);
						mm_jTextField_membership_number.setEditable(false);
					} catch (MemberRegisterException e) {
						JOptionPane.showMessageDialog(comp, "고객번호: "
								+ tmpnumber + "등록실패!!\r\n" + e.getMessage(),
								" 등록실패", JOptionPane.PLAIN_MESSAGE);
					}
				}
				// 수정
				else {
					try {
						mc.EditMember(tmpname, tmpnumber, tmpbirth, tmpphone,
								datetemp);
						JOptionPane.showMessageDialog(comp, "고객번호: "
								+ tmpnumber + "\r\n수정성공!", "",
								JOptionPane.PLAIN_MESSAGE);
						mm_jTextField_membership_number.setEditable(false);
					} catch (MemberRegisterException e) {
						JOptionPane.showMessageDialog(comp, "고객번호: "
								+ tmpnumber + "수정실패!!\r\n" + e.getMessage(),
								" 수정실패", JOptionPane.PLAIN_MESSAGE);
					}
				}

			}
		});
		thread.start();

		// TODO add your handling code here:
	}// GEN-LAST:event_mm_jButton_regist_memberActionPerformed

	// 매장 관리에서 from 연도 변경 시
	private void sm_jComboBox_from_yearActionPerformed(
			java.awt.event.ActionEvent evt) {// GEN-FIRST:event_sm_jComboBox_from_yearActionPerformed
		// TODO add your handling code here:
		set_day(sm_jComboBox_from_year, sm_jComboBox_from_month,
				sm_jComboBox_from_day);
		// 매장 관리 from 해당 연월에 맞게 일 세팅
	}// GEN-LAST:event_sm_jComboBox_from_yearActionPerformed
		// 매장 관리에서 from 월 변경시

	private void sm_jComboBox_from_monthActionPerformed(
			java.awt.event.ActionEvent evt) {// GEN-FIRST:event_sm_jComboBox_from_monthActionPerformed
		// TODO add your handling code here:
		set_day(sm_jComboBox_from_year, sm_jComboBox_from_month,
				sm_jComboBox_from_day);
		// 매장 관리 from 해당 연월에 맞게 일 세팅
	}// GEN-LAST:event_sm_jComboBox_from_monthActionPerformed

	// 매장 관리에서 to 연도 변경 시
	private void sm_jComboBox_to_yearActionPerformed(
			java.awt.event.ActionEvent evt) {// GEN-FIRST:event_sm_jComboBox_to_yearActionPerformed
		// TODO add your handling code here:
		set_day(sm_jComboBox_to_year, sm_jComboBox_to_month,
				sm_jComboBox_to_day);
		// 매장 관리 to 해당 연월에 맞게 일 세팅
	}// GEN-LAST:event_sm_jComboBox_to_yearActionPerformed
		// 매장 관리에서 to 월 변경 시

	private void sm_jComboBox_to_monthActionPerformed(
			java.awt.event.ActionEvent evt) {// GEN-FIRST:event_sm_jComboBox_to_monthActionPerformed
		// TODO add your handling code here:
		set_day(sm_jComboBox_to_year, sm_jComboBox_to_month,
				sm_jComboBox_to_day);
		// 매장 관리 to 해당 연월에 맞게 일 세팅
	}// GEN-LAST:event_sm_jComboBox_to_monthActionPerformed

	// 고객 관리에서 연도 변경 시
	private void mm_jComboBox_yearActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_mm_jComboBox_yearActionPerformed
		// TODO add your handling code here:
		set_day(mm_jComboBox_year, mm_jComboBox_month, mm_jComboBox_day);
		// 고객 관리에서 해당 연월에 맞게 일 세팅
	}// GEN-LAST:event_mm_jComboBox_yearActionPerformed

	// 고객 관리에서 월 변경시
	private void mm_jComboBox_monthActionPerformed(
			java.awt.event.ActionEvent evt) {// GEN-FIRST:event_mm_jComboBox_monthActionPerformed
		// TODO add your handling code here:
		set_day(mm_jComboBox_year, mm_jComboBox_month, mm_jComboBox_day);
		// 고객 관리에서 해당 연월에 맞게 일 세팅
	}// GEN-LAST:event_mm_jComboBox_monthActionPerformed

	// 연월일 콤보박스들 인자로 받아 해당 연월에 맞게 일 세팅
	private void set_day(javax.swing.JComboBox<String> year,
			javax.swing.JComboBox<String> month,
			javax.swing.JComboBox<String> day) {
		// 초기화 완료된 상태에만 반응하게
		if (checksetting) {
			// 현재 선택된 일수 임시 저장
			String tmp_day = (String) day.getSelectedItem();
			// 현재 아이템 제거
			day.removeAllItems();
			// calc_number_of_month함수로 연월에 맞는 일수 구하고 그거에 맞게 집어넣기
			for (int i = 1; i <= calc_number_of_month(year, month); i++) {
				String tmp = String.format(("%02d"), i);
				day.addItem(tmp);
			}
			// 임시로 저장 해 놓은것으로 설정, 없는 날이면 31,29... 1일로 자동 설정
			day.setSelectedItem(tmp_day);
		}
	}

	// 탭 눌릴 때 마다 갱신 해야 할것들
	private void coffe_shop_management_jTabbedPaneMouseClicked(
			java.awt.event.MouseEvent evt) {// GEN-FIRST:event_coffe_shop_management_jTabbedPaneMouseClicked
		// TODO add your handling code here:
		// 주문관리 탭 눌리면 날짜 확인해서 바꿔줌
		if (coffe_shop_management_jTabbedPane.getSelectedIndex() == 0) {
			SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat(
					"yyyy년 MM월 dd일");
			Date currentTime = new Date();
			Datestring = String.format(("%s"),
					mSimpleDateFormat.format(currentTime));
			om_jTextField_show_date.setText(Datestring);
		}
	}// GEN-LAST:event_coffe_shop_management_jTabbedPaneMouseClicked

	// 넷빈즈 제공..
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
			System.out.println("파일을 찾을 수 없습니다");
		} catch (IOException e) {
			System.out.println("파일을 읽어 올 수 없습니다");
		} finally {
			try {
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}