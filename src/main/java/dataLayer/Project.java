package dataLayer;

import java.util.Hashtable;
import java.util.LinkedList;

public class Project {
	
	private String elementId;
	
	private String name; 
	
	private LinkedList<User> users;
	
	public Project(String projectId, String projectName){
		this.elementId = projectId;
		this.name = projectName;
	}
	
	public String getProjectId() {
		return elementId;
	}

	public void setProjectId(String projectId) {
		this.elementId = projectId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LinkedList<User> getUsers() {
		return users;
	}

	public void setUsers(LinkedList<User> users) {
		this.users = users;
	}

	public void addUser(User user){
		users.add(user);
	}
	
}
