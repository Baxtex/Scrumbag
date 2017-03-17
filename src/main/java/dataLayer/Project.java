package dataLayer;

import java.util.Hashtable;
import java.util.LinkedList;

public class Project {
	
	private String projectId;
	private String elementId;
	
	private String name; 
	
	private LinkedList<String> users;
	
	public Project(){
		
	}
	
	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getElementId() {
		return elementId;
	}

	public void setElementId(String elementId) {
		this.elementId = elementId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LinkedList<String> getUsers() {
		return users;
	}

	public void setUsers(LinkedList<String> users) {
		this.users = users;
	}

	public void addUser(String user){
		users.add(user);
	}
	
}
