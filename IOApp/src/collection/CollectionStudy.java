
/*
 
 sun에서는 이 세상의 모든 객체들이 모여있는 모습을 크게 두 가지로 구분

 1) 순서있는 집합
	ㅁ List 유형
		- 배열과 거의 같지만 차이가 있다
			1) 자바에서의 배열은 기본 자료형, 객체 자료형 등 모든 자료형을 대상으로 하지만 List는 오직 객체만을 받는다
			2) 자바의 배열은 생성시 그 크기를 명시해야 하므로 크기가 고정되어 있지만, 컬렉션들은 Java Script처럼 크기가 동적으로 변할 수 있다(유연하다)
	
 2) 순서없는 집합
	ㅁ Set 유형
  	ㅁ Map 유형 ( Key를 꽂아 Value를 뽑아내는 구조) 
 
  Collection Framework ?
  - 객체를 모아서 처리할 때 유용한 api들
  
 */

package collection;

import java.awt.List;
import java.util.ArrayList;

import javax.swing.JButton;

public class CollectionStudy {
	public static void main(String[] args) {
//		List를 사용해본다
//		List는 Collection을 부모로 갖고 있기는 하지만 그 자체로 최상위 객체이기 때문에 자식들도 많음
//		순서있는 집합을 처리할 때 사용하는 대표적인 자료형!
//		웹개발시 압도적으로 많이 씀
//		Generic 타입 : 컬렉션에서 해당 컬렉션 객체의 자료형을 특정 자료형만으로 한정지을 수 있는 방법
//		1)객체가 섞여들어가지 않도록 컴파일 타임에 방지한다
//		2) 꺼낼 때 형변환 과정이 필요없다
//		ArrayList list = new ArrayList();  // List가 자식 = ArryaList가 부모라 성립하는 식
		ArrayList<String> list = new ArrayList<String>();
		list.add("apple");
		list.add("딸기");
		list.add("바나나");
//		list.add(new JButton(""));
		
		System.out.println(list.size()); // List에서의 size는 배열에서의 lenghth와 같다
//		for(int i = 0; i<list.size(); i++) {
//			Object obj = list.get(i); // apple, 딸기, 바나나가 자식인 String형이라 형변환을 하든 안하든 같다
//		}
//		for (int i = 0; i < list.size(); i++) {
//			Object obj = (String)list.get(i); // apple, 딸기, 바나나가 자식인 String형이라 형변환을 하든 안하든 같다
//			System.out.println(obj);
//		}
//		for (int i = 0; i < list.size(); i++) {
//			Object obj = list.get(i); // Generic 썼기 때문에  get이 String으로 받음
//			System.out.println(obj);
//		}
//		java vava 5버전부터 improved for문이 추가됨 
//		객체를 대상으로 한 반복문을 좀 더 간결하게
//		for(Object fruit :list) {
		for(String fruit : list) {
			System.out.println(fruit);
		}
	}
}
