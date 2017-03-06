package api;

import static spark.Spark.post;

import controller.Controller;

public class ApiV1 {
	private Controller contrl = new Controller();
	// Good tutorial: https://dzone.com/articles/building-simple-restful-api

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
		post("/login", (req, res) -> contrl.login(req.params(":username"), req.params(":password"), res));

		post("/hello", (req, res) -> {
	
			String str = 	req.attribute("username");
			System.out.println(str);
			return str;
		});
		post("/logout/:key", (req, res) -> contrl.logout(req.params(":key"), res));	
		post("/user/:key", (req, res) ->  contrl.createUser(req.params(":username"), req.params(":password"), req.params(":key"), res ));
	}
}
