package dataLayer;

import java.util.LinkedList;

/**
 * Represents a project with getters and setters.
 *
 */
public class Project {

	private String elementId, name;
	private LinkedList<String> users;

	public Project(String projectId, String projectName) {
		this.elementId = projectId;
		this.name = projectName;
		this.users = new LinkedList<String>();
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

	public LinkedList<String> getUsers() {
		return users;
	}

	public void setUsers(LinkedList<String> users) {
		this.users = users;
	}

	public void addUser(String user) {
		users.add(user);
	}

	public void removeUser(String user) {
		users.remove(user);
	}
}
