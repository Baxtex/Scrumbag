package securityDB;

/**
 * Represents a user with getters and setters.
 *
 */

public class User {
	private final String username, password;

	private int authority;

	public User(String username, String password, int authority) {
		this.username = username;
		this.password = password;
		this.authority = authority;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public int getAuthority() {
		return authority;
	}
}