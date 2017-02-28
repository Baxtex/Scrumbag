package api;

import static spark.Spark.get;

import org.codehaus.jettison.json.JSONObject;

public class ApiV1 {
	// Good tutorial: https://dzone.com/articles/building-simple-restful-api
	public static void main(String[] args) {
		// The URL for this is: http://localhost:4567/hello
		get("/hello", (req, res) -> "Hello World!");
		get("/helloAgain", (req, res) -> "Hello Again!");

		// Create Json object and return it.
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("message", "Hello Json!");
			jsonObject.put("antoher message", "Hello again Json!");
		} catch (Exception e) {
			e.printStackTrace();
		}

		get("/helloJson", (req, res) -> jsonObject);
	}
}
