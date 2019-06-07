package coffeshopmanagementsystem;

import java.util.*;
import java.io.*;

import javax.swing.JOptionPane;


public class MemberControl {
	// 멤버들 arraylist에 보관
	private ArrayList<Member> Memberlist;

	// 생성자
	MemberControl() {
		Memberlist = new ArrayList<Member>();
		// 파일 열기
		File input = new File("custom.txt");
		Scanner scanner = null;
		String name = "";
		String ID = "";
		String birth = "";
		String phone = "";
		String date = "";
		int ordernum = 0;
		try {
			scanner = new Scanner(input);

			while (scanner.hasNext()) {
				name = scanner.nextLine();

				// text file을 임의로 메모장으로 수정했을때 utf-8을 나타내는 byte가
				// text file앞에 들어감. 그것을 임의로 제거해줌
				if (name.startsWith("\uFEFF")) {
					name = name.substring(1);
				}

				ID = scanner.nextLine();
				birth = scanner.nextLine();
				phone = scanner.nextLine();
				date = scanner.nextLine();
				ordernum = Integer.parseInt(scanner.nextLine());
				Member temp = new Member(name, ID, birth, phone, date, ordernum);
				Memberlist.add(temp);

			}
			// 파일 닫기
			scanner.close();
		} catch (FileNotFoundException e) {
			// 처리 해줄 것 없음 그냥 없어도 정상 작동됨
		} catch (InputMismatchException e) {
			// 정상적으로 읽은 곳 까지만 처리됨
			// 파일 닫기
			scanner.close();
		} catch (NoSuchElementException e) {
			// 정상적으로 읽은 곳 까지만 처리됨
			// 파일 닫기
			scanner.close();
		} catch (MemberRegisterException e) {
			// 등록하다가 문제 생겼을 때 정상적으로 읽은곳 까지만 처리됨
			String tmp = String
					.format("에러 발생 한 곳 - 이름 : %s 고객번호 : %s 생일 : %s 전화번호 : %s 주문횟수 : %d\r\n에러메세지 %s",
							name, ID, birth, phone, ordernum, e.getMessage());
			JOptionPane.showMessageDialog(null, tmp, "ERROR!!",
					JOptionPane.PLAIN_MESSAGE);
			// 파일 닫기
			scanner.close();
		}

	}

	// 등록하기 - 다른 thread에서 동시에 작업 못함
	public synchronized void RegisterMember(String tmpname, String tmpmemberID,
			String tmpbirth, String tmpphoneNumber, String tmpdate)
			throws MemberRegisterException {
		Member temp = new Member(tmpname, tmpmemberID, tmpbirth,
				tmpphoneNumber, tmpdate, 0);
		Memberlist.add(temp);
		// 파일에 쓰기
		writelist();
	}

	// 수정하기 - 다른 thread에서 동시에 작업 못함
	public synchronized void EditMember(String tmpname, String tmpmemberID,
			String tmpbirth, String tmpphoneNumber, String tmpdate)
			throws MemberRegisterException {
		int size_of_list = Memberlist.size();
		for (int i = 0; i < size_of_list; i++) {
			if (Memberlist.get(i).Is_rignt_ID(tmpmemberID)) {
				Memberlist.get(i).setName(tmpname);
				Memberlist.get(i).setPhoneNumber(tmpphoneNumber);
				Memberlist.get(i).setbirthday(tmpbirth);
				Memberlist.get(i).setdate_of_register(tmpdate);
				// 파일에 쓰기
				writelist();
				return;
			}
		}
		throw new MemberRegisterException("해당고객이 없습니다.\r\n");
	}

	// 제거하기 없는 ID라면 false 리턴 - 다른 thread에서 동시에 작업 못함
	public synchronized boolean DeleteMember(String ID) {

		int size_of_list = Memberlist.size();
		for (int i = 0; i < size_of_list; i++) {
			if (Memberlist.get(i).Is_rignt_ID(ID)) {
				Memberlist.remove(i);
				Member.releaseMemberID(ID);
				// 파일에 쓰기
				writelist();
				return true;
			}
		}
		return false;
	}

	// ID 번호 받아 고객이름 return 없는 ID라면 예외 발생
	public String getMemberName(String ID) throws MemberRegisterException {
		int size_of_list = Memberlist.size();
		for (int i = 0; i < size_of_list; i++) {
			if (Memberlist.get(i).Is_rignt_ID(ID)) {
				return Memberlist.get(i).getName();
			}
		}
		throw new MemberRegisterException("없는 고객 번호 입니다.\r\n");
	}

	// ID 번호 받아 생일 return 없는 ID라면 null 리턴
	public String getMemberBirth(String ID) {
		int size_of_list = Memberlist.size();
		for (int i = 0; i < size_of_list; i++) {
			if (Memberlist.get(i).Is_rignt_ID(ID)) {
				return Memberlist.get(i).getBirth();
			}
		}
		return null;
	}

	// ID 번호 받아 전화번호 return 없는 ID라면 null 리턴
	public String getMemberPhoneNumber(String ID) {
		int size_of_list = Memberlist.size();
		for (int i = 0; i < size_of_list; i++) {
			if (Memberlist.get(i).Is_rignt_ID(ID)) {
				return Memberlist.get(i).getPhoneNumber();
			}
		}
		return null;
	}

	// ID 번호 받아 등록일 return 없는 ID라면 null 리턴
	public String getDate_of_register(String ID) {
		int size_of_list = Memberlist.size();
		for (int i = 0; i < size_of_list; i++) {
			if (Memberlist.get(i).Is_rignt_ID(ID)) {
				return Memberlist.get(i).getDate_of_register();
			}
		}
		return null;
	}

	// ID 번호 받아 주문회수 증가, 없는 ID 0, 주문 횟수 증가 1, 쿠폰 줄차례 2
	public int member_order(String ID) {
		int size_of_list = Memberlist.size();
		for (int i = 0; i < size_of_list; i++) {
			if (Memberlist.get(i).Is_rignt_ID(ID)) {
				if (Memberlist.get(i).add_num_of_order()) {
					writelist();
					return 2;
				} else {
					writelist();
					return 1;
				}

			}
		}
		return 0;
	}

	// 파일에 쓰기
	private void writelist() {
		int size_of_list = Memberlist.size();
		OutputStream out;
		try {

			out = new FileOutputStream("custom.txt", false);
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(out));
			for (int i = 0; i < size_of_list; i++) {

				String temp = String.format(
						"%s\r\n%s\r\n%s\r\n%s\r\n%s\r\n%d\r\n",
						Memberlist.get(i).getName(), Memberlist.get(i)
								.getMemberID(), Memberlist.get(i).getBirth(),
						Memberlist.get(i).getPhoneNumber(), Memberlist.get(i)
								.getDate_of_register(), Memberlist.get(i)
								.getNumofOrder());
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

}
