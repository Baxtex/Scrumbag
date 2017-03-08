package businessLayer;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import spark.Response;

public class Put {
	
	public JSONObject userManagement(String key, String pID, String action, String userIDs, Response res) {
		JSONObject jObj = new JSONObject();
		try {
			JSONObject jObjUserIDs = new JSONObject(userIDs);
			if (checkIfAdmin(key)) {
				if (checkProjectId(pID)) {
					if (action.equals("add users")) {
						if (addUsers(jObjUserIDs)) {
							jObj.put("message", "users added");
							res.status(200);
						} else {
							jObj.put("message", "invalid user names");
							res.status(404);
						}
					} else if (action.equals("remove users")) {
						if (removeUsers(jObjUserIDs)) {
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
			} else {
				jObj.put("message", "key does not exist");
				res.status(401);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		res.type("application/json");
		return jObj;
	}

	public JSONObject editActivity(String key, String aID, String pID, String title, String descr, String status,
			String prio, String expecTime, String addTime, String sprintID, String uID, Response res) {
		JSONObject jObj = new JSONObject();
		try {
			if (checkIfUser(key)) {

				if (checkProjectId(pID)) {

					if (checkActivityId(aID)) {
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
			} else {
				jObj.put("message", "key does not exist");
				res.status(401);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		res.type("application/json");
		return jObj;
	}
	private boolean checkIfAdmin(String key) {
		if (key.equals("validAdminKey")) {
			return true;
		} else {
			return false;
		}
	}

	private boolean checkIfUser(String key) {
		if (key.equals("validUserKey")) {
			return true;
		} else {
			return false;
		}
	}

	private boolean checkProjectId(String id) {
		if (id.equals("validProjectId")) {
			return true;
		} else {
			return false;
		}
	}

	private boolean checkActivityId(String id) {
		if (id.equals("validActivityId")) {
			return true;
		} else {
			return false;
		}
	}


	private boolean addUsers(JSONObject jsonObjUsers) {
		// TODO: Maybe loop all user names and check them to the db. If they
		// don't exist, return false

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

	private boolean removeUsers(JSONObject jsonObjUsers) {
		// TODO: Maybe loop all user names and check them to the db. If they
		// don't exist, return false
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
}
