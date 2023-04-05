

/* 순서 없는 컬렉션 중 Map을 학습해보자 */

package collection;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class MapTest {

	public static void main(String[] args) {
//		Kisses 초콜릿을 연상하자 // 라벨 : Key 초콜릿 몸체 : Value\
//		Kety-String, Value-String
		HashMap<String, String> map = new HashMap<String, String> (); // Interface인 Map은 최상위 객체이므로 직접 사용하지 않고, 그 자식인 HashMap을 쓴다
		
		map.put("Strawbeyrry", "딸기1"); // Key 값에 무엇을 주든 자유지만, 중복되면 사라진다 
		map.put("Blueberry", "딸기2"); 
		map.put("Cherry", "딸기3"); 
		
//		순서 없는 것을 순서있게 처리하여 반복문 사용해보기
//		Set<K>		keySet()		Returns a Set view of the keys contained in this map. 
//		Set으로 가서 Iterator를 가져온다음 재조회하고 Key값만 가져온다(Key값만 가져오면 몸체는 딸려온다)
		
//		본체가 아닌 Key만 모았다(순서없게)
//		Key를 수집
		Set<String> set = map.keySet(); // <String> 부븐은 필수가 아니라서 작성하지 않아도 되지만, 작성하지 않았을 때는 Object로 받기 때문에 어차피 형변환 할 거 미리 하는게 낫다
		
//		key값을 늘어뜨리자 (순서 있게)
		Iterator<String> it = set.iterator();
		
		while(it.hasNext()) {
			String key = it.next();
			System.out.println(key);
			
			String value = map.get(key);
			System.out.println(value);
		}
		
		
		
		
	}
	
}
