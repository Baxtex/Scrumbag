package apiLayer;

import static spark.Spark.delete;
import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.put;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import businessLayer.Delete;
import businessLayer.Get;
import businessLayer.Post;
import businessLayer.Put;
import dataLayer.DataHandler;
import securityLayer.Security;
import spark.Response;

/**
 * This class is the interface of the rest api. It routes the request and
 * extracts the correct parameters and pass them to the Controller for further
 * processing. Base URI is: http://localhost:4567/
 * 
 * @author Anton Gustafsson
 *
 */
public class ApiV1 {
	
	private final int STATUSCODE_OK = 200;
	private final int STATUSCODE_CREATED = 201;
	private final int STATUSCODE_INVALID = 401;				// Resursen finns inte
	private final int STATUSCODE_UNAUTHORIZED = 403;		// Resursen finns, men får inte
	private final int STATUSCODE_DUPLICATE = 409;			// Resursen finns redan
	
	private final int OPERATION_LOGIN = 0;
	private final int OPERATION_LOGOUT = 1;
	private final int OPERATION_VALIDATEKEY = 3;
	private final int OPERATION_AUTHORIZATION = 4;
	private final int OPERATION_CREATEUSER = 5;
	
	private final Get get;
	private final Post post;
	private final Put put;
	private final Delete delete;
	private final Security security;

	public static void main(String[] args) {
		new ApiV1();
	}

	public ApiV1() {
		
		DataHandler dataHandler = new DataHandler();
		get = new Get(dataHandler);
		post = new Post(dataHandler);
		put = new Put(dataHandler);
		delete = new Delete(dataHandler);
		security = new Security();
		
		setupGetEndpoints();
		setupPostEndpoints();
		setupPutEndpoints();
		setupDeleteEndpoints();
	}

	/**
	 * Initializes routes for get requests.
	 */
	
	private void setupGetEndpoints() {
		
		// Get activities from a certain project

		get("/activities/:project-id/:key", (req, res) -> {
			
			String key = req.params(":key");
			String projectId = req.params(":project-id");
			
			if(!security.isValidKey(key)) {
				return createErrorMsg(OPERATION_VALIDATEKEY, key, res);
			} else {
				return get.getActivities(projectId, res);
			}
		});
		
		// Get a certain activity

		get("/activity/:activity-id/:key", (req, res) -> {
			
			String key = req.params(":key");
			String activityId = req.params(":activity-id");

			if(!security.isValidKey(key)) {
				return createErrorMsg(OPERATION_VALIDATEKEY, key, res);
			} else {
				return get.getActivity(activityId, res);
			}
		});
	}

	/**
	 * Initializes routes for post requests.
	 */
	
	private void setupPostEndpoints() {
		
		// Login user

		post("/login", (req, res) -> {
			
			String username = req.queryParams("username");
			String password = req.queryParams("password");
			String key = security.login(username, password);
			
			if(key == null) {
				return createErrorMsg(OPERATION_LOGIN, username,  res);
			} else {
				return createSuccessMsg(OPERATION_LOGIN, key, res);
			}
		});
		
		// Logout user

		post("/logout/:key", (req, res) -> {
			
			String key = req.params(":key");
			
			if(!security.logout(key)) {
				return createErrorMsg(OPERATION_LOGOUT, key, res);
			} else {
				return createSuccessMsg(OPERATION_LOGOUT, key, res);
			}
		});
		
		// Create a user

		post("/user/:key", (req, res) -> {
						
			String key = req.params(":key");
			String username = req.queryParams("username");
			String password = req.queryParams("password");
			String authority = req.queryParams("authority");
			
			if(!security.isValidKey(key)) {
				return createErrorMsg(OPERATION_VALIDATEKEY, key, res);
			}
			if(!security.hasAdminAuthority(key)) {
				return createErrorMsg(OPERATION_AUTHORIZATION, key, res);
			}	
			if(!security.createUser(username, password, authority)) {
				return createErrorMsg(OPERATION_CREATEUSER, username, res);
			} else {
				return createSuccessMsg(OPERATION_CREATEUSER, username, res);
			}
		});
		
		// Create a project

		post("/project/:key", (req, res) -> {
			
			String key = req.params(":key");
			String projectName = req.queryParams("project-name");
			
			if(!security.isValidKey(key)) {
				return createErrorMsg(OPERATION_VALIDATEKEY, key, res);
			}
			if(!security.hasAdminAuthority(key)) {
				return createErrorMsg(OPERATION_AUTHORIZATION, key, res);
			} else {
				return post.createProject(projectName, res);
			}
		});
		
		// Create a sprint

		post("/sprint/:key", (req, res) -> {
			
			String key = req.params(":key");
			
			if(!security.isValidKey(key)) {
				return createErrorMsg(OPERATION_VALIDATEKEY, key, res);
			} else {
				String projectId = req.queryParams("project-id");
				String sprintTitle = req.queryParams("title");
				String sprintIndex = req.queryParams("index");
				return post.createSprint(projectId, sprintTitle, sprintIndex, res);
			}
		});
	}

