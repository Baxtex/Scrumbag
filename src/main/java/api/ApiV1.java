package api;

import static spark.Spark.*;

import controller.Controller;

// The URI for this is: http://localhost:4567/
public class ApiV1 {
	private Controller ctrl = new Controller();

	public static void main(String[] args) {
		new ApiV1();
	}

	public ApiV1() {
		setupEndpoints();
	}

	/**
	 * Setups all the endpoints for the API.
	 */
	private void setupEndpoints() {

		/**
		 * GET endpoints
		 */
		get("/activities/:project-id/:key",
				(req, res) -> ctrl.getActivities(req.params(":project-id"), req.params(":key"), res));

		get("/activity/:activity-id/:key",
				(req, res) -> ctrl.getActivity(req.params(":activity-id"), req.params(":key"), res));

		/**
		 * POST endpoints
		 */

		post("/login", (req, res) -> ctrl.login(req.queryParams("username"), req.queryParams("password"), res));

		post("/logout/:key", (req, res) -> ctrl.logout(req.params(":key"), res));

		post("/user/:key", (req, res) -> ctrl.createUser(req.queryParams("username"), req.queryParams("password"),
				req.params(":key"), res));

		post("/project/:key",
				(req, res) -> ctrl.createProject(req.params(":key"), req.queryParams("project-name"), res));

		post("/sprint/:key", (req, res) -> ctrl.createSprint(req.params(":key"), req.queryParams("project-id"),
				req.queryParams("title"), req.queryParams("index"), res));
		/**
		 * PUT endpoints
		 */

		put("/project/:key", (req, res) -> ctrl.userManagement(req.params(":key"), req.queryParams("project-id"),
				req.queryParams("action"), req.queryParams("user-ids"), res));

		/**
		 * DELETE endpoints
		 */
	}
}
