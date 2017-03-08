package apiLayer;

import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.put;
import static spark.Spark.delete;


import businessLayer.Delete;
import businessLayer.Get;
import businessLayer.Post;
import businessLayer.Put;

/**
 * This class is the interface of the rest api. It routes the request and
 * extrats the correct parameters and pass them to the Controller for further
 * processing. Base URI is: http://localhost:4567/
 * 
 * @author Anton
 *
 */
public class ApiV1 {
	private final static Get get = new Get();
	private final static Post post = new Post();
	private final static Put put = new Put();
	private final static Delete delete = new Delete();

	public static void main(String[] args) {
		new ApiV1();
	}

	public ApiV1() {
		setupGetEndpoints();
		setupPostEndpoints();
		setupPutEndpoints();
		setupDeleteEndpoints();
	}

	/**
	 * Initializes routes for get requests.
	 */
	private void setupGetEndpoints() {
		get("/activities/:project-id/:key",
				(req, res) -> get.getActivities(req.params(":project-id"), req.params(":key"), res));

		get("/activity/:activity-id/:key",
				(req, res) -> get.getActivity(req.params(":activity-id"), req.params(":key"), res));
	}

	/**
	 * Initializes routes for post requests.
	 */
	private void setupPostEndpoints() {
		post("/login", (req, res) -> post.login(req.queryParams("username"), req.queryParams("password"), res));

		post("/logout/:key", (req, res) -> post.logout(req.params(":key"), res));

		post("/user/:key", (req, res) -> post.createUser(req.queryParams("username"), req.queryParams("password"),
				req.params(":key"), res));

		post("/project/:key",
				(req, res) -> post.createProject(req.params(":key"), req.queryParams("project-name"), res));

		post("/sprint/:key", (req, res) -> post.createSprint(req.params(":key"), req.queryParams("project-id"),
				req.queryParams("title"), req.queryParams("index"), res));
	}

	/**
	 * Initializes routes for put requests.
	 */
	private void setupPutEndpoints() {
		put("/project/:key", (req, res) -> put.userManagement(req.params(":key"), req.queryParams("project-id"),
				req.queryParams("action"), req.queryParams("user-ids"), res));

		put("/activity/:key", (req, res) -> put.editActivity(req.params(":key"), req.queryParams("activity-id"),
				req.queryParams("project-id"), req.queryParams("title"), req.queryParams("description"),
				req.queryParams("status"), req.queryParams("priority"), req.queryParams("expected-time"),
				req.queryParams("additional-time"), req.queryParams("sprint-id"), req.queryParams("user-id"), res));
	}

	/**
	 * Initializes routes for delete requests. TODO: Not yet implemented,
	 * specification is lacking.
	 */
	private void setupDeleteEndpoints() {
		 delete("/activity/:activity-id/:key", (req, res) ->
		 delete.deleteActivity(req.params(":activity-id"), req.params(":key"),
		 res));
	}
}
