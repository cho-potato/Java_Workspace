package management;

public class ProjectMemberDTO {
	private int projectmember_idx;
	private String id;
	private String pass;
	private String name;

	public int getProjectmember_idx() {
		return projectmember_idx;
	}

	public void setProjectmember_idx(int projectmember_idx) {
		this.projectmember_idx = projectmember_idx;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
