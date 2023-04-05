package management;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;

public class ProjectPage extends JPanel{
	ProjectClientMain projectClientMain;
	
	public ProjectPage(ProjectClientMain projectClientMain) {
		this.projectClientMain = projectClientMain;
		
		this.setPreferredSize(new Dimension(1920, 1080));
		this.setBackground(Color.LIGHT_GRAY);
	}
}
