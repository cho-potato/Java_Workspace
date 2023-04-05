//패키지 선언
package db.diary;

import java.awt.Color;
//패키지 연결
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

//클래스 선언
public class Cell extends JPanel {
	String title; // 날짜
	String content; // 내용
	int fontSize;
	int x;
	int y;
	
    // 생성자 선언 
    public Cell(String title, String content, int fontSize, int x, int y) {
		this.title=title;
		this.content = content;
		this.fontSize=fontSize;
		this.x=x;
		this.y=y;
    }

   
}