package management;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import network.util.DBManager;

public class ProjectMainPage extends ProjectPage {
	JPanel mainPageBackPanel; // 밑 판
	
	JPanel mainPageNorth; // 북
	JPanel mainPageNorthNull;
	JPanel mainPageNorthPanel1; // 로그아웃 패널
	JPanel mainPageNorthPanel2; // 페이지 전환 버튼 패널
	JPanel mainPageNorthPanel3; // 페이지 전환 버튼 패널
	ImageIcon northButtonIcon; // 버튼에 들어갈 이미지
	JButton mainPageNorthButton; // 이미지 들어갈  버튼
	
	JButton northPanel1LogOut;
	JLabel northPanel1Account;
	JButton northPanel2Master, northPanel2Receive, northPanel2Sales, northPanel2Stock, northPanel2Work;
	ImageIcon selectIcon, excelIcon, closeIcon;
	JButton northPanel3Select, northPanel3Excel, northPanel3Close;	
	JLabel moveRight1, moveRight2, moveRight3;
	
	JPanel mainPageContainer; // 담고 있을 통
	JPanel mainPageContainerNorth;
	JPanel mainPageContainerCenter;
	JPanel mainPageContainerEast;
	
	ProjectInnerPage[] projectInnerPage = new ProjectInnerPage[7];
	MenuButton[] menuButton = new MenuButton[5];
	ProjectDateCheck projectDateCheck = new ProjectDateCheck();
	ProjectMemberDTO projectMemberDTO = new ProjectMemberDTO();
	public static final int INNERNOTICEPAGE = 0;
	public static final int NOTICEPAGEREGIST = 1;
	public static final int NOTICEPAGEDETAIL = 2;
	public static final int INNERRECEIVEPAGE = 3;
	public static final int INNERSALESPAGE = 4;
	public static final int INNERSTOCKPAGE = 5;
	public static final int INNERWORKPAGE = 6;
	
	DBManager dbManager = DBManager.getInstance();
	ProjectNoticeBoardDAO projectNoticeBoardDAO = new ProjectNoticeBoardDAO();
	ProjectWorkDAO projectWorkDAO = new ProjectWorkDAO();
	
