package util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class StringUtil {
//	넘겨받은 숫자가 1자리 수이면 앞에 0 붙이기
//	누군가가 이 메서드를 호출하면 처리결과를 반환받는다
	public static String getNumString(int num) {
		String str = null;
		if (num < 10) { // 한자리 수
			str = "0" + num; // 05
		} else { // 두자리 수
			str = Integer.toString(num);
		}
		return str;
	}

//	확장자 추출하여 반환받기
	public static String getExtend(String filename) {
//		String filename = "a.aa.png";
		int lastIndex = filename.lastIndexOf(".");
		System.out.println(lastIndex);
		return filename.substring(lastIndex + 1, filename.length());

	}
//	public static void main(String[] args) {
//		String result = getNumString(1);
//		System.out.println(result);
//		System.out.println(getExtend("png"));
//	}

//	- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -  
//	비밀번호 암호화 하기
//	자바의 보안과 관련된 기능을 지원하는 API가 모여있는 패키지가 java.security
	public static String getConvetredPassword(String pass) {
		String hex = null;
		StringBuffer hexString = new StringBuffer(); 
//		암호화객체
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] hash = digest.digest(pass.getBytes("UTF-8")); // String이 Byte배열로 보관됨

//			String은 불변이다. 그 값이 변경될 수 없다
//			따라서, String객체는 반복문 횟수가 클 때는 절대 누적식을 사용해서는 안된다
//			-> 변경 가능한 문자열 객체를 지원하는 StringBuffer 또는 StringBuilder 등을 활용하자 
//			StringBuffer, StringBuilder는 String 아님
			for (int i = 0; i < hash.length; i++) {
				hex = Integer.toHexString(0xff & hash[i]);
				System.out.println(hex);
				if (hex.length() == 1) {
//					hex = "0" + hex;
					hexString.append("0");
				}
//				hexString += "0";
//				hexString += hex; // 누적식이라 새로운 객체가 계속 생성됨 
				hexString.append(hex);
			}
//			System.out.println(hexString.toString()); // 자동호출되긴 하지만 스트링화 시키는게
//			System.out.println(hexString.length());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return hexString.toString();
	}

//	public static void main(String[] args) {
//		String result = getConvetredPassword("뒤질거같은데");
//		System.out.println(result.length());
//	}
}
