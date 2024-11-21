package basic;

/*
 * "두 번째 자바 프로그램입니다. 콘솔 화면에 출력을 합니다."
 * 라는 문자열을 화면(콘솔)에 출력하는 방법.
 */
public class Basic_02 {

	public static void main(String[] args) {
		
		// 첫번째 방법
		System.out.println("두 번째 자바 프로그램입니다. 콘솔 화면에 출력을 합니다.");
		
		// 두번째 방법
		System.out.print("두 번째 자바 프로그램입니다.");
		System.out.println("콘솔 화면에 출력을 합니다.");
		
		// 세번째 방법
		System.out.println("두 번째 자바 프로그램입니다." + "콘솔 화면에 출력을 합니다.");
	}

}
