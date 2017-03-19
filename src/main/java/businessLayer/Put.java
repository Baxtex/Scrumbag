package businessLayer;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import dataLayer.DataHandler;
import resource.Status;
import spark.Response;

/**
 * This module handles put requests.
 *
 */
public class Put {

	private final DataHandler dataHandler;

	public Put(DataHandler dataHandler) {
		this.dataHandler = dataHandler;
	}
	
	public JSONObject userManagement(String projectId, String action, String usernames, Response res) {
		JSONObject json = new JSONObject();
		
		try {	
			
			if(!dataHandler.checkProjectId(projectId)) {
				json.put("Message", "Failed to add users. Project does not exist.");
				res.status(Status.NO_RESOURCE.code());
				return json;
			}
			if(!action.equals("add users") && !action.equals("remove users")) {
				json.put("Message", "Failed to do anything. Action is invalid.");
				res.status(Status.UNAUTHORIZED.code());
				return json;
			}	
			JSONArray usersJSONArray = new JSONArray(usernames);
			for(int i = 0; i < usersJSONArray.length(); i++) {
				String username = usersJSONArray.getJSONObject(i).getString("name");
				if(!dataHandler.isValidUser(username)) {
					json.put("Message", "Failed to do anything. One or more users are invalid.");
					res.status(Status.UNAUTHORIZED.code());
					return json;
				}
			}
			if(action.equals("add users")) {
				for(int i = 0; i < usersJSONArray.length(); i++) {
					String username = usersJSONArray.getJSONObject(i).getString("name");
					dataHandler.addUserToProject(projectId, username);
				}
				json.put("Message", "Successfully added all users to project.");
				res.status(Status.OK.code()); 
				return json;
			} 
			if(action.equals("remove users")) {
				for(int i = 0; i < usersJSONArray.length(); i++) {
					String username = usersJSONArray.getJSONObject(i).getString("name");
					dataHandler.removeUserFromProject(projectId, username);
				}
				json.put("Message", "Successfully removed all users from project.");
				res.status(Status.OK.code());
				return json;
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}


	public JSONObject editActivity(String aID, String pID, String title, String descr, String status, String prio,
			String expecTime, String addTime, String sprintID, String uID, Response res) {
	
		JSONObject jObj = new JSONObject();
		try {
			if (dataHandler.checkProjectId(pID)) {
				if (dataHandler.checkActivityId(aID)) {
					dataHandler.editActivity(aID, pID, title, descr, status, prio, expecTime, addTime, sprintID, uID,
							res);
					jObj.put("message", "activity changed");
					res.status(Status.OK.code());
				} else {
					jObj.put("message", "activity does not exist");
					res.status(Status.NO_RESOURCE.code());
				}
			} else {
				jObj.put("message", "project does not exist");
				res.status(Status.NO_RESOURCE.code());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		res.type("application/json");
		return jObj;
	}
}
