package db.diary;

public class Dog {
	String name="치와와";
	private static Dog insatnce;//싱글턴패턴시 사용하는 단어 
	private Dog() {
	}
	public static Dog getInsatnce() {
		//만일 인스턴스 변수가 null이면 여기서 new해주면 된다.
		if(insatnce==null) {			
			//나는 접근이 가능하므로 인스턴스 생성해버리자!
			insatnce=new Dog();
		}
		return insatnce;
	}
}
