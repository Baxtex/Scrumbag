package controller;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import spark.Response;

public class Controller {

	private final String MSG = "message";

	/**
	 * Used for checking the user with the security database.
	 * 
	 * @param username
	 * @param password
	 * @param res
	 * @return
	 */

	public JSONObject login(String username, String password, Response res) {
		res.type("application/json");
		String key = "abc";
		JSONObject jObj = new JSONObject();
		try {
			if (username.equals("invalid")) {
				jObj.put(MSG, "user does not exist");
				res.status(404);
			} else {
				jObj.put("key", key);
				res.status(200);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jObj;
	}

	public JSONObject logout(String key, Response res) {
		JSONObject jObj = new JSONObject();
		try {
			if (key.equals("abc")) {
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

			if (checkAuthority(key)) {
				if (createUser(username, password)) {
					jObj.put("user-id", "XXX"); // TODO Not sure what this
												// should return, what is
												// "user-id"?
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

	private boolean checkAuthority(String key) {
		// TODO implement this.
		if (key.equals("12345")) {
			return true;
		} else {
			return false;
		}
	}

	private boolean createUser(String username, String password) {
		// TODO implement this.

		if (username.equals("invalid")) {
			return false;
		} else {
			return true;
		}
	}

	public JSONObject createProject(String key, String projectName, Response res) {
		JSONObject jObj = new JSONObject();
		try {
			if (checkAuthority(key)) {
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

	public Object userManagement(String key, String projectID, String action, String userIDs, Response res) {
		JSONObject jObj = new JSONObject();
		try {
			JSONObject jObjUserIDs = new JSONObject(userIDs);

			if (checkAuthority(key)) {
				if (projectID.equals("invalid")) {
					jObj.put(MSG, "not allowed");
					res.status(403);
				} else {
					if (action.equals("add users")) {
						if(addUsers(jObjUserIDs)) {
							jObj.put(MSG, "users added");
							res.status(200);
						}else {
							jObj.put(MSG, "invalid user names");
							res.status(404);
						}
					} else if (action.equals("remove users")) {
						if(removeUsers(jObjUserIDs)) {
							jObj.put(MSG, "users removed");
							res.status(200);
						}else {
							jObj.put(MSG, "invalid user names");
							res.status(404);
						}
					} else {
						jObj.put(MSG, "invalid action");
						res.status(403);
					}
				}
			} else {
				jObj.put(MSG, "key does not exist");
				res.status(401);
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jObj;
	}

	private boolean addUsers(JSONObject jsonObjUsers) {
		System.out.println(jsonObjUsers.toString());
		//TODO: Maybe loop all user names and check them to the db. If they don't exist, return false
		boolean allNamesExist = true;
		
		return allNamesExist;
				
				
				
	}

	private boolean removeUsers(JSONObject jsonObjUsers) {
		System.out.println(jsonObjUsers.toString());
		//TODO: Maybe loop all user names and check them to the db. If they don't exist, return false
		boolean allNamesExist = true;
		
		return allNamesExist;

	}

}
