package management;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

public class NoticeRow extends JPanel{
	int noticeNo; // 게시물 순번
	ProjectNoticeBoardDTO projectNoticeBoardDTO;
	InnerNoticePage innerNoticePage;
	
	JLabel innerNoticePageNumLabel;
	JLabel innerNoticePageTitleLabel;
	JLabel innerNoticePageWriterLabel;
	JLabel innerNoticePageRegdateLabel;
	JLabel innerNoticePageHitLabel;
	
	public NoticeRow(int no, ProjectNoticeBoardDTO projectNoticeBoardDTO, InnerNoticePage innerNoticePage) {
		this.projectNoticeBoardDTO = projectNoticeBoardDTO;
		this.innerNoticePage = innerNoticePage;
		
//		Row 테두리
		Border border = new LineBorder(Color.GRAY);
		this.setBorder(border);
		this.setPreferredSize(new Dimension(1400, 50));
		
		innerNoticePageNumLabel = new JLabel(Integer.toString(no));
		innerNoticePageNumLabel.setPreferredSize(new Dimension(30, 50));
		innerNoticePageTitleLabel = new JLabel(projectNoticeBoardDTO.getTitle());
		innerNoticePageTitleLabel.setPreferredSize(new Dimension(400, 50));
		innerNoticePageWriterLabel = new JLabel(projectNoticeBoardDTO.getWriter());
		innerNoticePageWriterLabel.setPreferredSize(new Dimension(120, 50));
		innerNoticePageRegdateLabel = new JLabel(projectNoticeBoardDTO.getRegdate());
		innerNoticePageRegdateLabel.setPreferredSize(new Dimension(150, 50));
		innerNoticePageHitLabel = new JLabel(Integer.toString(projectNoticeBoardDTO.getHit()));
		innerNoticePageHitLabel.setPreferredSize(new Dimension(30, 50));
		
		add(innerNoticePageNumLabel);
		add(innerNoticePageTitleLabel);
		add(innerNoticePageWriterLabel);
		add(innerNoticePageRegdateLabel);
		add(innerNoticePageHitLabel);
		
//		제목 라벨과 리스너 연결
		innerNoticePageTitleLabel.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
//				디테일 DB
				NoticePageDetail noticePageDetail = (NoticePageDetail) innerNoticePage.projectMainPage.projectInnerPage[ProjectMainPage.NOTICEPAGEDETAIL];
				noticePageDetail.getDetail(projectNoticeBoardDTO.getProjectNoticeBoard_idx());
//				디테일 Page
				innerNoticePage.projectMainPage.mainPageShowHide(ProjectMainPage.NOTICEPAGEDETAIL);
			}
			public void mouseEntered(MouseEvent e) {
				int index = innerNoticePage.rows.indexOf(NoticeRow.this);
				innerNoticePage.showLight(index);
			}
		});
	}
}
