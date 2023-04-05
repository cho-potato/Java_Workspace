package management;

import javax.swing.JPanel;

public class ProjectSalesCell extends JPanel {
	String title; // 날짜
	String content; // 내용
	int fontSize;
	int x;
	int y;

	public ProjectSalesCell(String title, String content, int fontSize, int x, int y) {
		this.title = title;
		this.content = content;
		this.fontSize = fontSize;
		this.x = x;
		this.y = y;
		
	}
}
//// 테이블에 마우스 리스너 연결
//table.addMouseListener(new MouseAdapter() {
//	public void mouseClicked(MouseEvent e) {
//		// 클릭시 상세보기 페이지 보여주기
//		appMain.showHide(appMain.BOOKDETAIL);
//		
//		int row = table.getSelectedRow();
//		Book book = model.bookList.get(row);
//		BookDetail bookDetail = (BookDetail)appMain.page[AppMain.BOOKDETAIL];
//		bookDetail.getDetail(book.getBook_idx());
//	}
//	
//});

//bt_delete
//		bt_del.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				if (JOptionPane.showConfirmDialog(appMain, "삭제하시겠습니까?") == JOptionPane.OK_OPTION) {
//					int result = appMain.bookDAO.delete(book.getBook_idx());
//
//					if (result > 0) { // 삭제 성공
//						JOptionPane.showMessageDialog(appMain, "삭제완료");
//
//						// 목록 갱신
//						BookPage bookPage = (BookPage) appMain.page[AppMain.BOOKPAGE];
//						bookPage.getList();
//
//						appMain.showHide(AppMain.BOOKPAGE);// 화면전환
//					}
//				}
//			}
//		});
