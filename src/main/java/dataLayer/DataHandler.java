package dataLayer;

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
	
	public enum Status {	
		UNPLANNED, NOT_STARTED, STARTED, FOR_TEST,
		DONE, IMPEDIMENTS
	}
	
	public enum Priority {
		LOW, MEDIUM, HIGH, UNDEFINED
	}
	
	private LinkedList<Project> projects;
	private LinkedList<Activity> activities;
	private LinkedList<Sprint> sprints;
	private LinkedList<User> users;
	
	
//	private Hashtable<String,String> user_project; //<UserID,ProjectId>
//	private Hashtable<String,Project>
//	private 
	
	
	/** Kalles
	 */
	
	
	public DataHandler(){
		idCounter = 0;
		projects = new LinkedList<Project>();
		activities = new LinkedList<Activity>();
		sprints = new LinkedList<Sprint>();
		users = new LinkedList<User>();
	}
	
	public boolean checkProjectId(String id) {
		Project tmp;
		for(int i = 0; i < projects.size(); i++){
			tmp = projects.get(i);
			if(tmp.getProjectId().equals(id)){
				return true;
			}
		}
		return false;
	}

	public boolean checkActivityId(String id) {
		if (id.equals("validActivityId")) {
			return true;
		} else {
			return false;
		}
	}

	public boolean checkProjectName(String name) {
		if (name.equals("validActivityName")) {
			return true;
		} else {
			return false;
		}
	}

	public boolean validUsers(JSONObject jsonObjUsers) {
		String invalid = "";
		try {
			invalid = jsonObjUsers.getString("name");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		if (invalid.contains("invalid")) {
			return false;
		} else {
			return true;
		}
	}

	public void deleteActivity() {
		// TODO Auto-generated method stub
	}

	public Activity[] getActivities(String pID) {
		int arrSize = 0;
		int arrIndex = 0;
		Activity[] rArr;
		Activity tmp;
		
		for(int i = 0; i < activities.size(); i++){
			tmp = activities.get(i);
			if(tmp.getProjectId().equals(pID)){
				arrSize++;
			}
		}
		
		rArr = new Activity[arrSize];
		
		for(int i = 0; i < activities.size(); i++){
			tmp = activities.get(i);
			if(tmp.getProjectId().equals(pID)){
				rArr[arrIndex++] = tmp;
				
			}
		}
		return rArr;
	}

	public Activity getActivity(String aID) {
		Activity rActivity = null;
		for(int i = 0; i < activities.size(); i++){
			if(activities.get(i).getActivityId().equals(aID)){
				rActivity = activities.get(i);				
			}
		}
		return rActivity;
	}

	public void createProject() {
		// TODO Auto-generated method stub
	}

	public void createSprint() {
		// TODO Auto-generated method stub
	}

	public void addUsers() {
		// TODO Auto-generated method stub
	}

	public void removeUsers() {
		// TODO Auto-generated method stub
	}

	public void editActivity() {
		// TODO Auto-generated method stub
	}
}
