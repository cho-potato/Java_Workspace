package excel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

// 

public class DumpApp extends JFrame {

//	서쪽 영역
	JPanel p_west;
	JLabel la_filename;
	JButton bt_find; // 파일 탐색기 호출
	JButton bt_regist; // 등록버튼

//	센터영역
	JPanel p_content; // 상품 리스트 Panel들이 부착될 컨테이너 영역
//	그리고 상품 한 개를 표현할 JPanel은 커스텀으로(클래스 정의, ProductItem)
	JScrollPane scroll; // 상품 리스트의 스크롤 처리
	JFileChooser chooser;
	File file; // 유저가 선택한 엑셀파일

//	멤버변수에 메인 쓰레드 대신 고생할 개발자 정의 쓰레드 선언
	Thread thread;

//	생성자 정의하고 메모리에 올리기
	public DumpApp() {
//		서쪽 영역
		p_west = new JPanel();
		la_filename = new JLabel("여기에 파일명");
		bt_find = new JButton("파일찾기"); // 파일 탐색기 호출
		bt_regist = new JButton("파일등록"); // 등록버튼

//		츄저 메모리에 올리기
		chooser = new JFileChooser("D:/java_data/networkapp/data/");

//		여기서 쓰레드 생성
		thread = new Thread() {
			public void run() {
				regist();
			}
		};
		p_west.add(la_filename);
		p_west.add(bt_find);
		p_west.add(bt_regist);

//		센터영역
		p_content = new JPanel();
		scroll = new JScrollPane(p_content);

//		스타일
		p_west.setPreferredSize(new Dimension(150, 700));
//		옆으로 퍼져나가는걸 아래로 출력하기
		p_content.setPreferredSize(new Dimension(700, 1000));

//		스크롤 크기를 지정해야 컨텐츠가 넘쳐날 때 스크롤 생김
		scroll.setPreferredSize(new Dimension(750, 700));

//		조립
		add(p_west, BorderLayout.WEST);
		add(scroll);

		setSize(900, 700);
		setVisible(true);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

//		getProducList();

		bt_find.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				findExcel(); // 덤프로 올려질 엑셀 선택
			}
		});
//		등록버튼 누르면 쓰레드 시작
		bt_regist.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				thread.start();
			}
		});
	}

	public void findExcel() {
		int result = chooser.showOpenDialog(this);
		if (result == JFileChooser.APPROVE_OPTION) {// OK버튼 누르면
//			유저가 선택한 엑셀파일에대한 스트림을 생성하여 파싱한다
//			파싱한 결과 내에서 이미지 경로를 웹으로 추적하여 이미지도 다운로드 받는다
			file = chooser.getSelectedFile();
		}
	}

