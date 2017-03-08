package businessLayer;

import org.codehaus.jettison.json.JSONObject;

import spark.Response;

public class Delete {

	public Object deleteActivity(String aID, String key, Response res) {
		JSONObject jObj = new JSONObject();
		try {
			if (checkIfUser(key)) {
				if (checkActivityId(aID)) {
					jObj.put("message", "activity deleted");
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

	private boolean checkActivityId(String id) {
		if (id.equals("validActivityId")) {
			return true;
		} else {
			return false;
		}
	}

}
