package securityDB;

/**
 * Representation of a row in "sessions table",
 */

public class Session {
	
	private String key;
	private long timestamp;
	private int authority;
	
	public Session(String key, long timestamp, int authority) {
		this.key = key;
		this.timestamp = timestamp;
		this.authority = authority;
	}
	
	public String getKey() {
		return key;
	}
	
	public long getTimestamp() {
		return timestamp;
	}
	
	public int getAuthority() {
		return authority;
	}
	
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
}
