package securityLayer;

import java.util.Random;

import org.codehaus.jettison.json.JSONObject;

import spark.Response;

public class SHandler {

	/**
	 * Other
	 */
	private Random rdm;

	private final String USERNAME_User = "Anton2005";
	private final String PASSWORD_User = "coolkille1337";
	private int key_User;

	private final String USERNAME_Admin = "Coffe1337";
	private final String PASSWORD_Admin = "kisekatt123";
	private int key_Admin;

	public SHandler() {
		rdm = new Random();
	}

	public int login(String username, String password) {
		if (username.equals(USERNAME_User) && password.equals(PASSWORD_User)) {
			key_User = rdm.nextInt() + 1;
			return key_User;
		} else if (username.equals(USERNAME_Admin) && password.equals(PASSWORD_Admin)) {
			key_Admin = rdm.nextInt();
			return key_Admin;
		} else {
			return 0;
		}

	}

	public String logout(String key) {
		return key + " : Has been logged out!";
	}

	public int checkAuthority(int key) {
		if (key == key_Admin) {
			return 2;
		} else if (key == key_User) {
			return 1;
		} else {
			return 0;
		}
	}

	/**
	 * Checks if the key is valid.
	 * 
	 * @param key
	 * @return
	 */
	public boolean checkKey(String key) {
		if (key.equals("validKey")) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Creates a new key. TODO: Obviously needs code for checking authority for
	 * example.
	 * 
	 * @return
	 */
	public String createKey() {
		return "abc";
	}

	/**
	 * Checks if username and password exists in the database. If they do, they
	 * should get a key
	 * 
	 * @param username
	 * @param password
	 * @param res
	 * @return
	 */
	public JSONObject login(String username, String password, Response res) {

		JSONObject jObj = new JSONObject();
		try {
			if (username.equals("invalid") && password.equals("invalid")) {
				jObj.put("message", "user does not exist");
				res.status(404);
			} else {
				jObj.put("key", createKey());
				res.status(200);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		res.type("application/json");
		return jObj;
	}

	/**
	 * If the user's key was valid, the user will be logged out.
	 * 
	 * @param res
	 * @return
	 */
	public JSONObject logout(Response res) {
		JSONObject jObj = new JSONObject();
		try {
			jObj.put("message", "user logged out");
			res.status(200);
		} catch (Exception e) {
			e.printStackTrace();
		}
		res.type("application/json");
		return jObj;
	}

	/**
	 * Creates a new user in the database. TODO: Might use a hashmap, list or
	 * something to store these to "simulate" a real database.
	 * 
	 * @param username
	 * @param password
	 * @param res
	 * @return
	 */
	public JSONObject createUser(String username, String password, Response res) {
		JSONObject jObj = new JSONObject();
		try {
			if (createUser(username, password)) {
				jObj.put("user-id", "XXX");
				res.status(201);
			} else {
				jObj.put("message", "not allowed");
				res.status(403);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		res.type("application/json");
		return jObj;
	}

	private boolean createUser(String username, String password) {
		if (username.equals("valid")) {
			return true;
		} else {
			return false;
		}
	}

}
