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

	public JSONObject userManagement(String pID, String action, String userIDs, Response res) {
		JSONObject jObj = new JSONObject();
		JSONArray userIDsArray = null;
		JSONObject usersIDsObj;
		try {
			usersIDsObj = new JSONObject(userIDs);
			userIDsArray = usersIDsObj.getJSONArray("user-ids");
		} catch (JSONException e1) {
			e1.printStackTrace();
		}

		try {
			JSONObject jObjUserIDs = new JSONObject(userIDs);
			if (dataHandler.checkProjectId(pID)) {

				if (action.equals("add users")) {
					int addedUsers = 0;
					for (int i = 0; i < userIDsArray.length(); i++) {
						JSONObject userObject = ((JSONObject) userIDsArray.get(i));
						String uID = (String) userObject.get("name");
						if (dataHandler.validateUser(uID)) {
							dataHandler.addUserToProject(pID, uID);
							addedUsers++;
						}
					}
					if (addedUsers == userIDsArray.length()) {
						jObj.put("message", "users added");
						res.status(Status.STATUSCODE_OK.getVal());
					} else {
						jObj.put("message", "invalid user names");
						res.status(Status.STATUSCODE_NO_RESOURCE.getVal());
					}

				} else if (action.equals("remove users")) {
					int removedUsers = 0;
					for (int i = 0; i < userIDsArray.length(); i++) {
						JSONObject userObject = ((JSONObject) userIDsArray.get(i));
						String uID = (String) userObject.get("name");
						if (dataHandler.validateUser(uID)) {
							dataHandler.removeUserFromProject(pID, uID);
							removedUsers++;
						}
					}
					if (removedUsers == userIDsArray.length()) {
						jObj.put("message", "users removed");
						res.status(Status.STATUSCODE_OK.getVal());
					} else {
						jObj.put("message", "invalid user names");
						res.status(Status.STATUSCODE_NO_RESOURCE.getVal());
					}

				} else {
					jObj.put("message", "invalid action");
					res.status(Status.STATUSCODE_UNAUTHORIZED.getVal());
				}
			} else {
				jObj.put("message", "project-id does not exist");
				res.status(Status.STATUSCODE_NO_RESOURCE.getVal());
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		res.type("application/json");
		return jObj;
	}

	public JSONObject editActivity(String aID, String pID, String title, String descr, String status, String prio,
			String expecTime, String addTime, String sprintID, String uID, Response res) {
		JSONObject jObj = new JSONObject();
		try {
			if (dataHandler.checkProjectId(pID)) {
				if (dataHandler.checkActivityId(aID)) {
					dataHandler.editActivity(aID, title, descr, status, status, expecTime, addTime, sprintID, uID);
					jObj.put("message", "activity changed");
					res.status(Status.STATUSCODE_OK.getVal());
				} else {
					jObj.put("message", "activity does not exist");
					res.status(Status.STATUSCODE_NO_RESOURCE.getVal());
				}
			} else {
				jObj.put("message", "project does not exist");
				res.status(Status.STATUSCODE_NO_RESOURCE.getVal());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		res.type("application/json");
		return jObj;
	}
}