// 엑셀을 Parsing하여 ArrayList를 반환해주는 메서드, 따라서 반환형을 둔다
	public List parseExcel() {
		ArrayList<Product> list = new ArrayList<Product>(); // 반환해야하니 try문 밖에 선언

//		엑셀 파일에 스트림 생성
		FileInputStream fis = null; // finally에서 닫기 위해 예외처리 블럭 밖에 선언
		try {
			fis = new FileInputStream(file); // 유저가 선택한 바로 그 파일(멤버변수로 빼두었따)
//			엑셀 파일에 접근하기
			HSSFWorkbook book = null; // 신버전의 경우 HSSF가 아닌 XSSF로 시작
			book = new HSSFWorkbook(fis);
//			엑셀에 접근했으니 Sheet에 접근
			HSSFSheet sheet = book.getSheetAt(0);
//			row와 cell에 접근하여 ArrayList 만들기(2중 반복문)
			int lastRow = sheet.getLastRowNum();

//			반복문 미리 정의
			for (int i = 1; i <= lastRow; i++) { // 층수는 1부터(제목제외)
				Product product = new Product(); // row 수 만큼 empty DTO 생성
				HSSFRow row = sheet.getRow(i);
				for (int a = 0; a < 4; a++) { // 컬럼은 길이가 정해져 있음
//					a의 값이 컬럼에 따라 숫자인지 문자인지 판별하여 DTO에 담기
					HSSFCell cell = row.getCell(a); // 여기서 셀 받고 가기
					switch (a) {
					case 0:
						product.setProduct_name(cell.getStringCellValue());
						break;
					case 1:
						product.setBrand(cell.getStringCellValue());
						break;
					case 2:
						product.setPrice((int) cell.getNumericCellValue());
						break;
					case 3:
						product.setFileurl(cell.getStringCellValue());
						break;
					}
				}
//				안 쪽 반복문에 의해 레코드 1건을 얻어오고, 그 레코드 1건을 DTO인스턴스 한에 담았음
//				담아진 DTO를 ArrayList에 가져오기
				list.add(product);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return list;
	}
//	ArrayList를 사용해야 하므로 매개변수로 받는다
	public void collectImage(List<Product> list) { // 매개변수와 함께 호출
//		file URL을 뽑아내서 Stream으로 수집하기
//		상품 수만큼 수집해야 하므로 
		for(int i = 0; i<list.size(); i++) {
			Product product = list.get(i); // Product DTO
//			URL 추출하기
			String fileurl= product.getFileurl();
//			로컬 패키지경로에 접근하는 것이 아니라 인터넷 상 자원에 접근해야 하므로
//			URL클래스의 인스턴스를 생성해야 함
//			this.getClass().getClassLoader() 이거 아님!!!!
			FileOutputStream fos = null;
			InputStream is = null;
//			파일 확장자는 fileurl로부터 구함 
			String ext = StringUtil.getExtend(fileurl);
//			파일명은 현재 시스템 시간으로 대체
			long time = System.currentTimeMillis();
			try {
				URL url = new URL(fileurl);
//				여기까지가 주소를 얻어온 것이고 이 주소 객체를 이용하여 스트림 생성
//				URL객체의 메서드 중 openStream() 메서드, 앞에 InputStream
				String dir = "D:/java_data/networkapp/data/";
				fos = new FileOutputStream(dir+time+"."+ext); // 디렉토리 경로도 엑셀파일과 같은 위치로
				product.setFileurl(dir+time+"."+ext); // 여기서 로컬 경로로 바뀜
				is = url.openStream(); // 반환형이 InputStream
				
//				이미지를 수집한 후 FileOutputStream
//				반복문
				int data = -1;
				while(true) {
					data = is.read(); // 1 byte읽기-> 속도를 높이기 위해 Buffer로 갈 수 있지만 로컬이 아닌 웹 상 파일이라 속도에 문제가 있을 수 있기 때문에 파일 복원이 안될 수 있음 따라서 그냥 1바이트로 ㄱ
					if(data== -1) break; // 모두 읽으면 빠져 나오기
//					break문을 만나지 않은 이 영역에서 파일 출력하기
//					-> Empty 파일을 생성해주는 FileOutputStream 필요
					fos.write(data);
				}
//				JOptionPane.showMessageDialog(this, "파일수집완료");
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if(fos !=null) {
					try {
						fos.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if(is !=null) {
					try {
						is.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			
		}
		
	}

//	등록하기
	public void regist() {
//		등록에 필요한 과정
//		1. 유저가 선택한 파일을 대상으로 스트림 얻어와 엑셀파일 접근 / 해석
//		-> 해석된 결과는 ArrayList가 될 예정
//		2. 엑셀 Parsing 후 이미지 경로를 이용하여 이미지 수집하기
//		위 두 과정을 하나로 합치면 복잡하므로 Parsing하는 과정을 메서드로 빼냄(=parseExcel)
//		parseExcel()호출하여 ArrayList 완성 확인
		List productList = parseExcel();
		System.out.println("분석 결과 반환된 상품 수는 "+productList.size());
//		이미지 수집하기(별도 메서드 구성=collectImage())
//		2. 엑셀 Parsing 후 이미지 경로를 이용하여 이미지 수집하기
		collectImage(productList);
//		엑셀의 레코드 수가 5이므로 try문에서 count 
//		count가 5에 도달하지 못하면 미완료로 판단, 5에 도달하면 완료로 처리(알아서)
		
//		collectImage 메서드 수행이 끝나면 아래 코드가 나오게
//		JOptionPane.showMessageDialog(this, "파일수집완료");
		
//		Image객체가 ProductItem 패널 안에 각각 있으므로 그 패널들에 Image 인스턴스를 전달
//		-> 생성자에서 호출했던 getProductList() 호출 주석 처리 후 여기서 호출
		getProductList(productList);
	}

//	상품리스트 생성하기
//	매서드의 매개변수로 위의 regist()에서 생성되엇던 productList 전달 받음
//	productList는 메서드와 메서드 호출시 계속 주소값을 전달하고 있기 때문에 멤버변수로 빼주지 않아도 살아있음
	public void getProductList(List<Product> productList) {
//		커스텀 패널을 10개 생성하여 content에 부착하기
//		
		for (int i = 0; i < productList.size(); i++) {
			
//			Product product = new Product();
			Product product = productList.get(i);
//			이미 데이터가 들어있는상태이므로 임시 데이터는 사용하지 않는다
//			product.setProduct_name("샤넬ㄱㅂ");
//			product.setBrand("샤넬");
//			product.setPrice(50000000);
//			로컬 이미지 경로를 이용하여 이미지 생성
			ProductItem item = new ProductItem(product); // DTO넣기
//			
//			content에 부착하기
			p_content.add(item);

		}
//		반복문이 끝나면 새로고침, 이 메서드를 생성자에서 호출
//		추후에는 생성자가 아닌 엑셀 등록이 완료되고 파싱 완료될 때 호출
		p_content.updateUI();
	}

	public static void main(String[] args) {
		new DumpApp();
	}
}
