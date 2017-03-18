package businessLayer;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import dataLayer.Activity;
import dataLayer.DataHandler;
import dataLayer.Project;
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

	public JSONObject getProjects(Response res) {

		return createSuccessMessage(Status.GET_PROJECTS, dataHandler.getProjects(), res);

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
			return createSuccessMessage(Status.GET_ACTIVITIES, dataHandler.getActivities(projectId), res);
		} else {
			return createErrorMessage(Status.GET_ACTIVITIES, projectId, res);
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
			return createSuccessMessage(Status.GET_ACTIVITY, dataHandler.getActivity(activityId), res);
		} else {
			return createErrorMessage(Status.GET_ACTIVITY, activityId, res);
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

	private JSONObject createSuccessMessage(Status operation, Object data, Response res) {
		JSONObject json = new JSONObject();
		res.type("application/json");
		try {

			if (Status.GET_ACTIVITY == operation) {
				Activity activity = (Activity) data; // TODO MIGHT NEED FIX
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
			} else if (Status.GET_ACTIVITIES == operation) {
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

			} else if (Status.GET_PROJECTS == operation) {
				Project[] arr = (Project[]) data;
				JSONObject innerJson = new JSONObject();
				for (int i = 0; i < arr.length; i++) {
					Project tmp = arr[i];
					innerJson.put("project-name", tmp.getName());
					innerJson.put("project-id", tmp.getProjectId());
					json.append("projekt", innerJson);
				}
			}
			res.status(Status.OK.code());

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

	private JSONObject createErrorMessage(Status operation, Object data, Response res) {
		JSONObject json = new JSONObject();
		res.type("application/json");
		try {

			if (Status.GET_ACTIVITY == operation) {
				json.put("Message", "Failed to return activity. It seems it does not exist.");
				json.put("Activity-ID", data);

			} else if (Status.GET_ACTIVITIES == operation) {
				json.put("Message", "Failed to return activities. It seems project does not exist.");
				json.put("Project-ID", data);
			}

			res.status(Status.NO_RESOURCE.code());
		} catch (JSONException e) {
			System.out.println("Failed when adding stuff to JSON object.");
		}
		return json;
	}
}
