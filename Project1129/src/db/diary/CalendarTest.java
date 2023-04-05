//패키지 선언
package db.diary;

//패키지 연결
import java.util.Calendar;

//클래스 선언
public class CalendarTest {

	// 메인메소드 선언
	public static void main(String[] args) {

		// 달력 객체 반환하기
		// abstract class Calendar : 추상클래스기 때문에 메소드를 통해 객체를 반환받아야 함
		// getInstance : 달력 가져오기(반환형 Calendar) 디폴트는 현재 시간
		Calendar cal = Calendar.getInstance();

		// 달력 조작하기
		cal.set(Calendar.MONTH, 10); // 월 (0부터 시작이니 11월로 조작함)
		cal.set(Calendar.DATE, 25); // 일

		// 달력 반환하기
		// 연
		// get(int field) : 상수를 넣어야 함
		int yy = cal.get(Calendar.YEAR);
		System.out.println("현재 연도는 : " + yy);

		// 월
		// MONTH : 0부터 시작
		int mm = cal.get(Calendar.MONTH);
		System.out.println("현재 월은 : " + mm);

		// 일
		int dd = cal.get(Calendar.DATE);
		System.out.println("현재 일은 : " + dd);

		// 요일
		int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
		System.out.println("현재 요일은 : " + dayOfWeek);
	}
}

