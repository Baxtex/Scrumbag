package dataLayer;

/**
 * This class represents a sprint with getters and setters.
 *
 */
public class Sprint {

	private String sprintId, projectId, title;

	private int index;

	public Sprint(String sprintId, String projectId, String title, int index) {

		this.sprintId = sprintId;
		this.projectId = projectId;
		this.title = title;
		this.index = index;
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
