package homework;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

// JButton이 현재 상황에 맞지 않기 때문에 원하는 버튼으로 그림을 재정의하기 위함
public class CustomButton extends JButton{
	int width;
	int height;
	int index; // 몇 번째 버튼인지 알 수 있는 기준 값
	ShurekGallery shurekGallery;
	public CustomButton(ShurekGallery shurekGallery, int index, int width, int height) {
		this.shurekGallery = shurekGallery;
		this.width = width;
		this.height = height;
		this.index = index;
		
//		this.setBorder(null);
		this.setPreferredSize(new Dimension(width, height));
		
		// 버튼에 리스너 연결
		this.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				shurekGallery.targetX = index*-500;
				System.out.println("???");
			}
		});
	}
	protected void paintComponent(Graphics g) {
		g.setColor(Color.RED);
		g.fillRect(0, 0, width, height);
		g.setColor(Color.GREEN);
		g.fillOval(0, 0, width, height); // JButton에 속해있기 떄문에 JPanel의 좌표가 아닌 버튼의 좌표부터 시작		
	}
}
