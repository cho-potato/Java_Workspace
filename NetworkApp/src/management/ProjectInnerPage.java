package management;

import java.awt.Dimension;

import javax.swing.JPanel;

public class ProjectInnerPage extends JPanel{
	ProjectMainPage projectMainPage;
	
	public ProjectInnerPage(ProjectMainPage projectMainPage) {
		this.projectMainPage = projectMainPage;
		this.setPreferredSize(new Dimension(1900, 700));
	}
}
