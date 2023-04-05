package util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

// 구글이 제공해주는 구글 메일 서버를 이용한 자바 메일 발송 

public class MailSender {
//	사용중인 G-mail
	String myAccountMail = "ymcho.dev@gmail.com"; 
	String password = "gmzoxabzurebhekt";

	public void sendMail(String to) {
//		메일 제목
		String subject = "받아주셔서 감자합니다";
//		보낸 사람 이메일
		String from = "ymcho.dev@gmail.com";
//		보낸사람
		String fromName = "고구마관리자";
//		받을 사람 이메일 ,(콤마로 다수의 메일 가능)
//		String to = "dokyy1226@gmail.com";

		StringBuffer content = new StringBuffer();
		content.append("<h1> 안녕하세요, 감자입니다</h1>");
		content.append("<p> 너무 ,,, 예 ,,, 아시죠 ? 고 구 마 좋 아 </p>");

//		메일 설정
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com"); // 보낼 때 쓰는 프로토콜 : smtp , Gmail 사용하겠다
		props.put("mail.smtp.port", "587"); // 포트번호 설정
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true"); // TLS 사용

//		구글 메일을 사용하기 위한 인증절차
//		JaVa 메일 API에서 지원하는 Session 객체 생성
		Session mailSession = Session.getInstance(props, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(myAccountMail, password);
			}
		});
//		보내기 위한 세팅
		MimeMessage message = new MimeMessage(mailSession);
		
//		보내는 사람 , 받는 사람 정보 세팅
		try {
			message.setFrom(new InternetAddress(from, MimeUtility.encodeText(fromName, "UTF-8", "B")));
//			- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 보내는 사람 정보
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
//			메일 제목 설정
			message.setSubject(subject); 
//			HTML 형식으로 보낼 예정
			message.setContent(content.toString(), "text/html;charset=UTF-8");
//			보내는 시간(날짜) 설정
			message.setSentDate(new Date());
//			전송하기
			Transport trans = mailSession.getTransport("smtp");
			trans.connect(myAccountMail, password);
			trans.send(message, message.getAllRecipients());
			trans.close();
			System.out.println("나 로 호 발 송 성 공");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
//	public static void main(String[] args) {
//		MailSender sender= new MailSender();
//		sender.sendMail();
//		
//	}
}
