package collection;

import java.util.HashSet;
import java.util.Iterator;

public class SetTest {
	public static void main(String[] args) {
//	순서 없는 컬렉션 자료형 중 Set을 학습해본다
		HashSet<String> set = new HashSet<String>(); // 최상위 객체인 interface Set을 직접적으로 사용하지 않고
		// 꺼낼 때 형변환 필요없음
		// 넣을 때 안전장치 필요없음
		set.add("딸기1");
		set.add("딸기2");
		set.add("딸기3");

//		순서 없는 것을 순서 있는 형태로 만들어주는 장치가 있음(Enumeration(구버전) / Iterator(신버전))
//		Iterator<E> iterator()
//		<E> 오브젝트인형이지만 용도가 Elements를 뽑아오는거임

//		boolean 	 hasNext() 	 Returns true if the iteration has more elements.
//		-> 있으면(true) 이동
//		E 	 next() 	 Returns the next element in the iteration.
//		-> 있든없든 앞으로 이동 
		
//		순서 없는 set을 Iterator로 나열해보자
		Iterator<String> it = set.iterator();
		while (it.hasNext()) { // 있으니까 true
			String fruit = it.next(); // 한 칸 이동
			System.out.println(fruit);
		}
	}
}
