package db.diary;

//이 클래스는 테이블의 인스턴스 1개를 매칭할 수 있는 용도로 정의된 것이며, 로직이 들어있는 클래스가 아니라 
//오직 데이터만을 담기위한 용도의 클래스이다. (데이터는 보호되어야 되기 때문에 private)
//어플리케이션 설계분야에서는 이러한 클래스를 가리켜 DTO(Data Transfer Object)라고 한다. 
//데이터 전달이 목적인 클래스이다.

public class Diary {
	private int diary_idx;
	private int yy;
	private int mm;
	private int dd;
	private String content;
	private String icon;
	public int getDiary_idx() {
		return diary_idx;
	}
	public void setDiary_idx(int diary_idx) {
		this.diary_idx = diary_idx;
	}
	public int getYy() {
		return yy;
	}
	public void setYy(int yy) {
		this.yy = yy;
	}
	public int getMm() {
		return mm;
	}
	public void setMm(int mm) {
		this.mm = mm;
	}
	public int getDd() {
		return dd;
	}
	public void setDd(int dd) {
		this.dd = dd;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	
	
	
	
	
	
	
	
	
}
