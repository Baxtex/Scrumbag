package dataLayer;

import dataLayer.DataHandler.Priority;
import dataLayer.DataHandler.Status;

public class Activity {
	
	private String elementId;
	private String projectId;
	private String sprintId;
	
	private String title;
	private String description;
	
	private String timeExpected;
	private String timeSpent;
	
	private String respUser;
	
	private Status status;
	private Priority priority;
	
	
	public Activity(String activityID, String projectID,
			String sprintID, String title, String description,
			String timeExpected, String timeSpent, String respUser,
			Status status, Priority priority){
		
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

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Priority getPriority() {
		return priority;
	}

	public void setPriority(Priority priority) {
		this.priority = priority;
	}

	public String getElementId() {
		return elementId;
	}

	public void setElementId(String elementId) {
		this.elementId = elementId;
	}
	
}
