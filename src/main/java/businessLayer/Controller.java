package controller;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import spark.Response;

/**
 * This class handles the buissness logic of the system.
 * 
 * @author station
 *
 */
public class Controller {

	private final String MSG = "message";

	/**
	 * GET methods
	 */

	public JSONObject getActivities(String pID, String key, Response res) {
		JSONObject jObj = new JSONObject();
		try {
			if (checkIfUser(key)) {
				if (checkProjectId(pID)) {
					jObj.put("activity-id", "abcdf");
					jObj.put("title", "this is a valid activity");
					jObj.put("status", "planned");
					res.status(200);
				} else {
					jObj.put(MSG, "activity does not exist");
					res.status(404);
				}
			} else {
				jObj.put(MSG, "key does not exist");
				res.status(401);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		res.type("application/json");
		return jObj;
	}

	public JSONObject getActivity(String aID, String key, Response res) {
		JSONObject jObj = new JSONObject();
		try {
			if (checkIfUser(key)) {
				if (checkActivityId(aID)) {
					jObj.put("activity-id", "xxx");
					jObj.put("project-id", "xxx");
					jObj.put("title", "xxx");
					jObj.put("description", "xxx");
					jObj.put("status", "xxx");
					jObj.put("priority", "xxx");
					jObj.put("expected-time", "xxx");
					jObj.put("additional-time", "xxx");
					jObj.put("sprint-id", "xxx");
					jObj.put("user-id", "xxx");
					res.status(200);
				} else {
					jObj.put(MSG, "activity does not exist");
					res.status(404);
				}
			} else {
				jObj.put(MSG, "key does not exist");
				res.status(401);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		res.type("application/json");
		return jObj;
	}

	/**
	 * POST methods
	 */

	public JSONObject login(String username, String password, Response res) {
		String key = "abc";
		JSONObject jObj = new JSONObject();
		try {
			if (username.equals("invalid") && password.equals("invalid")) {
				jObj.put(MSG, "user does not exist");
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
				jObj.put(MSG, "user logged out");
				res.status(200);
			} else {
				jObj.put(MSG, "user does not exist");
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
					jObj.put(MSG, "not allowed");
					res.status(403);
				}
			} else {
				jObj.put(MSG, "key does not exist");
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
					jObj.put(MSG, "not allowed");
					res.status(403);
				} else {
					jObj.put("project-id", "XXX");
					res.status(201);
				}
			} else {
				jObj.put(MSG, "key does not exist");
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
					jObj.put(MSG, "project does not exist");
					res.status(404);
				}
			} else {
				jObj.put(MSG, "key does not exist");
				res.status(401);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		res.type("application/json");
		return jObj;
	}

	/**
	 * PUT methods
	 */

	public JSONObject userManagement(String key, String pID, String action, String userIDs, Response res) {
		JSONObject jObj = new JSONObject();
		try {
			JSONObject jObjUserIDs = new JSONObject(userIDs);
			if (checkIfAdmin(key)) {
				if (checkProjectId(pID)) {
					if (action.equals("add users")) {
						if (addUsers(jObjUserIDs)) {
							jObj.put(MSG, "users added");
							res.status(200);
						} else {
							jObj.put(MSG, "invalid user names");
							res.status(404);
						}
					} else if (action.equals("remove users")) {
						if (removeUsers(jObjUserIDs)) {
							jObj.put(MSG, "users removed");
							res.status(200);
						} else {
							jObj.put(MSG, "invalid user names");
							res.status(404);
						}
					} else {
						jObj.put(MSG, "invalid action");
						res.status(403);
					}
				} else {
					jObj.put(MSG, "project-id does not exist");
					res.status(404);
				}
			} else {
				jObj.put(MSG, "key does not exist");
				res.status(401);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		res.type("application/json");
		return jObj;
	}

	public JSONObject editActivity(String key, String aID, String pID, String title, String descr, String status,
			String prio, String expecTime, String addTime, String sprintID, String uID, Response res) {
		JSONObject jObj = new JSONObject();
		try {
			if (checkIfUser(key)) {

				if (checkProjectId(pID)) {

					if (checkActivityId(aID)) {
						jObj.put(MSG, "activity changed");
						res.status(200);
					} else {
						jObj.put(MSG, "activity does not exist");
						res.status(404);
					}
				} else {
					jObj.put(MSG, "project does not exist");
					res.status(404);
				}
			} else {
				jObj.put(MSG, "key does not exist");
				res.status(401);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		res.type("application/json");
		return jObj;
	}

	/**
	 * DELETE methods
	 */

	public Object deleteActivity(String params, String params2, Response res) {
		// TODO TO BE IMPLEMENTED!
		return null;
	}

	/**
	 * Helper methods.
	 */

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

	private boolean addUsers(JSONObject jsonObjUsers) {
		// TODO: Maybe loop all user names and check them to the db. If they
		// don't exist, return false

		String invalid = "";
		try {
			invalid = jsonObjUsers.getString("name");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		if (invalid.contains("invalid")) {
			return false;
		} else {
			return true;
		}
	}

	private boolean removeUsers(JSONObject jsonObjUsers) {
		// TODO: Maybe loop all user names and check them to the db. If they
		// don't exist, return false
		String invalid = "";
		try {
			invalid = jsonObjUsers.getString("name");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		if (invalid.contains("invalid")) {
			return false;
		} else {
			return true;
		}
	}
}
