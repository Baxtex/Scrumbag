package securityDB;

/**
 * Represents a session with getters and setters.
 *
 */

public class Session {

	private final String key;
	private final int authority;
	private long timestamp;

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
