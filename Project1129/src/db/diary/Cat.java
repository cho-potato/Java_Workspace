package db.diary;

public class Cat  {//싱글턴패턴
	String name="체리";
	private static Cat instance=Cat.getInstence();
	private static Cat instance2=Cat.getInstence();

	
	private Cat() {
		// TODO Auto-generated constructor stub
	}
	/*접근제한자 
	protected : 상속관계 및 같은 패키지(디렉토리)
	default : 같은 패키지
	private : 자신 외의 객체는 접근이 불가능하다. 
	 */
	public static Cat getInstence() {
		if(instance==null) {
			instance=new Cat();
			instance2=new Cat();
			//new Cat();
		}
		return instance;
	}
	
}
