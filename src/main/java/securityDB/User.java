package securityDB;

/**
 * Representation of a row in "users table"
 */

public class User {
	private String username;
	private String password;
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