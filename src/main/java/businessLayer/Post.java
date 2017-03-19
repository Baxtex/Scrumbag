package businessLayer;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import dataLayer.DataHandler;
import resource.Status;
import spark.Response;

/**
 * This module handles post requests.
 *
 */

public class Post {

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
		return createSuccessMessage(Status.POST_PROJECT, projectId, res);
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
			return createErrorMessage(Status.POST_SPRINT, title, res);
		}
		String sprintId = dataHandler.createSprint(projectId, title, Integer.parseInt(index));
		return createSuccessMessage(Status.POST_SPRINT, sprintId, res);
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
			return createErrorMessage(Status.POST_ACTIVITY, projectId, res);
		}
		if (!dataHandler.checkSprint(sprintId)) {
			return createErrorMessage(Status.POST_ACTIVITY, sprintId, res);
		}
		String activityId = dataHandler.createActivity(projectId, sprintId, title, description, timeExpected, timeSpent,
				respUser, status, priority);
		return createSuccessMessage(Status.POST_ACTIVITY, activityId, res);
	}

	private JSONObject createSuccessMessage(Status operation, Object data, Response res) {
		JSONObject json = new JSONObject();
		res.type("application/json");
		try {
			if (Status.POST_PROJECT == operation) {
				json.put("Message", "Successfully created project.");
				json.put("Project-ID", data);
			} else if (Status.POST_SPRINT == operation) {
				json.put("Message", "Successfully created sprint.");
				json.put("Sprint-ID", data);
			} else if (Status.POST_ACTIVITY == operation) {
				json.put("Message", "Successfully created activity.");
				json.put("Activity-ID", data);
			}
			res.status(Status.CREATED.code());
		} catch (JSONException e) {
			System.out.println("Failed when adding stuff to JSON object.");
		}
		return json;
	}

	private JSONObject createErrorMessage(Status operation, Object data, Response res) {
		JSONObject json = new JSONObject();
		res.type("application/json");
		try {

			if (Status.POST_SPRINT == operation) {
				json.put("Message", "Failed to create sprint. It seems project does not exist.");
				json.put("Sprint title", data);
			} else if (Status.POST_ACTIVITY == operation) {
				json.put("Message", "Failed to create activity. It seems some resource does not exist.");
				json.put("Resource id", data);
			}
			res.status(Status.NO_RESOURCE.code());
		} catch (JSONException e) {
			System.out.println("Failed when adding stuff to JSON object.");
		}
		return json;
	}
}
