package apiLayer;

import static spark.Spark.delete;
import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.put;

import businessLayer.Delete;
import businessLayer.Get;
import businessLayer.Post;
import businessLayer.Put;
import dataLayer.DataHandler;
import resource.Status;
import securityLayer.Security;

/**
 * This class is the interface of the rest api. It routes the request and
 * extracts the correct parameters and pass them to the Controller for further
 * processing. Base URI is: http://localhost:4567/
 * 
 *
 */
public class ApiV1 {

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

		// Get all project, their names and id.
		get("/projects", (req, res) -> {
			return get.getProjects(res);
		});

		// Get activities from a certain project

		get("/activities/:project-id/:key", (req, res) -> {

			String key = req.params(":key");
			String projectId = req.params(":project-id");

			if (!security.isValidKey(key)) {
				return security.createErrorMsg(Status.VALIDATEKEY, key, res);
			} else {
				return get.getActivities(projectId, res);
			}
		});

		// Get a certain activity

		get("/activity/:activity-id/:key", (req, res) -> {
			String key = req.params(":key");
			String activityId = req.params(":activity-id");

			if (!security.isValidKey(key)) {
				return security.createErrorMsg(Status.VALIDATEKEY, key, res);
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

			if (key == null) {
				return security.createErrorMsg(Status.LOGIN, username, res);
			} else {
				return security.createSuccessMsg(Status.LOGIN, key, res);
			}
		});

		// Logout user

		post("/logout/:key", (req, res) -> {

			String key = req.params(":key");

			if (!security.logout(key)) {
				return security.createErrorMsg(Status.LOGOUT, key, res);
			} else {
				return security.createSuccessMsg(Status.LOGOUT, key, res);
			}
		});

		// Create a user

		post("/user/:key", (req, res) -> {

			String key = req.params(":key");
			String username = req.queryParams("username");
			String password = req.queryParams("password");
			String authority = req.queryParams("authority");

			if (!security.isValidKey(key)) {
				return security.createErrorMsg(Status.VALIDATEKEY, key, res);
			}
			if (!security.hasAdminAuthority(key)) {
				return security.createErrorMsg(Status.AUTHORIZATION, key, res);
			}
			if (!security.createUser(username, password, authority)) {
				return security.createErrorMsg(Status.CREATEUSER, username, res);
			} else {
				return security.createSuccessMsg(Status.CREATEUSER, username, res);
			}
		});

		// Create a project

		post("/project/:key", (req, res) -> {

			String key = req.params(":key");
			String projectName = req.queryParams("project-name");

			if (!security.isValidKey(key)) {
				return security.createErrorMsg(Status.VALIDATEKEY, key, res);
			}
			if (!security.hasAdminAuthority(key)) {
				return security.createErrorMsg(Status.AUTHORIZATION, key, res);
			} else {
				return post.createProject(projectName, res);
			}
		});

		// Create an activity

		post("/activity/:key", (req, res) -> {

			String key = req.params(":key");

			if (!security.isValidKey(key)) {
				return security.createErrorMsg(Status.VALIDATEKEY, key, res);
			} else {
				String projectId = req.queryParams("project-id");
				String sprintId = req.queryParams("sprint-id");
				String title = req.queryParams("title");
				String description = req.queryParams("description");
				String timeExpected = req.queryParams("timeExpected");
				String timeSpent = req.queryParams("timeSpent");
				String respUser = req.queryParams("respUser");
				String status = req.queryParams("status");
				String priority = req.queryParams("priority");
				return post.createActivity(projectId, sprintId, title, description, timeExpected, timeSpent, respUser,
						status, priority, res);
			}
		});

		// Create a sprint

		post("/sprint/:key", (req, res) -> {

			String key = req.params(":key");

			if (!security.isValidKey(key)) {

				return security.createErrorMsg(Status.VALIDATEKEY, key, res);
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

			if (!security.isValidKey(key)) {
				return security.createErrorMsg(Status.VALIDATEKEY, key, res);
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

			if (!security.isValidKey(key)) {
				return security.createErrorMsg(Status.VALIDATEKEY, key, res);
			} else {
				String activityId = req.queryParams("activity-id");
				String projectId = req.queryParams("project-id");
				String title = req.queryParams("title");
				String description = req.queryParams("description");
				String status = req.queryParams("status");
				String priority = req.queryParams("priority");
				String expectedTime = req.queryParams("expected-time");
				String additionalTime = req.queryParams("additional-time");
				String sprintId = req.queryParams("sprint-id");
				String userId = req.queryParams("user-id");
				return put.editActivity(activityId, projectId, title, description, status, priority, expectedTime,
						additionalTime, sprintId, userId, res);
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

			if (!security.isValidKey(key)) {
				return security.createErrorMsg(Status.VALIDATEKEY, key, res);
			} else {
				String activityId = req.params(":activity-id");
				return delete.deleteActivity(activityId, res);
			}
		});
	}
}
