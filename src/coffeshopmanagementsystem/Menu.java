package coffeshopmanagementsystem;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Menu {
	private String menu_name;// 메뉴이름
	private String menu_price;// 메뉴 가격

	Menu(String tmpname, String tmpprice) throws MenuRegisterException {
		Setmenuname(tmpname);
		Setmenuprice(tmpprice);
	}

	// 메뉴 이름 설정
	public final void Setmenuname(String tmpname) throws MenuRegisterException {
		menu_name = new String(tmpname);
		// 메뉴명은 10자이내 한글/영문/숫자조합 가능
		Pattern pa = Pattern.compile("^[a-zA-Z가-힣0-9]{1,10}$");
		Matcher ma = pa.matcher(menu_name);
		if (!ma.matches()) {
			throw new MenuRegisterException(
					"잘못된 메뉴명입니다.\r\n메뉴명은 10자이내 한글/영문/숫자조합 가능합니다.(2~10)\r\n");
		}
		if (menu_name.compareTo("쿠폰") == 0) {
			throw new MenuRegisterException("쿠폰은 메뉴명으로 사용할수 없습니다.\r\n");

		}
	}

	// 메뉴 가격 설정
	public final void Setmenuprice(String tmpprice)
			throws MenuRegisterException {
		menu_price = new String(tmpprice);
		// 가격은 4자리 이내 숫자
		Pattern pa = Pattern.compile("^[0-9]{1,4}$");
		Matcher ma = pa.matcher(menu_price);
		if (!ma.matches()) {
			throw new MenuRegisterException(
					"잘못된 가격입니다.\r\n가격은 4자 이내 숫자만 가능합니다.\r\n");
		}
	}

	// 메뉴 이름 얻기
	public String getmenuname() {
		return menu_name;
	}

	// 메뉴 가격 얻기
	public String getmenuprice() {
		return menu_price;
	}

	// 메뉴 이름 입력받아 같은지 확인 해줌 true면 같음
	public boolean Issamemenu(String tmpname) {
		if (tmpname == null) {
			return false;
		} else {
			if (tmpname.trim().compareTo(menu_name) == 0)
				return true;
			else
				return false;
		}
	}

}
