package businessLayer;

import org.codehaus.jettison.json.JSONObject;

import dataLayer.DBHandler;
import spark.Response;

public class Get {

	private final DBHandler dh;

	public Get(DBHandler dh) {
		this.dh = dh;
	}

	public JSONObject getActivities(String pID, Response res) {
		JSONObject jObj = new JSONObject();
		try {
			if (dh.checkProjectId(pID)) {
				dh.getActivities();
				jObj.put("activity-id", "abcdf");
				jObj.put("title", "this is a valid activity");
				jObj.put("status", "planned");
				res.status(200);
			} else {
				jObj.put("message", "activity does not exist");
				res.status(404);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		res.type("application/json");
		return jObj;
	}

	public JSONObject getActivity(String aID, Response res) {
		JSONObject jObj = new JSONObject();
		try {
			if (dh.checkActivityId(aID)) {
				dh.getActivity();
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
		} catch (Exception e) {
			e.printStackTrace();
		}
		res.type("application/json");
		return jObj;
	}
}
