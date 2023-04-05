package management;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ProjectDateCheck {
	LocalDateTime date;
	String formatedNow;

	public ProjectDateCheck() {
		date = LocalDateTime.now();
		System.out.println(date);

		formatedNow = date.format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 hh시 mm분 ss초"));
		System.out.println(formatedNow);

	}
}
