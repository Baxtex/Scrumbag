package securityLayer;

import java.util.Enumeration;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import securityDB.SecurityDBHandler;
import securityDB.Session;
import spark.Response;

/**
 * This class is keeping track of users offline and online. It is communicating with a
 * database through a database handler.
 * @author Kajsa Ornstein
 *
 */

public class Security {
	
	private final int STATUSCODE_OK = 200;
	private final int STATUSCODE_CREATED = 201;
	private final int STATUSCODE_INVALID = 401;		
	private final int STATUSCODE_UNAUTHORIZED = 403;	
	private final int STATUSCODE_DUPLICATE = 409;			
	
	private final int OPERATION_LOGIN = 0;
	private final int OPERATION_LOGOUT = 1;
	private final int OPERATION_VALIDATEKEY = 3;
	private final int OPERATION_AUTHORIZATION = 4;
	private final int OPERATION_CREATEUSER = 5;
	
	// To generate test keys, this main method will be removed.
	public static void main(String[] args) {
		Security s = new Security();
		String keyAdmin = s.generateKey("adminUsername", "adminPassword");
		String keyUser = s.generateKey("unauthorizedUsername", "unauthorizedPassword");
		System.out.println("Adminkey: " + keyAdmin);
		System.out.println("Userkey: " + keyUser);
	}
	
	private final int AUTHORITY_ADMIN = 1;
	private final SecurityDBHandler dbHandler;
	
	public Security() {
		this.dbHandler = new SecurityDBHandler();
	}
	
	// Public interface
	
	/**
	 * Returns a session key if login was successful, null if user is invalid.
	 * @param username
	 * @param password
	 * @return
	 */
	
	public String login(String username, String password) {
		if(isValidUser(username, password)) {
			return createSession(username, password);
			
		}
		return null;
	}
	
	/**
	 * Returns true if logout was successful, false if key is invalid.
	 * @param key
	 */
	
	public boolean logout(String key) {
		
		if(isValidKey(key)) {
			removeSession(key);
			return true;
		}
		return false;
	}
	
	/**
	 * Returns true if key exists. The key is supposed to be validated every time a user
	 * tries to make any sort of request. So, to keep track of active sessions and remove
	 * inactive sessions the timestamp will be refreshed every time the key is validated.
	 */
	
	public boolean isValidKey(String key) {
		if(dbHandler.isValidKey(key)) {
			refreshTimestamp(key);
			return true;
		}
		return false;
	}
	
	/**
	 * Returns true if user exists.
	 * @param username
	 * @param password
	 * @return
	 */
	
	public boolean isValidUser(String username, String password) {
		return dbHandler.isValidUser(username, password);
	}
	
	/**
	 * Returns true if the key has administrator authority.
	 * @param key
	 * @return
	 */
	
	public boolean hasAdminAuthority(String key) {
		int authority = dbHandler.getAuthority(key);
		if(authority == AUTHORITY_ADMIN) {
			return true;
		}
		return false;
	}
	
	/**
	 * Returns true if user was successfully created.
	 * @param username
	 * @param password
	 * @param authority
	 */
	
	public boolean createUser(String username, String password, String authority) {
		return dbHandler.createUser(username, password, Integer.parseInt(authority));
	}
	
	
	// Private methods
	
	/**
	 * Creates a new session.
	 * @param username
	 * @param password
	 */
	
	private String createSession(String username, String password) {
		String key = generateKey(username, password);
		long timestamp = System.currentTimeMillis();
		int authority = dbHandler.getAuthority(username, password);
		dbHandler.createSession(key, timestamp, authority);
		return key;
	}
	
	/**
	 * Removes a session.
	 * @param key
	 */
	
	private void removeSession(String key) {
		dbHandler.removeSession(key);
	}
	
	/**
	 * Iterates through all sessions and removes those inactive for more than 10 minutes.
	 */
	
	private void cleanSessions() {
		
		String[] keys = dbHandler.getAllKeys();
		
		for(int i = 0; i < keys.length; i++) {
			long currentTime = System.currentTimeMillis();
			long lastActiveTime = dbHandler.getTimestamp(keys[i]);
			long inactiveTime = currentTime - lastActiveTime;
			if(inactiveTime > 600000) {
				removeSession(keys[i]);
			}	
		}
	}
	
	/**
	 * Returns a naive encryption of username and password.
	 * @param username
	 * @param password
	 * @return
	 */
	
	private String generateKey(String username, String password) {		
		char[] letters = (username + password).toCharArray();
		for(int i = 0; i < letters.length; i++) {
			letters[i] += 1;
		}		
		return new String(letters);
	}
	
	/**
	 * Refreshes a session timestamp to current time.
	 */
	
	private void refreshTimestamp(String key) {
		long timestamp = System.currentTimeMillis();
		dbHandler.setTimestamp(key, timestamp);
	}
	
	/**
	 * Returns a JSON object with error information depending on operation.
	 * @param operation during which the error occurred
	 * @param data relevant data to the error
	 * @param res the response header
	 * @return
	 */
	
	public JSONObject createErrorMsg(int operation, Object data, Response res) {
		JSONObject json = new JSONObject();
		res.type("application/json");
		
		try {
			switch(operation) {
				case OPERATION_LOGIN: {
					json.put("Message", "Failed to log in.");
					json.put("Username", data);
					res.status(STATUSCODE_INVALID);
					break;
				}
				case OPERATION_LOGOUT: {
					json.put("Message", "Failed to log out.");
					json.put("Key", data);
					res.status(STATUSCODE_INVALID);
					break;
				}
				case OPERATION_VALIDATEKEY: {
					json.put("Message", "Key is invalid.");
					json.put("Key", data);
					res.status(STATUSCODE_INVALID);
					break;
				}
				case OPERATION_AUTHORIZATION: {
					json.put("Message", "User is unauthorized to do this.");
					json.put("Key", data);
					res.status(STATUSCODE_UNAUTHORIZED);
					break;
				}
				case OPERATION_CREATEUSER: {
					json.put("Message", "Could not create user. Username is taken.");
					json.put("Username", data);
					res.status(STATUSCODE_DUPLICATE);
					break;
				}
			}
		} catch (JSONException e) {
			System.out.println("Failed when adding stuff to JSON object.");
		}

		return json;
	}
	
	/**
	 * Returns a JSON object with success information depending on operation.
	 * @param operation during which the error occurred
	 * @param data relevant data to the error
	 * @param res the response header
	 * @return
	 */
	
	public JSONObject createSuccessMsg(int operation, Object data, Response res) {
		JSONObject json = new JSONObject();
		res.type("application/json");
		
		try {
			switch(operation) {
				case OPERATION_LOGIN: {
					json.put("Message", "Successfully logged in.");
					json.put("Key", data);
					res.status(STATUSCODE_CREATED);
					break;
				}
				case OPERATION_LOGOUT: {
					json.put("Message", "Succefully logged out.");
					json.put("Key", data);
					res.status(STATUSCODE_OK);
					break;
				}
				case OPERATION_CREATEUSER: {
					json.put("Message", "User was successfully created.");
					json.put("Username", data);
					res.status(STATUSCODE_CREATED);
					break;
				}
			}
		} catch (JSONException e) {
			System.out.println("Failed when adding stuff to JSON object.");
		}
		return json;
	}
}