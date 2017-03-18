package businessLayer;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import dataLayer.DataHandler;
import resource.Status;
import spark.Response;

/**
 * This module handles delete requests.
 *
 */

public class Delete {

	private final int OPERATION_DELETE_ACTIVITY = 0;
	private final int STATUSCODE_OK = 200;
	private final int STATUSCODE_NO_RESOURCE = 404;

	private final DataHandler dataHandler;

	public Delete(DataHandler dataHandler) {
		this.dataHandler = dataHandler;
	}

	/**
	 * Removes an activity and returns a JSONObject with result message and
	 * status code.
	 * 
	 * @param activityId the id of the activity to remove
	 * @param res the response message
	 * @return
	 */

	public JSONObject deleteActivity(String activityId, Response res) {

		if (dataHandler.checkActivityId(activityId)) {
			dataHandler.deleteActivity(activityId);
			return createSuccessMessage(Status.OPERATION_DELETE_ACTIVITY.getVal(), activityId, res);
		} else {
			return createErrorMessage(Status.OPERATION_DELETE_ACTIVITY.getVal(), activityId, res);
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
			switch (operation) {
			case 0: { //TODO CHECK THIS WITH KAJSA!
				json.put("Message", "Successfully removed activity.");
				json.put("Activity-ID", data);
				res.status(Status.STATUSCODE_OK.getVal());
				break;
			}
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
			switch (operation) {
			case OPERATION_DELETE_ACTIVITY: {
				json.put("Message", "Failed to remove activity. It seems it does not exist.");
				json.put("Activity-ID", data);
				res.status(Status.STATUSCODE_NO_RESOURCE.getVal());
				break;
			}
			}
		} catch (JSONException e) {
			System.out.println("Failed when adding stuff to JSON object.");
		}
		return json;
	}
}
