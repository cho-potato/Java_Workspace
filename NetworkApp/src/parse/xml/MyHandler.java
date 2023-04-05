package parse.xml;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

// parsing 대상이 되는 XML을 분석하여
// 각 노드마다 알맞는 이벤트를 발생시켜
// 개발자로 하여금 적절한 처리를 할 수 있는 기회를 줌

public class MyHandler extends DefaultHandler {
	ArrayList<Food> list;
	Food food;
//	현재 실행부가 어떤 태그를 지나는지 표시
	boolean isName = false;
	boolean isPrice = false;

	public void startDocument() throws SAXException {
		System.out.println("문서시작했어요");
	}

	public void startElement(String uri, String localName, String tag, Attributes attributes) throws SAXException {
		System.out.println("<" + tag + ">");

//		foodList를 만나면 ArrayList를 생성하여 DTO를 담을 준비하자
		if (tag.equals("foodList")) {
			list = new ArrayList<Food>();
		} else if (tag.equals("food")) { // food 태그를 만나면 DTO 생성
			food = new Food();
		} else if (tag.equals("name")) {
			isName = true;
		} else if (tag.equals("price")) {
			isPrice = true;
		}
	}

//	태그와 태그 사이 내용이 되는 노드.발견할 때 호출
//	characters : 내용만 만나면 무조건 동작하기 때문에 어느 항목에 해당해서 동작하는건지 알 수 없음
	public void characters(char[] ch, int start, int length) throws SAXException {
		String content = new String(ch, start, length);
//		DTO에 값을 채우되, 현재 true인 변수만 
		if (isName) {
			food.setName(content);
		} else if (isPrice) {
			food.setPrice(Integer.parseInt(content));
		}

//		System.out.println(content);
//		food.setName(content);
//		food.setPrice(Integer.parseInt(content));
	}

	public void endElement(String uri, String localName, String tag) throws SAXException {
		System.out.println("</" + tag + ">");
		if (tag.equals("name")) {
			isName = false;
		} else if (tag.equals("price")) {
			isPrice = false;
		} else if (tag.equals("food")) {
//			하나의 음식이 완성된 시점이므로 list에 추가
			list.add(food);
		}
	}

	public void endDocument() throws SAXException {
		System.out.println("담겨진 음식 수는"+list.size());
		for (int i = 0; i<list.size(); i++) {
			Food food = list.get(i);
			System.out.println("food명= "+food.getName()+", 가격= "+food.getPrice());
		}
//		System.out.println(list);

	}
}
