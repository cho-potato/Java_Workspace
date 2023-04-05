package io.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

// 원격지 데이터를 URL로 접근하여 실행 중인 자바 프로그램으로 읽어오기

public class GetRemoteData {

	public static void main(String[] args) {
//		원격지의 웹서버와의 접속을 통해 스트림을 생성하여 데이터 가져오기
		BufferedReader buffr = null;
		InputStreamReader reader = null;
		InputStream is = null;

		try {
			URL url = new URL("https://www.naver.com");
			URLConnection uc = url.openConnection();
			HttpURLConnection con = (HttpURLConnection) uc;
//			원격지의 웹서버에 요청을 시도할 수 있는 객체
//			웹 서버에 접속하여 해당 주소의 자원을 다운받아 오는 객체
			con.setRequestMethod("GET");
			con.setRequestProperty("Content-type", "text/html");
			int code = con.getResponseCode(); // 서버측의 응답처리 성공 여부
			System.out.println(code);

			is = con.getInputStream();
			reader = new InputStreamReader(is);
			buffr = new BufferedReader(reader);

			String data = null;
			while (true) {
				data = buffr.readLine();
				if (data == null)
					break;
				System.out.println(data);

			}

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (buffr != null) {
				try {
					buffr.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}
}
