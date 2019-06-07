package coffeshopmanagementsystem;


// 멤버 등록하다가 오류시
public class MemberRegisterException extends Exception {
	MemberRegisterException(String message) {
		super(message);
	}
}
