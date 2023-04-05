package management;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;

public class MenuButton extends JButton{
	ProjectMainPage projectMainPage;
	int targetPage;
	
	public MenuButton(String button, ProjectMainPage projectMainPage, int targetPage) {
		super(button);
		this.projectMainPage = projectMainPage;
		this.targetPage = targetPage;
		this.setFont(new Font("고딕", Font.BOLD, 15));
		this.setBorderPainted(false);
		this.setFocusPainted(false);
		this.setBackground(Color.WHITE);
		
		this.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				projectMainPage.mainPageShowHide(targetPage);
			}
		});
	}
}
