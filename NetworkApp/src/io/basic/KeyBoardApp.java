package io.basic;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

// 지금까지 우리가 다뤄왔던 입출력은 파일에 국한되었으나 
// 실행 중인 프로그램으로 데이터를 입력받거나 출력하는 것은
// 파일 뿐만 아니라 다양한 대상으로 할 수 있다
// 이번 예제에서는 키보드를 대상으로 입력을 처리해보자 

public class KeyBoardApp {
//	Sun에서는 어떠한 디바이스가 개발될 지 예측할 수 없으므로,
//	키보드를 비롯한 다양한 디바이스들에 대한 스트림 처리 클래스를 각각 정의할 수 없었고
// 스트림 객체 중 가장 추상적이면서 최상위 객체인 InputStream과 OutputStream으로 해결하고 있다

	public static void main(String[] args) {
//		그냥 입력 스트림
//		InputStream is = null;

//		키보드와 관련된 스트림은 개발자가 생성하지 않아도 이미 생성되어 있기 때문에 그냥 얻어오면 된다
//		키보드 스트림이 생성된 시점은 OS가 켜질 때 생성되며,
//		개발자의 역할이 아닌 개발자는 단지 생성된 스트림을 얻어오면 된다
		
//		바이트 기반 출력 스트림
		InputStream is = System.in;
		
//		문자기반으로 업그레이드
		InputStreamReader reader = null;
		reader = new InputStreamReader(is);
		
//		버퍼 처리 출력 스트림
		BufferedReader buffr = null;
		buffr = new BufferedReader(reader);
		
//		바이트 기반 출력 스트림
		OutputStream os = System.out;
		
//		문자기반으로 업그레이드
		OutputStreamWriter writer = null;
		writer = new OutputStreamWriter(os);
		
//		버퍼 처리 출력 스트림
//		BufferedWriter buffw = null;
//		buffw = new BufferedWriter(writer);
				
		String data = null;
		try {
//			키보드에서 입력한 문자들을 버퍼에 쌓아가다 사용자가 문자열의 끝임을 알려줄 수 있는 엔터를 치면
//			실행 중인 프로그램으로 데이터가 들어옴
			while (true) {
			data = buffr.readLine();
			System.out.println(data);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
