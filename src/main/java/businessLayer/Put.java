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
		try {
			userIDsArray = new JSONArray(userIDs);
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		try {
			if (dataHandler.checkProjectId(pID)) {
				if (action.equals("add users")) {
					int addedUsers = 0;
					for (int i = 0; i < userIDsArray.length(); i++) {
						String userName = userIDsArray.getString(i);
						if (dataHandler.validateUser(userName)) {
							dataHandler.addUserToProject(pID, userName);
							addedUsers++;
						}
					}
					if (addedUsers == userIDsArray.length()) {
						jObj.put("message", "users added");
						res.status(Status.OK.code());
					} else {
						jObj.put("message", "invalid user names");
						res.status(Status.NO_RESOURCE.code());
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
						res.status(Status.OK.code());
					} else {
						jObj.put("message", "invalid user names");
						res.status(Status.NO_RESOURCE.code());
					}

				} else {
					jObj.put("message", "invalid action");
					res.status(Status.UNAUTHORIZED.code());
				}
			} else {
				jObj.put("message", "project-id does not exist");
				res.status(Status.NO_RESOURCE.code());
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		res.type("application/json");
		return jObj;
	}

	public JSONObject editActivity(String aID, String pID, String title, String descr, String status, String prio,
			String expecTime, String addTime, String sprintID, String uID, Response res) {
		
		System.out.println("Start of editActivity");
		JSONObject jObj = new JSONObject();
		try {
			if (dataHandler.checkProjectId(pID)) {
				if (dataHandler.checkActivityId(aID)) {
					dataHandler.editActivity(  aID,  pID,  title,  descr,  status,  prio,
							 expecTime,  addTime,  sprintID,  uID,  res);
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
