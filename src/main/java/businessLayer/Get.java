package businessLayer;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import dataLayer.Activity;
import dataLayer.DataHandler;
import spark.Response;

public class Get {

	private final int OPERATION_GET_ACTIVITY = 0;
	private final int OPERATION_GET_ACTIVITIES = 1;
	private final int STATUSCODE_OK = 200;
	private final int STATUSCODE_NO_RESOURCE = 404;	
	private final DataHandler dataHandler;

	public Get(DataHandler dataHandler) {
		this.dataHandler = dataHandler;
	}
	
	/**
	 * Returns a JSONObject with information about all activities in a certain project.
	 * @param projectId the id of the project which activities to return
	 * @param res the response message
	 * @return
	 */

	public JSONObject getActivities(String projectId, Response res) {
		if(dataHandler.checkProjectId(projectId)) {
			return createSuccessMessage(OPERATION_GET_ACTIVITIES, dataHandler.getActivities(projectId), res);
		} else {
			return createErrorMessage(OPERATION_GET_ACTIVITIES, projectId, res);
		}
	}
	
	/**
	 * Returns a JSONObject with activity information and status code.
	 * @param activityId the id of the activity to return
	 * @param res the response message
	 * @return
	 */

	public JSONObject getActivity(String activityId, Response res) {
		if(dataHandler.checkActivityId(activityId)) {
			return createSuccessMessage(OPERATION_GET_ACTIVITY, dataHandler.getActivity(activityId), res);
		} else {
			return createErrorMessage(OPERATION_GET_ACTIVITY, activityId, res);
		}
	}
	
	/**
	 * Returns a success JSONObject corresponding to the specified operation and data.
	 * @param operation the kind of operation that has been successful
	 * @param data some relevant data to be displayed in response message
	 * @param res the response that is going to be sent back to client
	 * @return
	 */
	
	private JSONObject createSuccessMessage(int operation, Object data, Response res) {
		JSONObject json = new JSONObject();
		res.type("application/json");
		try {
			switch(operation) {
				case OPERATION_GET_ACTIVITY: {
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
					res.status(STATUSCODE_OK);
					break;
				}
				case OPERATION_GET_ACTIVITIES: {
					Activity[] activities = (Activity[]) data;					
					JSONArray jsonArray = new JSONArray();
					for(int i = 0; i < activities.length; i++){
						JSONObject jsonActivity = new JSONObject();
						jsonActivity.put("Activity-ID", activities[i].getActivityId());
						jsonActivity.put("Title", activities[i].getTitle());
						jsonActivity.put("Status", activities[i].getStatus());
						jsonArray.put(jsonActivity);
					}
					json.put("Message", "Successfully returned activites.");
					json.put("Activities", jsonArray);
				}
			}
		} catch (JSONException e) {
			System.out.println("Failed when adding stuff to JSON object.");
		}
		return json;
	}
	
	/**
	 * Returns a error JSONObject corresponding to the specified operation and data.
	 * @param operation the kind of operation that has failed
	 * @param data some relevant data to be displayed in response message
	 * @param res the response that is going to be sent back to client
	 * @return
	 */
	
	private JSONObject createErrorMessage(int operation, Object data, Response res) {
		JSONObject json = new JSONObject();
		res.type("application/json");
		try {
			switch(operation) {
				case OPERATION_GET_ACTIVITY: {
					json.put("Message", "Failed to return activity. It seems it does not exist.");
					json.put("Activity-ID", data);
					res.status(STATUSCODE_NO_RESOURCE);
					break;
				}
				case OPERATION_GET_ACTIVITIES: {
					json.put("Message", "Failed to return activities. It seems project does not exist.");
					json.put("Project-ID", data);
					res.status(STATUSCODE_NO_RESOURCE);
					break;
				}
			}
		} catch (JSONException e) {
			System.out.println("Failed when adding stuff to JSON object.");
		}
		return json;
	}	
}
