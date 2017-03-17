package dataLayer;

public class Sprint {
	
	private String sprintId;
	private String elementId;
	private String projectId;
	
	private String title;
	private int index;
	
	public Sprint(){
		
	}
	
	public String getSprintId() {
		return sprintId;
	}
	
	public void setSprintId(String sprintId) {
		this.sprintId = sprintId;
	}
	
	public String getProjectId() {
		return projectId;
	}
	
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public int getIndex() {
		return index;
	}
	
	public void setIndex(int index) {
		this.index = index;
	}
	
}
