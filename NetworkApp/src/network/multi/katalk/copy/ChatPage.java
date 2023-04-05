package network.multi.katalk.copy;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.plaf.DimensionUIResource;

import org.json.simple.JSONObject;

public class ChatPage extends Page {
	JPanel content;
	JScrollPane scroll;

	JPanel p_south;
	JComboBox<String> box_emoti;
	JTextField t_input;
	JButton bt_send;
	ClientMessageThread cmt;

	public ChatPage(ClientMain clientMain) {
		super(clientMain);

		// 가운데
		content = new JPanel();
		scroll = new JScrollPane(content);

		// 남쪽
		p_south = new JPanel();
		box_emoti = new JComboBox<String>();
		t_input = new JTextField();
		bt_send = new JButton("전송");
		createEmoti();

		content.setBackground(Color.YELLOW);
		content.setPreferredSize(new Dimension(380, 1000));

		scroll.setPreferredSize(new Dimension(380, 400));
		t_input.setPreferredSize(new Dimension(300, 35));

		p_south.add(box_emoti);
		p_south.add(t_input);
		p_south.add(bt_send);

		add(scroll);
		add(p_south, BorderLayout.SOUTH);

		setVisible(true);
		setPreferredSize(new Dimension(400, 500));

		// t_input과 리스너 연결
		t_input.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					send();
				}
			}
		});
	}

	// 이모티콘 채우기
	public void createEmoti() {
		box_emoti.addItem("이모티콘 채우기");
		box_emoti.addItem("heart.png");
		box_emoti.addItem("shy.png");
	}

	// 채팅서버 접속
	public void connect() {
		String ip = "localhost";
		int port = 8888;
		try {
			Socket socket = new Socket(ip, port);
			// 접속성공과 함께
			cmt = new ClientMessageThread(this, socket);
			cmt.start(); // 스레드 runnable 상태로 진입
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void send() {
		/*
		 * 서버에 데이터 전송시, 메시지 뿐 아니라 사용자 정보도 함께 보내야 한다. 따라서 복합된 형태의 데이터를 보내야 한다..
		 * "id=batman msg=안녕 filename=cry.png"
		 */
		// StringBuilder sb = new StringBuffer(); 다중스레드 환경에서 동기화,, 무거워서 여기선 안
		StringBuilder sb = new StringBuilder();

		String id = clientMain.chatMember.getId();
		String name = clientMain.chatMember.getName();
		String msg = t_input.getText(); // 대화 메시지
		String filename = (String) box_emoti.getSelectedItem();

		sb.append("{");
		sb.append("\"member\": {");
		sb.append("\"id\": \"" + id + "\",");
		sb.append("\"name\": \"" + name + "\"");
		sb.append("},");
		sb.append("\"data\": \"" + msg + "\",");
		sb.append("	\"filename\": \"" + filename + "\"");
		sb.append("}");

		cmt.sendMsg(sb.toString()); // 제이슨 문자열 보내

		// 다시 입력값 초기화
		t_input.setText("");
	}

	/* 대화의 내용이 텍스트일때는 JTextArea를 content라는 패널에 부착할 예정 */
	public void addString(JSONObject json) {
		JSONObject member = (JSONObject) json.get("member");
		String id = (String) member.get("id");
		String name = (String) member.get("name");
		String data = (String) json.get("data");

		JTextArea area = new JTextArea();
		area.setText(name + "님 :" + data);
		area.setLineWrap(true);
		area.setWrapStyleWord(true);

		// 채팅문자열의 총 길이 54.0
		float lineCount = (float) Math.ceil((float) data.length() / 30); // 정수와 정수는 결과도 정수...
		System.out.println("linecount is "+lineCount);
		area.setPreferredSize(new Dimension(350, ((int) lineCount * 15)));

		content.add(area);// 패널에 부착
		content.updateUI(); // 갱신
	}

	/* 이모티콘 생성 */
	public void addIcon(JSONObject json) {
		JSONObject member = (JSONObject) json.get("member");
		String id = (String) member.get("id");
		String name = (String) member.get("name");
		String filename = (String) json.get("filename");

		// 패널에 이미지를 부착할 예정
		URL url = this.getClass().getClassLoader().getResource("src/res/image/" + filename); /// -------filename
		try {
			Image image = ImageIO.read(url);
			ImageIcon icon = new ImageIcon(image);
			// 이미지는 UI 컴포넌트가 아니므로, 패널에 직접 붙일 수 없다..
			// 즉 버튼, 라벨 등등 사용자가 UI 조작할 수 있는 ui 컴포넌트만이 붙여질 대상이다
			JLabel la = new JLabel(icon);
			la.setPreferredSize(new DimensionUIResource(350, 70));
			content.add(la);
			content.updateUI();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
