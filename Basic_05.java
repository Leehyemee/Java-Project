package basic;

public class Basic_05 {

	public static void main(String[] args) {
		
		// 숫자 + 숫자 ==> 숫자("+" 의 역할은 덧셈의 의미)
		System.out.println(10+23); // 33
		
		// 문자 + 숫자 ==> 문자("+" 의 역할은 연결의 의미)
		System.out.println("10" + 23); // 1023
		
		// 숫자 + 문자 ==> 문자("+" 의 역할은 연결의 의미)
		System.out.println(10 + "23"); // 1023
		
		// 문자 + 문자 ==> 문자("+" 의 역할은 연결의 의미)
		System.out.println("10" + "23"); // 1023
		
		System.out.println("10" + 4 + 7); // 1047
		System.out.println("10" + (4 + 7)); // 1011

	}

}
