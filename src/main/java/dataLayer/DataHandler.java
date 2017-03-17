package dataLayer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.LinkedList;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

/**
 * Database handler for the database.
 * 
 * @author Anton
 *
 */
public class DataHandler {
	
	private int idCounter;
	
	private LinkedList<Project> projects;
	private LinkedList<Activity> activities;
	private LinkedList<Sprint> sprints;
	private LinkedList<User> users;
	private LinkedList<String> statuses;
	private LinkedList<String> priorities;
	
	public DataHandler(){
		idCounter = 0;
		projects = new LinkedList<Project>();
		activities = new LinkedList<Activity>();
		sprints = new LinkedList<Sprint>();
		users = new LinkedList<User>();
		statuses = getStatuses();
		priorities = getPriorities();
	}
	 
	private LinkedList<String> getStatuses() {
		LinkedList<String> statuses = new LinkedList<String>();
		statuses.add("unplanned");
		statuses.add("not-started");
		statuses.add("started");
		statuses.add("for-test");
		statuses.add("done");
		statuses.add("impediments");
		return statuses;
	}
	
	private LinkedList<String> getPriorities() {
		LinkedList<String> priorities = new LinkedList<String>();
		priorities.add("low");
		priorities.add("medium");
		priorities.add("high");
		priorities.add("undefined");
		return priorities;
	}
	
	/** 
	 * Activity
	 */
	
	public boolean checkActivityId(String aID) {
		for(int i = 0; i < activities.size(); i++){
			if(activities.get(i).getActivityId().equals(aID)){
				return true;
			}
		}
		return false;
	}
	
	public String createActivity(String projectID,
			String sprintID, String title, String description, String timeExpected,
			String timeSpent, String respUser, String status, String priority){
		
		Activity activity = new Activity((idCounter++)+"", projectID, sprintID, title
				, description, timeExpected, timeSpent, respUser, status, priority);
		return activity.getActivityId();

	}
	
	public void editActivity(String activityID, String projectID, String title, String description, 
			String timeExpected,String timeSpent, String respUser, String status, String priority){
		Activity a = getActivity(activityID);
		a.setProjectId(projectID);
		a.setTitle(title);
		a.setDescription(description);
		a.setTimeExpected(timeExpected);
		a.setTimeSpent(timeSpent);
		a.setRespUser(respUser);
		a.setStatus(status);
		a.setPriority(priority);

	}

	public void deleteActivity(String aID) {
		Activity tmp = null;
		for(int i = 0; i < activities.size(); i++){
			tmp = activities.get(i);
			if(tmp.getActivityId().equals(aID)){
				activities.remove(i);
				break;
			}
		}
	}

	public Activity[] getActivities(String pID) {
		int arrSize = 0;
		int arrIndex = 0;
		Activity[] rArr;
		Activity tmp;
		
		for(int i = 0; i < activities.size(); i++) {
			tmp = activities.get(i);
			if(tmp.getProjectId().equals(pID)){
				arrSize++;
			}
		}
		
		rArr = new Activity[arrSize];
		
		for(int i = 0; i < activities.size(); i++) {
			tmp = activities.get(i);
			if(tmp.getProjectId().equals(pID)){
				rArr[arrIndex++] = tmp;
				
			}
		}
		return rArr;
	}

	public Activity getActivity(String aID) {
		Activity rActivity = null;
		for(int i = 0; i < activities.size(); i++) {
			if(activities.get(i).getActivityId().equals(aID)){
				rActivity = activities.get(i);				
			}
		}
		return rActivity;
	}

	/**
	 * Project
	 */
	
	public boolean checkProjectId(String ID) {
		Project tmp;
		for(int i = 0; i < projects.size(); i++){
			tmp = projects.get(i);
			if(tmp.getProjectId().equals(ID)){
				return true;
			}
		}
		return false;
	}
	
	public boolean checkProjectName(String pName) {
		for(int i = 0; i < projects.size(); i++){
			if(projects.get(i).getName().equals(pName)){
				return true;
			}
		}
		return false;
	}
	
	public String createProject(String pName) { 
		Project tmp = new Project((idCounter++)+"",pName);
		projects.add(tmp);
		return tmp.getProjectId();
	}
	
	private Project getProject(String pID){
		Project tmp = null;
		for(int i = 0; i < projects.size(); i++){
			tmp = projects.get(i);
			if(tmp.getProjectId().equals(pID))
				break;
		}
		return tmp;
	}
	
	public boolean checkSprint(String sID) {
		for(int i = 0; i < sprints.size(); i++){
			if(sprints.get(i).getSprintId().equals(sID)){
				return true;
			}
		}
		return false;
	}

	public String createSprint(String pID, String title, int index) {
		Sprint tmp = new Sprint((idCounter++) + "",pID,title,index);
		sprints.add(tmp);
		return tmp.getSprintId();
	}
	
	
	/**
	 * User Methods
	 */
	public String createUser(String name) { 
		User tmp = new User((idCounter++)+"",name);
		users.add(tmp);
		return tmp.getUserId();
	}
	
	public void addUserToProject(String pID, String uID) {
		Project p = getProject(pID);
		User u = getUser(uID);
		p.addUser(u);
	}
	
	public void removeUserFromProject(String pID, String uID){
		Project p = getProject(pID);
		User u = getUser(uID);
		p.removeUser(u);
	}
	
	public boolean validateUser(String uID){
		for(int i = 0; i < users.size(); i++){
			if(users.get(i).getUserId().equals(uID)){
				return true;
			}
		}
		return false;
	}
	
	public User[] getUsers() {
		return (User[])users.toArray();
	}
	
	public User[] getUsers(String pID){
		Project p = getProject(pID);
		return (User[])p.getUsers().toArray();
	}
	
	private User getUser(String uID){
		User tmp = null;
		for(int i = 0; i < users.size(); i++){
			tmp = users.get(i);
			if(tmp.getUserId().equals(uID)){
				break;
			}
		}
		return tmp;
	}

	public boolean removeUser(String uID) {
		for(int i = 0; i < users.size(); i++){
			if(users.get(i).getUserId().equals(uID)){
				return true;
			}
		}
		return false;
	}
	
	public boolean isValidStatus(String status) {
		return statuses.contains(status);
	}
	
	public boolean isValidPriority(String priority) {
		return priorities.contains(priority);
	}
	
}
