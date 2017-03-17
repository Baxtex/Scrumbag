package dataLayer;

public class User {
	
	private String elementId;
	private String name;
	
	public User(String userId, String name){
		this.elementId = userId;
		this.name = name;
	}
	
	
	public String getUserId() {
		return elementId;
	}

	public void setUserId(String userId) {
		this.elementId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
