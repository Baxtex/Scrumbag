package businessLayer;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import dataLayer.DataHandler;
import spark.Response;

public class Put {

	private final DataHandler dataHandler;

	public Put(DataHandler dataHandler) {
		this.dataHandler = dataHandler;
	}

	public JSONObject userManagement(String pID, String action, String userIDs, Response res) {
		JSONObject jObj = new JSONObject();
		try {
			JSONObject jObjUserIDs = new JSONObject(userIDs);
			if (dataHandler.checkProjectId(pID)) {
				if (action.equals("add users")) {
					if (dataHandler.validUsers(jObjUserIDs)) {
						dataHandler.addUsers();
						jObj.put("message", "users added");
						res.status(200);
					} else {
						jObj.put("message", "invalid user names");
						res.status(404);
					}
				} else if (action.equals("remove users")) {
					if (dataHandler.validUsers(jObjUserIDs)) {
						dataHandler.removeUsers();
						jObj.put("message", "users removed");
						res.status(200);
					} else {
						jObj.put("message", "invalid user names");
						res.status(404);
					}
				} else {
					jObj.put("message", "invalid action");
					res.status(403);
				}
			} else {
				jObj.put("message", "project-id does not exist");
				res.status(404);
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
					dataHandler.editActivity();
					jObj.put("message", "activity changed");
					res.status(200);
				} else {
					jObj.put("message", "activity does not exist");
					res.status(404);
				}
			} else {
				jObj.put("message", "project does not exist");
				res.status(404);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		res.type("application/json");
		return jObj;
	}
}
