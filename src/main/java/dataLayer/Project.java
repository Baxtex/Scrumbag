package dataLayer;

import java.util.LinkedList;

/**
 * Represents a project with getters and setters.
 *
 */
public class Project {

	private String elementId, name;
	private LinkedList<User> users;

	public Project(String projectId, String projectName) {
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

	public void addUser(User user) {
		users.add(user);
	}

	public void removeUser(User user) {
		for (int i = 0; i < users.size(); i++) {
			if (users.get(i).equals(user)) {
				users.remove(i);
			}
		}
	}
}
