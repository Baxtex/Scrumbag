package api;

import static spark.Spark.post;

import org.codehaus.jettison.json.JSONObject;

import controller.Controller;

public class ApiV1 {
	private Controller contrl = new Controller();
	// Good tutorial: https://dzone.com/articles/building-simple-restful-api
	// Good rest client software https://insomnia.rest/download/#windows
	// The URL for this is: http://localhost:4567/	

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
		 * POST
		 */
		post("/login", (req, res) -> {
			JSONObject json = contrl.login(req.params(":username"), req.params(":password"));
			res.type("application/json");
			int statCode = (json.has("key")) ? 200 : 401;
			res.status(statCode);
			return json;
		});
	}
}
