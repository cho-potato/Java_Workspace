package db.diary;

public class UseDog {
	public static void main(String[] args) {
		Dog d=Dog.getInsatnce();
		System.out.println(d);
		Dog d2=Dog.getInsatnce();
		System.out.println(d);
		Dog d3=Dog.getInsatnce();
		System.out.println(d);
		//SingleTon Pattern
		//부를때마다 생성되는 것이 아니라 이미 만들어진 객체를 가져오는 것이라 주소값이 하나만 나온다.
	}

}
