package coffeshopmanagementsystem;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Member {
	private String name;// 이름 10자 제한
	private String memberID;// 고객번호
	private String birth;// 생일
	private String phoneNumber;// 전화번호
	private String date_of_register;// 등록일
	private int num_of_order;// 0~2 까지만 3되면 쿠폰 발행되고 초기화
	private static boolean[] memberIDSet; // 멤버아이디 중복 방지 check용, 사용된 memberID는
											// true가 됨

	// static initial block 딱 1번만 초기화 된다.
	static {
		memberIDSet = new boolean[10000];

		for (int i = 0; i < 10000; i++) {
			memberIDSet[i] = false;
		}
		memberIDSet[0] = true; // 비회원용
	}

	// 세팅시 문제 발생시 MemberRegisterException 넘김
	Member(String tmpname, String tmpmemberID, String tmpbirth,
			String tmpphoneNumber, String tmpdate, int tmpnumoforder)
			throws MemberRegisterException {
		if (tmpnumoforder < 3 && tmpnumoforder >= 0)
			num_of_order = tmpnumoforder;
		else {
			throw new MemberRegisterException("주문횟수는 0~2 사이입니다!\r\n");
		}
		// 이름 세팅 형식에 안맞으면 MemberRegisterException 발생
		setName(tmpname);
		// 번호 세팅 형식에 안맞으면 MemberRegisterException 발생
		setPhoneNumber(tmpphoneNumber);
		// 생일 세팅 형식에 안맞으면 MemberRegisterException 발생
		setbirthday(tmpbirth);
		// 등록일 세팅 형식에 안맞으면 MemberRegisterException 발생
		setdate_of_register(tmpdate);
		// 고객번호 세팅 형식에 안맞거나 중복시 MemberRegisterException 발생
		setMemberID(tmpmemberID);

	}

	// 등록일 세팅 형식에 안맞으면 MemberRegisterException 발생
	public final void setdate_of_register(String tmpdate)
			throws MemberRegisterException {
		date_of_register = new String(tmpdate);
		// 생일은 xxxx/xx/xx 형식으로만 입력가능
		Pattern pa = Pattern.compile("[0-9]{4}/[0-9]{2}/[0-9]{2}$");
		Matcher ma = pa.matcher(birth);
		if (!ma.matches()) {
			throw new MemberRegisterException(
					"잘못된 등록일입니다.\r\n등록일은 xxxx/xx/xx 형식으로만 입력 가능합니다.\r\n");
		}
	}

	// 생일 세팅 형식에 안맞으면 MemberRegisterException 발생
	public final void setbirthday(String tmpbirth)
			throws MemberRegisterException {
		birth = new String(tmpbirth);
		// 생일은 xxxx/xx/xx 형식으로만 입력가능
		Pattern pa = Pattern.compile("[0-9]{4}/[0-9]{2}/[0-9]{2}$");
		Matcher ma = pa.matcher(birth);
		if (!ma.matches()) {
			throw new MemberRegisterException(
					"잘못된 생일입니다.\r\n생일은 xxxx/xx/xx 형식으로만 입력 가능합니다.\r\n");
		}
	}

	// 번호 세팅 형식에 안맞으면 MemberRegisterException 발생
	public final void setPhoneNumber(String tmpphonenumber)
			throws MemberRegisterException {
		phoneNumber = new String(tmpphonenumber);
		// 전화번호는 xxx-xxxx-xxxx 형식으로만 입력가능
		Pattern pa = Pattern.compile("[0-9]{3}-[0-9]{4}-[0-9]{4}$");
		Matcher ma = pa.matcher(phoneNumber);
		if (!ma.matches()) {
			throw new MemberRegisterException(
					"잘못된 전화번호입니다.\r\n전화번호는 xxx-xxxx-xxxx 형식으로만 입력가능합니다.\r\n");
		}
	}

	// 고객번호 세팅 형식에 안맞거나 중복시 MemberRegisterException 발생
	private final void setMemberID(String tmpmemberID)
			throws MemberRegisterException {
		memberID = new String(tmpmemberID);
		// 고객번호는 1~4자리 숫자만 들어 갈수 있습니다.
		Pattern pa;
		pa = Pattern.compile("^[0-9]{1,4}$");
		Matcher ma = pa.matcher(memberID);
		if (!ma.matches()) {
			throw new MemberRegisterException(
					"잘못된 고객 번호 입니다.\r\n고객번호는 1~4자리 숫자만 들어 갈수 있습니다.\r\n");
		} else {
			int tmpmembernum = Integer.parseInt(memberID);
			if (!memberIDSet[tmpmembernum]) {
				memberIDSet[tmpmembernum] = true;
			} else {
				throw new MemberRegisterException("중복된 고객 번호 입니다.\r\n");
			}
		}
	}

	// 이름 세팅 형식에 안맞으면 MemberRegisterException 발생
	public final void setName(String tmpname) throws MemberRegisterException {
		name = new String(tmpname);
		// 이름에는 공백 영어 대소문자 한글이 최소 2자부터 15자까지 들어간다.
		Pattern pa = Pattern.compile("^[a-z A-Z가-힣]{2,10}$");
		Matcher ma = pa.matcher(name);
		if (!ma.matches()) {
			throw new MemberRegisterException(
					"잘못된 이름입니다.\r\n이름에는 한글, 공백, 영어 대소문자만 들어갈수 있습니다.(2~10)\r\n");
		}
	}

	// memberID 할당 제거
	public static void releaseMemberID(String tmpmemberid) {
		int tmpmembernum = Integer.parseInt(tmpmemberid);
		if (tmpmembernum != 0) {
			memberIDSet[tmpmembernum] = false;
		}
	}

	// 등록일 얻기
	public String getDate_of_register() {
		return date_of_register;
	}

	// 이름 얻기
	public String getName() {
		return name;
	}

	// 고객 번호 얻기
	public String getMemberID() {
		return memberID;
	}

	// 생일 얻기
	public String getBirth() {
		return birth;
	}

	// 전화번호 얻기
	public String getPhoneNumber() {
		return phoneNumber;
	}

	public int getNumofOrder() {
		return num_of_order;
	}

	// 주문시 마다 호출 시켜야함, 3번째 주문이면 true 리턴 그 이외 false
	public boolean add_num_of_order() {
		num_of_order++;
		if (num_of_order >= 3) {
			num_of_order = 0;
			return true;
		}
		return false;
	}

	// ID넘겨 받아 해당 ID 맞으면 true 아니면 false
	public boolean Is_rignt_ID(String ID) {
		int tmp = Integer.parseInt(ID);
		int id = Integer.parseInt(memberID);
		if (id == tmp) {
			return true;
		} else {
			return false;
		}
	}

}
