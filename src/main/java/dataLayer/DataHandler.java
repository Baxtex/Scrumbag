package dataLayer;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

/**
 * Database handler for the database.
 * 
 * @author Anton
 *
 */
public class DataHandler {

	public boolean checkProjectId(String id) {
		if (id.equals("validProjectId")) {
			return true;
		} else {
			return false;
		}
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

	public void getActivities() {
		// TODO Auto-generated method stub
	}

	public void getActivity() {
		// TODO Auto-generated method stub
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
