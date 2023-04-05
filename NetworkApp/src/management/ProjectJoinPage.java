package management;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import util.StringUtil;

public class ProjectJoinPage extends ProjectPage {
	JLabel joinPage_title;
	JTextField joinPage_id;
	JPasswordField joinPage_pass;
	JTextField joinPage_name;
	JButton joinPage_login;
	JButton joinPage_join;

	ProjectMemberDAO projectMemberDAO;

	public ProjectJoinPage(ProjectClientMain projectClientMain) {
		super(projectClientMain);
		projectMemberDAO = new ProjectOracleDAO();

		joinPage_title = new JLabel("SIGN-UP");
		joinPage_id = new JTextField();
		joinPage_pass = new JPasswordField();
		joinPage_name = new JTextField();
		joinPage_login = new JButton("LOGIN");
		joinPage_join = new JButton("SIGN-UP");

		joinPage_title.setPreferredSize(new Dimension(350, 200));
		joinPage_title.setFont(new Font("Verdana", Font.BOLD, 45));
		joinPage_title.setHorizontalAlignment(SwingConstants.CENTER);

		Dimension d1 = new Dimension(350, 30);
		joinPage_id.setPreferredSize(d1);
		joinPage_pass.setPreferredSize(d1);
		joinPage_name.setPreferredSize(d1);

		add(joinPage_title);
		add(joinPage_id);
		add(joinPage_pass);
		add(joinPage_name);
		add(joinPage_login);
		add(joinPage_join);

		setBackground(Color.LIGHT_GRAY);

		joinPage_login.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				projectLoginPage();
			}
		});
		joinPage_join.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				joinPageRegist();
			}
		});
	}

	public void projectLoginPage() {
		projectClientMain.projectShowHide(ProjectClientMain.PROJECTLOGINPAGE);
	}

	public void joinPageRegist() {
		ProjectMemberDTO projectMemberDTO = new ProjectMemberDTO();
		projectMemberDTO.setId(joinPage_id.getText());
		projectMemberDTO.setPass(StringUtil.getConvertedPassword(new String(joinPage_pass.getPassword())));
		projectMemberDTO.setName(joinPage_name.getText());

		int result = projectMemberDAO.insert(projectMemberDTO);
		if (result > 0) {
			JOptionPane.showMessageDialog(this, "노예계약성립");
		}
	}
}
