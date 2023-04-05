package parse.json;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

//

public class JsonApp {
//	JSON은 프로그래밍 언어가 아니라 단지 문자열에 불과하다
	public static void main(String[] args) {
//		JAVA는 JSON을 인식하지 못하지 때문에 문자열로 감싸주어야 한다
//		반려동물을 2마리 보유한 29살 철수
		StringBuffer sb = new StringBuffer();
//		{
//		"name": "철수",
//		"age": 29,
//		"hasPet": true,
//		"pets": [{
//			"name": "원조",
//			"type": "고양이"
//		}, {
//			"name": "마찌",
//			"type": "고양이"
//		}]
//	}
//		JSON 표기법은 Map이다 !
		sb.append("{");
		sb.append("\"name\" : \"철수\",");
		sb.append("\"age\" : 29,");
		sb.append("\"hasPet\" : true,");
		sb.append("\"pets\" : [");
		sb.append("{");
		sb.append("\"name\" : \"원조\", ");
		sb.append("\"type\" : \"고양이\"");
		sb.append("},");
		sb.append("{");
		sb.append("\"name\" : \"마찌\",");
		sb.append("\"type\" : \"고양이\"}]");
		sb.append("}");
		System.out.println(sb.toString()); // "JSON 문자열" 출력
//		위의 문자열을 실제 객체처럼 사용하려면 문자열을 객체화시키는 과정(해석)이 필요한데,
//		프로그래밍 분야에서는 이러한 과정을 "parsing한다"라고 표현한다
//		parsing하세요 = 해석(객체화)하세요
		JSONParser jsonParser = new JSONParser();
		
//		parsing하고 나면 문자열에 불과했던 데이터를 실제 객체로 반환해준다
		try {
			Object obj = jsonParser.parse(sb.toString());
//			Object가 최상위가 갖고 있는 것이 많지 않음. 따라서 형변환 필요
			
			JSONObject json = (JSONObject)obj;
			System.out.println(json.get("name"));
			System.out.println(json.get("age"));
			System.out.println(json.get("hasPet"));
			
			JSONArray array = (JSONArray)json.get("pets");
			System.out.println("반려동물 수는 = "+ array.size());
			
			for (int i = 0; i<array.size(); i++) {
				JSONObject pet = (JSONObject)array.get(i);
				System.out.println(pet.get("name"));
				
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
}
