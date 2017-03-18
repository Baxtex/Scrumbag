package dataLayer;

/**
 * Representerar en aktivitet i Scrum
 * @author KEJ
 *
 */
public class Activity {
	
	private String elementId;
	private String projectId;
	private String sprintId;
	
	private String title;
	private String description;
	
	private int timeExpectedHH;
	private int timeExpectedMM;

	private int timeSpentHH;
	private int timeSpentMM;
	
	private String respUser;
	
	private String status;
	private String priority;
	
	
	public Activity(String activityID, String projectID,
			String sprintID, String title, String description,
			String timeExpected, String timeAdded, String respUser,
			String status, String priority){
		
		this.elementId = activityID;
		this.projectId = projectID;
		this.sprintId = sprintID;
		this.title = title;
		this.description = description;
		this.timeExpectedHH = Integer.parseInt(timeExpected.split(":")[0]);
		this.timeExpectedMM = Integer.parseInt(timeExpected.split(":")[1]);
		this.respUser = respUser;
		this.status = status;
		this.priority = priority;
		addTime(timeAdded);
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
		return timeExpectedHH + ":" + timeExpectedMM;
	}
	
	public void setTimeExpected(String timeExpected) {
		this.timeExpectedHH = Integer.parseInt(timeExpected.split(":")[0]);
		this.timeExpectedMM = Integer.parseInt(timeExpected.split(":")[1]);
	}
	
	public String getTimeSpent() {
		return timeSpentHH + ":" + timeSpentMM;
	}
	
	public void setTimeSpent(String timeSpent) {
		this.timeSpentHH = Integer.parseInt(timeSpent.split(":")[0]);
		this.timeSpentMM = Integer.parseInt(timeSpent.split(":")[1]);
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
	
	public void addTime(String additionalTime){
		int additionalMinutes = Integer.parseInt(additionalTime.split(":")[1]);
		int additionalHours = Integer.parseInt(additionalTime.split(":")[0]);
		int carry = (timeSpentMM + additionalMinutes)/60;
		timeSpentMM = (timeSpentMM + additionalMinutes) % 60;
		timeSpentHH = timeSpentHH + additionalHours + carry;		
	}
	
}
