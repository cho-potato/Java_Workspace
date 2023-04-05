package parse.excel;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class ExcelRead {
	public static void main(String[] args) {
		// 엑셀을 제어하기 위해서는 라이브러리가 필요할까요? 안할까요? 필요하다
		// 자바는 엑셀을 자체적으로 이해하지 못해서 다운로드 받아야 한다.
		// 엑셀파일을 불러오기 위해서는 HSSWorkbook이라는 클래스가 필요하다.

		// 엑셀파일>sheet>표>row> column

		// 먼저 파일에 대한 스트림을 만들자
		FileInputStream fis = null;
		String path = "D:/java_data/networkapp/data/product.xls";
		try {
			// 1. 엑셀파일에 접근하기
			fis = new FileInputStream(path);
			HSSFWorkbook book = new HSSFWorkbook(fis);
			// HSSFWorkbook 클래스는 엑셀파일을 읽어들일때 사용한다.

			// 2. sheet에 접근하기
			// sheet명으로 접근하는 방법도 있고 순서로 접근하는 방법도 있다.
			// 순서로 가기
			HSSFSheet sheet = book.getSheetAt(0); // 0번재 시트얻기

			// 3.표에 접근하기 : 표는 row가 모여있는 집합이므로 그냥 row부터 접근하면 된다.
			int lastRow = sheet.getLastRowNum();// 마지막 row num 반환
			System.out.println("총 레코드 수는 " + lastRow);
//			모든 레코드를 출력하기 위한 2중 for()문 작성
//			자바에서 엑셀 Cell에 접근할 때 주의할 점
//			그 Cell의 자료형이 문자일 경우와 숫자일 때 조심해야 함
//			문자일 때는 get
//			숫자일 때는 
//			ArrayList 준비
			ArrayList<Product> list = new ArrayList<Product>();
//			
			for (int a = 1; a <= lastRow; a++) {
//				총 레코드를 얻엇으니 row에 접근해본다
				HSSFRow row = sheet.getRow(a); // 이 row는 제일 꼭대기 row(컬럼명)
//				모든 컬럼에 접근하기 위해 반복문 작성
//				Empty 상태의 DTO 생성
				Product obj = new Product();
				for (int i = 0; i < 3; i++) { // 0, 1, 2번쨰 컬럼에 접근
//					column에 접근하기
//					결국 0번째 컬럼은 product_name이므로 문자 
//					1번 째 컬럼은 brand이므로 문자
//					2번째 컬럼은 price이므로 숫자
//					따라서 조건의 대상인 숫자는switch~case문으로 작성
					HSSFCell cell = row.getCell(i);
//					여기에서 얻어온 Cell이 숫자인지 문자인지 판단
					switch (i) { // i의 값이 0일 때 문자, 1일 때 문자, 2일 땐 숫자임(엑셀에서)
//					0일 때는 제품 명이 들어가야 하므로 getStringCellValue()
					case 0: obj.setProduct_name(cell.getStringCellValue()); break;
//					1일 때는 브랜드가 들어가야 하므로 getStringCellValue()
					case 1 : obj.setBrand(cell.getStringCellValue()); break;
//					2일 때는 price가 들어가야 하므로 getNumericCellValue()
//					주의 : 메서드가 double을 반환하므로 int형으로 변경
					case 2 : obj.setPrice((int)cell.getNumericCellValue()); break;
					}
//					switch문이 한 번씩 수행될 때마다 하나의 레코드가 수행된 것이므로
//					이 떄 채워진 DTO를 ArrayList에 담아야 한다
//					엑셀의 컬럼값이 문자열인 경우 아래의 메서드로 접근
//					System.out.println(cell.getRichStringCellValue());
				}
//				안쪽 for()문이 끝나는 곳에서 list에 담는다(=층수와 연관되므로 바깥 for()문의 영역에서 담는다)
				list.add(obj);
			} // 바깥 for()문 끝나는 곳에서 ArrayList 출력
			for(Product product : list) {
				System.out.println(product.getProduct_name()+", "+product.getBrand()+", "+product.getPrice());
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fis!=null) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}
}