	/**
	 * Initializes routes for put requests.
	 */
	
	private void setupPutEndpoints() {
		
		// Change a project

		put("/project/:key", (req, res) -> {
			
			String key = req.params(":key");
			
			if(!security.isValidKey(key)) {
				return createErrorMsg(OPERATION_VALIDATEKEY, key, res);
			} else {
				String projectId = req.queryParams("project-id");
				String action = req.queryParams("action");
				String userIds = req.queryParams("user-ids");
				return put.userManagement(projectId, action, userIds, res);
			}
		});
		
		// Change an activity

		put("/activity/:key", (req, res) -> {
			
			String key = req.params(":key");
			
			if(!security.isValidKey(key)) {
				return createErrorMsg(OPERATION_VALIDATEKEY, key, res);
			} else {
				String activityId = req.queryParams("activity-id");
				String projectId = req.queryParams("project-id");
				String title = req.queryParams("activity-id");
				String description = req.queryParams("description");
				String status = req.queryParams("status");
				String priority = req.queryParams("priority");
				String expectedTime = req.queryParams("expected-time");
				String additionalTime = req.queryParams("additional-time");
				String sprintId = req.queryParams("sprint-id");
				String userId = req.queryParams("user-id");
				return put.editActivity(activityId, projectId, title, description, status,
								priority, expectedTime, additionalTime, sprintId, userId, res);
			}
		});

	}

	/**
	 * Initializes routes for delete requests.
	 */
	
	private void setupDeleteEndpoints() {
		
		// Remove an activity

		delete("/activity/:activity-id/:key", (req, res) -> {
			
			String key = req.params(":key");
			
			if(!security.isValidKey(key)) {
				return createErrorMsg(OPERATION_VALIDATEKEY, key, res);
			} else {
				String activityId = req.params(":activity-id");
				return delete.deleteActivity(activityId, res);
			}
		});
	}
	
	/**
	 * Returns a JSON object with error information depending on operation.
	 * @param operation during which the error occurred
	 * @param data relevant data to the error
	 * @param res the response header
	 * @return
	 */
	
	public JSONObject createErrorMsg(int operation, Object data, Response res) {
		JSONObject json = new JSONObject();
		res.type("application/json");
		
		try {
			switch(operation) {
				case OPERATION_LOGIN: {
					json.put("Message", "Failed to log in.");
					json.put("Username", data);
					res.status(STATUSCODE_INVALID);
					break;
				}
				case OPERATION_LOGOUT: {
					json.put("Message", "Failed to log out.");
					json.put("Key", data);
					res.status(STATUSCODE_INVALID);
					break;
				}
				case OPERATION_VALIDATEKEY: {
					json.put("Message", "Key is invalid.");
					json.put("Key", data);
					res.status(STATUSCODE_INVALID);
					break;
				}
				case OPERATION_AUTHORIZATION: {
					json.put("Message", "User is unauthorized to do this.");
					json.put("Key", data);
					res.status(STATUSCODE_UNAUTHORIZED);
					break;
				}
				case OPERATION_CREATEUSER: {
					json.put("Message", "Could not create user. Username is taken.");
					json.put("Username", data);
					res.status(STATUSCODE_DUPLICATE);
					break;
				}
			}
		} catch (JSONException e) {
			System.out.println("Failed when adding stuff to JSON object.");
		}

		return json;
	}
	
	/**
	 * Returns a JSON object with success information depending on operation.
	 * @param operation during which the error occurred
	 * @param data relevant data to the error
	 * @param res the response header
	 * @return
	 */
	
	public JSONObject createSuccessMsg(int operation, Object data, Response res) {
		JSONObject json = new JSONObject();
		res.type("application/json");
		
		try {
			switch(operation) {
				case OPERATION_LOGIN: {
					json.put("Message", "Successfully logged in.");
					json.put("Key", data);
					res.status(STATUSCODE_CREATED);
					break;
				}
				case OPERATION_LOGOUT: {
					json.put("Message", "Succefully logged out.");
					json.put("Key", data);
					res.status(STATUSCODE_OK);
					break;
				}
				case OPERATION_CREATEUSER: {
					json.put("Message", "User was successfully created.");
					json.put("Username", data);
					res.status(STATUSCODE_CREATED);
					break;
				}
			}
		} catch (JSONException e) {
			System.out.println("Failed when adding stuff to JSON object.");
		}
		
		return json;
	}
}
