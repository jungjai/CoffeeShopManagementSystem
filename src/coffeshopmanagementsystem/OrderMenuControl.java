package coffeshopmanagementsystem;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Scanner;

import javax.swing.JOptionPane;

public class OrderMenuControl {
	private ArrayList<Menu> menuList;
	private ArrayList<Order> orderList;

	OrderMenuControl() {
		menuList = new ArrayList<Menu>();
		orderList = new ArrayList<Order>();

		// 파일 열기
		File input = new File("menu.txt");
		Scanner scanner = null;
		String menu = "";
		String price = "";
		String date = "";
		int num_of_menu = 0;
		try {
			scanner = new Scanner(input);
			if (scanner.hasNextLine()) {
				// 메뉴의 수를 구함
				String tmptmp = scanner.nextLine();

				// text file을 임의로 메모장으로 수정했을때 utf-8을 나타내는 byte가
				// text file앞에 들어감. 그것을 임의로 제거해줌
				if (tmptmp.startsWith("\uFEFF")) {
					tmptmp = tmptmp.substring(1);
				}

				num_of_menu = Integer.parseInt(tmptmp);

				// 메뉴 읽기
				for (int i = 0; i < num_of_menu; i++) {
					menu = scanner.nextLine();
					price = scanner.nextLine();
					Menu temp = new Menu(menu, price);
					menuList.add(temp);
				}
				scanner.nextLine();
				// 매출 정보 읽기
				while (scanner.hasNext()) {
					date = scanner.nextLine();
					menu = scanner.nextLine();
					price = scanner.nextLine();
					Order temp = new Order(date, menu, price);
					orderList.add(temp);
				}
				scanner.close();
			}
		} catch (NoSuchElementException e) {
			// 정상적으로 읽은 곳 까지 처리
			scanner.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
		} catch (MenuRegisterException e) {
			// TODO Auto-generated catch block
			// 등록하다가 문제 생겼을 때 정상적으로 읽은곳 까지만 처리됨
			String tmp = String.format(
					"에러 발생 한 곳 - menu : %s price %s\r\n에러메세지 %s", menu, price,
					e.getMessage());
			JOptionPane.showMessageDialog(null, tmp, "ERROR!!",
					JOptionPane.PLAIN_MESSAGE);
			// 파일 닫기
			scanner.close();
		}
	}

	// 메뉴 등록, 등록시 문제 생기면 예외발생, 중복시 예외 발생
	public synchronized void registerMenu(String tmpmenu, String tmpprice)
			throws MenuRegisterException {
		int size_of_list = menuList.size();
		tmpmenu = tmpmenu.trim();
		tmpprice = tmpprice.trim();
		// 중복된 메뉴 있는지 검색
		for (int i = 0; i < size_of_list; i++) {
			if (menuList.get(i).Issamemenu(tmpmenu)) {
				throw new MenuRegisterException(
						"중복메뉴입니다.\r\n수정은 검색 후에 가능합니다.\r\n");

			}
		}
		// 중복된 메뉴 아니면 새로 등록
		Menu temp = new Menu(tmpmenu, tmpprice);
		menuList.add(temp);
		writelist();
		return;
	}

	// 메뉴 수정, 수정시 문제 생기면 예외발생, 중복시 예외 발생
	public synchronized void editMenu(String tmpmenu, String tmpprice)
			throws MenuRegisterException {
		int size_of_list = menuList.size();
		tmpmenu = tmpmenu.trim();
		tmpprice = tmpprice.trim();
		// 메뉴 있는지 검색
		for (int i = 0; i < size_of_list; i++) {
			if (menuList.get(i).Issamemenu(tmpmenu)) {
				// 메뉴 수정
				menuList.get(i).Setmenuprice(tmpprice);
				writelist();
				return;
			}
		}
		throw new MenuRegisterException("메뉴가 존재 하지 않습니다.\r\n");
	}

	// 메뉴 삭제 없으면 false 리턴
	public synchronized boolean deleteMenu(String tmpmenu) {
		int size_of_list = menuList.size();
		tmpmenu = tmpmenu.trim();
		// 메뉴 있는지 검색
		for (int i = 0; i < size_of_list; i++) {
			if (menuList.get(i).Issamemenu(tmpmenu)) {

				menuList.remove(i);
				// 파일에 쓰기
				writelist();
				return true;
			}
		}
		return false;
	}

