package securityDB;

import java.util.Hashtable;
import java.util.Set;

/**
 * This class is a stub(!) representing an actual database with two tables represented as hash tables.
 * 
 * 1. 	Users table (primary key is username)
 * 		| username | password | authority |
 * 
 * 2.	Sessions table (primary key is key)
 * 		| key | timestamp | authority |
 * 
 * 
 * @author Kajsa Ornstein
 *
 */

public class SecurityDB {
	
	// Constructor is only for testing purposes. May be removed later.
	
	public SecurityDB() {
		createUser("adminUsername", "adminPassword", 1);
		createUser("unauthorizedUsername", "unauthorizedPassword", 0);
	}
	
	private Hashtable<String, User> users = new Hashtable<String, User>();
	private Hashtable<String, Session> sessions = new Hashtable<String, Session>();
	
	/**
	 * Returns the session matching the key, null if there is none.
	 */
	
	protected Session getSession(String key) {
		return sessions.get(key);
	}
	
	/**
	 * Returns the user matching the username, null if there is none.
	 */
	
	protected User getUser(String username) {
		return users.get(username);
	}
	
	/**
	 * Returns the authority of the session matching the key, -1 if there is none.
	 */
	
	protected int getAuthority(String key) {
		Session session = sessions.get(key);
		if(session != null) {
			return session.getAuthority();
		}
		return -1;
	}
	
	/**
	 * Returns the authority of the user matching the user name, -1 if there is none.
	 */
	
	protected int getAuthority(String username, String password) {
		User user = users.get(username);
		if(user != null) {
			return user.getAuthority();
		}
		return -1;
	}
	
	/**
	 * Creates a new session and stores in "database".
	 */
	
	protected void createSession(String key, long timestamp, int authority) {
		sessions.put(key, new Session(key, timestamp, authority));		
	}
	
	/**
	 * Creates a new user and stores in "database", if the user name is not taken.
	 */
	
	protected boolean createUser(String username, String password, int authority) {		
		User user = users.get(username);
		if(user != null) {
			return false;
		} 
		users.put(username, new User(username, password, authority));		
		return true;
	}
	
	/**
	 * Removes a session from "database".
	 */
	
	protected void removeSession(String key) {
		sessions.remove(key);
	}
	
	/**
	 * Returns an array of all session keys.
	 */
	
	protected String[] getAllKeys() {
		Set<String> keys = sessions.keySet();
		return (String[]) keys.toArray();
	}
	
	/**
	 * Returns the time stamp of a session.
	 */
	
	protected long getTimestamp(String key) {
		Session session = sessions.get(key);
		if(session != null) {
			return session.getTimestamp();
		}
		return -1;
	}
	
	/**
	 * Updates the time stamp of a session.
	 */
	
	protected void setTimestamp(String key, long timestamp) {
		Session session = sessions.get(key);
		if(session != null) {
			session.setTimestamp(timestamp);
		}
	}
}
