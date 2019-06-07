package coffeshopmanagementsystem;

//메뉴 등록하다 예외
public class MenuRegisterException extends Exception {
	MenuRegisterException(String message) {
		super(message);
	}
}