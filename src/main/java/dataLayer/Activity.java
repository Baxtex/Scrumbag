package dataLayer;

/**
 * Represents an activity with getters and setters.
 *
 */
public class Activity {

	private String elementId, projectId, sprintId, title, description, timeExpected, timeSpent, respUser, status,
			priority;

	public Activity(String activityID, String projectID, String sprintID, String title, String description,
			String timeExpected, String timeSpent, String respUser, String status, String priority) {

		this.elementId = activityID;
		this.projectId = projectID;
		this.sprintId = sprintID;
		this.title = title;
		this.description = description;
		this.timeExpected = timeExpected;
		this.timeSpent = timeSpent;
		this.respUser = respUser;
		this.status = status;
		this.priority = priority;
	}

	public String getActivityId() {
		return elementId;
	}

	public void setActivityId(String activityId) {
		this.elementId = activityId;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getSprintId() {
		return sprintId;
	}

	public void setSprintId(String sprintId) {
		this.sprintId = sprintId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTimeExpected() {
		return timeExpected;
	}

	public void setTimeExpected(String timeExpected) {
		this.timeExpected = timeExpected;
	}

	public String getTimeSpent() {
		return timeSpent;
	}

	public void setTimeSpent(String timeSpent) {
		this.timeSpent = timeSpent;
	}

	public String getRespUser() {
		return respUser;
	}

	public void setRespUser(String respUser) {
		this.respUser = respUser;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getElementId() {
		return elementId;
	}

	public void setElementId(String elementId) {
		this.elementId = elementId;
	}
}
