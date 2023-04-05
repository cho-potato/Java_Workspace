package parse.xml;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

/*자바언어에서 xml 파싱하는 방법*/
//	1) DOM Parsing
//		- 임베디드 분야에서 극혐하는 방법. 
//		- 태그마다 일대일 대응하는 객체를 메모리에 생성하므로, 요즘 같은 스파트폰이 활성화 된 개발분야에서는 잘 쓰지 않는다
//	2) SAX Parsing
//		- 발견되는 태그마다 이벤트를 발생시켜주는 파싱방식
//		- JAVA에서 별도로 다운받을 필요 없다(서로 친함)

public class XMLApp {
	public static void main(String[] args) {
//		GOF의 패턴 중 팩토리 패턴으로 생성
		SAXParserFactory factory = SAXParserFactory.newInstance();
		try {
			SAXParser saxParser = factory.newSAXParser();
			String path = "C:/java_workspace2/NetworkApp/res/food.xml";
			saxParser.parse(new File(path), new MyHandler());
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
}
