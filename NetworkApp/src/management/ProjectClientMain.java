package management;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class ProjectClientMain extends JFrame {
	JPanel container;

	ProjectPage[] projectPage = new ProjectPage[3];
	public static final int PROJECTLOGINPAGE = 0;
	public static final int PROJECTJOINPAGE = 1;
	public static final int PROJECTMAINPAGE = 2;

	ProjectMemberDTO projectMemberDTO;

	public ProjectClientMain() {
		container = new JPanel();

		projectPage[0] = new ProjectLoginPage(this);
		projectPage[1] = new ProjectJoinPage(this);
		projectPage[2] = new ProjectMainPage(this);

		for (int i = 0; i < projectPage.length; i++) {
			container.add(projectPage[i]);
		}


		add(container);
//		container.setBackground(Color.WHITE);

		projectShowHide(PROJECTLOGINPAGE);

		setSize(1920, 1080);
		setVisible(true);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

	}

	public void projectShowHide(int n) {
		for (int i = 0; i < projectPage.length; i++) {
			if (n == i) {
				projectPage[i].setVisible(true);
			} else {
				projectPage[i].setVisible(false);
			}
		}
	}

	public static void main(String[] args) {
		new ProjectClientMain();
	}
}
