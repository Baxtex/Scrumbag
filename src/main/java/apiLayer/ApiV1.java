package apiLayer;

import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.put;

import org.codehaus.jettison.json.JSONObject;

import static spark.Spark.delete;

import businessLayer.Delete;
import businessLayer.Get;
import businessLayer.Post;
import businessLayer.Put;
import dataLayer.DBHandler;
import securityLayer.SHandler;
import spark.Response;

/**
 * This class is the interface of the rest api. It routes the request and
 * extrats the correct parameters and pass them to the Controller for further
 * processing. Base URI is: http://localhost:4567/
 * 
 * @author Anton
 *
 */
public class ApiV1 {
	private final DBHandler dh = new DBHandler();
	private final SHandler sh = new SHandler();
	private final Get get = new Get(dh);
	private final Post post = new Post(dh);
	private final Put put = new Put(dh);
	private final Delete delete = new Delete(dh);

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

		get("/activities/:project-id/:key", (req, res) -> {
			boolean keyIsValid = sh.checkKey(req.params(":key"));
			if (keyIsValid) {
				return get.getActivities(req.params(":project-id"), res);
			} else {
				return createErrorJSON(res);
			}
		});

		get("/activity/:activity-id/:key", (req, res) -> {
			boolean keyIsValid = sh.checkKey(req.params(":key"));
			if (keyIsValid) {
				return get.getActivity(req.params(":activity-id"), res);
			} else {
				return createErrorJSON(res);
			}
		});
	}

	/**
	 * Initializes routes for post requests.
	 */
	private void setupPostEndpoints() {

		post("/login", (req, res) -> sh.login(req.queryParams("username"), req.queryParams("password"), res));

		post("/logout/:key", (req, res) -> {
			boolean keyIsValid = sh.checkKey(req.params(":key"));
			if (keyIsValid) {
				return sh.logout(res);
			} else {
				return createErrorJSON(res);
			}
		});

		post("/user/:key", (req, res) -> {
			boolean keyIsValid = sh.checkKey(req.params(":key"));
			if (keyIsValid) {
				return sh.createUser(req.queryParams("username"), req.queryParams("password"), res);
			} else {
				return createErrorJSON(res);
			}
		});

		post("/project/:key", (req, res) -> {
			boolean keyIsValid = sh.checkKey(req.params(":key"));
			if (keyIsValid) {
				return post.createProject(req.queryParams("project-name"), res);
			} else {
				return createErrorJSON(res);
			}
		});

		post("/sprint/:key", (req, res) -> {
			boolean keyIsValid = sh.checkKey(req.params(":key"));
			if (keyIsValid) {
				return post.createSprint(req.queryParams("project-id"), req.queryParams("title"),
						req.queryParams("index"), res);
			} else {
				return createErrorJSON(res);
			}
		});
	}

	/**
	 * Initializes routes for put requests.
	 */
	private void setupPutEndpoints() {

		put("/project/:key", (req, res) -> {
			boolean keyIsValid = sh.checkKey(req.params(":key"));
			if (keyIsValid) {
				return put.userManagement(req.queryParams("project-id"), req.queryParams("action"),
						req.queryParams("user-ids"), res);
			} else {
				return createErrorJSON(res);
			}
		});

		put("/activity/:key", (req, res) -> {
			boolean keyIsValid = sh.checkKey(req.params(":key"));
			if (keyIsValid) {
				return put.editActivity(req.queryParams("activity-id"), req.queryParams("project-id"),
						req.queryParams("title"), req.queryParams("description"), req.queryParams("status"),
						req.queryParams("priority"), req.queryParams("expected-time"),
						req.queryParams("additional-time"), req.queryParams("sprint-id"), req.queryParams("user-id"),
						res);
			} else {
				return createErrorJSON(res);
			}
		});

	}

	/**
	 * Initializes routes for delete requests.
	 */
	private void setupDeleteEndpoints() {

		delete("/activity/:activity-id/:key", (req, res) -> {
			boolean keyIsValid = sh.checkKey(req.params(":key"));
			if (keyIsValid) {
				return delete.deleteActivity(req.params(":activity-id"), res);
			} else {
				return createErrorJSON(res);
			}
		});
	}

	/**
	 * If key isn't valid, create and return a json object containing an error
	 * message and status code.
	 * 
	 * @param res
	 * @return
	 */
	public JSONObject createErrorJSON(Response res) {
		JSONObject jObj = new JSONObject();
		try {
			jObj.put("message", "key does not exist");
			res.status(401);
		} catch (Exception e) {
			e.printStackTrace();
		}
		res.type("application/json");
		return jObj;
	}
}