	// 메뉴 이름 받아 가격 출력 없으면 예외 발생
	public String getPrice(String menunametmp) throws MenuRegisterException {
		int size_of_list = menuList.size();
		menunametmp = menunametmp.trim();
		for (int i = 0; i < size_of_list; i++) {
			if (menuList.get(i).Issamemenu(menunametmp)) {
				return menuList.get(i).getmenuprice();
			}
		}
		throw new MenuRegisterException("없는 메뉴 입니다.\r\n");
	}

	// 매출 상황 등록
	public void registerOrder(String tmpdate, String tmpmenu, String tmpprice) {
		Order temp = new Order(tmpdate, tmpmenu, tmpprice);
		orderList.add(temp);
		writelist();
	}

	// 매출 정보 저장을 위한 inner class
	class SalesInformation {
		public String temp_menu;
		public String temp_price;
		public int temp_num_of_order = 0;
	}

	// 기간을 입력 받아 해당일에 해당하는 매출 정보 출력
	public String getSalesInformation(String from_date, String to_date) {
		String tmp = "";

		ArrayList<SalesInformation> salesinfo = new ArrayList<SalesInformation>();

		int size_of_list = orderList.size();
		for (int i = 0; i < size_of_list; i++) {
			// 해당 기간에 맞는 주문인지 확인
			if (orderList.get(i).Isvaliddate(from_date, to_date)) {
				int tempsize = salesinfo.size();
				boolean findsame = false;
				// 같은 주문 있었는지 확인하고 찾으면 주문수 늘림
				for (int j = 0; j < tempsize; j++) {
					if (orderList.get(i).getMenu()
							.compareTo(salesinfo.get(j).temp_menu) == 0) {
						if (orderList.get(i).getPrice()
								.compareTo(salesinfo.get(j).temp_price) == 0) {
							salesinfo.get(j).temp_num_of_order++;
							findsame = true;
							break;
						}
					}
				}
				// 찾지 못했을 때
				if (!findsame) {
					SalesInformation tempsf = new SalesInformation();
					tempsf.temp_menu = orderList.get(i).getMenu();
					tempsf.temp_price = orderList.get(i).getPrice();
					tempsf.temp_num_of_order = 1;
					salesinfo.add(tempsf);
				}
			}
		}

		// 해당기간 판매 정보 String에 넣기
		size_of_list = salesinfo.size();
		tmp = String.format("%-10s%-5s%-10s\r\n++++++++++++++++++++++\r\n",
				"품명", "갯수", "매출금액");
		int sumofnum = 0;
		int sumofsales = 0;
		for (int i = 0; i < size_of_list; i++) {
			int number = salesinfo.get(i).temp_num_of_order;
			int totalSum = number
					* Integer.parseInt(salesinfo.get(i).temp_price);
			String totalSumS = NumberFormat.getNumberInstance(Locale.US)
					.format(totalSum);
			tmp = String.format("%s%-10s%-5d%-10s\r\n", tmp,
					salesinfo.get(i).temp_menu, number, totalSumS);

			sumofnum += number;
			sumofsales += totalSum;
		}
		String sumofsalesS = NumberFormat.getNumberInstance(Locale.US).format(
				sumofsales);
		tmp = String.format("%s++++++++++++++++++++++\r\n%-10s%-5d%-10s\r\n",
				tmp, "합계", sumofnum, sumofsalesS);
		return tmp;
	}

	private void writelist() {
		int size_of_list = menuList.size();
		OutputStream out;
		try {
			out = new FileOutputStream("menu.txt", false);
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(out));

			bw.write(String.format("%d\r\n", size_of_list));
			for (int i = 0; i < size_of_list; i++) {
				String temp = String.format("%s\r\n%s\r\n", menuList.get(i)
						.getmenuname(), menuList.get(i).getmenuprice());
				bw.write(temp);
			}
			bw.write("@@@\r\n");
			size_of_list = orderList.size();
			for (int i = 0; i < size_of_list; i++) {
				String temp = String.format("%s\r\n%s\r\n%s\r\n", orderList
						.get(i).getDate(), orderList.get(i).getMenu(),
						orderList.get(i).getPrice());
				bw.write(temp);
			}

			bw.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, "파일을 쓸수 없습니다.", "ERROR!!",
					JOptionPane.PLAIN_MESSAGE);
			System.exit(1);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, "파일을 쓸수 없습니다.", "ERROR!!",
					JOptionPane.PLAIN_MESSAGE);
			System.exit(1);
		}

	}

	public ArrayList<String> getMenuList() {
		int size = menuList.size();
		ArrayList<String> temp = new ArrayList<String>();
		for (int i = 0; i < size; i++) {
			temp.add(menuList.get(i).getmenuname());
		}
		return temp;
	}

}
