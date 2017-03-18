package securityDB;

/**
 * This class is a stub(!) representing a database handler communicating with a
 * stub(!) database. In a real implementation this handler would make SQL
 * queries instead of normal Java commands to a real database.
 * 
 */

public class SecurityDBHandler {

	private final SecurityDB db;

	public SecurityDBHandler() {
		this.db = new SecurityDB();
	}

	/**
	 * Returns true if key exists in "database".
	 */

	public boolean isValidKey(String key) {
		if (db.getSession(key) != null) {
			return true;
		}
		return false;
	}

	/**
	 * Returns true if user exists in "database".
	 */

	public boolean isValidUser(String username, String password) {
		if (db.getUser(username) != null) {
			return true;
		}
		return false;
	}

	/**
	 * Returns the authority of a certain key, -1 if key is invalid.
	 */

	public int getAuthority(String key) {
		return db.getAuthority(key);
	}

	/**
	 * Returns the authority of a certain user, -1 if user is invalid.
	 */

	public int getAuthority(String username, String password) {
		return db.getAuthority(username, password);
	}

	/**
	 * Creates a new session.
	 */

	public void createSession(String key, long timestamp, int authority) {
		db.createSession(key, timestamp, authority);
	}

	/**
	 * Creates a new user.
	 */

	public boolean createUser(String username, String password, int authority) {
		return db.createUser(username, password, authority);
	}

	/**
	 * Removes a session.
	 */

	public void removeSession(String key) {
		db.removeSession(key);
	}

	/**
	 * Returns an array of all keys.
	 */

	public String[] getAllKeys() {
		return db.getAllKeys();
	}

	/**
	 * Returns the time stamp of a certain key.
	 */

	public long getTimestamp(String key) {
		return db.getTimestamp(key);
	}

	/**
	 * Sets the time stamp of a certain key.
	 */

	public void setTimestamp(String key, long timestamp) {
		db.setTimestamp(key, timestamp);
	}
}