	public ProjectMainPage(ProjectClientMain projectClientMain) {
		super(projectClientMain);
		
		mainPageBackPanel = new JPanel();
		
		mainPageNorth = new JPanel();
		mainPageNorthNull = new JPanel();
		mainPageNorthPanel1 = new JPanel(); 
		mainPageNorthPanel2 = new JPanel(); 
		mainPageNorthPanel3 = new JPanel(); 
		northButtonIcon = new ImageIcon("src/res/image/mainwest.jpg");
		mainPageNorthButton = new JButton(northButtonIcon);
		
		northPanel1LogOut = new JButton("로그아웃");
		
//		northPanel2Master = new JButton("마스터");
//		northPanel2Receive = new JButton("수주관리");
//		northPanel2Sales = new JButton("매출관리");
//		northPanel2Stock = new JButton("재고관리");
//		northPanel2Work = new JButton("근태관리");
		
//		selectIcon = new ImageIcon("src/res/image/select.png");
//		northPanel3Select = new JButton(selectIcon);
		northPanel3Select = new JButton("조 회");
//		excelIcon = new ImageIcon("src/res/image/excel.png");
//		northPanel3Excel = new JButton(excelIcon);
		northPanel3Excel = new JButton("엑 셀");
//		closeIcon = new ImageIcon("src/res/image/close.png");
//		northPanel3Close = new JButton(closeIcon);
		northPanel3Close = new JButton("닫 기");
		
		northPanel1Account = new JLabel("감자창고님");

		moveRight1 = new JLabel();
		moveRight2 = new JLabel();
		moveRight3 = new JLabel();
		
		mainPageContainer = new JPanel();
		mainPageContainerNorth = new JPanel();
		mainPageContainerCenter = new JPanel();
		mainPageContainerEast = new JPanel();
		
//		add + style + layout
		add(mainPageBackPanel);
		
		mainPageBackPanel.setLayout(new BorderLayout());
		mainPageBackPanel.setBackground(Color.WHITE);
		
		mainPageBackPanel.add(mainPageNorth, BorderLayout.NORTH);
		mainPageBackPanel.add(mainPageContainer);
		
		mainPageNorth.setLayout(new BorderLayout());
		mainPageNorth.setPreferredSize(new Dimension(1920, 150));
		mainPageNorth.setBackground(Color.WHITE);
		
		mainPageNorth.add(mainPageNorthNull);
		
		mainPageNorthNull.setPreferredSize(new Dimension(1920, 150));
		mainPageNorthNull.setBackground(Color.WHITE);
		mainPageNorthNull.setLayout(null);
		
		mainPageNorthNull.add(mainPageNorthButton);
		mainPageNorthButton.setBounds(0, 0, 300, 150);
		mainPageNorthButton.setBorderPainted(false);
		mainPageNorthButton.setFocusPainted(false);
		
		mainPageNorthNull.add(mainPageNorthPanel1);
		mainPageNorthPanel1.setBounds(300, 0, 1620, 50);
		mainPageNorthPanel1.setBackground(Color.GRAY);
		mainPageNorthNull.add(mainPageNorthPanel2);
		mainPageNorthPanel2.setBounds(300, 50, 1620, 50);
		mainPageNorthPanel2.setBackground(Color.LIGHT_GRAY);
		mainPageNorthNull.add(mainPageNorthPanel3);
		mainPageNorthPanel3.setBounds(300, 100, 1620, 50);
		mainPageNorthPanel3.setBackground(Color.WHITE);
		
		mainPageNorthPanel1.add(moveRight1);
		moveRight1.setPreferredSize(new Dimension(1400, 0));
		
		mainPageNorthPanel1.add(northPanel1Account);
		northPanel1Account.setPreferredSize(new Dimension(80, 40));
		northPanel1Account.setFont(new Font("고딕", Font.BOLD, 15));
		mainPageNorthPanel1.add(northPanel1LogOut);
		northPanel1LogOut.setPreferredSize(new Dimension(100, 40));
		northPanel1LogOut.setBackground(Color.LIGHT_GRAY);
		northPanel1LogOut.setFont(new Font("고딕", Font.BOLD, 15));
		northPanel1LogOut.setBorderPainted(false);
		northPanel1LogOut.setFocusPainted(false);
				
		mainPageNorthPanel2.add(moveRight2);
		moveRight2.setPreferredSize(new Dimension(1065, 0));
		
//		mainPageNorthPanel2.add(northPanel2Master);
//		northPanel2Master.setPreferredSize(new Dimension(100, 40));
//		northPanel2Master.setBackground(Color.WHITE);
//		northPanel2Master.setFont(new Font("고딕", Font.BOLD, 15));
//		northPanel2Master.setBorderPainted(false);
//		northPanel2Master.setFocusPainted(false);
//		
//		mainPageNorthPanel2.add(northPanel2Receive);
//		northPanel2Receive.setPreferredSize(new Dimension(100, 40));
//		northPanel2Receive.setBackground(Color.WHITE);
//		northPanel2Receive.setFont(new Font("고딕", Font.BOLD, 15));
//		northPanel2Receive.setBorderPainted(false);
//		northPanel2Receive.setFocusPainted(false);
//		
//		mainPageNorthPanel2.add(northPanel2Sales);
//		northPanel2Sales.setPreferredSize(new Dimension(100, 40));
//		northPanel2Sales.setBackground(Color.WHITE);
//		northPanel2Sales.setFont(new Font("고딕", Font.BOLD, 15));
//		northPanel2Sales.setBorderPainted(false);
//		northPanel2Sales.setFocusPainted(false);
//		
//		mainPageNorthPanel2.add(northPanel2Stock);
//		northPanel2Stock.setPreferredSize(new Dimension(100, 40));
//		northPanel2Stock.setBackground(Color.WHITE);
//		northPanel2Stock.setFont(new Font("고딕", Font.BOLD, 15));
//		northPanel2Stock.setBorderPainted(false);
//		northPanel2Stock.setFocusPainted(false);
//		
//		mainPageNorthPanel2.add(northPanel2Work);
//		northPanel2Work.setPreferredSize(new Dimension(100, 40));
//		northPanel2Work.setBackground(Color.WHITE);
//		northPanel2Work.setFont(new Font("고딕", Font.BOLD, 15));
//		northPanel2Work.setBorderPainted(false);
//		northPanel2Work.setFocusPainted(false);
		
		mainPageNorthPanel3.add(moveRight3);
		moveRight3.setPreferredSize(new Dimension(1365, 0));
		
		mainPageNorthPanel3.add(northPanel3Select);
//		northPanel3Select.setPreferredSize(new Dimension(60, 40));
		northPanel3Select.setPreferredSize(new Dimension(70, 40));
		northPanel3Select.setBackground(Color.RED);
		northPanel3Select.setForeground(Color.WHITE);
		northPanel3Select.setFont(new Font("고딕", Font.BOLD, 15));
		northPanel3Select.setBorderPainted(false);
		northPanel3Select.setFocusPainted(false);
		
		mainPageNorthPanel3.add(northPanel3Excel);
//		northPanel3Excel.setPreferredSize(new Dimension(60, 40));
		northPanel3Excel.setPreferredSize(new Dimension(70, 40));
		northPanel3Excel.setBackground(Color.RED);
		northPanel3Excel.setForeground(Color.WHITE);
		northPanel3Excel.setFont(new Font("고딕", Font.BOLD, 15));
		northPanel3Excel.setBorderPainted(false);
		northPanel3Excel.setFocusPainted(false);
		
		mainPageNorthPanel3.add(northPanel3Close);
//		northPanel3Close.setPreferredSize(new Dimension(60, 40));
		northPanel3Close.setPreferredSize(new Dimension(70, 40));
		northPanel3Close.setBackground(Color.RED);
		northPanel3Close.setForeground(Color.WHITE);
		northPanel3Close.setFont(new Font("고딕", Font.BOLD, 15));
		northPanel3Close.setBorderPainted(false);
		northPanel3Close.setFocusPainted(false);
		
		mainPageContainer.setLayout(new BorderLayout());
		mainPageContainer.setPreferredSize(new Dimension(1920, 850));
		mainPageContainer.setBackground(Color.BLACK);
		
		mainPageContainer.add(mainPageContainerNorth, BorderLayout.NORTH);
		mainPageContainerNorth.setPreferredSize(new Dimension(1920, 100));
		mainPageContainerNorth.setBackground(Color.LIGHT_GRAY);
		mainPageContainer.add(mainPageContainerCenter);
		mainPageContainerCenter.setPreferredSize(new Dimension(1920, 700));
		mainPageContainerCenter.setBackground(Color.LIGHT_GRAY);
//		mainPageContainer.add(mainPageContainerEast, BorderLayout.EAST);
//		mainPageContainerEast.setPreferredSize(new Dimension(650, 700));
//		mainPageContainerEast.setBackground(Color.LIGHT_GRAY);
		
		createPage();
		createMenu();
		mainPageShowHide(INNERNOTICEPAGE);
		
		northPanel1LogOut.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if(JOptionPane.showConfirmDialog(projectClientMain, "로그아웃 하시겠습니까 정녕?")==JOptionPane.OK_OPTION) {
					JOptionPane.showMessageDialog(projectClientMain, projectClientMain.projectMemberDTO.getId() + " 님 " + projectDateCheck.formatedNow + " 에 로그아웃 하셨습니다");
					projectClientMain.projectShowHide(projectClientMain.PROJECTLOGINPAGE);
				}
			}	
		});
	}
	public void createPage() {
		projectInnerPage[0] = new InnerNoticePage(this);
		projectInnerPage[1] = new NoticePageRegist(this);
		projectInnerPage[2] = new NoticePageDetail(this);
		projectInnerPage[3] = new InnerReceivePage(this);
		projectInnerPage[4] = new InnerSalesPage(this);
		projectInnerPage[5] = new InnerStockPage(this);
		projectInnerPage[6] = new InnerWorkPage(this);

//		중앙 패널에 부착
		for(int i = 0; i<projectInnerPage.length; i++) {
			mainPageContainerCenter.add(projectInnerPage[i]);
		}
	}
	public void createMenu() {
		menuButton[0] = new MenuButton("공지사항", this, INNERNOTICEPAGE);
		menuButton[1] = new MenuButton("수주관리", this, INNERRECEIVEPAGE);
		menuButton[2] = new MenuButton("매출관리", this, INNERSALESPAGE);
		menuButton[3] = new MenuButton("재고관리", this, INNERSTOCKPAGE);
		menuButton[4] = new MenuButton("근태관리", this, INNERWORKPAGE);
		
		for(int i = 0; i<menuButton.length; i++) {
			mainPageNorthPanel2.add(menuButton[i]);
			menuButton[i].setPreferredSize(new Dimension(100, 40));
		}
		mainPageNorthPanel2.updateUI();
	}
	public void mainPageShowHide(int n) {
		for (int i=0; i<projectInnerPage.length; i++) {
			if(i == n) {
				projectInnerPage[i].setVisible(true);
			} else {
				projectInnerPage[i].setVisible(false);
			}
		}
	}
}
