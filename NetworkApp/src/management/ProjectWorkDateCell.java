package management;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Calendar;

import javax.swing.JOptionPane;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import util.StringUtil;

public class ProjectWorkDateCell extends ProjectWorkCell{
	Color color=Color.LIGHT_GRAY;
	InnerWorkPage innerWorkPage;
	
	public ProjectWorkDateCell(InnerWorkPage innerWorkPage, String title, String content, int fontSize, int x, int y) {
		super(title, content, fontSize, x, y);
		this.innerWorkPage = innerWorkPage;
		
		Border border = new LineBorder(Color.GRAY);
		setBorder(border);
		
		this.setFont(new Font("돋움", Font.BOLD|Font.ITALIC, 20));
		addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (color == Color.LIGHT_GRAY) {
					color = Color.YELLOW;
//						콤보박스에 날짜 채워넣기 (콤보박스는 디자인이니까 디자인쪽에서 처리)
					ProjectWorkDTO dates = new ProjectWorkDTO();
					dates.setYy(innerWorkPage.currentObj.get(Calendar.YEAR));
					dates.setMm(innerWorkPage.currentObj.get(Calendar.MONTH));
					dates.setDd(Integer.parseInt(ProjectWorkDateCell.this.title));
					ProjectWorkDTO result = innerWorkPage.projectWorkDAO.selectByDates(dates);
					
					innerWorkPage.setDateInfo(ProjectWorkDateCell.this.title, result);
				} else {
					color = Color.LIGHT_GRAY;
				}
				repaint();
			}
		});
	}

	protected void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.clearRect(0, 0, 200, 100);

		// 배경색 적용하기
		g2.setColor(color);
		g2.fillRect(0, 0, 200, 100);

		g2.setColor(Color.DARK_GRAY);
		g2.drawString(title, x+120, y+10); // 날짜 그리기
		
//		String s = content.toString();
//		StringBuffer temp = new StringBuffer();
//		int lineSize = 10;
//		for(int i=0;i<s.length();i++) {
//			temp.append(String.valueOf(s.charAt(i)));
//			if((i+1)%lineSize==0) {
//				System.out.println(temp.toString());
//				g2.drawString(temp.toString(), x-30, y+15 * ((i+1)/lineSize));
//				temp.setLength(0);
//			}
//		}
		
//		String s = content.toString() + "\n";
//		int count = StringUtil.getCountOfChar('\n', s);
//		String[] lines = s.toString().split("\n");
//		for(int i =0;i<count; i++) {
//			g2.drawString(content, x-30, y+25 * i);  // 내용 그리기
//		}

		g2.drawString(content, x-30, y+30); // 내용 그리기

	}
}

//public void del() {
//if (JOptionPane.showConfirmDialog(projectMainPage, "삭제하시겠습니까?") == JOptionPane.OK_OPTION) {
//	if(projectWorkDTO!=null) {
//		System.out.println("PK : " + projectWorkDTO.getProjectworkdiary_idx());
//		int result = projectWorkDAO.delete(projectWorkDTO.getProjectworkdiary_idx());
//		System.out.println("bt_del"+result);
//		setDateInfo(currentTitle, projectWorkDTO);
//		if (result > 0) { // 삭제 성공
//			JOptionPane.showMessageDialog(projectMainPage, "삭제완료");
//			// 목록 갱신
//			InnerWorkPage innerWorkPage = (InnerWorkPage) projectMainPage.projectInnerPage[projectMainPage.INNERWORKPAGE];
//			innerWorkPage.updateUI();
//		}
//	}
//}
//}
