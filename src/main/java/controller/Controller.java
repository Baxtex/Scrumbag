package controller;

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
		System.out.println("Here we go...");
		System.out.println(username);
		System.out.println(password);
		String key = createKey(username, password);
		JSONObject jObj = new JSONObject();
		try {
			System.out.println("inside try..");
			if (key != null) {
				System.out.println("here is the key");
				jObj.put("key", key);
				res.status(200);
			} else {
				System.out.println("Does not exist.");
				jObj.put(MSG, "user does not exist.");
				res.status(404);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jObj;
	}

	@SuppressWarnings("unused")
	public JSONObject logout(String key, Response res) {
		JSONObject jObj = new JSONObject();
		try {
			if (true) {
				jObj.put(MSG, "user logged out");
				res.status(200);
			} else {
				jObj.put(MSG, "user does not exist.");
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
					jObj.put(MSG, "user created");
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
		return true;
	}

	private boolean createUser(String username, String password) {
		// TODO implement this.
		return true;
	}
	
	private String createKey(String username, String password) {
		// TODO implement this.
		if(username.equals("inval")) {
			System.out.println("invalid!");
			return null;
		}
		return "abc";
	}

}
