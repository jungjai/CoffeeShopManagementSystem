package coffeshopmanagementsystem;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Order {
	private String date;// 날짜
	private String menu;// 메뉴
	private String price;// 가격

	// 생성자
	Order(String tmpdate, String tmpmenu, String tmpprice) {
		date = new String(tmpdate);
		menu = new String(tmpmenu);
		price = new String(tmpprice);
	}

	public String getDate() {
		return date;
	}

	public String getMenu() {
		return menu;
	}

	public String getPrice() {
		return price;
	}

	// 날짜가 from부터 to까지 에 해당하는지 확인해줌, 해당한다면 true return
	public boolean Isvaliddate(String from, String to) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		Date date0;
		try {
			date0 = sdf.parse(date);// 원래 날짜
			Date date1 = sdf.parse(from);// ~부터
			Date date2 = sdf.parse(to);// ~까지

			if (date0.compareTo(date1) >= 0 && date0.compareTo(date2) <= 0) {
				return true;
			} else {
				return false;
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			return false;
		}

	}

}
