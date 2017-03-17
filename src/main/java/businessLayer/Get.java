package businessLayer;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

import dataLayer.Activity;
import dataLayer.DataHandler;
import spark.Response;

public class Get {

	private final DataHandler dataHandler;

	public Get(DataHandler dataHandler) {
		this.dataHandler = dataHandler;
	}

	public JSONObject getActivities(String pID, Response res) {
		JSONObject jObj = new JSONObject();
		try {
			if (dataHandler.checkProjectId(pID)) {
				Activity[] activities = dataHandler.getActivities(pID);
				JSONArray jsonArray = new JSONArray();
				for(int i = 0; i < activities.length; i++){
					JSONObject jObjActivity = new JSONObject();
					jObjActivity.put("activity-id", activities[i].getActivityId());
					jObjActivity.put("title", activities[i].getTitle());
					jObjActivity.put("status", activities[i].getStatus());
					jsonArray.put(jObjActivity);
				}
				jObj.put("project-id", pID);
				jObj.put("activities", jsonArray);
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
		Activity activity;
		try {
			if (dataHandler.checkActivityId(aID)) {
				activity = dataHandler.getActivity(aID);
				jObj.put("activity-id", activity.getActivityId());
				jObj.put("project-id", activity.getProjectId());
				jObj.put("title", activity.getTitle());
				jObj.put("description", activity.getDescription());
				jObj.put("status", activity.getStatus());
				jObj.put("priority", activity.getPriority());
				jObj.put("expected-time", activity.getTimeExpected());
				jObj.put("sprint-id", activity.getSprintId());
				jObj.put("user-id", activity.getRespUser());
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
