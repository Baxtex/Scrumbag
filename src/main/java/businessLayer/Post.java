package businessLayer;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import dataLayer.DataHandler;
import spark.Response;

/**
 * This module handles post requests.
 *
 */

public class Post {

	private final int OPERATION_POST_PROJECT = 0;
	private final int OPERATION_POST_SPRINT = 1;
	private final int OPERATION_POST_ACTIVITY = 2;
	private final int STATUSCODE_CREATED = 201;
	private final int STATUSCODE_NO_RESOURCE = 404;
	private final DataHandler dataHandler;

	/**
	 * Constructor. Takes a data handler for communication with database.
	 * 
	 * @param dataHandler
	 */

	public Post(DataHandler dataHandler) {
		this.dataHandler = dataHandler;
	}

	/**
	 * Creates a new project and returns a JSONObject with project id.
	 * 
	 * @param projectName
	 * @param res
	 * @return
	 */

	public JSONObject createProject(String projectName, Response res) {
		String projectId = dataHandler.createProject(projectName);
		return createSuccessMessage(OPERATION_POST_PROJECT, projectId, res);
	}

	/**
	 * Creates a new sprint and returns a JSONObject with sprint id, or error
	 * information.
	 * 
	 * @param projectId
	 * @param title
	 * @param index
	 * @param res
	 * @return
	 */

	public JSONObject createSprint(String projectId, String title, String index, Response res) {
		if (!dataHandler.checkProjectId(projectId)) {
			return createErrorMessage(OPERATION_POST_SPRINT, title, res);
		}
		String sprintId = dataHandler.createSprint(projectId, title, Integer.parseInt(index));
		return createSuccessMessage(OPERATION_POST_SPRINT, sprintId, res);
	}

	/**
	 * Creates a new activity and returns a JSONObject with activity id, or
	 * error information.
	 * 
	 * @param projectId
	 * @param sprintId
	 * @param title
	 * @param description
	 * @param timeExpected
	 * @param timeSpent
	 * @param respUser
	 * @param status
	 * @param priority
	 * @param res
	 * @return
	 */

	public JSONObject createActivity(String projectId, String sprintId, String title, String description,
			String timeExpected, String timeSpent, String respUser, String status, String priority, Response res) {
		if (!dataHandler.checkProjectId(projectId)) {
			return createErrorMessage(OPERATION_POST_ACTIVITY, projectId, res);
		}
		if (!dataHandler.checkSprint(sprintId)) {
			return createErrorMessage(OPERATION_POST_ACTIVITY, sprintId, res);
		}
		String activityId = dataHandler.createActivity(projectId, sprintId, title, description, timeExpected, timeSpent,
				respUser, status, priority);
		return createSuccessMessage(OPERATION_POST_ACTIVITY, activityId, res);
	}

	private JSONObject createSuccessMessage(int operation, Object data, Response res) {
		JSONObject json = new JSONObject();
		res.type("application/json");
		try {
			switch (operation) {
			case OPERATION_POST_PROJECT: {
				json.put("Message", "Successfully created project.");
				json.put("Project-ID", data);
				res.status(STATUSCODE_CREATED);
				break;
			}
			case OPERATION_POST_SPRINT: {
				json.put("Message", "Successfully created sprint.");
				json.put("Sprint-ID", data);
				res.status(STATUSCODE_CREATED);
				break;
			}
			case OPERATION_POST_ACTIVITY: {
				json.put("Message", "Successfully created activity.");
				json.put("Activity-ID", data);
				res.status(STATUSCODE_CREATED);
				break;
			}
			}
		} catch (JSONException e) {
			System.out.println("Failed when adding stuff to JSON object.");
		}
		return json;
	}

	private JSONObject createErrorMessage(int operation, Object data, Response res) {
		JSONObject json = new JSONObject();
		res.type("application/json");
		try {
			switch (operation) {
			case OPERATION_POST_SPRINT: {
				json.put("Message", "Failed to create sprint. It seems project does not exist.");
				json.put("Sprint title", data);
				res.status(STATUSCODE_NO_RESOURCE);
				break;
			}
			case OPERATION_POST_ACTIVITY: {
				json.put("Message", "Failed to create activity. It seems some resource does not exist.");
				json.put("Resource id", data);
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
