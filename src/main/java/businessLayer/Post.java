package businessLayer;

import org.codehaus.jettison.json.JSONObject;

import spark.Response;

public class Post {
	public JSONObject login(String username, String password, Response res) {
		String key = "abc";
		JSONObject jObj = new JSONObject();
		try {
			if (username.equals("invalid") && password.equals("invalid")) {
				jObj.put("message", "user does not exist");
				res.status(404);
			} else {
				jObj.put("key", key);
				res.status(200);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		res.type("application/json");
		return jObj;
	}

	public JSONObject logout(String key, Response res) {
		JSONObject jObj = new JSONObject();
		try {
			if (key.equals("validKey")) {
				jObj.put("message", "user logged out");
				res.status(200);
			} else {
				jObj.put("message", "user does not exist");
				res.status(401);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		res.type("application/json");
		return jObj;
	}

	public JSONObject createUser(String username, String password, String key, Response res) {
		JSONObject jObj = new JSONObject();
		try {

			if (checkIfAdmin(key)) {
				if (createUser(username, password)) {
					jObj.put("user-id", "XXX");
					res.status(201);
				} else {
					jObj.put("message", "not allowed");
					res.status(403);
				}
			} else {
				jObj.put("message", "key does not exist");
				res.status(401);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		res.type("application/json");
		return jObj;
	}

	public JSONObject createProject(String key, String projectName, Response res) {
		JSONObject jObj = new JSONObject();
		try {
			if (checkIfAdmin(key)) {
				if (projectName.equals("invalid")) {
					jObj.put("message", "not allowed");
					res.status(403);
				} else {
					jObj.put("project-id", "XXX");
					res.status(201);
				}
			} else {
				jObj.put("message", "key does not exist");
				res.status(401);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		res.type("application/json");
		return jObj;
	}

	public JSONObject createSprint(String key, String pID, String title, String index, Response res) {
		JSONObject jObj = new JSONObject();

		try {
			if (checkIfUser(key)) {
				if (checkProjectId(pID)) {
					jObj.put("sprint-id", "xxx");
					res.status(201);
				} else {
					jObj.put("message", "project does not exist");
					res.status(404);
				}
			} else {
				jObj.put("message", "key does not exist");
				res.status(401);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		res.type("application/json");
		return jObj;
	}

	private boolean checkIfAdmin(String key) {
		if (key.equals("validAdminKey")) {
			return true;
		} else {
			return false;
		}
	}

	private boolean checkIfUser(String key) {
		if (key.equals("validUserKey")) {
			return true;
		} else {
			return false;
		}
	}

	private boolean checkProjectId(String id) {
		if (id.equals("validProjectId")) {
			return true;
		} else {
			return false;
		}
	}

	private boolean checkActivityId(String id) {
		if (id.equals("validActivityId")) {
			return true;
		} else {
			return false;
		}
	}

	private boolean createUser(String username, String password) {
		if (username.equals("valid")) {
			return true;
		} else {
			return false;
		}
	}
}
