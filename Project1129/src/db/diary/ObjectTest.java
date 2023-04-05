package db.diary;

public class ObjectTest {
	public static void main(String[] args) {
		String s1 = "korea";
		String s2 = "korea";
		
		System.out.println(s1==s2);
		System.out.println(s1.equals(s2));
		
//		자바는 내용비교가 아닌 내부적으로 주소비교를 한 것
//		내용비교를 할 때는 equals를 쓰는데에 적합
		

		
	}
}
