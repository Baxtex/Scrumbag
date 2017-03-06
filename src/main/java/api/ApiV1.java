package api;

import static spark.Spark.*;

import controller.Controller;

// The URL for this is: http://localhost:4567/
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

		/**
		 * POST endpoints
		 */
		post("/login", (req, res) -> ctrl.login(req.queryParams("username"), req.queryParams("password"), res));

		post("/logout/:key", (req, res) -> ctrl.logout(req.params(":key"), res));

		post("/user/:key", (req, res) -> ctrl.createUser(req.queryParams("username"), req.queryParams("password"),
				req.params(":key"), res));
		/**
		 * PUT endpoints
		 */

		/**
		 * DELETE endpoints
		 */
	}
}
