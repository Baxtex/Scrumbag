package businessLayer;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import dataLayer.DataHandler;
import spark.Response;

public class Delete {
	
	private final int OPERATION_ACTIVITY = 0;
	private final int STATUSCODE_OK = 200;
	private final int STATUSCODE_NO_RESOURCE = 404;		

	private final DataHandler dataHandler;

	public Delete(DataHandler dataHandler) {
		this.dataHandler = dataHandler;
	}
	
	/**
	 * Removes an activity and returns a JSONObject with result message and status code.
	 * @param activityId the id of the activity to remove
	 * @param res the response message
	 * @return
	 */

	public JSONObject deleteActivity(String activityId, Response res) {
		
		if (dataHandler.checkActivityId(activityId)) {
			dataHandler.deleteActivity();
			return createSuccessMessage(OPERATION_ACTIVITY, activityId, res);
		} else {
			return createErrorMessage(OPERATION_ACTIVITY, activityId, res);
		}
	}
	
	/**
	 * Returns a success JSONObject corresponding to the specified operation and data.
	 * @param operation
	 * @param data
	 * @param res
	 * @return
	 */
	
	private JSONObject createSuccessMessage(int operation, Object data, Response res) {
		JSONObject json = new JSONObject();
		res.type("application/json");
		try {
			switch(operation) {
				case OPERATION_ACTIVITY: {
					json.put("Message", "Successfully removed activity.");
					json.put("Activity-ID", data);
					res.status(STATUSCODE_OK);
					break;
				}
			}
		} catch (JSONException e) {
			System.out.println("Failed when adding stuff to JSON object.");
		}
		return json;
	}
	
	/**
	 * Returns a error JSONObject corresponding to the specified operation and data.
	 * @param operation
	 * @param data
	 * @param res
	 * @return
	 */
	
	private JSONObject createErrorMessage(int operation, Object data, Response res) {
		JSONObject json = new JSONObject();
		res.type("application/json");
		try {
			switch(operation) {
				case OPERATION_ACTIVITY: {
					json.put("Message", "Failed to remove activity. It seems it does not exist.");
					json.put("Activity-ID", data);
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
