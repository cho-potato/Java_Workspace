package util;

import java.awt.Image;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class ImageManager {
//	이미지에 대한 경로를 전달하면 이미지 객체를 반환하는 메서드 정의
	public Image[] createImages(String[] imgName) {
		Class myClass = this.getClass(); // 현재 클래스에 대한 정보를 가진 클래스를 얻는다
		Image[] images = new Image[imgName.length];

		for (int i = 0; i < imgName.length; i++) {
			URL url = myClass.getClassLoader().getResource(imgName[i]); // 디렉토리를 적을 필요 없어 많이 쓰임(OS를 가리지 않음)
			try {
//		 생성된 이미지를 배열에 담기
				images[i] = ImageIO.read(url);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return images;
	}

	/*
	 * path : 클래스 패스 내의 이미지 경로 
	 * width, height : 크기를 조정하고 싶은 값
	 */
//	버튼 메뉴에 사용할 아이콘 생성하기
	public ImageIcon getIcon(String path, int width, int height) {
		Class myClass = this.getClass();
		ClassLoader loader = myClass.getClassLoader();
		URL url = loader.getResource(path);
//	- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 여기까지가 아이콘을 URL에 가져오는 
		Image scaledImg = null;
		try {
			Image img = ImageIO.read(url);
			scaledImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH); // getScaledInstance : Creates a scaled version of this image
		} catch (IOException e) {
			e.printStackTrace();
		}
//		ImageIcon icon = new ImageIcon(url);
		ImageIcon icon = new ImageIcon(scaledImg);
		return icon;
	}
//	- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 위는 아이콘 아래는 이미지
//	이미지 얻어오기
	public Image getImage(String path, int width, int height) {
		Image image=null;
		URL url = this.getClass().getClassLoader().getResource(path);
		try {
			image = ImageIO.read(url);
			image = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return image;
	}
}
