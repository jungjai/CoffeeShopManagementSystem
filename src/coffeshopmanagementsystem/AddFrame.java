package coffeshopmanagementsystem;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;
import coffeshopmanagementsystem.CoffeShopManagementSystemJFrame;
public class AddFrame extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JLabel label;
	private JTextField textField_1;
	private JLabel label_1;
	private JTextField textField_2;
	private JButton button;

	CoffeShopManagementSystemJFrame coffee;
	private JButton button_1;
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					//AddFrame frame = new AddFrame();
					//frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public AddFrame() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 500, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("예약자명 : ");
		lblNewLabel.setFont(new Font("굴림", Font.BOLD, 20));
		lblNewLabel.setBounds(12, 10, 121, 36);
		contentPane.add(lblNewLabel);
		
		textField = new JTextField();
		textField.setBounds(152, 10, 320, 36);
		contentPane.add(textField);
		textField.setColumns(10);
		
		label = new JLabel("전화번호 :");
		label.setFont(new Font("굴림", Font.BOLD, 20));
		label.setBounds(12, 56, 121, 36);
		contentPane.add(label);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(152, 56, 320, 36);
		contentPane.add(textField_1);
		
		label_1 = new JLabel("날     짜  :");
		label_1.setFont(new Font("굴림", Font.BOLD, 20));
		label_1.setBounds(12, 102, 121, 36);
		contentPane.add(label_1);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(152, 102, 188, 36);
		contentPane.add(textField_2);
		
		JButton btnNewButton = new JButton("예약하기");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Thread thread1 = new Thread() {
					public void run() {
						reserve();
					}
				};
				thread1.start();
			}
			private synchronized void reserve() {
				try {
					String name = textField.getText();
					String phone = textField_1.getText();			
					if (name.length() > 5)
						throw new MyException1("이름은 5자 이내여야 합니다");
					else if (!phone.matches("^[0-9]{2,3}-[0-9]{3,4}-[0-9]{4}$"))
						throw new MyException2("전화번호를 다시 입력해주세요");
					else if (name.equals("") || phone.equals(""))
						throw new MyException3("이름과 전화번호를 입력해주세요");
					else if (name.matches(".*[0-9].*"))
						throw new MyException4("이름에 숫자가 포함되었습니다");
					else {				        
						JOptionPane.showMessageDialog(contentPane, "예약되었습니다.");
						dispose();
					}
					
				} catch (MyException1 e1) {
					JOptionPane.showMessageDialog(contentPane, e1.getMessage());
				} catch (MyException2 e2) {
					JOptionPane.showMessageDialog(contentPane, e2.getMessage());
				} catch (MyException3 e3) {
					JOptionPane.showMessageDialog(contentPane, e3.getMessage());
				} catch (MyException4 e4) {
					JOptionPane.showMessageDialog(contentPane, e4.getMessage());
				}
				
			}
		});
		btnNewButton.setFont(new Font("굴림", Font.BOLD, 25));
		btnNewButton.setBounds(12, 308, 217, 36);
		contentPane.add(btnNewButton);
		
		button = new JButton("예약취소");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(contentPane, "취소되었습니다.");
				dispose();
			}
		});
		button.setFont(new Font("굴림", Font.BOLD, 25));
		button.setBounds(255, 308, 217, 36);
		contentPane.add(button);
		
		JTable table_1 = new JTable();
		DefaultTableModel defaultTableModel = (DefaultTableModel) table_1.getModel();
		String []content = {"자리 NO.", "예약자명", "연락처"};
		defaultTableModel.setColumnIdentifiers(content);
		for (int i = 1; i < coffee.total; i++)
			defaultTableModel.addRow(coffee.contents[i]);
		
		JScrollPane panel_1 = new JScrollPane(table_1);
		panel_1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		panel_1.setBounds(12, 148, 460, 134);
		contentPane.add(panel_1);
		
		button_1 = new JButton("예약조회");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Thread thread2 = new Thread() {
					public void run() {
						daysearch();
					}
				};
				thread2.start();				
			}
			
			private synchronized void daysearch() {
				try {
					String day = textField_2.getText();
					if (day.equals(""))
						throw new MyException5("날짜가 비어있습니다.");
					else if (!day.matches("^[0-9]{4}-[0-9]{2}-[0-9]{2}$"))
						throw new MyException6("날짜를 다시 입력해주세요");
					else if (!day.matches(".*[0-9].*"))
						throw new MyException7("날짜에 글자가 포함되었습니다");
					else {				        
						JOptionPane.showMessageDialog(contentPane, "조회되었습니다.");
					}
					
				} catch (MyException5 e5) {
					JOptionPane.showMessageDialog(contentPane, e5.getMessage());
				} catch (MyException6 e6) {
					JOptionPane.showMessageDialog(contentPane, e6.getMessage());
				} catch (MyException7 e7) {
					JOptionPane.showMessageDialog(contentPane, e7.getMessage());
				}
			}
		});
		button_1.setFont(new Font("굴림", Font.BOLD, 20));
		button_1.setBounds(352, 102, 120, 36);
		contentPane.add(button_1);
	}
}

class MyException1 extends Exception {
	public MyException1(String e) {
		super(e);
	}
}

class MyException2 extends Exception{
	public MyException2(String e) {
		super(e);
	}
}

class MyException3 extends Exception{
	public MyException3(String e) {
		super(e);
	}
}

class MyException4 extends Exception{
	public MyException4(String e) {
		super(e);
	}
}

class MyException5 extends Exception{
	public MyException5(String e) {
		super(e);
	}
}

class MyException6 extends Exception{
	public MyException6(String e) {
		super(e);
	}
}

class MyException7 extends Exception{
	public MyException7(String e) {
		super(e);
	}
}

