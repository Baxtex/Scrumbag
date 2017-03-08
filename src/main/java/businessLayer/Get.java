package businessLayer;

import org.codehaus.jettison.json.JSONObject;

import spark.Response;

public class Get {

	public JSONObject getActivities(String pID, String key, Response res) {
		JSONObject jObj = new JSONObject();
		try {
			if (checkIfUser(key)) {
				if (checkProjectId(pID)) {
					jObj.put("activity-id", "abcdf");
					jObj.put("title", "this is a valid activity");
					jObj.put("status", "planned");
					res.status(200);
				} else {
					jObj.put("message", "activity does not exist");
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

	public JSONObject getActivity(String aID, String key, Response res) {
		JSONObject jObj = new JSONObject();
		try {
			if (checkIfUser(key)) {
				if (checkActivityId(aID)) {
					jObj.put("activity-id", "xxx");
					jObj.put("project-id", "xxx");
					jObj.put("title", "xxx");
					jObj.put("description", "xxx");
					jObj.put("status", "xxx");
					jObj.put("priority", "xxx");
					jObj.put("expected-time", "xxx");
					jObj.put("additional-time", "xxx");
					jObj.put("sprint-id", "xxx");
					jObj.put("user-id", "xxx");
					res.status(200);
				} else {
					jObj.put("message", "activity does not exist");
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

}
