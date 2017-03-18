package businessLayer;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import dataLayer.Activity;
import dataLayer.DataHandler;
import resource.Status;
import spark.Response;

/**
 * This module handles get requests.
 *
 */
public class Get {

	private final DataHandler dataHandler;

	public Get(DataHandler dataHandler) {
		this.dataHandler = dataHandler;
	}

	/**
	 * Returns a JSONObject with information about all activities in a certain
	 * project.
	 * 
	 * @param projectId the id of the project which activities to return
	 * @param res the response message
	 * @return
	 */

	public JSONObject getActivities(String projectId, Response res) {
		if (dataHandler.checkProjectId(projectId)) {
			return createSuccessMessage(Status.GET_ACTIVITIES.code(), dataHandler.getActivities(projectId), res);
		} else {
			return createErrorMessage(Status.GET_ACTIVITIES.code(), projectId, res);
		}
	}

	/**
	 * Returns a JSONObject with activity information and status code.
	 * 
	 * @param activityId the id of the activity to return
	 * @param res the response message
	 * @return
	 */

	public JSONObject getActivity(String activityId, Response res) {
		if (dataHandler.checkActivityId(activityId)) {
			return createSuccessMessage(Status.GET_ACTIVITY.code(), dataHandler.getActivity(activityId), res);
		} else {
			return createErrorMessage(Status.GET_ACTIVITY.code(), activityId, res);
		}
	}

	/**
	 * Returns a success JSONObject corresponding to the specified operation and
	 * data.
	 * 
	 * @param operation the kind of operation that has been successful
	 * @param data some relevant data to be displayed in response message
	 * @param res the response that is going to be sent back to client
	 * @return
	 */

	private JSONObject createSuccessMessage(int operation, Object data, Response res) {
		JSONObject json = new JSONObject();
		res.type("application/json");
		try {

			if (Status.GET_ACTIVITY.code() == operation) {
				Activity activity = (Activity) data;
				json.put("Message", "Successfully returned activity.");
				json.put("Activity-ID", activity.getActivityId());
				json.put("Project-ID", activity.getProjectId());
				json.put("Title", activity.getTitle());
				json.put("Description", activity.getDescription());
				json.put("Status", activity.getStatus());
				json.put("Priority", activity.getPriority());
				json.put("Expected-time", activity.getTimeExpected());
				json.put("Sprint-id", activity.getSprintId());
				json.put("User-ID", activity.getRespUser());
				res.status(Status.OK.code());
			} else if (Status.GET_ACTIVITIES.code() == operation) {
				Activity[] activities = (Activity[]) data;
				JSONArray jsonArray = new JSONArray();
				for (Activity activity : activities) {
					JSONObject jsonActivity = new JSONObject();
					jsonActivity.put("Activity-ID", activity.getActivityId());
					jsonActivity.put("Title", activity.getTitle());
					jsonActivity.put("Status", activity.getStatus());
					jsonArray.put(jsonActivity);
				}
				json.put("Message", "Successfully returned activites.");
				json.put("Activities", jsonArray);

			}

		} catch (JSONException e) {
			System.out.println("Failed when adding stuff to JSON object.");
		}
		return json;
	}

	/**
	 * Returns a error JSONObject corresponding to the specified operation and
	 * data.
	 * 
	 * @param operation the kind of operation that has failed
	 * @param data some relevant data to be displayed in response message
	 * @param res the response that is going to be sent back to client
	 * @return
	 */

	private JSONObject createErrorMessage(int operation, Object data, Response res) {
		JSONObject json = new JSONObject();
		res.type("application/json");
		try {

			if (Status.GET_ACTIVITY.code() == operation) {
				json.put("Message", "Failed to return activity. It seems it does not exist.");
				json.put("Activity-ID", data);
				res.status(Status.NO_RESOURCE.code());
			} else if (Status.GET_ACTIVITIES.code() == operation) {
				json.put("Message", "Failed to return activities. It seems project does not exist.");
				json.put("Project-ID", data);
				res.status(Status.NO_RESOURCE.code());
			}

		} catch (JSONException e) {
			System.out.println("Failed when adding stuff to JSON object.");
		}
		return json;
	}
}
